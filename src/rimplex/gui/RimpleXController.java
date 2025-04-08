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
    //    System.out.println(ac + " was pressed.");
    switch (ac)
    {
      case "ONE":
        display.setText(display.getText() + "1");
        break;
      case "TWO":
        display.setText(display.getText() + "2");
        break;
      case "THREE":
        display.setText(display.getText() + "3");
        break;
      case "FOUR":
        display.setText(display.getText() + "4");
        break;
      case "FIVE":
        display.setText(display.getText() + "5");
        break;
      case "SIX":
        display.setText(display.getText() + "6");
        break;
      case "SEVEN":
        display.setText(display.getText() + "7");
        break;
      case "EIGHT":
        display.setText(display.getText() + "8");
        break;
      case "NINE":
        display.setText(display.getText() + "9");
        break;
      case "ZERO":
        display.setText(display.getText() + "0");
        break;
      case "BACKSPACE":
        if (display.getText().length() != 0)
        {
          display.setText(display.getText().substring(0, display.getText().length() - 1));
        }
        break;
      case "DECIMAL":
        // This is a temporary solution since this won't work when operators are in the current
        // expression.
        
        /* Fixed decimal issue by checking to make sure the
        last character in the string is a digit. - John */
        boolean canPlace = !(display.getText().length() == 0)
            && Character.isDigit(display.getText().charAt(display.getText().length() - 1));
        if (canPlace) 
        {
          display.setText(display.getText() + ".");
          break;
        } else
        {
          break;
        }
        /* String currentExpression = display.getText();
        currentExpression += ".";
        try {
          Float.parseFloat(currentExpression);
          display.setText(currentExpression);
        }
        catch (NumberFormatException nfe) {
          System.out.println("Invalid decimal placement.");
        } */
      case "OPEN_PARENTHESIS":
        if (lastChar() == '.')
        {
          break;
        } else
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
        } else 
        {
          display.setText(display.getText() + ")");
          parenCount--;
          break;
        }
      case "CLEAR":
        display.setText("");
    	  break;
      case "RESET":
        display.setText("");    	  
        break;
      case "SIGN":
    	  if (display.getText().charAt(0) == '-') {
    		  display.setText(display.getText().substring(1));
    	  }
    	  else { 
    		  display.setText("-" + display.getText());
    	  }
    	  break;
      case "ADD":
          if (lastChar() == '.' || lastChar() == '+' || lastChar() == '-' 
          	|| lastChar() == '×' || lastChar() == '÷')
          {
            break;
          } else
          {
            display.setText(display.getText() + "+");
            break;
          }
      case "SUBTRACT":
          if (lastChar() == '.' || lastChar() == '+' || lastChar() == '-' 
          	|| lastChar() == '×' || lastChar() == '÷')
          {
            break;
          } else
          {
            display.setText(display.getText() + "-");
            break;
          }
      case "MULTIPLY":
          if (lastChar() == '.' || lastChar() == '+' || lastChar() == '-' 
          	|| lastChar() == '×' || lastChar() == '÷')
          {
            break;
          } else
          {
            display.setText(display.getText() + "×");
            break;
          }
      case "DIVIDE":
          if (lastChar() == '.' || lastChar() == '+' || lastChar() == '-' 
          	|| lastChar() == '×' || lastChar() == '÷')
          {
            break;
          } else
          {
            display.setText(display.getText() + "÷");
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
   *          The window
   */
  public void setDisplay(final JLabel display)
  {
    this.display = display;
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
}
