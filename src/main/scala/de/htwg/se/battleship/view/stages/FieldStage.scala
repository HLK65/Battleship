package de.htwg.se.battleship.view.stages

import de.htwg.se.battleship.controller.Controller
import de.htwg.se.battleship.model.handler.ShipActionHandler
import de.htwg.se.battleship.model._

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.event.ActionEvent
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.layout.{ GridPane, HBox, VBox }
import scalafx.scene.paint.Color._
import scalafx.scene.paint.{ LinearGradient, Stops }
import scalafx.scene.shape.Rectangle
import scalafx.scene.text.Text

object FieldStage extends JFXApp {

  def printField(player: Player, placeTurn: Boolean, controller: Controller): PrimaryStage = {

    val field = player.field
    createStage(player, createFieldGrid(field, placeTurn, controller, player))
  }

  def createFieldGrid(field: Field, placeTurn: Boolean, controller: Controller, player: Player): GridPane = {
    val shipActionHandler = new ShipActionHandler(controller)

    var gridPane = new GridPane {
      hgap = 25
      vgap = 15
      padding = Insets(20)
    }

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
            gridPane.add(createButton(x, y, shipActionHandler, player), x, y, x, y)
          }
        }
      }
    }
    gridPane
  }

  private def createButton(x: Int, y: Int, actonHandler: ShipActionHandler, player: Player): Button = {

    var shipButton = new Button("-")
    val ship = new Ship(1)

    shipButton.onAction = (even: ActionEvent) => {
      actonHandler.getShipPlaceAction(x, y, ship, Orientation.HORIZONTAL, player)
    }
    shipButton
  }

  private def createShip(xCord: Int, yCord: Int): Rectangle = {
    new Rectangle {
      x = xCord
      y = yCord
      width = 10
      height = 10
      fill = SandyBrown
    }
  }

  def createStage(player: Player, field: GridPane): PrimaryStage = {

    val stage = new PrimaryStage {
      title.value = player.COLOR + "s field"
      width = 600
      height = 500
      scene = new Scene {
        fill = DarkBlue
        content = field
      }
    }
    stage.setFullScreen(true)
    stage
  }

  private def createText(content: String): Text = {
    new Text {
      text = content
      style = "-fx-font-size: 12pt;"
      fill = new LinearGradient(
        endX = 0,
        stops = Stops(White, WhiteSmoke)
      )
    }
  }
}