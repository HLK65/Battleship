
package de.htwg.se.battleship.view.stages

import javafx.embed.swing.JFXPanel

import de.htwg.se.battleship.model._
import de.htwg.se.battleship.view.GuiView

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.event.ActionEvent
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.layout.{ GridPane, VBox }
import scalafx.scene.paint.Color._
import scalafx.scene.paint.{ LinearGradient, Stops }
import scalafx.scene.shape.Rectangle
import scalafx.scene.text.Text

object GuiViewStage extends JFXApp {

  var shipSelection = new ShipSelection(-1, Orientation.HORIZONTAL)
  val args: Array[String] = new Array[String](1)
  var guiView: GuiView = null

  var readyButton = new Button("Ready")
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
  def startGame(player: Player): Unit = {
    //start the main bevore you can change the stage

    new JFXPanel()
    main(args)
  }

  def placeShip(player: Player): Unit = {
    selectShip(player)
  }

  def firstTurn(player: Player): Unit = {
    stage = FieldStage.createStage(player, createFieldGrid(player.field, true, player))
  }

  def shootTurn(player: Player): Unit = {
    FieldStage.createStage(player, createFieldGrid(player.field, false, player))
  }

  def announceWinner(player: Player): Unit = {

    WinnerAnnouncementStage.announceWinner(player)
  }

  def printMessage(message: String): Unit = ???

  def selectShip(player: Player): Unit = {
    //FieldStage.createStage(player, createFieldGrid(player.field, true, player))
    switchScene(createFieldGrid(player.field, true, player))
  }

  private def switchScene(gridPane: GridPane) = {
    val scene = new Scene {
      fill = DarkBlue
      content = gridPane
    }
    stage.scene = scene
  }

  def createFieldGrid(field: Field, placeTurn: Boolean, player: Player): GridPane = {

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
            gridPane.add(createButton(x, y, player), x, y, x, y)
          } else {
            gridPane.add(createHitButton(x, y, player), x, y, x, y)
          }
        }
      }
    }
    if (placeTurn) {
      createShipSelection(gridPane, player, field.size)
    }
    gridPane
  }

  private def createHitButton(x: Int, y: Int, player: Player): Button = {
    var button = new Button("X")
    button.onAction = (even: ActionEvent) => {
      guiView.hitShipCall(player, new Point(x, y))
    }
    button
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

  private def createButton(x: Int, y: Int, player: Player): Button = {

    var shipButton = new Button("-")

    shipButton.onAction = (even: ActionEvent) => {
      val ship = new Ship(shipSelection.size)
      guiView.placeShipCall(player, new Point(x, y), ship.SIZE, shipSelection.orientation)
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

}