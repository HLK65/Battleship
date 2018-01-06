package de.htwg.se.battleship.model

case class Field(size: Int) {

  val fieldGrid: scala.collection.mutable.Map[Point, Ship] = scala.collection.mutable.Map()

  /*
    shoot on point, if water return water, if hit return hit, if hit & sunk return sunk
   */
  def hitField(point: Point): String = {
    val hitShip = "hit water"
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
  //TODO use orientation enum instead of string
  def placeShip(point: Point, size: Int, orientation: String): Boolean = {
    val fieldGridCopy = fieldGrid.clone()
    var success = true
    val ship = Ship(size)
    if (orientation.equals(Orientation.HORIZONTAL.toString)) {
      for (x <- 0 until ship.SIZE) {
        val pointToPlace = Point(point.x + x, point.y)
        if (!addPointToGrid(pointToPlace, ship)) success = false
      }
    } else {
      for (y <- 0 until ship.SIZE) {
        val pointToPlace = Point(point.x, point.y + y)
        if (!addPointToGrid(pointToPlace, ship)) success = false
      }
    }

    if (!success) {
      //rollback
      fieldGrid.clear()
      fieldGrid.++(fieldGridCopy.clone())
    }
    success
  }

  /*
    tries to place point on grid, returns true if possible, false otherwise
   */
  def addPointToGrid(pointToPlace: Point, ship: Ship): Boolean = {
    if (fieldGrid.contains(pointToPlace) || pointToPlace.x < 1 || pointToPlace.x > size || pointToPlace.y < 1 || pointToPlace.y > size) {
      return false
    }
    fieldGrid += pointToPlace -> ship
    true
  }
}
