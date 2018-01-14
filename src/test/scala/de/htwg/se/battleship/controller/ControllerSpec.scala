package de.htwg.se.battleship.controller

import de.htwg.se.battleship.model.Point
import de.htwg.se.battleship.view.View
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._
import org.scalatest.{ FlatSpec, Matchers }

class ControllerSpec extends FlatSpec with Matchers {

  /*val view = mock(classOf[View])
  val controller = Controller(4, view)
  when(view.selectShip(controller.player2)).thenReturn(2)
  when(view.selectShip(controller.player1)).thenReturn(1).thenReturn(2)
  when(view.readPoint()).thenReturn(Point(1, 5)).thenReturn(Point(1, 3)).thenReturn(Point(3, 1))
  when(view.readOrientation()).thenReturn(1).thenReturn(2).thenReturn(1)
  when(view.shootTurn()).thenReturn(Point(3, 1)).
    thenReturn(Point(1, 3)).thenReturn(Point(5, 1)).thenReturn(Point(3, 2)).thenReturn(Point(2, 3)).
    thenReturn(Point(4, 1)).thenReturn(Point(1, 4))
  "a Controller" should "play a game" in {
    controller.gameStart()
  }*/

}
