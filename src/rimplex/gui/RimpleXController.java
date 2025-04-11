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
  private boolean parenPresent;
  private boolean equalsPresent;
  private Complex previousResult;
  private int pos;
  private String input;

  /**
   * Constructor for a RimpleXController.
   */
  public RimpleXController()
  {
    super();
    equalsPresent = false;
    previousResult = new Complex(0, 0);
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
        if (!checkDigitPlacement(display))
        {
          break;
        }
        display.setText(display.getText() + "1");
        break;
      case "TWO":
        if (!checkDigitPlacement(display))
        {
          break;
        }
        display.setText(display.getText() + "2");
        break;
      case "THREE":
        if (!checkDigitPlacement(display))
        {
          break;
        }
        display.setText(display.getText() + "3");
        break;
      case "FOUR":
        if (!checkDigitPlacement(display))
        {
          break;
        }
        display.setText(display.getText() + "4");
        break;
      case "FIVE":
        if (!checkDigitPlacement(display))
        {
          break;
        }
        display.setText(display.getText() + "5");
        break;
      case "SIX":
        if (!checkDigitPlacement(display))
        {
          break;
        }
        display.setText(display.getText() + "6");
        break;
      case "SEVEN":
        if (!checkDigitPlacement(display))
        {
          break;
        }
        display.setText(display.getText() + "7");
        break;
      case "EIGHT":
        if (!checkDigitPlacement(display))
        {
          break;
        }
        display.setText(display.getText() + "8");
        break;
      case "NINE":
        if (!checkDigitPlacement(display))
        {
          break;
        }
        display.setText(display.getText() + "9");
        break;
      case "ZERO":
        if (!checkDigitPlacement(display))
        {
          break;
        }
        display.setText(display.getText() + "0");
        break;
      case "BACKSPACE":
        String text = display.getText();
        if (!text.isEmpty())
        {
          if (text.endsWith("𝑖"))
          {
            display.setText(text.substring(0, text.length() - 1));
          }
          else
          {
            display.setText(text.substring(0, text.length() - 1));
          }
        }
        else if (!topDisplay.getText().isEmpty())
        {
          display.setText(topDisplay.getText());
          topDisplay.setText("");
        }
        break;
      // if (display.getText().length() != 0)
      // {
      // if (display.getText().endsWith("\uD835\uDC56"))
      // {
      // display.setText(display.getText().substring(0, display.getText().length() - 2));
      // }
      // else
      // {
      // display.setText(display.getText().substring(0, display.getText().length() - 1));
      // }
      // }
      // else
      // {
      // if (topDisplay.getText().length() != 0)
      // {
      // display.setText(topDisplay.getText());
      // topDisplay.setText("");
      // }
      // }
      // break;
      case "DECIMAL":
        // This is a temporary solution since this won't work when operators are in the current
        // expression.
        boolean canPlace = !(display.getText().length() == 0)
            && Character.isDigit(display.getText().charAt(display.getText().length() - 1));
        if (canPlace)
        {
          // Remove the parentheses to not set the parser off.
          String displayText = new String(display.getText());
          displayText = displayText.replace("(", "");
          displayText = displayText.replace(")", "");
          displayText = displayText.replace("+", " ");
          displayText = displayText.replace("-", " ");
          displayText = displayText.replace("×", " ");
          displayText = displayText.replace("÷", " ");
          String[] operands = displayText.split(" ");
          // For debugging
          // for (String string : operands)
          // {
          // System.out.print(string + ", ");
          // }
          // System.out.println();
          String lastOperand = operands[operands.length - 1];
          // For debugging
          // System.out.println("lastOperand = " + lastOperand);
          try
          {
            lastOperand += ".";
            Float.parseFloat(lastOperand);
            display.setText(display.getText() + ".");
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
        if (display.getText().length() == 0)
        {
          display.setText(display.getText() + "(");
          parenPresent = true;
          break;
        }
      case "CLOSED_PARENTHESIS":
        String displayText = display.getText();
        if (displayText.length() == 0)
        {
          break;
        }
        char lastVal = displayText.charAt(displayText.length() - 1);
        if (parenPresent && (Character.isDigit(lastVal) || !checkDigitPlacement(display)))
        {
          display.setText(display.getText().replace("(", ""));
          parenPresent = false;
          break;
        }
        else
        {
          break;
        }
      case "CLEAR":
        display.setText("");
        break;
      case "RESET":
        topDisplay.setText("");
        display.setText("");
        equalsPresent = false;
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
        if (display.getText().length() == 0 && !equalsPresent)
        {
          break;
        }
        else
        {
          setOperator(display, topDisplay, "+");
          break;
        }
      case "SUBTRACT":
        if (display.getText().length() == 0 && !equalsPresent)
        {
          break;
        }
        // NOTE! This "subtraction" sign is a MINUS character. Not a hyphen.
        else
        {
          setOperator(display, topDisplay, "-");
          break;
        }
      case "MULTIPLY":
        if (display.getText().length() == 0 && !equalsPresent)
        {
          break;
        }
        else
        {
          setOperator(display, topDisplay, "×");
          break;
        }
      case "DIVIDE":
        if (display.getText().length() == 0 && !equalsPresent)
        {
          break;
        }
        else
        {
          setOperator(display, topDisplay, "÷");
          break;
        }
      case "ACTION_EXIT":
        System.exit(0);
        break;
      case "EQUALS":
        try
        {
          String expression;
          if (equalsPresent)
          {
            expression = previousResult.toString() + display.getText();
          }
          else
          {
            expression = topDisplay.getText() + display.getText();
          }
          String displayExpression = expression.replace("/", "÷").replace("*", "×").replace("i",
              "𝑖");
          expression = expression.replace("×", "*").replace("÷", "/").replace("𝑖", "i");
          Complex result = evaluate(expression);
          topDisplay.setText(displayExpression + " = " + result);
          display.setText("");
          previousResult = result;
          equalsPresent = true;
        }
        catch (Exception e)
        {
          display.setText("Error");
        }
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

  /**
   * Evaluates the expression given as a string.
   *
   * @param expr
   *          The expression to evaluate.
   * @return The result as a double.
   */
  private Complex evaluate(String expr) throws Exception
  {
    this.input = expr.replaceAll("\\s+", "");
    this.pos = 0;
    return parseExpression();
  }

  /**
   * A method that handles addition and subtraction.
   * 
   * @return The result of an expression that has addition and subtraction handled.
   */
  private Complex parseExpression()
  {
    Complex result = parseTerm();
    while (pos < input.length())
    {
      char op = input.charAt(pos);
      if (op == '+' || op == '-')
      {
        pos++;
        Complex right = parseTerm();
        if (op == '+')
        {
          result = result.add(right);
        }
        else
        {
          result = result.subtract(right);
        }
      }
      else
      {
        break;
      }
    }
    return result;
  }

  /**
   * A method that handles multiplication and subtraction.
   * 
   * @return An expression that has multiplication and subtraction handled.
   */
  private Complex parseTerm()
  {
    Complex result = parseFactor();
    while (pos < input.length())
    {
      char op = input.charAt(pos);
      if (op == '*' || op == '/')
      {
        pos++;
        Complex right = parseFactor();
        if (op == '*')
        {
          result = result.multiply(right);
        }
        else
        {
          result = result.divide(right);
        }
      }
      else
      {
        break;
      }
    }
    return result;
  }

  /**
   * A method that handles numbers, complex numbers, and parentheses.
   * 
   * @return An expression's result that handled all digits, imaginary numbers, and parentheses.
   */
  private Complex parseFactor()
  {
    if (pos >= input.length())
      return new Complex(0, 0);
    char ch = input.charAt(pos);
    if (ch == '(')
    {
      pos++;
      Complex inner = parseExpression();
      if (pos < input.length() && input.charAt(pos) == ')')
      {
        pos++;
      }
      return inner;
    }
    StringBuilder sb = new StringBuilder();
    boolean hasDecimal = false;
    if (ch == '+' || ch == '-')
    {
      sb.append(ch);
      pos++;
    }
    while (pos < input.length())
    {
      ch = input.charAt(pos);
      if (Character.isDigit(ch))
      {
        sb.append(ch);
        pos++;
      }
      else if (ch == '.' && !hasDecimal)
      {
        sb.append(ch);
        hasDecimal = true;
        pos++;
      }
      else
      {
        break;
      }
    }
    double value = sb.length() > 0 ? Double.parseDouble(sb.toString()) : 0.0;
    if (pos < input.length() && input.charAt(pos) == 'i')
    {
      pos++;
      return new Complex(0, value == 0.0 ? 1.0 : value);
    }
    else
    {
      return new Complex(value, 0);
    }
  }

  /**
   * Check if a digit can be placed based on display length and presence of imaginary unit.
   *
   * @param display
   *          The display to extract the text from.
   * @return True if digit can be placed.
   */
  private boolean checkDigitPlacement(final JLabel display)
  {
    if (display.getText().length() != 0)
    {
      if (display.getText().endsWith("\uD835\uDC56"))
      {
        return false;
      }
    }
    return true;
  }

  /**
   * Set the operator onto the display depending on presence of parentheses and other operators
   * within the current expression.
   *
   * @param display
   *          The display that holds the current operand.
   * @param topDisplay
   *          The display that holds the left operand.
   * @param operator
   *          The operator to place.
   */
  private void setOperator(final JLabel display, final JLabel topDisplay, final String operator)
  {
    if (equalsPresent)
    {
      if (display.getText().length() == 0) // Combining previous equation with new one.
      {
        display.setText(operator);
        return;
      }
      else
      {
        if (display.getText().contains("+") || display.getText().contains("-")
            || display.getText().contains("×") || display.getText().contains("÷"))
        { // Allows for multiple operators when using a previous equation.
          topDisplay.setText(previousResult + display.getText() + operator);
          display.setText("");
          equalsPresent = false;
          return;
        }
        if ((lastChar() != '.' && lastChar() != '+' && lastChar() != '-' && lastChar() != '×'
            && lastChar() != '÷') && !parenPresent) // Creating new equation.
        {
          topDisplay.setText(display.getText() + operator);
          display.setText("");
          equalsPresent = false;
          return;
        }
      }
    }
    else
    {
      if ((lastChar() != '.' && lastChar() != '+' && lastChar() != '-' && lastChar() != '×'
          && lastChar() != '÷') && !parenPresent)
      {
        topDisplay.setText(topDisplay.getText() + display.getText() + operator);
        display.setText("");
        return;
      }
      else
      {
        if (!display.getText().contains("+") && !display.getText().contains("-")
            && !display.getText().contains("×") && !display.getText().contains("÷"))
        {
          topDisplay.setText(display.getText() + operator);
          display.setText("");
          return;
        }
      }
    }
  }
}
