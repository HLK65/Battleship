package de.htwg.se.battleship.view.Swing

import java.awt.Dimension

import de.htwg.se.battleship.model.{Field, Player, Point}
import de.htwg.se.battleship.view.GuiView
import de.htwg.se.battleship.view.stages.GuiViewStage._

import scala.swing._

class BattleshipWindow(guiView: GuiView) extends MainFrame{
  title = "BattleShips"
  peer.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE)
  preferredSize = new Dimension(1500, 1500)

  def placeShip(player: Player) = {
    contents
  }

  private def createSelectionField(): BoxPanel = {
    new BoxPanel(Orientation.Horizontal)
  }

  private def createSelectionBox: BoxPanel =  {
    new BoxPanel(Orientation.Vertical)
  }

  private def createField(field: Field,placeTurn: Boolean, player: Player ): BoxPanel = {

   var panel = new GridPanel(field.size, field.size)
    panel.contents
    for (y <- 0 to field.size) {
      for (x <- 0 to field.size) {
        if (x == 0 && y == 0) gridPane.add(createText(""), 0, 0, 1, 1) //0 0
        else if (y == 0 && x != 0) {
          gridPane.add(createText(" " + x), x, 0, x, 1)
        } else if (x == 0 && y != 0) {
          gridPane.add(createText(" " + y), 0, y, 1, y)
        } else if (field.hasShip(Point(x, y))) {
          gridPane.add(createShip(x, y), x, y, x, y)
        } else {
          if (placeTurn) {
            gridPane.add(createButton(x, y, player), x, y, x, y)
          } else {
            gridPane.add(createHitButton(x, y, player), x, y, x, y)
          }
        }
      }
    }
    val boxPanel = new BoxPanel(Orientation.Vertical)
    if (placeTurn) {
      createShipSelection(boxPanel, player, field.size)
    }
    boxPanel
  }

  def createText(text: String): Label = {
    var label = new Label(text)
    label
  }

  def createButton(x: Int, y: Int, player: Player): Button = {
   new Button("")
  }
  def createHitButton(x: Int, y: Int, player: Player): Button = {
    new Button("A")
  }
  def createShipSelection(panel: BoxPanel, player: Player, fieldSize: Int): Unit = {

  }
}
