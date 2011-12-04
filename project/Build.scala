import sbt._
import sbt.Defaults.{defaultSettings => default}
import Keys._

object ProjectBuild extends Build {

  val cps = Seq(autoCompilerPlugins := true,
              addCompilerPlugin("org.scala-lang.plugins" % "continuations" % "2.9.1"),
              scalacOptions += "-P:continuations:enable")

  lazy val core  = Project(
    id = "core",
    base = file("core"),
    settings = default ++ cps
  )

  lazy val demos = Project(
    id = "demos",
    base = file("demos"),
    dependencies = Seq(core),
    settings = default ++ cps
  )

  lazy val plugin = Project(
    id = "plugin",
    base = file("plugin"),
    settings = default ++ cps ++ Seq(
      libraryDependencies += "org.scala-lang" % "scala-compiler" % "2.9.1"
    )
  )
}

