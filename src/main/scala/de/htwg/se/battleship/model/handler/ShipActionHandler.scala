package de.htwg.se.battleship.model.handler

import de.htwg.se.battleship.controller.Controller
import de.htwg.se.battleship.model.{ Orientation, Player, Point, Ship }

import scalafx.event.ActionEvent

case class ShipActionHandler(controller: Controller) {

  def getShipPlaceAction(x: Int, y: Int, ship: Ship, orientation: Orientation, player: Player): Unit = {

    controller.placeShip(player, new Point(x, y), ship.SIZE, orientation)
  }
  def getShipHitAction(player: Player, x: Int, y: Int): Unit = {

    controller.hitShip(player, new Point(x, y))
  }
}
