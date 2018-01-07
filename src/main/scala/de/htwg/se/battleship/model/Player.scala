package de.htwg.se.battleship.model

case class Player(COLOR: String, field: Field, shipInventory: scala.collection.mutable.Map[Int, Int]) {
  def placeShip(startPoint: Point, shipSize: Int, orientation: Orientation): Boolean = {
    //check if player still got this ship in inventory
    if (shipInventory.contains(shipSize)) {
      val success = field.placeShip(startPoint, shipSize, orientation.toString) //check if placement possible
      if (success) {
        val amount = shipInventory.get(shipSize).get
        if (amount - 1 > 0) shipInventory.put(shipSize, amount - 1) //reduce amount in inv
        else shipInventory.remove(shipSize) //delete key if last ship of size got placed
      }
      success
    } else false
  }
}
