package de.htwg.se.battleship.controller

import de.htwg.se.battleship.model.{ Field, Orientation, Player, Point }

case class Controller(fieldSize: Int) {

  val player1Color = "Red"
  val player2Color = "Blue"

  val field1 = Field(fieldSize)
  val field2 = Field(fieldSize)

  //1x5Felder, 2x4Felder, 3x3Felder, 4x2Felder
  val shipConfig: scala.collection.mutable.Map[ /*size*/ Int, /*amount*/ Int] = scala.collection.mutable.Map( /*5 -> 1, 4 -> 2, 3 -> 3, */ 2 -> 1)

  val player1 = Player(player1Color, field1, shipConfig.clone())
  val player2 = Player(player2Color, field2, shipConfig.clone())

  def placeShip(player: Player, startPoint: Point, shipSize: Int, orientation: Orientation): Boolean = {
    player.field.placeShip(startPoint, shipSize, orientation.toString)
  }

  def hitShip(playerToHit: Player, pointToHit: Point): String = {
    playerToHit.field.hitField(pointToHit)
  }

}
