package de.htwg.se.battleship

import de.htwg.se.battleship.controller.Controller
import de.htwg.se.battleship.model._
import de.htwg.se.battleship.view._

object Battleship {
  def main(args: Array[String]): Unit = {

    //TODO make size changeable
    val controller = Controller(10, new TuiView())
    controller.gameStart()

  }
}
