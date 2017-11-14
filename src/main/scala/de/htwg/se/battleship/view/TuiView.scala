package de.htwg.se.battleship.view

import de.htwg.se.battleship.controller.Controller
import de.htwg.se.battleship.model._

class TuiView {

  //TODO make size changeable
  val controller = Controller(10)

  def gameStart() = {
    println("Game starts")
    placeShipTurn(controller.player1, controller.player2)
  }

  def placeShipTurn(player: Player, nextPlayer: Player): Unit = {
    //check if the player still has ships to place
    if (player.shipConfig.size > 0) {
      if (player.COLOR.toString.equals(controller.player1.COLOR.toString)) {
        print(Console.RED)
      } else {
        print(Console.BLUE)
      }
      println(player.COLOR + "s turn")

      printField(player.field, player.COLOR)

      //show player what ships he still has to place
      println("Ships you can place" + player.shipConfig.toString())
      //read what kind of ship the player wanted to place
      println("Select size of the ship you want to place")
      val inputSize = scala.io.StdIn.readInt()
      //check for valid inputSize
      if (!player.shipConfig.contains(inputSize)) {
        println("Invalid inputSize, try again")
        placeShipTurn(player, nextPlayer)
        return
      }

      //read Point
      println("Select Point. x then y")
      val pointInputX = scala.io.StdIn.readInt()
      val pointInputY = scala.io.StdIn.readInt()
      val point = Point(pointInputX, pointInputY)
      println("Choose orientation. 1 horizontal, else vertical")
      val inputOrientation = scala.io.StdIn.readInt()

      if (controller.placeShip(player, point, inputSize, if (inputOrientation == 1) Orientation.HORIZONTAL else Orientation.VERTICAL)) {
        //remove ship from inventory
        val shipsOfShipsizeLeft = player.shipConfig(inputSize).toInt.-(1)
        player.shipConfig(inputSize) = shipsOfShipsizeLeft
        if (shipsOfShipsizeLeft <= 0) player.shipConfig.remove(inputSize)
        println("Ship placed")
        placeShipTurn(nextPlayer, player)
      } else {
        println("Can´t place ship there, try again \n ###################################### \n")
        placeShipTurn(player, nextPlayer)
      }
    } else {
      //todo
      println("all ships placed")
    }
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
