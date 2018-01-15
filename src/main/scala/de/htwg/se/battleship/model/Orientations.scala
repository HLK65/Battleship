package de.htwg.se.battleship.model

sealed trait Orientation

case object HORIZONTAL extends Orientation

case object VERTICAL extends Orientation

