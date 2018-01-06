package de.htwg.se.battleship.model

object Akka {

  case class StartGame() //tells controller to start the game

  case class RegisterObserver() //registers observer and "responds" current state
  case class UnregisterObserver() //unregisters observer

  case class Update(state: _, activePlayer: Player, otherPlayer: Player) //update observers after action or registration
  case class PrintMessage(message: String) //ask to print/react to a message (e.g. "invalid placement")

  //current phases of the game; what is the controller waiting for
  case class PlaceShipTurn() //ask to place ship (coordinates, size and orientation)
  case class ShootTurn() //ask to shoot at coordinates
  case class AnnounceWinner()

  //announce winner

  //actions of player
  case class PlaceShip(player: Player, startPoint: Point, shipSize: Int, orientation: Orientation) //place ship
  case class HitShip(playerToHit: Player, pointToHit: Point) //shoot at coordinates

}
