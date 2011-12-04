package fruit

import java.awt.event.ActionEvent
import java.awt.event.ActionListener

import scala.util.continuations.reset
import scala.util.continuations.shift
import scala.util.continuations.suspendable

object Fruit {

  def monitor(a: Selectable)(f: Signal => Unit @suspendable): Unit = reset(f(new Signal(a)))
  def monitor(a: Selectable, b: Selectable)(f: (Signal, Signal) => Unit @suspendable): Unit = reset(f(new Signal(a), new Signal(b)))

  class Signal(selectable: Selectable) {

    private var c: Option[String => Unit] = None

    selectable.addActionListener(new ActionListener() {
      def actionPerformed(e: ActionEvent) {
        c.foreach { k => k(selectable.getSelectedItem.toString) }
      }
    })

    def apply(): String @suspendable = shift { k: (String => Unit) =>
      c = Option(k)
      k(selectable.getSelectedItem.toString)
    }
  }

  type Selectable = {
    def getSelectedItem(): AnyRef
    def addActionListener(a: ActionListener): Unit
  }
}

 