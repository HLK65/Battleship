import de.htwg.se.battleship.model.{Point, Ship}

val shipConfig: scala.collection.mutable.Map[Int, Int] = scala.collection.mutable.Map(5 -> 1, 4 -> 2, 3 -> 3, 2 -> 4)
var a = shipConfig(4)

shipConfig(4).##.-(1)

val fieldGrid: scala.collection.mutable.Map[Point, Ship] = scala.collection.mutable.Map()



/* Todo
  * save and visualize where you have shot already
 */