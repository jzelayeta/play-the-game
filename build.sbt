

val Http4sVersion = "0.20.0"
val CirceVersion = "0.11.1"
val Specs2Version = "4.1.0"
val LogbackVersion = "1.2.3"


lazy val root = (project in file("."))
  .settings(
    organization := "com.zeta",
    name := "play_the_game",
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
      "org.mongodb.scala" %% "mongo-scala-driver" % "2.6.0",
      "com.github.pureconfig" %% "pureconfig" % "0.11.0",
      "org.scalactic" %% "scalactic" % "3.0.5",
      "org.typelevel" %% "cats-core" % "2.0.0-M1",
      "org.specs2"      %% "specs2-core"         % Specs2Version % "test",
      "io.gatling.highcharts" % "gatling-charts-highcharts" % "3.0.0" % "test,it",
      "org.scalatest" %% "scalatest" % "3.0.5" % "test",
      "io.gatling"            % "gatling-test-framework"    % "3.0.0" % "test,it"
    ),
    addCompilerPlugin("org.spire-math" %% "kind-projector"     % "0.9.6"),
    addCompilerPlugin("com.olegpy"     %% "better-monadic-for" % "0.2.4")
  ).enablePlugins(JavaAppPackaging)
   .enablePlugins(DockerPlugin)
   .enablePlugins(AshScriptPlugin)
  .enablePlugins(GatlingPlugin)


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

mainClass in Compile := Some("com.zeta.playthegame.Main")
dockerBaseImage := "openjdk:jre-alpine"
packageName in Docker := "play_the_game"
dockerExposedPorts := Seq(8080)
bashScriptExtraDefines += """addJava "-Dconfig.resource=production.conf""""





