package de.htwg.se.battleship.model

/**
 * Creates the Ship. The size and color can´t be changed.
 *
 * @param SIZE  the amount of fields the ship takes.
 * @param COLOR the color the ships has. THe player has the same color so that ships can be linked to the player
 */
// TODO Enum for color
case class Ship(val SIZE: Int, val COLOR: String) {

  var hitPoints = SIZE // TODO var vermeiden

  /**
   * Removes one point from the hitPoints
   *
   * @return new hitPoints
   */
  def hitShip(): Int = {
    hitPoints = hitPoints - 1 //-= 1 schöner?
    hitPoints
  }
}
