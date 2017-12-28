package de.htwg.se.battleship.view

import de.htwg.se.battleship.controller.Controller
import de.htwg.se.battleship.model.handler.ShipActionHandler
import de.htwg.se.battleship.model._
import de.htwg.se.battleship.view.stages.{ FieldStage, PlayerSwitchStage, WinnerAnnouncementStage }

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

object GuiView extends JFXApp with View {

  var shipSelection = new ShipSelection(-1, Orientation.HORIZONTAL)
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
    var button = new Button("Ready")
    button.onAction = (event: ActionEvent) => {
      this.selectShip(player)
    }
    PlayerSwitchStage.createStage(player, button)
  }

  override def printField(field: Field, color: String): Unit = {
  }

  override def shootTurn(): Point = ???

  override def printMessage(message: String): Unit = ???

  override def selectShip(player: Player): Int = {
    FieldStage.createStage(player, createFieldGrid(player.field, true, controller, player))
    0
  }

  override def readPoint(): Point = ???

  override def readOrientation(): Int = ???

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
    if (placeTurn) {
      createShipSelection(gridPane, player, field.size)
    }
    gridPane
  }

  private def createShipSelection(gridPane: GridPane, player: Player, x: Int): Unit = {

    var placeGrid = new GridPane {
      hgap = 25
      vgap = 15
      padding = Insets(20)
    }
    placeGrid.style = "-fx-background-color:grey"
    placeGrid.add(this.createText("Place Ships"), 1, 0)
    val shipText = this.createText("")
    placeGrid.add(shipText, 1, 1)
    for ((key, ship) <- player.shipInventory) {

      var button = new Button(ship + " sized H")
      button.onAction = (event: ActionEvent) => {
        shipSelection = new ShipSelection(ship, Orientation.HORIZONTAL)
        shipText.text = ship + "sized ship Selected"
      }
      placeGrid.add(button, key, 1)
      var vButton = new Button(ship + " sized V")
      placeGrid.add(vButton, key, 2)
      vButton.onAction = (event: ActionEvent) => {
        shipSelection = new ShipSelection(ship, Orientation.VERTICAL)
        shipText.text = ship + "sized ship Selected"
      }
    }
    gridPane.add(placeGrid, x + 2, 1)
  }

  private def createButton(x: Int, y: Int, actonHandler: ShipActionHandler, player: Player): Button = {

    var shipButton = new Button("-")

    shipButton.onAction = (even: ActionEvent) => {
      val ship = new Ship(shipSelection.size)
      if (actonHandler.getShipPlaceAction(x, y, ship, shipSelection.orientation, player)) {
        this.playerSwitch(this.selectPlayer(player))
      }
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

  private def selectPlayer(player: Player): Player = {
    if (player.COLOR.equals(controller.player1Color)) {
      return controller.player2
    }
    controller.player1
  }
}