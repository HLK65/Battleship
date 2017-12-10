package de.htwg.se.battleship.view

import de.htwg.se.battleship.model.{ Field, Player, Point }

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.event.ActionEvent
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.layout.{ HBox, VBox }
import scalafx.scene.paint.Color._
import scalafx.scene.paint.{ LinearGradient, Stops }
import scalafx.scene.text.Text

object GuiView extends JFXApp with View {

  val z: Array[String] = new Array[String](1)

  val readyButton = new Button("Ready")

  readyButton.onAction = (event: ActionEvent) => {
    stage.hide();
  }

  readyButton.style = "-fx-font-size: 12pt"

  stage = new PrimaryStage {
    title.value = "Scala Battle Ships"
    width = 100
    height = 150
    scene = new Scene {
      fill = Black
      content = new VBox {
        padding = Insets(20)
        children = Seq(
          new Text {
            text = "Player Red "
            style = "-fx-font-size: 12pt"
            fill = new LinearGradient(
              endX = 0,
              stops = Stops(Red, Red)
            )
          },
          new Text {
            text = "Player Blue"
            style = "-fx-font-size: 12pt"
            fill = new LinearGradient(
              endX = 0,
              stops = Stops(Blue, Blue)
            )
          },
          readyButton
        )
      }
    }
  }

  override def startGame: Unit = {
    //start the main bevore you can change the stage
    main(z);
  }

  override def announceWinner(color: String): Unit = {

    WinnerAnnouncement.announceWinner(z, color)
  }

  override def playerSwitch(player: Player): Unit = ???

  override def printField(field: Field, color: String): Unit = ???

  override def shootTurn(): Point = ???

  override def printMessage(message: String): Unit = ???

  override def selectShip(player: Player): Int = ???

  override def readPoint(): Point = ???

  override def readOrientation(): Int = ???
}