package de.htwg.se.battleship.view.Swing

import java.awt.{ Color, Dimension }

import de.htwg.se.battleship
import de.htwg.se.battleship.model.{ Orientation => _, _ }
import de.htwg.se.battleship.view.GuiView
import de.htwg.se.battleship.view.stages.GuiViewStage.shipSelection

import scala.swing._

class BattleshipWindow(guiView: GuiView) extends MainFrame {
  title = "BattleShips"
  peer.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE)
  preferredSize = new Dimension(1500, 1500)

  var shipSelection: ShipSelection = null

  def placeShip(player: Player) = {
    contents = createPlaceField(player)
  }

  def hitSHip(player: Player) = {
    contents = createHitField(player)
  }

  def printMessage(message: String) = {

    Dialog.showMessage(contents.head, message, "Attention")
  }

  def endgame() = {
    var box = new BoxPanel(Orientation.Vertical)
    val labe = createText("The Game is over!")
    box.contents += labe
    contents = box

  }

  private def createPlaceField(player: Player): BoxPanel = {
    createField(player.field, true, player)
  }

  private def createHitField(player: Player): BoxPanel = {
    createField(player.field, false, player)
  }

  private def createField(field: Field, placeTurn: Boolean, player: Player): BoxPanel = {

    shipSelection = null;
    var panel = new GridPanel(field.size + 1, field.size + 1)
    for (y <- 0 to field.size) {
      for (x <- 0 to field.size) {
        if (x == 0 && y == 0) panel.contents += createText("")
        else if (y == 0 && x != 0) {
          panel.contents += createText(" " + x)
        } else if (x == 0 && y != 0) {
          panel.contents += createText(" " + y)
        } else if (field.hasShip(Point(x, y)) && placeTurn) {
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
    val label = createText(player.COLOR + "´s turn")
    boxPanel.contents += label
    if (placeTurn) {
      boxPanel.contents += createShipSelection(player)
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
    var button = new Button("");
    button.action = new Action("") {
      override def apply(): Unit = {
        if (shipSelection != null) {
          guiView.placeShipCall(player, new battleship.model.Point(x, y), shipSelection.size, shipSelection.orientation)

        }
      }
    }
    button
  }
  private def createHitButton(x: Int, y: Int, player: Player): Button = {
    var button = new Button("")
    button.background = Color.RED
    button.action = new Action("") {
      override def apply(): Unit = {
        guiView.hitShipCall(player, new battleship.model.Point(x, y));
      }
    }
    button
  }
  private def createShipSelection(player: Player): BoxPanel = {
    var selectPannel = new BoxPanel(Orientation.Horizontal)
    val shipText = this.createText("");
    for ((ship, key) <- player.shipInventory) {

      var box = new BoxPanel(Orientation.Vertical)
      var hButton = new Button(ship + " sized Horizontal")
      hButton.action = new Action(ship + " sized Horizontal") {
        override def apply(): Unit = {
          shipSelection = new ShipSelection(ship, de.htwg.se.battleship.model.Orientation.HORIZONTAL)
          shipText.text = ship + " sized selected"
        }
      }
      var vButton = new Button(ship + " sized Vertical")
      vButton.action = new Action(ship + " sized Vertical") {
        override def apply(): Unit = {
          shipSelection = new ShipSelection(ship, de.htwg.se.battleship.model.Orientation.VERTICAL)
          shipText.text = ship + " sized selected"
        }
      }
      box.contents += hButton
      box.contents += vButton
      selectPannel.contents += box
    }
    selectPannel.contents += shipText
    selectPannel
  }
}
