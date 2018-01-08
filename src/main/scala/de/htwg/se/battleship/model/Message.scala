package de.htwg.se.battleship.model

object Message {

  case class StartGame() //tells controller to start the game

  case class RegisterObserver() //registers observer and "responds" current state
  case class UnregisterObserver() //unregisters observer

  //current phases of the game; what is the controller waiting for
  sealed trait Phase

  case class PrintMessage(message: String) //ask to print/react to a message (e.g. "invalid placement")

  case class Update(state: Phase, activePlayer: Player, otherPlayer: Player) //update observers after action or registration

  case object Init extends Phase
  case object PlaceShipTurn extends Phase //ask to place ship (coordinates, size and orientation)
  case object ShootTurn extends Phase //ask to shoot at coordinates
  case object AnnounceWinner extends Phase

  //announce winner

  //actions of player
  case class PlaceShip(player: Player, startPoint: Point, shipSize: Int, orientation: Orientation) //place ship
  case class HitShip(playerToHit: Player, pointToHit: Point) //shoot at coordinates

}
