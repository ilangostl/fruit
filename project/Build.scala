import sbt._
import sbt.Defaults.{ defaultSettings => default }
import Keys._

object ProjectBuild extends Build {

  lazy val buildSettings = default ++ Seq(
    organization := "com.earldouglas",
    version := "0.1-SNAPSHOT",
    scalaVersion := "2.9.1",
    autoCompilerPlugins := true,
    addCompilerPlugin("org.scala-lang.plugins" % "continuations" % "2.9.1"),
    scalacOptions += "-P:continuations:enable")

  lazy val core = Project(
    id = "fruit-core",
    base = file("core"),
    settings = buildSettings)

  lazy val demos = Project(
    id = "fruit-demos",
    base = file("demos"),
    dependencies = Seq(core),
    settings = buildSettings ++ Seq(
      scalacOptions += ("-Xplugin:./plugin/target/scala-2.9.1/fruit-plugin_2.9.1-0.1-SNAPSHOT.jar")))

  lazy val plugin = Project(
    id = "fruit-plugin",
    base = file("plugin"),
    settings = buildSettings ++ Seq(
      libraryDependencies += "org.scala-lang" % "scala-compiler" % "2.9.1"))
}

