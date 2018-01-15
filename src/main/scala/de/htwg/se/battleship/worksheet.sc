import de.htwg.se.battleship.model.{Point, Ship}

val shipInventory: scala.collection.mutable.Map[Int, Int] = scala.collection.mutable.Map(5 -> 1, 4 -> 2, 3 -> 3, 2 -> 4)
val fieldGrid: scala.collection.mutable.Map[Point, Ship] = scala.collection.mutable.Map()

shipInventory(4).##.-(1)
var a = shipInventory(4)