package de.htwg.se.battleship.model

import org.scalatest._

class PointSpec extends FlatSpec with Matchers {

  "A Point" should "have a x value " in {
    Point(1, 2).x should be(1)
  }
  "A Point" should "have a y value" in {
    Point(1, 2).y should be(2)
  }
}
