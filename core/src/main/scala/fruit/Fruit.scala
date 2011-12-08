package fruit

import java.awt.event.ActionEvent
import java.awt.event.ActionListener

import scala.util.continuations.reset
import scala.util.continuations.shift
import scala.util.continuations.suspendable

trait Fruit {

  type Signals = collection.mutable.HashMap[Int, Signal]
  private[this] val signals = new Signals()

  def signal(selectable: Selectable): String =
    throw new NoSuchMethodException("this code must be compiled with the Fruit plugin")

  def signal(selectable: Selectable, id: Int): String @suspendable = shift { k: (String => Unit) =>
    if (!signals.contains(id)) signals(id) = new Signal(selectable)
    signals(id)(k)
  }

  def fruit(f: => Unit @suspendable) = {
    reset(f)
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
 
