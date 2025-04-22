package rimplex.gui;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import utilities.Complex;
import utilities.Evaluator;

/**
 * The observer of all GUI components of the RimpleX application.
 *
 * General structure taken from Dr. Bernstein's Serialization Lab:
 * (https://w3.cs.jmu.edu/bernstdh/web/common/labs/experience_serialization/tempz/index.php)
 *
 *  @author Joseph Pogoretskiy, Benjamin Bonnell, Kalani Johnson, John Paul, Sofia Miller
 *
 * This work complies with JMU Honor Code.
 */
public class RimpleXController implements ActionListener
{
  private static final String ADD = "+";
  private static final String SUBTRACTION = "—";
  private static final String MULTIPLICATION = "×";
  private static final String DIVISION = "÷";
  private static final String NEGATIVE = "-";
  private static final String POWER = "^";

  @SuppressWarnings("unused")
  private RimpleXWindow window;
  private JLabel topDisplay;
  private JLabel display;
  private boolean parenPresent;
  private boolean parenClosed = false;
  private boolean equalsPresent = false;
  private String fullExpression;
  private boolean runningCalc = true;
  private Complex result;
  private boolean polarFormEnabled = false;
  private Complex polarizedComplex;

  /**
   * Constructor for a RimpleXController.
   */
  public RimpleXController()
  {
    super();
  }

