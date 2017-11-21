package de.htwg.se.battleship.view

import de.htwg.se.battleship.model.{ Field, Player }

trait View {

  def playerSwitch(player: Player): Unit
  def printField(field: Field, color: String): Unit

}
