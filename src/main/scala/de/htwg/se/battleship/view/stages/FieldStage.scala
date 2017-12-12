package de.htwg.se.battleship.view.stages

import de.htwg.se.battleship.model.{ Field, Player, Point }

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

  def printField(player: Player): PrimaryStage = {

    val field = player.field
    createStage(player, createFieldGrid(field))
  }

  def createFieldGrid(field: Field): GridPane = {
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
          gridPane.add(createText("-"), x, y, x, y)
        }
      }
    }
    gridPane
  }

  private def createShip(xCord: Int, yCord: Int): Rectangle = {
    new Rectangle {
      x = xCord
      y = yCord
      width = 10
      height = 10
      fill = Green
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