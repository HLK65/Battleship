package de.htwg.se.battleship.view

import akka.actor.Actor
import de.htwg.se.battleship.model.{ Field, Orientations, Player, Point }

trait View extends Actor {

  def announceWinner(winner: Player): Unit

  def playerSwitch(player: Player): Unit

  def printField(field: Field, color: String): Unit

  def shootTurn(enemy: Player): Unit

  def printMessage(message: String)

  def selectShip(player: Player): Int

  def readPoint(): Point

  def readOrientation(): Orientations.o

}
