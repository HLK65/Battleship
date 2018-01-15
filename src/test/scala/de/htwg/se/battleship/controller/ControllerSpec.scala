package de.htwg.se.battleship.controller

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit, TestProbe}
import com.typesafe.config.ConfigFactory
import de.htwg.se.battleship.Battleship
import de.htwg.se.battleship.model.Point
import de.htwg.se.battleship.view.{TuiView, View}
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._
import org.scalatest.{BeforeAndAfterAll, FlatSpec, FlatSpecLike, Matchers}

class ControllerSpec extends TestKit(ActorSystem("Spec")) with ImplicitSender
  with BeforeAndAfterAll with Matchers with FlatSpecLike {
  private val config = ConfigFactory.load()
  private val fieldSize = config.getInt("battleship.fieldSize")
  private val actorSystemName = config.getString("battleship.actorSystemName")
  private val controllerActorName = config.getString("battleship.controllerActorName")

  override def afterAll: Unit = {
    TestKit.shutdownActorSystem(system)
  }

  "A minimal game" should "do something" in {
    val actorSystem = ActorSystem.create(actorSystemName)
    val controller = actorSystem.actorOf(Controller.props(fieldSize), controllerActorName)
    val tui = actorSystem.actorOf(Props(new TuiView(controller)))

    val testProbePlayerController = TestProbe()

  }

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
