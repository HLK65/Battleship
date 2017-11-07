package de.htwg.se.battleship.view

import de.htwg.se.battleship.model._

class TuiView {
  def printField(player: Player): Unit = {
    val field = player.field
    println("Field of player " + player.COLOR)
    for (y <- 0 to field.size) {
      println()
      for (x <- 0 to field.size) {
        if (x == 0 && y == 0) print("   ") //0 0
        else if (y == 0 && x != 0) {
          if (x.toString.length == 2) print(" " + x)
          else print(" " + x + " ")
        } else if (x == 0 && y != 0) {
          if (y.toString.length == 2) print(" " + y)
          else print(" " + y + " ")
        } else if (field.grid.contains(Point(x, y))) {
          print(" x ")
        } else {
          print(" - ")
        }
      }
    }
    println()
  }
}
