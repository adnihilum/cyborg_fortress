name := "df_shameless_clone"

version := "0.1"

scalaVersion := "2.12.6"

Compile/mainClass := Some("main.Main")

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


//lwjgl
libraryDependencies ++= {
  val version = "3.1.6"

  Seq(
    "lwjgl",
    "lwjgl-glfw",
    "lwjgl-opengl"
    //Add more modules here
  ).flatMap {
    module => {
      Seq(
        "org.lwjgl" % module % version,
        "org.lwjgl" % module % version classifier "natives-linux" //Change if linux/mac
      )
    }
  }
}
