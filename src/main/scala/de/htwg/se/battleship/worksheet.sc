import de.htwg.se.battleship.model.{Point, Ship}

val shipInventory: scala.collection.mutable.Map[Int, Int] = scala.collection.mutable.Map(5 -> 1, 4 -> 2, 3 -> 3, 2 -> 4)
var a = shipInventory(4)

shipInventory(4).##.-(1)

val fieldGrid: scala.collection.mutable.Map[Point, Ship] = scala.collection.mutable.Map()



/* Todo
  * refactor, mvc pattern!
  * unit tests
  * GUI (swing or scalaFX)
  * WUI (play)
  * save and visualize where you have shot already
  * akka
 */
