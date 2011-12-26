package com.earldouglas.fruit

import scala.tools.nsc.plugins.Plugin
import scala.tools.nsc.plugins.PluginComponent
import scala.tools.nsc.transform.Transform
import scala.tools.nsc.transform.TypingTransformers
import scala.tools.nsc.Global

// TODO: Fix this.  See here for help: http://suereth.blogspot.com/2009/02/levaraging-annotations-in-scala-part-2.html

class FruitPlugin(val global: Global) extends Plugin {

  val name = "fruit"
  val description = "fruit"
  val components = List[PluginComponent](FruitComponent)

  private object FruitComponent extends PluginComponent with Transform with TypingTransformers {

    val global = FruitPlugin.this.global

    import global._
    import definitions._

    lazy val Fruit = definitions.getClass("com.earldouglas.fruit.Fruit")
    lazy val MethSignal = definitions.getMember(Fruit, "signal")
    // lazy val MethSignalR = definitions.getMember(Fruit, "signalR")

    val runsAfter = List[String]("parser") // pickler
    override val runsBefore = List[String]("namer") // uncurry

    val phaseName = FruitPlugin.this.name

    private[this] var counter: Int = 0

    private[this] def next(): Int = synchronized {
      counter = counter + 1
      counter
    }

    def newTransformer(unit: CompilationUnit) = new FruitTransformer(unit)

    class FruitTransformer(unit: CompilationUnit) extends TypingTransformer(unit) {
      private[this] var counter = 0
      override def transform(tree: Tree): Tree = super.transform(tree match {
        // case a @ Apply(fun, args) if (fun.hasSymbol && fun.symbol == MethSignal) =>
        // val funR = gen.mkAttributedRef(MethSignalR)
        // treeCopy.Apply(tree, fun, args ::: List(Literal(Constant(next()))))
        case a @ Apply(fun, args) if (fun.toString() == "signal" && args.length == 1) =>
          // val funR = gen.mkAttributedRef(MethSignalR)
          Apply(fun, args ::: List(Literal(Constant(next()))))
        case t => t
      })
    }
  }
}
