/*
package de.htwg.se.battleship.view.stages

import de.htwg.se.battleship.model._
import de.htwg.se.battleship.view.GuiView

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.event.ActionEvent
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.layout.GridPane
import scalafx.scene.paint.Color._
import scalafx.scene.paint.{ LinearGradient, Stops }
import scalafx.scene.text.Text

object PlayerSwitchStage extends JFXApp {

  def createStage(player: Player, button: Button): PrimaryStage = {

    var grid = new GridPane {
      hgap = 25
      vgap = 15
      padding = Insets(20)
    }

    grid.add(new Text {
      text = player.COLOR + "s turn"
      style = "-fx-font-size: 12pt;"
      fill = new LinearGradient(
        endX = 0,
        stops = Stops(White, WhiteSmoke)
      )
    }, 5, 5)

    grid.add(button, 5, 6)

    val stage = new PrimaryStage {
      title.value = player.COLOR + "s turn"
      width = 600
      height = 500
      scene = new Scene {
        fill = DarkBlue
        content = grid
      }
    }
    stage
  }

}*/
