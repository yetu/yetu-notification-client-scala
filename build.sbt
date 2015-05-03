import bintray.Keys._

name := """yetu-notification-client-scala"""

organization := "com.yetu"

resolvers += "Sonatype snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

resolvers += "The New Motion Public Repo" at "http://nexus.thenewmotion.com/content/groups/public/"

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

name := """yetu-notification-client-scala"""


lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  ws,
  "com.yetu"          %% "oauth2-resource-server" % "0.2.10",
  "com.yetu"          %% "oauth2-resource-server" % "0.2.10" % "test" classifier "tests",
  "net.logstash.logback" % "logstash-logback-encoder" % "3.0",
  "org.scalatest"     %% "scalatest" % "2.2.4" % "test",
  "org.scalamock"     %% "scalamock-scalatest-support" % "3.2" % "test",
  "org.scalacheck"    %% "scalacheck" % "1.12.1" % "test",
  "org.scalatestplus" %% "play" % "1.2.0" % "test",
  "com.github.sstone" %% "amqp-client" % "1.4",
  "com.typesafe.akka" %% "akka-actor" % "2.3.9",
  "com.typesafe.akka" %% "akka-testkit" % "2.3.9"
)


// ----------- publishing settings -----------------------------------
// http://www.scala-sbt.org/0.13.5/docs/Detailed-Topics/Publishing.html
// -------------------------------------------------------------------

crossScalaVersions := Seq("2.10.5", "2.11.6")

// sbt-release plugin settings:
releaseSettings

publishMavenStyle := true

// settings for bintray publishing

bintrayPublishSettings

repository in bintray := "maven"

licenses +=("MIT", url("http://opensource.org/licenses/MIT"))

packageLabels in bintray := Seq("rabbitmq", "yetu")

bintrayOrganization in bintray := Some("yetu")
