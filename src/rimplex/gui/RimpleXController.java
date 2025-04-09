package rimplex.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;

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
          setOperator(display, topDisplay, "+");
          break;
        }
      case "SUBTRACT":
        if (display.getText().length() == 0)
        {
          break;
        }
        // NOTE! This "subtraction" sign is a MINUS character. Not a hyphen.
        else
        {
          setOperator(display, topDisplay, "−");
          break;
        }
      case "MULTIPLY":
        if (display.getText().length() == 0)
        {
          break;
        }
        else
        {
          setOperator(display, topDisplay, "×");
          break;
        }
      case "DIVIDE":
        if (display.getText().length() == 0)
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
          String expression = display.getText().replace("×",  "*").replace("÷", "/");
          double result = evaluate(expression);
          display.setText(Double.toString(result));
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
   * @param expr The expression to evaluate.
   * @return The result as a double.
   */
  private double evaluate(String expr) throws Exception
  {
    return parseExpression(new StringTokenizer(expr, "+-*/()", true));
  }
  
  private double parseExpression(StringTokenizer tokens)
  {
    double value = parseTerm(tokens);
    while (tokens.hasMoreTokens())
    {
      String op = tokens.nextToken().trim();
      if (op.isEmpty()) continue;
      if (op.equals("+"))
      {
        value += parseTerm(tokens);
      }
      else if (op.equals("-"))
      {
        value -= parseTerm(tokens);
      }
      else
      {
        tokens = putBackToken(tokens, op);
        break;
      }
    }
    return value;
  }
  
  private double parseTerm(StringTokenizer tokens)
  {
    double value = parseFactor(tokens);
    while (tokens.hasMoreTokens())
    {
      String op = tokens.nextToken().trim();
      if (op.isEmpty()) continue;
      if (op.equals("*"))
      {
        value *= parseFactor(tokens);
      }
      else if (op.equals("/"))
      {
        value /= parseFactor(tokens);
      }
      else
      {
        tokens = putBackToken(tokens, op);
        break;
      }
    }
    return value;
  }

  private double parseFactor(StringTokenizer tokens)
  {
    if (!tokens.hasMoreTokens()) throw new IllegalArgumentException("Unexpected end of expression");
    String token = tokens.nextToken().trim();
    if (token.equals("("))
    {
      double value = parseExpression(tokens);
      if (!tokens.hasMoreTokens() || !tokens.nextToken().equals(")"))
      {
        throw new IllegalArgumentException("Missing closing parenthesis");
      }
      return value;
    }
    else
    {
      return Double.parseDouble(token);
    }
  }
  
  private StringTokenizer putBackToken(StringTokenizer oldTokens, String token)
  {
    StringBuilder remaining = new StringBuilder(token);
    while (oldTokens.hasMoreTokens())
    {
      remaining.append(oldTokens.nextToken());
    }
    return new StringTokenizer(remaining.toString(), "+-*/()", true);
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
    if ((lastChar() != '.' && lastChar() != '+' && lastChar() != '-' && lastChar() != '×'
        && lastChar() != '÷') && !parenPresent)
    {
      topDisplay.setText(display.getText() + operator);
      display.setText("");
      return;
    }
    else
    {
      if (!display.getText().contains("+") && !display.getText().contains("-")
          && !display.getText().contains("×") && !display.getText().contains("÷"))
      {
        display.setText(display.getText() + operator);
        return;
      }
      return;
    }
  }
}
