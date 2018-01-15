package de.htwg.se.battleship.model

/**
 * Creates the Ship. The size and color can´t be changed.
 *
 * @param SIZE the amount of fields the ship takes.
 */
case class Ship(SIZE: Int) {

  var hitPoints: Int = SIZE

  /**
   * Removes one point from the hitPoints
   *
   * @return new hitPoints
   */
  def hitShip(): Int = {
    hitPoints -= 1
    hitPoints
  }
}
