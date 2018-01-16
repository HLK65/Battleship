package de.htwg.se.battleship.model

import org.scalatest.{ BeforeAndAfter, FlatSpec, Matchers }

class FieldSpec extends FlatSpec with Matchers with BeforeAndAfter {
  var field: Field = _

  before {
    field = Field(20)
  }

  "A Field" should "have a Size" in {
    field.size should be(20)
  }

  "A Field" should "have a emptry FieldGrid on initialization" in {
    field.fieldGrid.isEmpty should be(true)
  }

  "A Field" should "have a setter on fieldGrid" in {
    field.fieldGrid += Point(1, 1) -> Ship(2)
    field.fieldGrid.size should be(1)
  }

  "A Field" should "have a contains on fieldGrid and be true" in {
    field.fieldGrid += Point(1, 1) -> Ship(2)
    field.fieldGrid.contains(Point(1, 1)) should be(true)
  }

  "A Field" should "have a contains on fieldGrid and be false" in {
    field.fieldGrid += Point(1, 1) -> Ship(2)
    field.fieldGrid.contains(Point(0, 0)) should be(false)
  }

  "A Field" should "have a placeShip method" in {
    val shipSize = 2
    field.placeShip(Point(1, 10), shipSize, HORIZONTAL)
    field.fieldGrid.size should be(shipSize)
  }

  "A Field" should "have a hitShip method and hit water" in {
    field.hitField(Point(1, 1)) should be("hit water")
  }

  "A Field" should "have a hitShip method and hit ship" in {
    field.placeShip(Point(5, 5), 2, HORIZONTAL)
    field.hitField(Point(6, 5)) should be("hit ship")
  }
}