package de.htwg.se.battleship.model

case class Player(COLOR: String, field: Field, shipConfig: scala.collection.mutable.Map[Int, Int]) {
}
