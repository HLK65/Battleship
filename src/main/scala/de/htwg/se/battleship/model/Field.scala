package de.htwg.se.battleship.model

case class Field(size: Int, player: Player) {

  val fruit: List[Point] = List()
  def hitField(point: Point): Boolean = {
    //check if there is an enemy ship on this field
    //if true hit ship remove one point return true
    //if false return false
    return true // TODO
  }
  def placeShip(point: Point, size: Int): Unit = {
    val ship = Ship(size, player.COLOR)
    point.placeShip(ship)

  }

}
