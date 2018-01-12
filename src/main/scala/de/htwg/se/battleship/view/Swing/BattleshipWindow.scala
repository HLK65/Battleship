package de.htwg.se.battleship.view.Swing

import java.awt.Dimension

import de.htwg.se.battleship.model.{ Field, Player, Point }
import de.htwg.se.battleship.view.GuiView
import de.htwg.se.battleship.view.stages.GuiViewStage._

import scala.swing._

class BattleshipWindow(guiView: GuiView) extends MainFrame {
  title = "BattleShips"
  peer.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE)
  preferredSize = new Dimension(1500, 1500)

  def placeShip(player: Player) = {
    contents = createPlaceField(player)
  }

  def hitSHip(player: Player) = {
    contents = createHitField(player)
  }

  private def createPlaceField(player: Player): BoxPanel = {
    createField(player.field, true, player)
  }

  private def createHitField(player: Player): BoxPanel = {
    createField(player.field, false, player)
  }

  private def createField(field: Field, placeTurn: Boolean, player: Player): BoxPanel = {

    var panel = new GridPanel(field.size, field.size)
    for (y <- 0 to field.size) {
      for (x <- 0 to field.size) {
        if (x == 0 && y == 0) panel.contents += createText("")
        else if (y == 0 && x != 0) {
          panel.contents += createText(" " + x)
        } else if (x == 0 && y != 0) {
          panel.contents += createText(" " + y)
        } else if (field.hasShip(Point(x, y))) {
          panel.contents += createShip()
        } else {
          if (placeTurn) {
            panel.contents += createButton(x, y, player)
          } else {
            panel.contents += createHitButton(x, y, player)
          }
        }
      }
    }
    val boxPanel = new BoxPanel(Orientation.Vertical)
    if (placeTurn) {
      createShipSelection(boxPanel, player, field.size)
    }
    boxPanel.contents += panel
    boxPanel
  }

  private def createText(text: String): Label = {
    var label = new Label(text)
    label
  }

  private def createShip(): Label = {
    val label = new Label("X")
    label
  }

  private def createButton(x: Int, y: Int, player: Player): Button = {
    new Button("")
  }
  private def createHitButton(x: Int, y: Int, player: Player): Button = {
    new Button("A")
  }
  private def createShipSelection(panel: BoxPanel, player: Player, fieldSize: Int): BoxPanel = {
    new BoxPanel(Orientation.Horizontal)
  }
}
