package de.htwg.se.battleship.controller

import akka.actor.{Actor, ActorRef, Props}
import de.htwg.se.battleship.model.Message._
import de.htwg.se.battleship.model._

object Controller {
  def props(fieldSize: Int, shipInventory: scala.collection.mutable.Map[Int, Int]): Props =
    Props(new Controller(fieldSize, shipInventory))
}

case class Controller(fieldSize: Int, shipInventory: scala.collection.mutable.Map[Int, Int]) extends Actor {

  val player1Color = "Red"
  val player2Color = "Blue"
  val field1 = Field(fieldSize)
  val field2 = Field(fieldSize)
  val player1 = Player(player1Color, field1, shipInventory.clone())
  val player2 = Player(player2Color, field2, shipInventory.clone())
  var state = Update(Init, player1, player2)
  private val observers = scala.collection.mutable.SortedSet.empty[ActorRef]

  /**
   * handle incoming akka messages
   */
  override def receive: Receive = {
    case StartGame => gameStart()
    case RegisterObserver =>
      observers += sender(); sender() ! state
    case UnregisterObserver => observers -= sender()

    case PlaceShip(myPlayer: Player, startPoint: Point, shipSize: Int, orientation: Orientation) =>
      handlePlaceShipAction(myPlayer, startPoint, shipSize, orientation, sender())
    case HitShip(playerToHit: Player, pointToHit: Point) =>
      handleHitShipAction(playerToHit, pointToHit, sender())
    case PlaceShipViaColor(playerColor: String, startPoint: Point, shipSize: Int, orientation: Orientation) =>
      val player = if (player1.COLOR.equals(playerColor)) player1 else player2
      handlePlaceShipAction(player, startPoint, shipSize, orientation, sender())
    case HitShipViaColor(playerColorToHit: String, pointToHit: Point) =>
      val playerToHit = if (player1.COLOR.equals(playerColorToHit)) player1 else player2
      handleHitShipAction(playerToHit, pointToHit, sender())
  }

  /**
   * handles incoming place ship action
   *
   * @param myPlayer    trying to place the ship
   * @param startPoint  top-left point where to place the ship
   * @param shipSize    size of the ship
   * @param orientation orientation of the ship
   * @param sender      ActorRef to sender
   */
  def handlePlaceShipAction(myPlayer: Player, startPoint: Point, shipSize: Int, orientation: Orientation, sender: ActorRef): Unit = {
    val player = if (player1.COLOR.equals(myPlayer.COLOR)) player1 else player2
    if (state.state.equals(PlaceShipTurn)
      && player.COLOR.equals(state.activePlayer.COLOR)) {
      placeShip(player, startPoint, shipSize, orientation)
    } else {
      sender ! PrintMessage("Player is not allowed to place ships at the moment")
      sender ! state
    }
  }

  /**
   * handles incoming hit ship action
   *
   * @param playerToHit player which field gets shot on
   * @param pointToHit  coordinates where to shoot
   * @param sender      ActorRef to sender
   */
  def handleHitShipAction(playerToHit: Player, pointToHit: Point, sender: ActorRef): Unit = {
    val player = if (player1.COLOR.equals(playerToHit.COLOR)) player1 else player2
    if (state.state.equals(ShootTurn)
      && playerToHit.COLOR.equals(state.otherPlayer.COLOR)) {
      hitShip(player, pointToHit)
    } else {
      sender ! PrintMessage("Player is not allowed to shoot at the moment")
      sender ! state
    }
  }

  /**
   * triggers the game start
   */
  def gameStart(): Unit = {
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
    val result = playerToHit.field.hitField(pointToHit)
    observers.foreach(_ ! PrintMessage(result))
    shootShipTurn(state.otherPlayer, state.activePlayer)
  }
}
