package de.htwg.se.battleship

import de.htwg.se.battleship.model.Student

object Hello {
  def main(args: Array[String]): Unit = {
    val student = Student("Your Name")
    println("Hello, " + student.name)
  }
}
