package de.htwg.se.battleship.view

import de.htwg.se.battleship.controller.Controller
import de.htwg.se.battleship.model.{ Field, Orientation, Player, Point }
import de.htwg.se.battleship.view.stages.{ FieldStage, WinnerAnnouncementStage }

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

  val args: Array[String] = new Array[String](1)
  val readyButton = new Button("Ready")
  //TODO Replace this shit with controller interface
  var controller: Controller = controller
  readyButton.onAction = (event: ActionEvent) => {
    this.playerSwitch(controller.player1)
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
    main(args);
  }

  override def announceWinner(color: String): Unit = {

    WinnerAnnouncementStage.announceWinner(color)
  }

  override def playerSwitch(player: Player): Unit = {
    player.field.placeShip(new Point(1, 1), 2, Orientation.VERTICAL.toString)
    stage = FieldStage.printField(player)
  }

  override def printField(field: Field, color: String): Unit = ???

  override def shootTurn(): Point = ???

  override def printMessage(message: String): Unit = ???

  override def selectShip(player: Player): Int = ???

  override def readPoint(): Point = ???

  override def readOrientation(): Int = ???
}