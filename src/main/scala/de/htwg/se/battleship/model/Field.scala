package de.htwg.se.battleship.model

import scala.collection.mutable.ListBuffer

case class Field(size: Int) {

  val grid = ListBuffer[Point]()
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
    }
    hitShip
  }

  /*
  calculate points used, check if all points free -> place ship and return true else return false
   */
  def placeShip(point: Point, size: Int, orientation: String): Boolean = {

    if (fieldGrid.contains(point)) {
      false
    } else {
      val ship = Ship(size)
      fieldGrid += point -> ship
      true
    }
  }

}
