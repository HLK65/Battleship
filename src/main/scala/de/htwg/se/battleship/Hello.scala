package de.htwg.se.battleship

import de.htwg.se.battleship.model._
import de.htwg.se.battleship.view._

object Hello {
  def main(args: Array[String]): Unit = {
    val player1 = Player("Red")
    val player2 = Player("Blue")
    val fieldSize = 15

    println("Hello, " + player1.COLOR, player2.COLOR)

    val field1 = Field(fieldSize, player1)
    val field2 = Field(fieldSize, player2)

    new TuiView().printField(field1)
  }
}