  @Override
  /**
   * Action handler when buttons are clicked.
   *
   * @param ae
   *          The ActionEvent that generated the message.
   */
  public void actionPerformed(final ActionEvent ae)
  {
    String ac = ae.getActionCommand();
    // General structure: if (ac.equals(NAME_OF_BUTTON)) { do stuff... }.
    // For testing if buttons are linked with actions.
    // System.out.println(ac + " was pressed.");
    if (display.getText().equals("Error"))
    {
      display.setText("");
      topDisplay.setText("");
    }
    switch (ac)
    {
      case "ONE":
        if (!checkDigitPlacement(display))
        {
          break;
        }
        if (equalsPresent)
        {
          topDisplay.setText("");
          equalsPresent = false;
        }
        display.setText(display.getText() + "1");
        break;
      case "TWO":
        if (!checkDigitPlacement(display))
        {
          break;
        }
        if (equalsPresent)
        {
          topDisplay.setText("");
          equalsPresent = false;
        }
        display.setText(display.getText() + "2");
        break;
      case "THREE":
        if (!checkDigitPlacement(display))
        {
          break;
        }
        if (equalsPresent)
        {
          topDisplay.setText("");
          equalsPresent = false;
        }
        display.setText(display.getText() + "3");
        break;
      case "FOUR":
        if (!checkDigitPlacement(display))
        {
          break;
        }
        if (equalsPresent)
        {
          topDisplay.setText("");
          equalsPresent = false;
        }
        display.setText(display.getText() + "4");
        break;
      case "FIVE":
        if (!checkDigitPlacement(display))
        {
          break;
        }
        if (equalsPresent)
        {
          topDisplay.setText("");
          equalsPresent = false;
        }
        display.setText(display.getText() + "5");
        break;
      case "SIX":
        if (!checkDigitPlacement(display))
        {
          break;
        }
        if (equalsPresent)
        {
          topDisplay.setText("");
          equalsPresent = false;
        }
        display.setText(display.getText() + "6");
        break;
      case "SEVEN":
        if (!checkDigitPlacement(display))
        {
          break;
        }
        if (equalsPresent)
        {
          topDisplay.setText("");
          equalsPresent = false;
        }
        display.setText(display.getText() + "7");
        break;
      case "EIGHT":
        if (!checkDigitPlacement(display))
        {
          break;
        }
        if (equalsPresent)
        {
          topDisplay.setText("");
          equalsPresent = false;
        }
        display.setText(display.getText() + "8");
        break;
      case "NINE":
        if (!checkDigitPlacement(display))
        {
          break;
        }
        if (equalsPresent)
        {
          topDisplay.setText("");
          equalsPresent = false;
        }
        display.setText(display.getText() + "9");
        break;
      case "ZERO":
        if (!checkDigitPlacement(display))
        {
          break;
        }
        if (equalsPresent)
        {
          topDisplay.setText("");
          equalsPresent = false;
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
          else if (parenClosed)
          {
            display.setText("(" + display.getText().substring(0, display.getText().length()));
            parenClosed = false;
          }
          else
          {
            display.setText(display.getText().substring(0, display.getText().length() - 1));
            if (display.getText().length() == 0 && parenPresent)
            {
              parenPresent = false;
            }
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
          // Remove the parentheses so as not to set the parser off.
          String displayText = new String(display.getText());
          displayText = displayText.replace("(", "").replace(")", "").replace("+", " ")
              .replace("-", " ").replace("×", " ").replace("÷", " ");
          String[] operands = displayText.split(" ");
          String lastOperand = operands[operands.length - 1];
          try
          {
            lastOperand += ".";
            Float.parseFloat(lastOperand);
            display.setText(display.getText() + ".");
          }
          catch (NumberFormatException nfe)
          {
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
          if (equalsPresent)
          {
            topDisplay.setText("");
            equalsPresent = false;
          }
          parenPresent = true;
        }
        break;
      case "CLOSED_PARENTHESIS":
        String dispText = display.getText();
        if (dispText.length() == 0)
        {
          break;
        }
        char lastVal = dispText.charAt(dispText.length() - 1);
        if (parenPresent && (Character.isDigit(lastVal) || !checkDigitPlacement(display)))
        {
          display.setText(display.getText().replace("(", ""));
          parenPresent = false;
          parenClosed = true;
        }
        break;
      case "CLEAR":
        display.setText("");
        break;
      case "RESET":
        topDisplay.setText("");
        display.setText("");
        equalsPresent = false;
        polarFormEnabled = false;
        polarizedComplex = null;
        parenPresent = false;
        parenClosed = false;
        break;
      case "SIGN":
        // This is a HYPHEN, not a DASH
        if (display.getText().charAt(0) == '-')
          display.setText(display.getText().substring(1));
        else
          display.setText("-" + display.getText());
        fullExpression = fullExpression.substring(0,
            fullExpression.length() - display.getText().length()) + "-1*("
            + fullExpression.substring(fullExpression.length() - display.getText().length()) + ")";
        break;
      case "ADD":
        setOperator(display, topDisplay, ADD);
        break;
      case "SUBTRACT":
        setOperator(display, topDisplay, SUBTRACTION);
        break;
      case "MULTIPLY":
        setOperator(display, topDisplay, MULTIPLICATION);
        break;
      case "DIVIDE":
        setOperator(display, topDisplay, DIVISION);
        break;
      case "ACTION_EXIT":
        System.exit(0);
        break;
      case "ACTION_HELP":
        File htmlFile = new File("help.html");
        try
        {
          Desktop.getDesktop().browse(htmlFile.toURI());
        }
        catch (IOException e)
        {
          System.out.println("HTML help file lost.");
        }
        catch (UnsupportedOperationException e)
        {
          // on linux this will likely happen.
          try
          {
            Runtime.getRuntime().exec(new String[] {"xdg-open", htmlFile.getAbsolutePath()});
          }
          catch (IOException e1)
          {
            System.out.println("HTML help file lost.");
          }
        }
        break;
      case "ACTION_ABOUT":
        try
        {
          RimpleXAboutWindow aboutWindow = new RimpleXAboutWindow();
          aboutWindow.setVisible(true);
        }
        catch (IOException e)
        {
          e.printStackTrace();
        }
        break;
      case "EQUALS":
        if (!parenPresent && checkOperatorPlacement(display))
        {
          String leftOperand;
          if (polarFormEnabled)
          {
            leftOperand = polarizedComplex.toString();
          }
          else
          {
            leftOperand = topDisplay.getText().substring(0, topDisplay.getText().length() - 1);
          }
          String operator = topDisplay.getText().substring(topDisplay.getText().length() - 1);
          String rightOperand = display.getText();

          // For debugging:
          // System.out.println("leftOperand: " + leftOperand);
          // System.out.println("operator: " + operator);
          // System.out.println("rightOperand: " + rightOperand);

          String evaluation = Evaluator.evaluate(leftOperand, operator, rightOperand);
          result = Complex.parse(evaluation);
          topDisplay.setText(leftOperand + operator + " " + rightOperand + " = " + evaluation);

          if (polarFormEnabled)
          {
            Complex evaluated = Complex.parse(evaluation);
            String polarForm = evaluated.getPolarForm();
            topDisplay.setText(polarizedComplex.getPolarForm() + " " + operator + " " + rightOperand + " = " + polarForm);
            polarizedComplex = evaluated;
          }
          else
          {
            topDisplay.setText(leftOperand + operator + " " + rightOperand + " = " + evaluation);
          }
          display.setText("");

          equalsPresent = true;
          parenClosed = false;
        }
        break;
      case "UNIT":
        if (display.getText().length() != 0 && Character.isDigit(lastChar()))
        {
          display.setText(display.getText() + "𝑖");
        }
        break;
      case "INVERT":
        if (parenClosed || (!parenPresent && !parenClosed && !display.getText().isEmpty()))
        {
          String evaluated = Evaluator.evaluate(display.getText(), "Invert", "");
          topDisplay.setText(display.getText() + " = " + evaluated);
          display.setText("");
          parenClosed = false;
          equalsPresent = true;
        }
        else if (equalsPresent)
        {
          Complex complexNum = Complex.parse(topDisplay.getText().substring(topDisplay.getText().indexOf("=") + 1));
          complexNum.inverse();
          topDisplay.setText(topDisplay.getText().substring(topDisplay.getText().indexOf("=") + 2) + " = " + complexNum.toString());
        }
        break;
      case "IMAGINARY_PART":
        if (parenClosed || (!parenPresent && !parenClosed && !display.getText().isEmpty()))
        {
          String evaluated = Evaluator.evaluate(display.getText(), "", "");
          Complex imaginary = new Complex(0.0, Complex.parse(evaluated).getImaginary());
          topDisplay.setText(display.getText() + " = " + imaginary.toString());
          display.setText("");
          parenClosed = false;
          equalsPresent = true;
        }
        else if (equalsPresent)
        {
          Complex complexNum = Complex.parse(topDisplay.getText().substring(topDisplay.getText().indexOf("=") + 1));
          Complex imaginary = new Complex(0.0, complexNum.getImaginary());
          topDisplay.setText(topDisplay.getText().substring(topDisplay.getText().indexOf("=") + 2) + " = " + imaginary.toString());
        }
        break;
      case "REAL_PART":
        if (parenClosed || (!parenPresent && !parenClosed && !display.getText().isEmpty()))
        {
          String evaluated = Evaluator.evaluate(display.getText(), "", "");
          Complex real = new Complex(Complex.parse(evaluated).getReal(), 0.0);
          topDisplay.setText(display.getText() + " = " + real.toString());
          display.setText("");
          parenClosed = false;
          equalsPresent = true;
        }
        else if (equalsPresent)
        {
          Complex complexNum = Complex.parse(topDisplay.getText().substring(topDisplay.getText().indexOf("=") + 1));
          Complex real = new Complex(complexNum.getReal(), 0.0);
          topDisplay.setText(topDisplay.getText().substring(topDisplay.getText().indexOf("=") + 2) + " = " + real.toString());
        }
        break;
      case "POLAR_FORM":
        if (parenClosed || (!parenPresent && !parenClosed && !display.getText().isEmpty()))
        {
          if (!polarFormEnabled)
          {
            String evaluated = Evaluator.evaluate(display.getText(), "", "");
            polarizedComplex = Complex.parse(evaluated);
            topDisplay.setText(display.getText() + " = " + polarizedComplex.getPolarForm());
            display.setText("");
            parenClosed = false;
            equalsPresent = true;
            polarFormEnabled = true;
          }
        }
        else if (equalsPresent)
        {
          if (!polarFormEnabled)
          {
            polarizedComplex = Complex.parse(topDisplay.getText().substring(topDisplay.getText().indexOf("=") + 1));
            String polarForm = polarizedComplex.getPolarForm();
            topDisplay.setText(topDisplay.getText().substring(topDisplay.getText().indexOf("=") + 2) + " = " + polarForm);
            polarFormEnabled = true;
          }
          else
          {
            topDisplay.setText(topDisplay.getText().substring(topDisplay.getText().indexOf("=") + 2) + " = " + polarizedComplex.toString());
            polarFormEnabled = false;
          }
        }
        break;
      case "CONJUGATE":
        if (parenClosed || (!parenPresent && !parenClosed && !display.getText().isEmpty()))
        {
          String evaluated = Evaluator.evaluate(display.getText(), "Conjugate", "");
          topDisplay.setText(display.getText() + " = " + evaluated);
          display.setText("");
          parenClosed = false;
          equalsPresent = true;
        }
        else if (equalsPresent)
        {
          Complex complexNum = Complex.parse(topDisplay.getText().substring(topDisplay.getText().indexOf("=") + 1));
          complexNum.conjugate();
          topDisplay.setText(topDisplay.getText().substring(topDisplay.getText().indexOf("=") + 2) + " = " + complexNum.toString());
        }
        break;
      case "SQUARE_ROOT":
        if (display.getText().length() == 0 && !runningCalc)
          break;
        display.setText("√(" + display.getText() + ")");
        break;
      case "EXPONENT":
        if (display.getText().length() == 0 && !runningCalc)
          break;
        setOperator(display, topDisplay, "^");
        fullExpression += "^";
        break;
      case "LOGARITHM":
        if (display.getText().length() == 0 && !runningCalc)
          break;
        display.setText("log(" + display.getText() + ")");
        break;
      default:
        break;
    }
    window.requestFocusInWindow();
  }

  public void setWindow(final RimpleXWindow window)
  {
    this.window = window;
  }

  public void setDisplays(final JLabel display, final JLabel topDisplay)
  {
    this.display = display;
    this.topDisplay = topDisplay;
  }

  private char lastChar()
  {
    String txt = display.getText();
    return txt.charAt(txt.length() - 1);
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
    String topDisplayValue = topDisplay.getText();
    if (equalsPresent)
    {
      topDisplay
          .setText(topDisplayValue.substring(topDisplayValue.indexOf("=") + 2) + " " + operator);
      equalsPresent = false;
    }
    else
    {
      if (!parenPresent)
      {
        if (!topDisplay.getText().isBlank())
        {
          String leftOperand = topDisplay.getText().replace(" ", "").substring(0,
              topDisplay.getText().length() - 2);
          String prevOperator = topDisplay.getText().substring(topDisplay.getText().length() - 1);
          String rightOperand = display.getText();

          String evaluation = Evaluator.evaluate(leftOperand, prevOperator, rightOperand);
          topDisplay.setText(evaluation + " " + operator);
        }
        else
        {
          topDisplay.setText(display.getText() + " " + operator);
        }
        display.setText("");
        parenClosed = false;
      }
      else
      {
        if (checkOperatorPlacement(display))
        {
          display.setText(display.getText() + operator);
        }
      }
    }
  }

  private boolean checkOperatorPlacement(final JLabel display)
  {
    char lastChar = display.getText().charAt(display.getText().length() - 1);
    if (lastChar == '+' || lastChar == '—' || lastChar == '^' || lastChar == '×' || lastChar == '÷'
        || lastChar == '÷')
    {
      return false;
    }
    return true;
  }

  public Complex getResult()
  {
    return result;
  }
}
