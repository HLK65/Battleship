package de.htwg.se.battleship.view

import akka.actor.{Actor, ActorRef}
import de.htwg.se.battleship.model.Message.ProcessTuiInput

class TuiInput(val tuiView: ActorRef) extends Actor {
  while (true) {
    tuiView ! ProcessTuiInput(readInt())
  }

  /**
    * reads int from console, wrapped with try catch
    *
    * @return integer
    */
  def readInt(): Int = {
    try {
      scala.io.StdIn.readInt()
    } catch {
      case _: NumberFormatException =>
        println("try again... numbers only")
        readInt()
    }
  }

  override def receive: Receive = _
}
