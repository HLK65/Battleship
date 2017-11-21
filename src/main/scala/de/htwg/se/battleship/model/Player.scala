package de.htwg.se.battleship.model

case class Player(COLOR: String, field: Field, shipInventory: scala.collection.mutable.Map[Int, Int]) {
}
