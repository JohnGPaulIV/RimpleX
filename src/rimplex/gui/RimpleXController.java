package rimplex.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * The observer of all GUI components of the RimpleX application.
 * 
 * General structure taken from Dr. Bernstein's Serialization Lab:
 * (https://w3.cs.jmu.edu/bernstdh/web/common/labs/experience_serialization/tempz/index.php)
 * 
 * This work complies with JMU Honor Code.
 */
public class RimpleXController implements ActionListener
{
  private RimpleXWindow window;
  private JLabel        display;

  public RimpleXController()
  {
    super();
  }

  @Override
  /**
   * Action handler when buttons or clicked.
   * 
   * @param ae
   *          The ActionEvent that generated the message.
   */
  public void actionPerformed(ActionEvent ae)
  {
    String ac = ae.getActionCommand();

    // General structure of handling actions:
    // if (ac.equals(NAME_OF_BUTTON)) { do stuff...}.

    // For testing if buttons are linked with actions.
    //    System.out.println(ac + " was pressed.");
    switch (ac)
    {
      case "ONE":
      {
        display.setText(display.getText() + "1");
        break;
      }
      case "TWO":
      {
        display.setText(display.getText() + "2");
        break;
      }
      case "THREE":
      {
        display.setText(display.getText() + "3");
        break;
      }
      case "FOUR":
      {
        display.setText(display.getText() + "4");
        break;
      }
      case "FIVE":
      {
        display.setText(display.getText() + "5");
        break;
      }
      case "SIX":
      {
        display.setText(display.getText() + "6");
        break;
      }
      case "SEVEN":
      {
        display.setText(display.getText() + "7");
        break;
      }
      case "EIGHT":
      {
        display.setText(display.getText() + "8");
        break;
      }
      case "NINE":
      {
        display.setText(display.getText() + "9");
        break;
      }
      case "ZERO":
      {
        display.setText(display.getText() + "0");
        break;
      }
      case "BACKSPACE":
        if (display.getText().length() != 0)
        {
          display.setText(display.getText().substring(0, display.getText().length() - 1));
        }
    }
  }

  /**
   * Set the RimpleXWindow that this object is controlling.
   * 
   * @param window
   *          The window
   */
  public void setWindow(RimpleXWindow window)
  {
    this.window = window;
  }
  
  /**
   * Set the display that this object is controlling.
   * 
   * @param window
   *          The window
   */
  public void setDisplay(JLabel display)
  {
    this.display = display;
  }
}
