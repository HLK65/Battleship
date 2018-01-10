package de.htwg.se.battleship.view

import akka.actor.{ Actor, ActorRef }
import de.htwg.se.battleship.model.Message._
import de.htwg.se.battleship.model.{ Orientation, Player, Point }
import de.htwg.se.battleship.view.stages.GuiViewStage

import scalafx.scene.control.Button

class GuiView(val controller: ActorRef) extends Actor {

  controller ! RegisterObserver
  var gameStarted = false
  var firstPlayer: Player = null

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
    GuiViewStage.guiView = this
  }

  def printMessage(message: String): Unit = {
    GuiViewStage.printMessage(message)
  }

  def placeShip(player: Player): Unit = {
    if (!gameStarted) {
      GuiViewStage.guiView = this
      firstPlayer = player
      gameStarted = true
      GuiViewStage.firstTurn(player)
      GuiViewStage.startGame(player)
    } else {
      GuiViewStage.placeShip(player)
    }
  }

  def shootTurn(player: Player): Unit = {
    GuiViewStage.shootTurn(player)
  }

  def announceWinner(winner: Player): Unit = {
    GuiViewStage.announceWinner(winner)
  }

  def hitShipCall(player: Player, point: Point): Unit = {
    controller ! HitShip(player, point)
  }
  def placeShipCall(player: Player, point: Point, size: Int, orientation: Orientation): Unit = {
    controller ! PlaceShip(player, point, size, orientation)
  }
}
