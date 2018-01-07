package de.htwg.se.battleship

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import de.htwg.se.battleship.controller.Controller
import de.htwg.se.battleship.model.Message.StartGame
import de.htwg.se.battleship.view.TuiView

object Battleship {

  def main(args: Array[String]): Unit = {

    val (fieldSize, actorSystemName, controllerActorName) = getConfig
    val actorSystem = ActorSystem.create(actorSystemName)
    val controller = actorSystem.actorOf(Controller.props(fieldSize), controllerActorName)
    val tui = actorSystem.actorOf(Props(new TuiView(controller)))

    controller.tell(StartGame, null)

  }

  private def getConfig = {
    val config = ConfigFactory.load()
    val fieldSize = config.getInt("battleship.fieldSize")
    val actorSystemName = config.getString("battleship.actorSystemName")
    val controllerActorName = config.getString("battleship.controllerActorName")
    (fieldSize, actorSystemName, controllerActorName)
  }

}
