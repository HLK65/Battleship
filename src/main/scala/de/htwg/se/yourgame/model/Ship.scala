package de.htwg.se.yourgame.model

/**
  * Creates the Ship. The size and color can´t be changed.
  * @param size the amount of fields the ship takes.
  * @param COLOR the color the ships has. THe player has the same color so that ships can be linked to the player
  */
case class Ship(val size: Int, val COLOR: Enumeration) {

  var hitPoints = size

  /**
    * Removes one point from the hitpoints
    * @return new hitpoints
    */
  def hitShip(): Int = {
    hitPoints = hitPoints-1
    return hitPoints
  }
}

