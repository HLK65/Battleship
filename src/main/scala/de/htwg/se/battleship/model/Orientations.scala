package de.htwg.se.battleship.model

object Orientations {
  sealed trait o
  case object HORIZONTAL extends o
  case object VERTICAL extends o

}
