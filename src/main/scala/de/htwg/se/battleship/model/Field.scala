package de.htwg.se.battleship.model
import scala.collection.mutable.ListBuffer

case class Field(size: Int) {

  val fieldGrid: scala.collection.mutable.Map[Point, Ship] = scala.collection.mutable.Map()
  /*
    shoot on point, if water return water, if hit return hit, if hit & sunk return sunk
   */
  def hitField(point: Point): String = {
    var hitShip = "hit water"
    if (fieldGrid.contains(point)) {
      val ship = fieldGrid.apply(point)
      fieldGrid -= point
      if (ship.hitPoints == 0) {
        ship.SIZE + " sunk"
      } else {
        "hit ship"
      }
    } else {
      hitShip
    }
  }

  def hasShip(point: Point): Boolean = {
    fieldGrid.contains(point)
  }
  /*
  calculate points used, check if all points free -> place ship and return true else return false
   */
  def placeShip(point: Point, size: Int, orientation: String): Boolean = {

    if (fieldGrid.contains(point)) {
      false
    } else {
      val ship = Ship(size)
      if (orientation.equals(Orientation.HORIZONTAL.toString)) {

        for (x <- 0 to ship.SIZE) {
          var pointToPlace = Point(point.x + x, point.y)
          fieldGrid += pointToPlace -> ship
        }
      } else {

        fieldGrid += point -> ship
      }
      true
    }
  }

}
