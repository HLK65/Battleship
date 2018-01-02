package de.htwg.se.battleship.view

import akka.actor.ActorRef
import de.htwg.se.battleship.model.Akka._
import de.htwg.se.battleship.model._

class TuiView(val controller: ActorRef) extends View {

  controller ! RegisterObserver

  override def receive: Receive = {
    case StartGame => startGame
    case AnnounceWinner(winnerColor: String) => announceWinner(winnerColor)
    case PrintField(field: Field, color: String) => printField(field, color)

  } //todo

  override def startGame: Unit = {
    println("Game starts")
  }

  override def announceWinner(color: String): Unit = {
    println(Console.BLACK + color + " won")
  }

  def shootTurn(): Point = {

    println("Select Point you want to shoot. x then y")
    val xInput = scala.io.StdIn.readInt()
    val yInput = scala.io.StdIn.readInt()
    val point = Point(xInput, yInput)
    point
  }

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

  override def printMessage(message: String): Unit = {
    print(message)
  }

  override def selectShip(player: Player): Int = {
    //show player what ships he still has to place
    println("Ships you can place: " + player.shipInventory.toString() + " [Size -> Amount]")
    //read what kind of ship the player wanted to place
    println("Select size of the ship you want to place")
    scala.io.StdIn.readInt()
  }

  override def readPoint(): Point = {
    println("Select Point. x then y")
    val pointInputX = scala.io.StdIn.readInt()
    val pointInputY = scala.io.StdIn.readInt()
    val point = Point(pointInputX, pointInputY)
    point
  }

  override def readOrientation(): Int = {
    println("Choose orientation. 1 horizontal, else vertical")
    scala.io.StdIn.readInt()
  }

}
