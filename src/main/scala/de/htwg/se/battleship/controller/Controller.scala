package de.htwg.se.battleship.controller

import akka.actor.{Actor, ActorRef, Props}
import de.htwg.se.battleship.model.Message._
import de.htwg.se.battleship.model._
import de.htwg.se.battleship.view.Swing.Player1Container

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

  var state = Update(Init, player1, player2)

  /**
    * handle incoming akka messages
    */
  override def receive: Receive = {
    case StartGame => gameStart()
    case RegisterObserver =>
      observers += sender(); sender() ! state
    case UnregisterObserver => observers -= sender()

    case PlaceShip(player: Player, startPoint: Point, shipSize: Int, orientation: Orientation) =>
      if (state.state.equals(PlaceShipTurn)
        && player.COLOR.equals(state.activePlayer.COLOR)) {
        placeShip(player, startPoint, shipSize, orientation)
      } else {
        sender() ! PrintMessage("Player is not allowed to place ships at the moment")
        sender() ! state
      }
    case HitShip(playerToHit: Player, pointToHit: Point) =>
      if (state.state.equals(ShootTurn)
        && playerToHit.COLOR.equals(state.otherPlayer.COLOR)) {
        hitShip(playerToHit, pointToHit)
      } else {
        sender() ! PrintMessage("Player is not allowed to shoot at the moment")
        sender() ! state
      }
    case PlaceShipViaColor(playerColor: String, startPoint: Point, shipSize: Int, orientation: Orientation) =>
      val player = if (player1.COLOR.equals(playerColor)) player1 else player2
      if (state.state.equals(PlaceShipTurn)
        && player.COLOR.equals(state.activePlayer.COLOR)) {
        placeShip(player, startPoint, shipSize, orientation)
      } else {
        sender() ! PrintMessage("Player is not allowed to place ships at the moment")
        sender() ! state
      }
    case HitShipViaColor(playerColorToHit: String, pointToHit: Point) =>
      val playerToHit = if (player1.COLOR.equals(playerColorToHit)) player1 else player2
      if (state.state.equals(ShootTurn)
        && playerToHit.COLOR.equals(state.otherPlayer.COLOR)) {
        hitShip(playerToHit, pointToHit)
      } else {
        sender() ! PrintMessage("Player is not allowed to shoot at the moment")
        sender() ! state
      }
  }

  /**
    * triggers the game start
    */
  def gameStart(): Unit = {
    Player1Container.playerOne = player1
    placeShipTurn(player1, player2)
  }

  /**
    * place a ship
    *
    * @param player      for the given player
    * @param startPoint  at the given point (further points will be calculated)
    * @param shipSize    with the given size
    * @param orientation and orientation
    */
  def placeShip(player: Player, startPoint: Point, shipSize: Int, orientation: Orientation): Unit = {
    if (player.placeShip(startPoint, shipSize, orientation)) {
      observers.foreach(_ ! PrintMessage("Ship placed"))
      placeShipTurn(state.otherPlayer, state.activePlayer)
    } else {
      observers.foreach(_ ! PrintMessage("placing ship failed"))
      observers.foreach(_ ! state)
    }
  }

  /**
    * asks the UIs for a ship placement action
    *
    * @param player     the place the ship
    * @param nextPlayer enemy
    */
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

  /**
    * asks the UIs for a shoot ship action
    *
    * @param player     to shoot
    * @param nextPlayer to get shot at
    */
  def shootShipTurn(player: Player, nextPlayer: Player): Unit = {
    if (player.field.fieldGrid.nonEmpty) {
      state = Update(ShootTurn, player, nextPlayer)
      observers.foreach(_ ! state)
    } else {
      state = Update(AnnounceWinner, nextPlayer, player) //view.announceWinner(winner.COLOR)
      observers.foreach(_ ! state)
    }
  }

  /**
    * shoot action, try to hit a ship. Returns the result directly to UIs.
    *
    * @param playerToHit player on which field will be shot
    * @param pointToHit  coordinates to shoot at
    */
  def hitShip(playerToHit: Player, pointToHit: Point): Unit = {
    observers.foreach(_ ! PrintMessage(playerToHit.field.hitField(pointToHit)))
    shootShipTurn(state.otherPlayer, state.activePlayer)
  }
}
