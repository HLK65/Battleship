package de.htwg.se.battleship.controller

import akka.actor.{Actor, ActorRef, Props}
import de.htwg.se.battleship.model.Akka._
import de.htwg.se.battleship.model._

object Controller {
  def props(fieldSize: Int): Props = Props(new Controller(fieldSize))
}

case class Controller(fieldSize: Int) extends Actor {

  private val observers = scala.collection.mutable.SortedSet.empty[ActorRef]

  val player1Color = "Red"
  val player2Color = "Blue"

  val field1 = Field(fieldSize)
  val field2 = Field(fieldSize)

  //1x5Felder, 2x4Felder, 3x3Felder, 4x2Felder
  val shipInventory: scala.collection.mutable.Map[ /*size*/ Int, /*amount*/ Int] = scala.collection.mutable.Map(/*5 -> 1, 4 -> 2, 3 -> 3, */ 2 -> 1)

  val player1 = Player(player1Color, field1, shipInventory.clone())
  val player2 = Player(player2Color, field2, shipInventory.clone())

  var state = Update(PlaceShipTurn, player1, player2)

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

  def gameStart(): Unit = {
    placeShipTurn(player1, player2)
  }

  def placeShipTurn(player: Player, nextPlayer: Player): Unit = {
    //check if the player still has ships to place
    if (player.shipInventory.nonEmpty) {
      state = Update(PlaceShipTurn, player, nextPlayer)
      observers.foreach(_ ! state)
    } else {
      observers.foreach(_ ! PrintMessage("all ships placed")) //view.printMessage("all ships placed")
      shootShipTurn(player, nextPlayer)
    }
  }

  def placeShip(player: Player, startPoint: Point, shipSize: Int, orientation: Orientation): Unit = {
    if (player.field.placeShip(startPoint, shipSize, orientation.toString)) {
      observers.foreach(_ ! "Ship placed")
      placeShipTurn(state.otherPlayer, state.activePlayer)
    } else {
      observers.foreach(_ ! "placing ship failed")
      observers.foreach(_ ! state)
    }
  }

  def shootShipTurn(player: Player, nextPlayer: Player): Unit = {
    if (nextPlayer.field.fieldGrid.nonEmpty) {
      state = Update(ShootTurn, player, nextPlayer)
      observers.foreach(_ ! state)
    } else {
      state = Update(AnnounceWinner, player, null) //view.announceWinner(winner.COLOR)
      observers.foreach(_ ! state)
    }
  }

  def hitShip(playerToHit: Player, pointToHit: Point): Unit = {
    observers.foreach(_ ! PrintMessage(playerToHit.field.hitField(pointToHit)))
    shootShipTurn(state.otherPlayer, state.activePlayer)
  }
}


