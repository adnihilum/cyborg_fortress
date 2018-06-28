// factor out common settings into a sequence
lazy val commonSettings = Seq(
  organization := "org.example",
  version := "0.0.0",
  // set the Scala version used for the project
  scalaVersion := "2.12.2"
)




lazy val root = (project in file("."))
  .settings(
    commonSettings,

    // set the name of the project
    name := "My Project",

    libraryDependencies += 
      
  )
