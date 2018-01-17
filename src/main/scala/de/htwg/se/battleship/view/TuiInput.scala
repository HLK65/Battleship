package de.htwg.se.battleship.view

import akka.actor.{Actor, ActorRef}
import de.htwg.se.battleship.model.Message.ProcessTuiInput

class TuiInput(val tuiView: ActorRef) {
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
    }
    catch {
      case _: Throwable =>
        println("try again... numbers only")
        readInt()
    }
  }
}
