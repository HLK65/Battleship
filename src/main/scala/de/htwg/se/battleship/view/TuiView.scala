package de.htwg.se.battleship.view

import de.htwg.se.battleship.model._

import scala.util.Random

class TuiView {
  def printField(field: Field): Unit = {
    for (y <- 0 to field.size) {
      println()
      for (x <- 0 to field.size) {
        if (x == 0 && y == 0) print("   ") //0 0
        else if (y == 0 && x != 0) {
          if (x.toString.length == 2) print(" " + x)
          else print(" " + x + " ")
        }
        else if (x == 0 && y != 0) {
          if (y.toString.length == 2) print(" " + y)
          else print(" " + y + " ")
        }
        else if (Random.nextBoolean()) {
          print(" x ")
        }
        else {
          print(" - ")
        }
      }
    }
  }
}
