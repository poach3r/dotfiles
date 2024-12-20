enablePlugins(ScalaNativePlugin)

lazy val root = (project in file("."))
  .settings(
    name := "EwwInfo",
    version := "0.1.0",
    scalaVersion := "3.3.4",
    organization := "org.poach3r",
    organizationName := "poach3r",
  )

libraryDependencies += "com.lihaoyi" %%% "os-lib" % "0.11.4-M3"
libraryDependencies ++= Seq(
  "io.circe" %%% "circe-core",
  "io.circe" %%% "circe-generic",
  "io.circe" %%% "circe-parser"
).map(_ % "0.14.10")