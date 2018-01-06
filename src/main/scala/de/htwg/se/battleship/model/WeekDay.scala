package de.htwg.se.battleship.model

object WeekDay {
  val daysOfWeek = Seq(Mon, Tue, Wed, Thu, Fri)

  sealed trait EnumVal

  case object Mon extends EnumVal

  case object Tue extends EnumVal

  case object Wed extends EnumVal

  case object Thu extends EnumVal

  case object Fri extends EnumVal

}