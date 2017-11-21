package de.htwg.se.battleship.controller

import de.htwg.se.battleship.model.{ Field, Orientation, Player, Point }
import de.htwg.se.battleship.view.View

case class Controller(fieldSize: Int, view: View) {

  val player1Color = "Red"
  val player2Color = "Blue"

  val field1 = Field(fieldSize)
  val field2 = Field(fieldSize)

  //1x5Felder, 2x4Felder, 3x3Felder, 4x2Felder
  val shipConfig: scala.collection.mutable.Map[ /*size*/ Int, /*amount*/ Int] = scala.collection.mutable.Map( /*5 -> 1, 4 -> 2, 3 -> 3, */ 2 -> 1)

  val player1 = Player(player1Color, field1, shipConfig.clone())
  val player2 = Player(player2Color, field2, shipConfig.clone())

  def placeShip(player: Player, startPoint: Point, shipSize: Int, orientation: Orientation): Boolean = {
    player.field.placeShip(startPoint, shipSize, orientation.toString)
  }

  def hitShip(playerToHit: Player, pointToHit: Point): String = {
    playerToHit.field.hitField(pointToHit)
  }

  def gameStart() = {
    println("Game starts")
    placeShipTurn(player1, player2)
    val winner = shootShipTurn(player1, player2)
    println(Console.BLACK + winner.COLOR + " won")

  }

  /*
   recursively called method to shoot other players ship until only one player got ships left
   winning player is returned
  */
  def shootShipTurn(player: Player, nextPlayer: Player): Player = {
    view.playerSwitch(player)

    println("Select Point you want to shoot. x then y")
    val xInput = scala.io.StdIn.readInt()
    val yInput = scala.io.StdIn.readInt()
    val point = Point(xInput, yInput)
    println(nextPlayer.field.hitField(point))

    if (nextPlayer.field.fieldGrid.isEmpty) return player //return winning player

    shootShipTurn(nextPlayer, player)

  }

  def placeShipTurn(player: Player, nextPlayer: Player): Unit = {
    //check if the player still has ships to place
    if (player.shipConfig.size > 0) {
      view.playerSwitch(player)

      view.printField(player.field, player.COLOR)

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

      if (placeShip(player, point, inputSize, if (inputOrientation == 1) Orientation.HORIZONTAL else Orientation.VERTICAL)) {
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

}
