package de.htwg.se.battleship.view

import akka.actor.Actor
import de.htwg.se.battleship.model.{Field, Orientation, Player, Point}

trait View extends Actor {

  def startGame: Unit
  def announceWinner(color: String): Unit
  def playerSwitch(player: Player): Unit
  def printField(field: Field, color: String): Unit
  def shootTurn(): Point
  def printMessage(message: String)
  def selectShip(player: Player): Int
  def readPoint(): Point

  def readOrientation(): Orientation

}
