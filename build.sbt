name := """play-the-game"""
organization := "com.zeta"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.8"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.1" % Test
libraryDependencies += "org.reactivemongo" %% "play2-reactivemongo" % "0.16.5-play27"
// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.zeta.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.zeta.binders._"
