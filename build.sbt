name := """ComputerStore"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  evolutions,
  cache,
  javaWs,
  "com.loicdescotte.coffeebean" %% "html5tags" % "1.2.2",
  "org.mindrot" % "jbcrypt" % "0.3m"
)

resolvers += Resolver.url("github repo for html5tags", url("http://loicdescotte.github.io/Play2-HTML5Tags/releases/"))(Resolver.ivyStylePatterns)
