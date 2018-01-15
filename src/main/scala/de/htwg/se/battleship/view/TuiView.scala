package de.htwg.se.battleship.view

import akka.actor.ActorRef
import de.htwg.se.battleship.model.Message._
import de.htwg.se.battleship.model._

class TuiView(val controller: ActorRef) extends View {
  val readOnly = false

  controller ! RegisterObserver

  override def receive: Receive = {
    case Update(state: Phase, activePlayer: Player, otherPlayer: Player) => update(state, activePlayer, otherPlayer)
    case PrintMessage(message: String) => println(Console.BLACK + message)
  }

  def update(state: Phase, activePlayer: Player, otherPlayer: Player): Unit = {
    if (readOnly) {
      printField(activePlayer.field, activePlayer.COLOR)
    } else {
      playerSwitch(activePlayer)
      state match {
        case PlaceShipTurn =>
          printField(activePlayer.field, activePlayer.COLOR)
          placeShip(activePlayer)
        case ShootTurn => shootTurn(otherPlayer)
        case AnnounceWinner => announceWinner(activePlayer)
        case Init => println("Init")
      }
    }
  }

  def placeShip(player: Player): Unit = {
    val size = selectShip(player)
    val point = readPoint()
    val orientation = readOrientation()
    controller ! PlaceShip(player, point, size, orientation)
  }

  override def announceWinner(winner: Player): Unit = {
    println(Console.BLACK + winner.COLOR + " won")
  }

  override def shootTurn(enemy: Player): Unit = {
    println("Select Point you want to shoot. x then y")
    val xInput = readInt()
    val yInput = readInt()
    val point = Point(xInput, yInput)
    controller ! HitShip(enemy, point)
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
        printOperation(x, y, field)
      }
    }
    println()
  }

  def printOperation(x: Int, y: Int, field: Field): Unit = {
    if (x == 0 && y == 0) {
      print("   ")
    } else if (y == 0 && x != 0) {
      if (x.toString.length == 2) {
        print(" " + x)
      } else {
        print(" " + x + " ")
      }
    } else if (x == 0 && y != 0) {
      if (y.toString.length == 2) {
        print(" " + y)
      } else {
        print(" " + y + " ")
      }
    } else if (field.hasShip(Point(x, y))) {
      print(" x ")
    } else {
      print(" - ")
    }
  }

  override def readOrientation(): Orientations.o = {
    println("Choose orientation. 1 horizontal, else vertical")
    if (readInt() == 1) {
      Orientations.HORIZONTAL
    } else {
      Orientations.VERTICAL
    }
  }

  override def selectShip(player: Player): Int = {
    //show player what ships he still has to place
    println("Ships you can place: " + player.shipInventory.toString() + " [Size -> Amount]")
    //read what kind of ship the player wanted to place
    println("Select size of the ship you want to place")
    readInt()
  }

  override def readPoint(): Point = {
    println("Select top-left Point. x then y")
    val pointInputX = readInt()
    val pointInputY = readInt()
    val point = Point(pointInputX, pointInputY)
    point
  }

  override def printMessage(message: String): Unit = {
    println(message)
  }

  def readInt(): Int = {
    try scala.io.StdIn.readInt()
    catch {
      case _: Throwable =>
        println("try again... numbers only")
        readInt()
    }
  }

}
