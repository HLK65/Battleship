package de.htwg.se.battleship.model

import org.scalatest._

class ShipSpec extends FlatSpec with Matchers {

  "A Ship" should "have a Size" in {
    Ship(3).SIZE should be(3)
  }
  "A Ship" should "have hitpoints" in {
    Ship(3).hitPoints should be(3)
  }
  "A Ship" should "lose hitPoints" in {
    Ship(3).hitShip() should be(2)
  }
}
