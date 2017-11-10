package de.htwg.se.battleship.controller

import de.htwg.se.battleship.model.{Field, Orientation, Player, Point}

case class Controller(fieldSize: Int) {

  val player1Color = "Red"
  val player2Color = "Blue"


  val field1 = Field(fieldSize)
  val field2 = Field(fieldSize)

  val player1 = Player(player1Color, field1)
  val player2 = Player(player2Color, field2)


  def placeShip(player:Player, startPoint: Point, shipSize: Int, orientation: Orientation): Boolean={
    return player.field.placeShip(startPoint, shipSize, orientation.toString)
  }
  def hitShip(playerToHit:Player, pointToHit: Point): String={
    return playerToHit.field.hitField(pointToHit)
  }

}
