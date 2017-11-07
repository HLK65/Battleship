package de.htwg.se.battleship.model
import scala.collection.mutable.ListBuffer

case class Field(size: Int) {

  val grid = ListBuffer[Point]()

  /*
    shoot on point, if water return water, if hit return hit, if hit & sunk return sunk
   */
  def hitField(point: Point): String = {
    var hitShip = false
    if(grid.contains(point)){
      hitShip = true
      grid -= point
    }
    return hitShip.toString
  }

  /*
  calculate points used, check if all points free -> place ship and return true else return false
   */
  def placeShip(point: Point, size: Int, orientation: String): Boolean = {
    /*val ship = Ship(size, player.COLOR)
    for (x <- 0 until points.length) {
      grid += points(x)
    }*/
    return null //TODO
  }

}
