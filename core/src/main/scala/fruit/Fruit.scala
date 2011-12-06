package fruit

import java.awt.event.ActionEvent
import java.awt.event.ActionListener

import scala.util.continuations.reset
import scala.util.continuations.shift
import scala.util.continuations.suspendable

object Fruit {

  type Signals = collection.mutable.HashMap[Int, Signal]

  def signal(selectable: Selectable): String = ""

  def signal(selectable: Selectable, id: Int)(implicit signals: Signals): String @suspendable = shift { k: (String => Unit) =>
    if (!signals.contains(id)) signals(id) = new Signal(selectable)
    signals(id)(k)
  }

  def fruit(f: Signals => Unit @suspendable) = {
    reset(f(new Signals()))
  }

  class Signal(selectable: Selectable) {

    private var c: Option[String => Unit] = None

    selectable.addActionListener(new ActionListener() {
      def actionPerformed(e: ActionEvent) {
        c.foreach { k => k(selectable.getSelectedItem.toString) }
      }
    })

    def apply(k: (String => Unit)) {
      c = Option(k)
      k(selectable.getSelectedItem.toString)
    }
  }

  type Selectable = {
    def getSelectedItem(): AnyRef
    def addActionListener(a: ActionListener): Unit
  }
}
 