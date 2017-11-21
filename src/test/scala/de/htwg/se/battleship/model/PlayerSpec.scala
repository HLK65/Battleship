package de.htwg.se.battleship.model

import org.scalatest.{FlatSpec, Matchers}

class PlayerSpec extends FlatSpec with Matchers {
  //TODO check for "java before"
  val shipInventory: scala.collection.mutable.Map[ /*size*/ Int, /*amount*/ Int] =
    scala.collection.mutable.Map(/*5 -> 1, 4 -> 2, 3 -> 3, */ 2 -> 1)
  val playerColor = "Red"
  val field = Field(2)
  val player = Player(playerColor, Field(2), shipInventory)

  "A Player" should "have a Color" in {
    player.COLOR should be(playerColor)
  }

  "A Player" should "have a Field" in {
    player.field should be(field)
  }

  "A Player" should "hava a shipInventory" in {
    player.shipInventory should be(shipInventory)
  }
}