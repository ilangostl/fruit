name := "Functional Reactive UI Thing"

version := "0.1"

organization := "com.earldouglas"

scalaVersion := "2.9.1"

autoCompilerPlugins := true

addCompilerPlugin("org.scala-lang.plugins" % "continuations" % "2.9.1")

scalacOptions += "-P:continuations:enable"

