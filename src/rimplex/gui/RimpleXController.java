package rimplex.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

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
  @SuppressWarnings("unused")
  private RimpleXWindow window;
  private JLabel topDisplay;
  private JLabel display;
  private int parenCount;

  /**
   * Constructor for a RimpleXController.
   */
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
  public void actionPerformed(final ActionEvent ae)
  {
    String ac = ae.getActionCommand();

    // General structure of handling actions:
    // if (ac.equals(NAME_OF_BUTTON)) { do stuff...}.

    // For testing if buttons are linked with actions.
    // System.out.println(ac + " was pressed.");
    switch (ac)
    {
      case "ONE":
        if (display.getText().length() != 0)
        {
          if (display.getText().endsWith("\uD835\uDC56"))
          {
            break;
          }
        }
        display.setText(display.getText() + "1");
        break;
      case "TWO":
        if (display.getText().length() != 0)
        {
          if (display.getText().endsWith("\uD835\uDC56"))
          {
            break;
          }
        }
        display.setText(display.getText() + "2");
        break;
      case "THREE":
        if (display.getText().length() != 0)
        {
          if (display.getText().endsWith("\uD835\uDC56"))
          {
            break;
          }
        }
        display.setText(display.getText() + "3");
        break;
      case "FOUR":
        if (display.getText().length() != 0)
        {
          if (display.getText().endsWith("\uD835\uDC56"))
          {
            break;
          }
        }
        display.setText(display.getText() + "4");
        break;
      case "FIVE":
        if (display.getText().length() != 0)
        {
          if (display.getText().endsWith("\uD835\uDC56"))
          {
            break;
          }
        }
        display.setText(display.getText() + "5");
        break;
      case "SIX":
        if (display.getText().length() != 0)
        {
          if (display.getText().endsWith("\uD835\uDC56"))
          {
            break;
          }
        }
        display.setText(display.getText() + "6");
        break;
      case "SEVEN":
        if (display.getText().length() != 0)
        {
          if (display.getText().endsWith("\uD835\uDC56"))
          {
            break;
          }
        }
        display.setText(display.getText() + "7");
        break;
      case "EIGHT":
        if (display.getText().length() != 0)
        {
          if (display.getText().endsWith("\uD835\uDC56"))
          {
            break;
          }
        }
        display.setText(display.getText() + "8");
        break;
      case "NINE":
        if (display.getText().length() != 0)
        {
          if (display.getText().endsWith("\uD835\uDC56"))
          {
            break;
          }
        }
        display.setText(display.getText() + "9");
        break;
      case "ZERO":
        if (display.getText().length() != 0)
        {
          if (display.getText().endsWith("\uD835\uDC56"))
          {
            break;
          }
        }
        display.setText(display.getText() + "0");
        break;
      case "BACKSPACE":
        if (display.getText().length() != 0)
        {
          if (display.getText().endsWith("\uD835\uDC56"))
          {
            display.setText(display.getText().substring(0, display.getText().length() - 2));
          }
          else
          {
          display.setText(display.getText().substring(0, display.getText().length() - 1));
          }
        }
        else
        {
          if (topDisplay.getText().length() != 0)
          {
            display.setText(topDisplay.getText());
            topDisplay.setText("");
          }
        }
        break;
      case "DECIMAL":
        // This is a temporary solution since this won't work when operators are in the current
        // expression.
        boolean canPlace = !(display.getText().length() == 0)
            && Character.isDigit(display.getText().charAt(display.getText().length() - 1));
        if (canPlace)
        {
          // Remove the parentheses to not set the parser off.
          String str = display.getText().replace("(", "");
          str.replace(")", "");
          String[] operands = str.split(" ");

          // For debugging
          for (String string : operands)
          {
            System.out.print(string + ", ");
          }
          System.out.println();

          String lastOperand = operands[operands.length - 1];
          try
          {
            lastOperand += ".";
            Float.parseFloat(lastOperand);
            display.setText(lastOperand);
          }
          catch (NumberFormatException nfe)
          {
            System.out.println("Invalid decimal placement.");
          }
          break;
        }
        else
        {
          break;
        }
      case "OPEN_PARENTHESIS":
        if (display.getText().length() == 0 || lastChar() != '.')
        {
          display.setText(display.getText() + "(");
          parenCount++;
          break;
        }
      case "CLOSED_PARENTHESIS":
        String displayText = display.getText();
        char lastVal = displayText.charAt(displayText.length() - 1);
        if (parenCount == 0 || !Character.isDigit(lastVal))
        {
          break;
        }
        else
        {
          display.setText(display.getText() + ")");
          parenCount--;
          break;
        }
      case "CLEAR":
        display.setText("");
        break;
      case "RESET":
        topDisplay.setText("");
        display.setText("");
        break;
      case "SIGN":
        if (display.getText().charAt(0) == '-')
        {
          display.setText(display.getText().substring(1));
        }
        else
        {
          display.setText("-" + display.getText());
        }
        break;
      case "ADD":
        if (display.getText().length() == 0)
        {
          break;
        }
        else
        {
          if (lastChar() != '.' && lastChar() != '+' && lastChar() != '-' && lastChar() != '×'
              && lastChar() != '÷')
          {
            topDisplay.setText(display.getText() + "+");
            display.setText("");
            break;
          }
        }
      case "SUBTRACT":
        if (display.getText().length() == 0)
        {
          break;
        }
        else
        {
          if (lastChar() != '.' && lastChar() != '+' && lastChar() != '-' && lastChar() != '×'
              && lastChar() != '÷')
          {
            topDisplay.setText(display.getText() + "-");
            display.setText("");
            break;
          }
        }
      case "MULTIPLY":
        if (display.getText().length() == 0)
        {
          break;
        }
        else
        {
          if (lastChar() != '.' && lastChar() != '+' && lastChar() != '-' && lastChar() != '×'
              && lastChar() != '÷')
          {
            topDisplay.setText(display.getText() + "×");
            display.setText("");
            break;
          }
        }
      case "DIVIDE":
        if (display.getText().length() == 0)
        {
          break;
        }
        else
        {
          if (lastChar() != '.' && lastChar() != '+' && lastChar() != '-' && lastChar() != '×'
              && lastChar() != '÷')
          {
            topDisplay.setText(display.getText() + "÷");
            display.setText("");
            break;
          }
        }
      case "ACTION_EXIT":
        System.exit(0);
        break;
      case "UNIT":
        if (display.getText().length() != 0 && Character.isDigit(lastChar()))
        {
          display.setText(display.getText() + "𝑖");
        }
        break;
      default:
        break;
    }
  }

  /**
   * Set the RimpleXWindow that this object is controlling.
   * 
   * @param window
   *          The window
   */
  public void setWindow(final RimpleXWindow window)
  {
    this.window = window;
  }

  /**
   * Set the display that this object is controlling.
   * 
   * @param display
   *          The display with the current expression.
   * @param topDisplay
   *          The display with the left operand.
   */
  public void setDisplays(final JLabel display, final JLabel topDisplay)
  {
    this.display = display;
    this.topDisplay = topDisplay;
  }

  /**
   * Gets the last character in the display window.
   * 
   * @return The last character.
   */
  private char lastChar()
  {
    String txt = display.getText();
    return txt.charAt(txt.length() - 1);
  }

}
