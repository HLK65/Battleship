package de.htwg.se.battleship.view.stages

import de.htwg.se.battleship.model._

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.layout.{ GridPane, HBox, VBox }
import scalafx.scene.paint.Color._

object FieldStage extends JFXApp {

  def createStage(player: Player, field: GridPane): PrimaryStage = {

    val stage = new PrimaryStage {
      title.value = player.COLOR + "s field"
      width = 1000
      height = 1500
      scene = new Scene {
        fill = DarkBlue
        content = field
      }
    }
    stage
  }

}
