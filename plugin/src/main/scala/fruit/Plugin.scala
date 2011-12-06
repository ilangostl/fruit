package fruit

import scala.tools.nsc.plugins.Plugin
import scala.tools.nsc.plugins.PluginComponent
import scala.tools.nsc.transform.Transform
import scala.tools.nsc.transform.TypingTransformers
import scala.tools.nsc.Global

class FruitPlugin(val global: Global) extends Plugin {

  val name = "fruit"
  val description = "fruit"
  val components = List[PluginComponent](FruitComponent)

  private object FruitComponent extends PluginComponent with Transform with TypingTransformers {

    val global: FruitPlugin.this.global.type = FruitPlugin.this.global
    import global._

    val runsAfter = List[String]("parser")
    override val runsBefore = List[String]("namer")
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
        case a @ Apply(fun, args) =>
          if (fun.toString() == "signal" && args.length == 1) Apply(fun, args ::: List(Literal(Constant(next()))))
          else a
        case t => t
      })
    }
  }
}
