package de.htwg.se.battleship.model

object Message {

  sealed trait Phase //current phases of the game; what is the controller waiting for

  case class StartGame() //tells controller to start the game

  case class RegisterObserver() //registers observer and "responds" current state

  case class UnregisterObserver() //unregisters observer

  case class PrintMessage(message: String) //ask to print/react to a message (e.g. "invalid placement")

  case class Update(state: Phase, activePlayer: Player, otherPlayer: Player) //update observers after action or registration

  case class ProcessTuiInput(int: Int)

  //actions of player
  case class PlaceShip(player: Player, startPoint: Point, shipSize: Int, orientation: Orientation) //place ship

  case class HitShip(playerToHit: Player, pointToHit: Point) //shoot at coordinates

  case class PlaceShipViaColor(playerColor: String, startPoint: Point, shipSize: Int, orientation: Orientation) //place ship

  case class HitShipViaColor(playerColorToHit: String, pointToHit: Point) //shoot at coordinates

  case object Init extends Phase // game not started yet

  case object PlaceShipTurn extends Phase //ask to place ship (coordinates, size and orientation)

  case object ShootTurn extends Phase //ask to shoot at coordinates

  case object AnnounceWinner extends Phase //announce winner

}
