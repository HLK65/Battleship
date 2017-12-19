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
    stage
  }

}