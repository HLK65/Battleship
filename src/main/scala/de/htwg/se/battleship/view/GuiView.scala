package de.htwg.se.battleship.view

import akka.actor.{ Actor, ActorRef }
import de.htwg.se.battleship.model.Message._
import de.htwg.se.battleship.model.{ Orientation, Player, Point }
import de.htwg.se.battleship.view.Swing.BattleshipWindow

class GuiView(val controller: ActorRef) extends Actor {

  controller ! RegisterObserver
  val battleshipWindow = new BattleshipWindow(this)

  override def receive: Receive = {
    case Update(state: Phase, activePlayer: Player, otherPlayer: Player) => update(state, activePlayer, otherPlayer)
    case PrintMessage(message: String) => printMessage(message)
  }

  def update(state: Phase, activePlayer: Player, otherPlayer: Player): Unit = {
    state match {
      case PlaceShipTurn =>
        placeShip(activePlayer)
      case ShootTurn => shootTurn(otherPlayer)
      case AnnounceWinner => announceWinner(activePlayer)
      case Init => init()
    }
  }

  def init(): Unit = {
  }

  def printMessage(message: String): Unit = {
  }

  def placeShip(player: Player): Unit = {
    battleshipWindow.placeShip(player)
    battleshipWindow.visible = true
  }

  def shootTurn(player: Player): Unit = {
  }

  def announceWinner(winner: Player): Unit = {
  }

  def hitShipCall(player: Player, point: Point): Unit = {
    controller ! HitShip(player, point)
  }
  def placeShipCall(player: Player, point: Point, size: Int, orientation: Orientation): Unit = {
    controller ! PlaceShip(player, point, size, orientation)
  }
}
