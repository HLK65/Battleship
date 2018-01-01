package de.htwg.se.battleship.model

object Akka {

  case class HelloWorld()

  case class RegisterObserver()

  case class UnregisterObserver()

  case class StartGame()

  case class PlaceShipTurn(player1: Player, player2: Player)

  case class AnnounceWinner(winnerColor: _) //todo color instead of _
  case class PlayerSwitch(player: Player)

  case class PrintMessage(message: String)

  case class ShootTurn()

  case class PrintField(field: _, playerColor: _)

}
