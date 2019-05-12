name := """play-the-game"""
organization := "com.zeta"

version := "1.0-SNAPSHOT"
lazy val GatlingTest = config("gatling") extend Test


scalaVersion := "2.12.8"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.1" % Test
libraryDependencies += "org.reactivemongo" %% "play2-reactivemongo" % "0.16.5-play27"
libraryDependencies += "org.typelevel" %% "cats-core" % "1.6.0"
libraryDependencies += "io.gatling.highcharts" % "gatling-charts-highcharts" % "3.1.1" % Test
libraryDependencies += "io.gatling" % "gatling-test-framework"    % "3.1.1"  % Test
// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.zeta.controllers._"


lazy val root = (project in file(".")).enablePlugins(PlayScala)
  .enablePlugins(GatlingPlugin)
  .configs(GatlingTest)
  .settings(inConfig(GatlingTest)(Defaults.testSettings): _*)
  .settings(
    name := """play-scala-rest-api-example""",
    scalaSource in GatlingTest := baseDirectory.value / "/gatling/simulation"
  )
// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.zeta.binders._"
scalacOptions += "-Ypartial-unification"
