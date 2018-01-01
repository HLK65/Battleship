package de.htwg.se.battleship

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import de.htwg.se.battleship.controller.Controller

object Battleship {

  def main(args: Array[String]): Unit = {

    val (fieldSize, actorSystemName, controllerActorName) = getConfig
    val actorSystem = ActorSystem.create(actorSystemName)
    val controller = actorSystem.actorOf(Controller.props(fieldSize), controllerActorName)
    //    val tui = actorSystem.actorOf(Props(new TuiView(controller)))
    controller.tell("helloWorld", null)

  }

  def getConfig = {
    val config = ConfigFactory.load()
    val fieldSize = config.getInt("battleship.fieldSize")
    val actorSystemName = config.getString("battleship.actorSystemName")
    val controllerActorName = config.getString("battleship.controllerActorName")
    (fieldSize, actorSystemName, controllerActorName)
  }
}
