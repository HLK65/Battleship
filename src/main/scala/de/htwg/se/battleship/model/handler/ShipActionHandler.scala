/*
package de.htwg.se.battleship.model.handler

import de.htwg.se.battleship.controller.Controller
import de.htwg.se.battleship.model.{ Orientation, Player, Point, Ship }

import scalafx.event.ActionEvent

case class ShipActionHandler(controller: Controller) {

  def getShipPlaceAction(x: Int, y: Int, ship: Ship, orientation: Orientation, player: Player): Boolean = {

    if (ship.SIZE < 0) {
      return false
    }

    //TODO remove the ship from inventory
    controller.placeShip(player, new Point(x, y), ship.SIZE, orientation)
  }
  def getShipHitAction(player: Player, x: Int, y: Int): String = {

    controller.hitShip(player, new Point(x, y))
  }
}

*/
