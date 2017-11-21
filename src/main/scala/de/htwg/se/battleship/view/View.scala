package de.htwg.se.battleship.view

import de.htwg.se.battleship.model.Field

trait View {

  def printField(field: Field, color: String): Unit

}
