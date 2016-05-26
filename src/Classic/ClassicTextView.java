package Classic;
/*
 * First and Last name: Kal Cramer and David Aaron
 * Assignment name: Hunt the Wumpus: Iteration 2
 * Date due:9/19/14
 */
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JTextArea;



public class ClassicTextView extends JPanel implements Observer
{
  private JTextArea textArea;

  public ClassicTextView()
  {
    textArea = new JTextArea();
    setLayout(null);
    textArea.setSize(500, 500);
    textArea.setFont(new Font("monospaced", Font.BOLD, 18));
    add(textArea);
    textArea.setEditable(false);

    // this.buttons = buttons;

  }

  public void update(Observable arg0, Object arg1)
  {
    ClassicMap theModel = (ClassicMap) arg0;
    textArea.setText(theModel.printGame());

  }

}
