package de.htwg.se.battleship

import de.htwg.se.battleship.controller.Controller
import de.htwg.se.battleship.model._
import de.htwg.se.battleship.view._

object Battleship {

  val fieldSizeDefault = 20
  val fieldSizeEnvName = "fieldSize"

  def main(args: Array[String]): Unit = {

    val fieldSize: Int = if (sys.env(fieldSizeEnvName).isEmpty) fieldSizeDefault else sys.env(fieldSizeEnvName).toInt

    val controller = Controller(fieldSize, new TuiView)
        controller.gameStart()

  }
}
