name := """changeme"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  "org.mongodb" % "mongo-java-driver" % "3.0.2",
  "org.mongodb.morphia" % "morphia" % "1.0.1",
  "org.mongodb" % "mongodb-driver-rx" % "1.2.0",
  "com.netflix.hystrix" % "hystrix-core" % "1.5.1"
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
