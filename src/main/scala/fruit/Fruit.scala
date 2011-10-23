package fruit

import java.awt.event.ActionEvent
import java.awt.event.ActionListener

import scala.util.continuations.reset
import scala.util.continuations.shift
import scala.util.continuations.suspendable

object Fruit {

  type Signals = collection.mutable.HashMap[Selectable, Signal]

  def signal(selectable: Selectable)(implicit signals: Signals): String @suspendable = shift { k: (String => Unit) =>
    if (!signals.contains(selectable)) signals(selectable) = new Signal(selectable)
    signals(selectable)(k)
  }

  def fruit(f: Signals => Unit @suspendable) = {
    reset(f(new Signals()))
  }

  class Signal(selectable: Selectable) {

    private var c: List[String => Unit] = Nil

    selectable.addActionListener(new ActionListener() {
      def actionPerformed(e: ActionEvent) {
        c.foreach { k =>
          k(selectable.getSelectedItem.toString)
        }
      }
    })

    def apply(k: (String => Unit)) {
      c = c ::: k :: Nil
      k(selectable.getSelectedItem.toString)
    }
  }

  type Selectable = {
    def getSelectedItem(): AnyRef
    def addActionListener(a: ActionListener): Unit
  }
}

 