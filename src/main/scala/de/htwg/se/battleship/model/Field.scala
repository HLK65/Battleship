package de.htwg.se.battleship.model
import scala.collection.mutable.ListBuffer

case class Field(size: Int, player: Player) {

  val grid = ListBuffer[Point]()
  def hitField(point: Point): Boolean = {
    var hitShip = false
    if(grid.contains(point)){
      hitShip = true
      grid -= point
    }
    return hitShip 
  }
  def placeShip(points: Array[Point], size: Int): Unit = {
    val ship = Ship(size, player.COLOR)
    for (x <- 0 until points.length) {
      val point: Point = Point(points(x).x, points(x).y, ship)
      grid += point
    }

  }

}
