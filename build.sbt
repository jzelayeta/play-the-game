val Http4sVersion = "0.20.0"
val CirceVersion = "0.11.1"
val Specs2Version = "4.1.0"
val LogbackVersion = "1.2.3"

mainClass in Compile := Some("com.zeta.playthegame.Main")
dockerBaseImage := "openjdk:jre-alpine"

lazy val root = (project in file("."))
  .settings(
    organization := "com.zeta",
    name := "play-the-game",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.12.8",
    scalacOptions ++= Seq("-Ypartial-unification"),
    libraryDependencies ++= Seq(
      "org.http4s"      %% "http4s-blaze-server" % Http4sVersion,
      "org.http4s"      %% "http4s-blaze-client" % Http4sVersion,
      "org.http4s"      %% "http4s-circe"        % Http4sVersion,
      "org.http4s"      %% "http4s-dsl"          % Http4sVersion,
      "io.circe"        %% "circe-generic"       % CirceVersion,
      "io.circe"        %% "circe-core"          % CirceVersion,
      "io.circe"        %% "circe-parser"        % CirceVersion,
      "ch.qos.logback"  %  "logback-classic"     % LogbackVersion,
      "io.circe"        %% "circe-literal"       % CirceVersion,
      "org.specs2"      %% "specs2-core"         % Specs2Version % "test",
      "org.mongodb.scala" %% "mongo-scala-driver" % "2.6.0",
      "com.typesafe" % "config" % "1.3.4",
    ),
    addCompilerPlugin("org.spire-math" %% "kind-projector"     % "0.9.6"),
    addCompilerPlugin("com.olegpy"     %% "better-monadic-for" % "0.2.4")
  )

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-language:higherKinds",
  "-language:postfixOps",
  "-feature",
  "-Ypartial-unification",
  "-Xfatal-warnings",
)

enablePlugins(JavaAppPackaging)
enablePlugins(DockerPlugin)
enablePlugins(AshScriptPlugin)


