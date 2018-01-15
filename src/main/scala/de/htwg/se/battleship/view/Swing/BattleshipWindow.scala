package de.htwg.se.battleship.view.Swing

import java.awt.{Color, Toolkit}

import de.htwg.se.battleship
import de.htwg.se.battleship.model.{Orientation => _, _}
import de.htwg.se.battleship.view.GuiView

import scala.swing._

class BattleshipWindow(guiView: GuiView) extends MainFrame {
  title = "BattleShips"
  peer.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE)
  // preferredSize = new Dimension(1500, 1500)
  val dimension = Toolkit.getDefaultToolkit().getScreenSize()

  var shipSelection: ShipSelection = null

  def placeShip(player: Player) = {
    contents = createPlaceField(player)
  }

  private def createPlaceField(player: Player): BoxPanel = {
    createField(player.field, true, player)
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
    if (placeTurn) {
      val label = createPlaceHeader(player)
      boxPanel.contents += label
    } else {
      val label = createHitHeader(player)
      boxPanel.contents += label
    }
    if (placeTurn) {
      boxPanel.contents += createShipSelection(player)
    }
    boxPanel.contents += panel
    boxPanel
  }

  private def createShip(): Label = {
    val label = new Label("X")
    label
  }

  private def createHitHeader(player: Player): Label = {
    var label = new Label()
    if (player.COLOR.equals("Red")) {
      label = createText("Blue´s turn")
      label.foreground = Color.BLUE
    } else {
      label = createText("Red´s turn")
      label.foreground = Color.RED
    }
    label
  }

  def hitSHip(player: Player) = {
    contents = createHitField(player)
  }

  private def createPlaceHeader(player: Player): Label = {
    var label = createText(player.COLOR + "´s turn")
    if (player.COLOR.equals("Red")) {
      label.foreground = Color.RED
    } else {
      label.foreground = Color.BLUE
    }
    label
  }

  private def createButton(x: Int, y: Int, player: Player): Button = {
    var button = new Button("")
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
          shipSelection = new ShipSelection(ship, HORIZONTAL)
          shipText.text = ship + " sized selected"
        }
      }
      var vButton = new Button(ship + " sized Vertical")
      vButton.action = new Action(ship + " sized Vertical") {
        override def apply(): Unit = {
          shipSelection = new ShipSelection(ship, VERTICAL)
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

  private def createHitField(player: Player): BoxPanel = {
    createField(player.field, false, player)
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

  private def createText(text: String): Label = {
    var label = new Label(text)
    label
  }
}
