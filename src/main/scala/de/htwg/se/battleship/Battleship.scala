package de.htwg.se.battleship

import de.htwg.se.battleship.model._
import de.htwg.se.battleship.view._

object Battleship {
  def main(args: Array[String]): Unit = {
    val fieldSize = 15
    val player1Color = "Red"
    val player2Color = "Blue"


    val field1 = Field(fieldSize)
    val field2 = Field(fieldSize)

    val player1 = Player(player1Color, field1)
    val player2 = Player(player2Color, field2)


    val tuiView = new TuiView()

    val points = Array(Point(1, 1), Point(2, 1))

    player1.field.placeShip(Point(1, 2), 5, horizontal)
    field1.grid += (Point(5, 5))
    println(field1.grid.contains(Point(5, 5)))

    tuiView.printField(field1)
    println()
    tuiView.printField(field2)
  }
}
