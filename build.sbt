name := "htwg-scala-battleship"
organization := "de.htwg.se"
version := "0.0.1"
scalaVersion := "2.11.8"
scalacOptions := Seq("-unchecked", "-feature", "-deprecation", "-encoding", "utf8")

resolvers += Resolver.jcenterRepo

libraryDependencies ++= {
  val scalaTestV = "3.0.0-M15"
  val scalaMockV = "3.2.2"
  val akkaV = "2.5.3"
  val configV = "1.3.1"
  val mockitoV = "1.8.4"
  val swingV = "2.11.0-M7"

  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaV,
    "com.typesafe.akka" %% "akka-testkit" % akkaV,
    "com.typesafe" % "config" % configV,
    "org.scalatest" %% "scalatest" % scalaTestV % "test",
    "org.scalamock" %% "scalamock-scalatest-support" % scalaMockV % "test",
    "org.mockito" % "mockito-all" % mockitoV % "test",
    "org.scala-lang" % "scala-swing" % swingV
  )
}