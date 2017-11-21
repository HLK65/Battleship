package de.htwg.se.battleship.model

import org.scalatest._

class ShipSpec extends FlatSpec with Matchers {

  "A Ship" should "have a Size" in {
    var ship = Ship(5).SIZE should be(5)
  }
  "A Ship" should "have hitpoints" in {
    var ship = Ship(5).hitPoints should be(5)
  }
  "A Ship" should "lose hitPoints" in {
    var ship = Ship(5).hitShip() should be(4)
  }
}
