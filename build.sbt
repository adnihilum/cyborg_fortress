name := "df_shameless_clone"

version := "0.1"

scalaVersion := "2.12.6"

mainClass := Some("Main")

// for cats library
libraryDependencies +=
  "org.typelevel" %% "cats-core" % "1.0.0"

scalacOptions ++= Seq(
  "-Xfatal-warnings",
  "-Ypartial-unification"
)

scalacOptions ++= Seq("-feature")

// for swing
libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "2.0.0-M2"

// tests
libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.5"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"
