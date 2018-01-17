package de.htwg.se.battleship.view

import akka.actor.{Actor, ActorRef}
import de.htwg.se.battleship.model.Message._
import de.htwg.se.battleship.model._

class TuiView(val controller: ActorRef) extends Actor {
  //vars for async, non blocking input
  var waitingForInput = "none"
  var placeShip_size: Int = -1
  var placeShip_x: Int = -1
  var placeShip_y: Int = -1
  var hitShip_x: Int = -1
  var activePlayer: Player = _
  var otherPlayer: Player = _

  controller ! RegisterObserver

  /**
    * akka receiver
    */
  override def receive: Receive = {
    case Update(state: Phase, activePlayer: Player, otherPlayer: Player) => update(state, activePlayer, otherPlayer)
    case PrintMessage(message: String) => printMessage(Console.BLACK + message)
    case ProcessTuiInput(input: Int) => processTuiInput(input)
  }

  /**
    * process async, non blocking input
    *
    * @param input integer, size, coordinate or orientation
    */
  def processTuiInput(input: Int): Unit = {
    waitingForInput match {
      case "placeShip_size" =>
        placeShip_size = input
        waitingForInput = "placeShip_x"
        printMessage("Select top-left Point. x then y")
      case "placeShip_x" =>
        placeShip_x = input
        waitingForInput = "placeShip_y"
      case "placeShip_y" =>
        placeShip_y = input
        waitingForInput = "placeShip_orientation"
        printMessage("Choose orientation. 1 horizontal, else vertical")
      case "placeShip_orientation" =>
        waitingForInput = "none"
        controller ! PlaceShip(activePlayer, Point(placeShip_x, placeShip_y), placeShip_size, convertOrientation(input))
      case "hitShip_x" =>
        hitShip_x = input
        waitingForInput = "hitShip_y"
      case "hitShip_y" =>
        waitingForInput = "none"
        controller ! HitShip(otherPlayer, Point(hitShip_x, input))
      case "none" => printMessage("Input ignored, no input expected")
    }
  }

  /**
    * converts integer input to orientation
    *
    * @param int 1 for horizontal, else vertical
    * @return Horizontal or Vertical
    */
  def convertOrientation(int: Int): Orientation = {
    if (int == 1) {
      HORIZONTAL
    } else {
      VERTICAL
    }
  }

  /**
    * prints string
    * useful to add file output or logger with a single line
    *
    * @param message string to handle
    */
  def printMessage(message: String): Unit = {
    println(message)
  }

  /**
    * processes updates of the controller
    *
    * @param state        of the game
    * @param activePlayer who's turn it is
    * @param otherPlayer  enemy player
    */
  def update(state: Phase, activePlayer: Player, otherPlayer: Player): Unit = {
    this.activePlayer = activePlayer
    this.otherPlayer = otherPlayer

    playerSwitch(activePlayer)
    state match {
      case PlaceShipTurn =>
        printField(activePlayer.field, activePlayer.COLOR)
        printMessage("Ships you can place: " + activePlayer.shipInventory.toString() + " [Size -> Amount]")
        printMessage("Select size of the ship you want to place")
        waitingForInput = "placeShip_size"
      case ShootTurn =>
        waitingForInput = "hitShip_x"
        printMessage("Select Point you want to shoot. x then y")
      case AnnounceWinner => announceWinner(activePlayer)
      case Init => printMessage("Init")

    }
  }

  /**
    * prints out winner
    *
    * @param winner player who won the game
    */
  def announceWinner(winner: Player): Unit = {
    printMessage(Console.BLACK + winner.COLOR + " won")
  }

  /**
    * announces players turn and changes console output color
    *
    * @param player to switch to
   */
  def playerSwitch(player: Player): Unit = {
    if (player.COLOR.toString.equals("Red")) {
      print(Console.RED)
    } else {
      print(Console.BLUE)
    }
    printMessage(player.COLOR + "s turn")
  }

  /**
    * prints the players field
    *
    * @param field of the player
    * @param color of the player
   */
  def printField(field: Field, color: String): Unit = {
    printMessage("Field of player " + color)
    for (y <- 0 to field.size) {
      printMessage("")
      for (x <- 0 to field.size) {
        printFieldCoordinate(x, y, field)
      }
    }
    printMessage("")
  }

  /**
    * prints a coordinate of the field
    *
    * @param x     of coordinate
    * @param y     of coordinate
    * @param field of the player
    */
  def printFieldCoordinate(x: Int, y: Int, field: Field): Unit = {
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
}
