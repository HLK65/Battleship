package de.htwg.se.battleship.model

case class Point(x: Int, y: Int) {
  var ships = new Array[Ship](2)
  def placeShip(ship: Ship) = {
    if(ships(0) == null){
      ships(0) = ship
    }else{
      ships(1) = ship
    }
  }
  def hitPoint(): Boolean = {
    if(ships.isEmpty){
      return false
    }
    else{
      ships = new Array[Ship](2)
      return true
    }
  }
}
