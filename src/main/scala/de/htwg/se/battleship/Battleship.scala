package de.htwg.se.battleship

import akka.actor.{ ActorSystem, Props }
import com.typesafe.config.ConfigFactory
import de.htwg.se.battleship.controller.Controller
import de.htwg.se.battleship.model.Message.StartGame
import de.htwg.se.battleship.view.TuiView

object Battleship {

  def main(args: Array[String]): Unit = {
    val (fieldSize, actorSystemName, controllerActorName) = getConfig
    //1x5Felder, 2x4Felder, 3x3Felder, 4x2Felder //Size -> Amount
    val shipInventory: scala.collection.mutable.Map[Int, Int] = scala.collection.mutable.Map( /*5 -> 1, 4 -> 2, 3 -> 3*/ 2 -> 1)
    val actorSystem = ActorSystem.create(actorSystemName)
    val controller = actorSystem.actorOf(Controller.props(fieldSize, shipInventory), controllerActorName)
    val tui = actorSystem.actorOf(Props(new TuiView(controller)))
    //    val gui = actorSystem.actorOf(Props(new GuiView(controller)))

    controller ! StartGame
  }

  private def getConfig = {
    val config = ConfigFactory.load()
    val fieldSize = config.getInt("battleship.fieldSize")
    val actorSystemName = config.getString("battleship.actorSystemName")
    val controllerActorName = config.getString("battleship.controllerActorName")
    (fieldSize, actorSystemName, controllerActorName)
  }

}
