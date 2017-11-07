package de.htwg.se.battleship

import de.htwg.se.battleship.model._
import de.htwg.se.battleship.view._

object Battleship {
  def main(args: Array[String]): Unit = {
    val fieldSize = 15
    val player1Color = "Red"
    val player2Color = "Blue"


    val player1 = Player(player1Color, Field(fieldSize))
    val player2 = Player(player2Color, Field(fieldSize))


    val tuiView = new TuiView()

    val points = Array(Point(1, 1), Point(2, 1))

    player1.field.placeShip(Point(1, 2), 5, "horizontal")


    println(player1.field.grid.contains(Point(5, 5)))

    tuiView.printField(player1)
    println()
    tuiView.printField(player2)
  }
}
