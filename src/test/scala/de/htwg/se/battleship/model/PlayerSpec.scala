package de.htwg.se.battleship.model

import org.scalatest.{ FlatSpec, Matchers }

class PlayerSpec extends FlatSpec with Matchers {
  val shipInventory: scala.collection.mutable.Map[Int, Int] =
    scala.collection.mutable.Map( /*5 -> 1, 4 -> 2, 3 -> 3, */ 2 -> 1)
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

  "A Player" should "allow placing a ship and remove it from inventory" in {
    player.placeShip(Point(1, 1), 2, Orientations.HORIZONTAL) should be(true)
    player.shipInventory.size should be(0)
  }

  "A Player" should "not allow placing a ship at invalid coordinates" in {
    player.placeShip(Point(3, 3), 2, Orientations.HORIZONTAL) should be(false)
  }

  "A Player" should "not allow placing a ship not in inventory" in {
    player.placeShip(Point(1, 1), 3, Orientations.HORIZONTAL) should be(false)
  }
}