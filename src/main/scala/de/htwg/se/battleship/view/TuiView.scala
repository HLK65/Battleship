package de.htwg.se.battleship.view

import de.htwg.se.battleship.controller.Controller
import de.htwg.se.battleship.model._

class TuiView extends View {

  def playerSwitch(player: Player): Unit = {
    if (player.COLOR.toString.equals("Red")) {
      print(Console.RED)
    } else {
      print(Console.BLUE)
    }
    println(player.COLOR + "s turn")
  }

  def printField(field: Field, color: String): Unit = {
    println("Field of player " + color)
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
        } else if (field.hasShip(Point(x, y))) {
          print(" x ")
        } else {
          print(" - ")
        }
      }
    }
    println()
  }
}
