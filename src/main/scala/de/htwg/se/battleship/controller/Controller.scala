package de.htwg.se.battleship.controller

import akka.actor.{Actor, ActorRef, Props}
import de.htwg.se.battleship.model.Akka._
import de.htwg.se.battleship.model.{Field, Orientation, Player, Point}

object Controller {
  def props(fieldSize: Int): Props = Props(new Controller(fieldSize))
}

case class Controller(fieldSize: Int) extends Actor {

  private val observers = scala.collection.mutable.SortedSet.empty[ActorRef]

  override def receive: Receive = {
    case "helloWorld" => gameStart()
    case RegisterObserver => observers += sender(); sender() ! "currentState" /*todo*/ ; sender()
    case UnregisterObserver => observers -= sender()

  } //todo

  val player1Color = "Red"
  val player2Color = "Blue"

  val field1 = Field(fieldSize)
  val field2 = Field(fieldSize)

  //1x5Felder, 2x4Felder, 3x3Felder, 4x2Felder
  val shipInventory: scala.collection.mutable.Map[ /*size*/ Int, /*amount*/ Int] = scala.collection.mutable.Map(/*5 -> 1, 4 -> 2, 3 -> 3, */ 2 -> 1)

  val player1 = Player(player1Color, field1, shipInventory.clone())
  val player2 = Player(player2Color, field2, shipInventory.clone())

  def placeShip(player: Player, startPoint: Point, shipSize: Int, orientation: Orientation): Boolean = {
    player.field.placeShip(startPoint, shipSize, orientation.toString)
  }

  def hitShip(playerToHit: Player, pointToHit: Point): String = {
    playerToHit.field.hitField(pointToHit)
  }

  def gameStart() = {
    /*GuiView.controller = this
    GuiView.startGame*/
    observers.foreach(_ ! StartGame) //view.startGame
    placeShipTurn(player1, player2)
    val winner = shootShipTurn(player1, player2)
    observers.foreach(_ ! AnnounceWinner(winner.COLOR)) //view.announceWinner(winner.COLOR)
  }

  /*
   recursively called method to shoot other players ship until only one player got ships left
   winning player is returned
  */
  def shootShipTurn(player: Player, nextPlayer: Player): Player = {
    observers.foreach(_ ! PlayerSwitch(player)) //view.playerSwitch(player)

    var point = view.shootTurn()
    view.printMessage(nextPlayer.field.hitField(point))

    if (nextPlayer.field.fieldGrid.isEmpty) return player //return winning player

    shootShipTurn(nextPlayer, player)
  }

  def placeShipTurn(player: Player, nextPlayer: Player): Unit = {
    //check if the player still has ships to place
    if (player.shipInventory.size > 0) {
      observers.foreach(_ ! PlayerSwitch(player)) //view.playerSwitch(player)

      observers.foreach(_ ! PrintField(player.field, player.COLOR)) //view.printField(player.field, player.COLOR)

      val inputSize = view.selectShip(player)
      if (!player.shipInventory.contains(inputSize)) {
        observers.foreach(_ ! PrintMessage("Invalid inputSize, try again")) //view.printMessage("Invalid inputSize, try again")
        placeShipTurn(player, nextPlayer)
        return
      }

      //read Point
      val point = view.readPoint()
      val inputOrientation = view.readOrientation()

      if (placeShip(player, point, inputSize, if (inputOrientation == 1) Orientation.HORIZONTAL else Orientation.VERTICAL)) {
        //remove ship from inventory
        val shipsOfShipsizeLeft = player.shipInventory(inputSize).toInt.-(1)
        player.shipInventory(inputSize) = shipsOfShipsizeLeft
        if (shipsOfShipsizeLeft <= 0) player.shipInventory.remove(inputSize)
        observers.foreach(_ ! PrintMessage("Ship placed")) //view.printMessage("Ship placed")
        placeShipTurn(nextPlayer, player)
      } else {
        observers.foreach(_ ! PrintMessage("Can´t place ship there, try again \\n ###################################### \\n")) //view.printMessage("Can´t place ship there, try again \n ###################################### \n")
        placeShipTurn(player, nextPlayer)
      }
    } else {
      //todo
      observers.foreach(_ ! PrintMessage("all ships placed")) //view.printMessage("all ships placed")
    }
  }

}
