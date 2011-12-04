package fruit

import java.awt.Component
import java.awt.GridLayout

import javax.swing.JComboBox
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel

object FruitDemo extends App {

  import Fruit.monitor

  Display.display("Fruit Demo", simpleUi)

  def simpleUi = {
    val panel = new JPanel(new GridLayout(0, 1))

    val combo1 = new JComboBox(Array[AnyRef]("Fred", "Sam", "Joe"))
    val combo2 = new JComboBox(Array[AnyRef]("rockin'", "stinky", "ok"))
    val label1 = new JLabel()
    val label2 = new JLabel()
    val label3 = new JLabel()
    val label4 = new JLabel()

    monitor(combo1) { name => label1.setText(name()) }
    monitor(combo2) { desc => label2.setText(desc()) }
    monitor(combo1, combo2) { (name, desc) => label3.setText(name() + " is " + desc()) }
    monitor(combo1, combo2) { (name, desc) => label4.setText(name() + " ain't " + desc()) }

    panel.add(label1)
    panel.add(label2)
    panel.add(label3)
    panel.add(label4)
    panel.add(combo1)
    panel.add(combo2)

    panel
  }
}

object Display {
  def display(title: String, component: Component) {
    val frame = new JFrame(title)
    frame.add(component)
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    frame.pack()
    frame.setVisible(true)
  }
}
