package fruit;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JavaDemo {

  private final JPanel panel = new JPanel(new GridLayout(0, 1));

  private final JComboBox combo1 = new JComboBox(new String[] {"Fred", "Sam", "Joe"});
  private final JComboBox combo2 = new JComboBox(new String[] {"rockin'", "stinky", "ok"});
  private final JLabel label1 = new JLabel();
  private final JLabel label2 = new JLabel();
  private final JLabel label3 = new JLabel();
  private final JLabel label4 = new JLabel();

  private String label3Prefix = "";
  private String label3Suffix = "";

  private String updateLabel3Prefix(String value) {
    label3Prefix = value;
    return label3Text();
  }

  private String updateLabel3Suffix(String value) {
    label3Suffix = value;
    return label3Text();
  }

  private String label3Text() {
    return label3Prefix + " is " + label3Suffix;
  }

  private String label4Prefix = "";
  private String label4Suffix = "";

  private String updateLabel4Prefix(String value) {
    label4Prefix = value;
    return label4Text();
  }

  private String updateLabel4Suffix(String value) {
    label4Suffix = value;
    return label4Text();
  }

  private String label4Text() {
    return label4Prefix + " ain't " + label4Suffix;
  }

  private void combo1Updated() {
    String value = combo1.getSelectedItem().toString();
    label1.setText(value);
    label3.setText(updateLabel3Prefix(value));
    label4.setText(updateLabel4Prefix(value));
  }

  private void combo2Updated() {
    String value = combo2.getSelectedItem().toString();
    label2.setText(value);
    label3.setText(updateLabel3Suffix(value));
    label4.setText(updateLabel4Suffix(value));
  }

  public JavaDemo() {
    combo1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        combo1Updated();
      }
    });
    combo1Updated();

    combo2.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        combo2Updated();
      }
    });
    combo2Updated();

    panel.add(label1);
    panel.add(label2);
    panel.add(label3);
    panel.add(label4);
    panel.add(combo1);
    panel.add(combo2);
  }

  public static void main(String[] args) {
    JFrame frame = new JFrame("JFruit Demo");
    frame.add(new JavaDemo().panel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
  }

}
