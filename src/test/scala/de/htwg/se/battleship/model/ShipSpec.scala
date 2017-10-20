package de.htwg.se.battleship.model

import org.scalatest._


class ShipSpec extends FlatSpec with Matchers {
  "A Ship" should "have a color" in {
    Ship(2, "white").COLOR should be("white")
  }

  "A Ship" should "have a size" in {
    Ship(2, "white").SIZE should be(2)
  }

  "A Ship" should "have hitpoints" in {
    Ship(2, "white").hitPoints should be(2)
  }

  "A Ship" should "loose hitPoints" in {
    val ship = Ship(2, "white").hitShip() should be(1)
  }
}