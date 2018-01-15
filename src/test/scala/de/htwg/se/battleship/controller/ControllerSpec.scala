package de.htwg.se.battleship.controller

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit}
import de.htwg.se.battleship.model.Message._
import de.htwg.se.battleship.model._
import org.scalatest.{BeforeAndAfterAll, FlatSpecLike, Matchers}

class ControllerSpec() extends TestKit(ActorSystem("battleship")) with ImplicitSender with FlatSpecLike with Matchers with BeforeAndAfterAll {
  private val fieldSize = 10
  private val shipInventory: scala.collection.mutable.Map[Int, Int] = scala.collection.mutable.Map(2 -> 1)
  private val actorSystemName = "battleship"
  private val controllerActorName = "controller"

  override def afterAll: Unit = {
    TestKit.shutdownActorSystem(system)
  }

  "A minimal game" should "work" in {
    val actorSystem = ActorSystem.create(actorSystemName)
    val controller = actorSystem.actorOf(Controller.props(fieldSize, shipInventory), controllerActorName)
    //    val tui = actorSystem.actorOf(Props(new TuiView(controller)))
    //    val testProbePlayerController = TestProbe()

    val player1 = Player("Red", Field(fieldSize), shipInventory.clone())
    val player2 = Player("Blue", Field(fieldSize), shipInventory.clone())

    controller ! RegisterObserver
    expectMsg(Update(Init, player1, player2))

    controller ! StartGame
    expectMsg(Update(PlaceShipTurn, player1, player2))

    controller ! PlaceShip(player1, Point(1, 1), 2, HORIZONTAL)
    player1.shipInventory.remove(2)
    expectMsgAllOf(PrintMessage("Ship placed"), Update(PlaceShipTurn, player2, player1))

    controller ! PlaceShip(player2, Point(1, 1), 2, VERTICAL)
    player2.shipInventory.remove(2)
    expectMsgAllOf(PrintMessage("Ship placed"), PrintMessage("all ships placed"), Update(ShootTurn, player1, player2))

    controller ! HitShip(player2, Point(3, 3))
    expectMsgAllOf(PrintMessage("hit water"), Update(ShootTurn, player2, player1))

    controller ! HitShip(player1, Point(2, 1))
    player1.field.hitField(Point(2, 1))
    expectMsgAllOf(PrintMessage("hit ship"), Update(ShootTurn, player1, player2))

    controller ! HitShip(player2, Point(1, 2))
    player2.field.hitField(Point(1, 2))
    expectMsgAllOf(PrintMessage("hit ship"), Update(ShootTurn, player2, player1))

    controller ! HitShip(player1, Point(1, 1))
    player1.field.hitField(Point(1, 1))
    expectMsgAllOf(PrintMessage("2 sunk"))

    expectMsg(Update(AnnounceWinner, player2, player1))
  }

}
