package de.htwg.se.battleship.model

case class Field(size: Int, playerOne: Player, playerTwo: Player) {

  //  val grid = Set[Point] = Set()

  def hitField(point: Point): Boolean = {
      //check if there is an enemy ship on this field
      //if true hit ship remove one point return true
      //if false return false
    return true // TODO
  }
  def placeShip(playerColor: String, point: Point, size: Int) = {
    
  }

}
