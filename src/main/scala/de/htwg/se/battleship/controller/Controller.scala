package de.htwg.se.battleship.controller

import akka.actor.{Actor, ActorRef, Props}
import de.htwg.se.battleship.model.Akka._
import de.htwg.se.battleship.model._

object Controller {
  def props(fieldSize: Int): Props = Props(new Controller(fieldSize))
}

case class Controller(fieldSize: Int) extends Actor {

  private val observers = scala.collection.mutable.SortedSet.empty[ActorRef]
  var state = Update(PlaceShipTurn, player1, player2)

  val player1Color = "Red"
  val player2Color = "Blue"

  val field1 = Field(fieldSize)
  val field2 = Field(fieldSize)

  //1x5Felder, 2x4Felder, 3x3Felder, 4x2Felder
  val shipInventory: scala.collection.mutable.Map[ /*size*/ Int, /*amount*/ Int] = scala.collection.mutable.Map(/*5 -> 1, 4 -> 2, 3 -> 3, */ 2 -> 1)

  val player1 = Player(player1Color, field1, shipInventory.clone())
  val player2 = Player(player2Color, field2, shipInventory.clone())


  override def receive: Receive = {
    case StartGame => gameStart()
    case RegisterObserver => observers += sender(); sender() ! state
    case UnregisterObserver => observers -= sender()

    case PlaceShip(player: Player, startPoint: Point, shipSize: Int, orientation: Orientation) =>
      if (state.state.equals(PlaceShipTurn) /*getClass.getTypeName.equals(PlaceShipTurn.getClass.getTypeName)) // TODO find propper solution*/
        && player.COLOR.equals(state.activePlayer.COLOR)) {
        placeShip(player, startPoint, shipSize, orientation)
      } else {
        sender() ! PrintMessage("Player is not allowed to place ships at the moment")
        sender() ! state
      }
    case HitShip(playerToHit: Player, pointToHit: Point) =>
      if (state.state.equals(ShootTurn) /*getClass.getTypeName.equals(PlaceShipTurn.getClass.getTypeName)) // TODO find propper solution*/
        && playerToHit.COLOR.equals(state.otherPlayer.COLOR)) {
        hitShip(playerToHit, pointToHit)
      } else {
        sender() ! PrintMessage("Player is not allowed to shoot at the moment")
        sender() ! state
      }

  }


  def placeShip(player: Player, startPoint: Point, shipSize: Int, orientation: Orientation) = {
    if (player.field.placeShip(startPoint, shipSize, orientation.toString)) {
      //update, players switched todo
    } else {
      //msg + try again todo
    }
  }

  def hitShip(playerToHit: Player, pointToHit: Point) = {
    observers.foreach(_ ! PrintMessage(playerToHit.field.hitField(pointToHit)))
    //update todo
  }

  def gameStart() = {
    /*GuiView.controller = this
    GuiView.startGame*/
    //    observers.foreach(_ ! StartGame) //view.startGame

    placeShipTurn(player1, player2)

    shootShipTurn(player1, player2)
  }

  def placeShipTurn(player: Player, nextPlayer: Player): Unit = {
    //check if the player still has ships to place
    if (player.shipInventory.size > 0) {
      observers.foreach(_ ! PlayerSwitch(player)) //view.playerSwitch(player)

      observers.foreach(_ ! PrintField(player.field, player.COLOR)) //view.printField(player.field, player.COLOR)

      /*val inputSize = view.selectShip(player) todo lukas akka
      if (!player.shipInventory.contains(inputSize)) {
        observers.foreach(_ ! PrintMessage("Invalid inputSize, try again")) //view.printMessage("Invalid inputSize, try again")
        placeShipTurn(player, nextPlayer)
        return
      }*/

      //read Point
      /* val point = view.readPoint() todo lukas akka
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
       }*/
    } else {
      //todo
      observers.foreach(_ ! PrintMessage("all ships placed")) //view.printMessage("all ships placed")
    }
  }

  /*
   recursively called method to shoot other players ship until only one player got ships left
   winning player is returned
  */
  def shootShipTurn(player: Player, nextPlayer: Player): Unit = {
    observers.foreach(_ ! PlayerSwitch(player)) //view.playerSwitch(player)
    observers.foreach(_ ! ShootTurn)

    //    var point = view.shootTurn() todo lukas akka
    //    view.printMessage(nextPlayer.field.hitField(point)) todo lukas akka

    if (nextPlayer.field.fieldGrid.isEmpty) {
      state = Update(AnnounceWinner, player, null) //view.announceWinner(winner.COLOR)
      observers.foreach(_ ! state)
      return
    }

    shootShipTurn(nextPlayer, player)
  }

}
