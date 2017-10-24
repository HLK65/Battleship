package de.htwg.se.battleship.view

import de.htwg.se.battleship.model._

import scala.util.Random

class TuiView {
  def printField(field: Field): Unit = {
    for (y <- 1 to field.size) {
      println()
      for (x <- 1 to field.size) {
        if (Random.nextBoolean()) {
          print("x ")
        }
        else {
          print("- ")
        }
      }
    }
  }
}
