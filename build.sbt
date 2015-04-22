name := """yetu-notification-client-scala"""

resolvers += "Sonatype snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"


version := "1.0"

scalaVersion := "2.11.5"

// Change this to another test framework if you prefer
libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-json" % "2.3.8",
  "com.typesafe.play" %% "play-ws" % "2.3.8",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test",
  "com.github.sstone" %% "amqp-client" % "1.4",
  "com.typesafe.akka" %% "akka-actor" % "2.3.9",
  "com.typesafe.akka" %% "akka-testkit" % "2.3.9"
)

// Uncomment to use Akka
//libraryDependencies += "com.typesafe.akka" % "akka-actor_2.11" % "2.3.9"

