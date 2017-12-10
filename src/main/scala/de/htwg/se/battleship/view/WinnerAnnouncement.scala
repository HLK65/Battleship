package de.htwg.se.battleship.view

import de.htwg.se.battleship.model.{ Field, Player, Point }
import de.htwg.se.battleship.view.GuiView.readyButton

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.event.ActionEvent
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.layout.VBox
import scalafx.scene.paint.Color._
import scalafx.scene.paint.{ LinearGradient, Stops }
import scalafx.scene.text.Text

object WinnerAnnouncement extends JFXApp {

  def announceWinner(array: Array[String], color: String): Unit = {

    val readyButton = new Button("Ready")

    readyButton.onAction = (event: ActionEvent) => {
      JFXApp.ActiveApp.stage.close()
    }

    readyButton.style = "-fx-font-size: 12pt"
    JFXApp.ActiveApp.stage = new PrimaryStage {
      title.value = "We have a Winner!"
      width = 300
      height = 250
      scene = new Scene {
        fill = Black
        content = new VBox {
          padding = Insets(20)
          children = Seq(
            new Text {
              text = "The Winner is:"
              style = "-fx-font-size: 12pt"
              fill = new LinearGradient(
                endX = 0,
                stops = Stops(Gold, Gold)
              )
            },
            new Text {
              text = color
              style = "-fx-font-size: 12pt"
              fill = new LinearGradient(
                endX = 0,
                stops = Stops(color, color)
              )
            },
            readyButton
          )
        }
      }
    }
    //    stage.show()
  }
}