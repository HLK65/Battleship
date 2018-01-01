package de.htwg.se.battleship

import com.typesafe.config.ConfigFactory
import de.htwg.se.battleship.controller.Controller

object Battleship {

  def main(args: Array[String]): Unit = {

    val (fieldSize, dummy) = getConfig
    val controller = Controller(fieldSize, new TuiView)
        controller.gameStart()

  }

  def getConfig = {
    val config = ConfigFactory.load()
    val fieldSize = config.getInt("battleship.fieldSize")
    val dummy = config.getString("battleship.dummy")
    (fieldSize, dummy)
  }
}
