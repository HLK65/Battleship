package de.htwg.se.battleship.view

import de.htwg.se.battleship.controller.Controller
import de.htwg.se.battleship.model._

class TuiView {


  //TODO make size changeable
  val controller = Controller(15)

  def gameStart()={
    println("Game starts")
    placeShipTurn(controller.player1, controller.player2)
  }

  def placeShipTurn(player: Player, nextPlayer: Player): Boolean={
    //TODO check if the player still has ships to place
    printField(player.field, player.COLOR)
    println("Select Ship type to place")
    //TODO show player what ships he still has to place
    //TODO read what kind of ship the player wanted to place
    println("Select Point and Orientation")
    //TODO read Point
    val point = Point(10, 10)
    if(controller.placeShip(player, point, 5, Orientation.HORIZONTAL)){
      println("Ship placed")
      println(nextPlayer.COLOR +"s turn")
      placeShipTurn(nextPlayer, player)
    }else{
         println("Can´t place ship there, try again")
         placeShipTurn(player, nextPlayer)
        }

  }

  def printField(field: Field, color:String): Unit = {
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
