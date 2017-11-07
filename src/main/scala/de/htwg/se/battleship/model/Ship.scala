package de.htwg.se.battleship.model

/**
  * Creates the Ship. The size and color can´t be changed.
  *
  * @param SIZE the amount of fields the ship takes.
  */
case class Ship(val SIZE: Int) {

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
