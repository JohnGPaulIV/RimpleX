package rimplex.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import utilities.Complex;

/**
 * The observer of all GUI components of the RimpleX application.
 *
 * General structure taken from Dr. Bernstein's Serialization Lab:
 * (https://w3.cs.jmu.edu/bernstdh/web/common/labs/experience_serialization/tempz/index.php)
 *
 * This work complies with JMU Honor Code.
 */
public class RimpleXController implements ActionListener {
  @SuppressWarnings("unused")
  private RimpleXWindow window;
  private JLabel topDisplay;
  private JLabel display;
  private boolean parenPresent;
  private boolean equalsPresent;
  private Complex previousResult;
  private String previousAnswer;
  private String fullExpression;
  private int currentTokenIndex;
  private boolean runningCalc = true;
  private boolean newStarted = false;

  /**
   * Constructor for a RimpleXController.
   */
  public RimpleXController() {
    super();
    equalsPresent = false;
    previousResult = Complex.fromReal(0);
    previousAnswer = "";
    fullExpression = "";
  }

  @Override
  /**
   * Action handler when buttons are clicked.
   *
   * @param ae The ActionEvent that generated the message.
   */
  public void actionPerformed(final ActionEvent ae) {
    String ac = ae.getActionCommand();
    // General structure: if (ac.equals(NAME_OF_BUTTON)) { do stuff... }.
    // For testing if buttons are linked with actions.
    // System.out.println(ac + " was pressed.");
    if (display.getText().equals("Error")) {
    	display.setText("");
    	topDisplay.setText("");
    }
    switch (ac) {
      case "ONE":
        if (!checkDigitPlacement(display)) { break; }
        if (!runningCalc && !newStarted) {
          display.setText("");
          topDisplay.setText("");
        }
        display.setText(display.getText() + "1");
        fullExpression += "1";
        newStarted = true;
        break;
      case "TWO":
        if (!checkDigitPlacement(display)) { break; }
        if (!runningCalc && !newStarted) {
          display.setText("");
          topDisplay.setText("");
        }
        display.setText(display.getText() + "2");
        fullExpression += "2";
        newStarted = true;
        break;
      case "THREE":
        if (!checkDigitPlacement(display)) { break; }
        if (!runningCalc && !newStarted) {
          display.setText("");
          topDisplay.setText("");
        }
        display.setText(display.getText() + "3");
        fullExpression += "3";
        newStarted = true;
        break;
      case "FOUR":
        if (!checkDigitPlacement(display)) { break; }
        if (!runningCalc && !newStarted) {
          display.setText("");
          topDisplay.setText("");
        }
        display.setText(display.getText() + "4");
        fullExpression += "4";
        newStarted = true;
        break;
      case "FIVE":
        if (!checkDigitPlacement(display)) { break; }
        if (!runningCalc && !newStarted) {
          display.setText("");
          topDisplay.setText("");
        }
        display.setText(display.getText() + "5");
        fullExpression += "5";
        newStarted = true;
        break;
      case "SIX":
        if (!checkDigitPlacement(display)) { break; }
        if (!runningCalc && !newStarted) {
          display.setText("");
          topDisplay.setText("");
        }
        display.setText(display.getText() + "6");
        fullExpression += "6";
        newStarted = true;
        break;
      case "SEVEN":
        if (!checkDigitPlacement(display)) { break; }
        if (!runningCalc && !newStarted) {
          display.setText("");
          topDisplay.setText("");
        }
        display.setText(display.getText() + "7");
        fullExpression += "7";
        newStarted = true;
        break;
      case "EIGHT":
        if (!checkDigitPlacement(display)) { break; }
        if (!runningCalc && !newStarted) {
          display.setText("");
          topDisplay.setText("");
        }
        display.setText(display.getText() + "8");
        fullExpression += "8";
        newStarted = true;
        break;
      case "NINE":
        if (!checkDigitPlacement(display)) { break; }
        if (!runningCalc && !newStarted) {
          display.setText("");
          topDisplay.setText("");
        }
        display.setText(display.getText() + "9");
        fullExpression += "9";
        newStarted = true;
        break;
      case "ZERO":
        if (!checkDigitPlacement(display)) { break; }
        if (!runningCalc && !newStarted) {
          display.setText("");
          topDisplay.setText("");
        }
        display.setText(display.getText() + "0");
        fullExpression += "0";
        newStarted = true;
        break;
      case "BACKSPACE":
        if (display.getText().length() != 0) {
          if (display.getText().endsWith("\uD835\uDC56")) {
            display.setText(display.getText().substring(0, display.getText().length() - 2));
            fullExpression = fullExpression.substring(0, fullExpression.length() - 2);
          } else {
            display.setText(display.getText().substring(0, display.getText().length() - 1));
            if (fullExpression.substring(fullExpression.length() - 1).equals(")")) {
              fullExpression = fullExpression.substring(0, fullExpression.length() - 2)
                + fullExpression.substring(fullExpression.length() - 1);
            }
            fullExpression = fullExpression.substring(0, fullExpression.length() - 1);
          }
        } else {
          if (topDisplay.getText().length() != 0) {
            display.setText(topDisplay.getText());
            topDisplay.setText("");
          }
        }
        break;
      case "DECIMAL":
        // This is a temporary solution since this won't work when operators are in the current expression.
        boolean canPlace = !(display.getText().length() == 0)
          && Character.isDigit(display.getText().charAt(display.getText().length() - 1));
        if (canPlace) {
          // Remove the parentheses so as not to set the parser off.
          String displayText = new String(display.getText());
          displayText = displayText.replace("(", "").replace(")", "")
            .replace("+", " ").replace("-", " ").replace("×", " ").replace("÷", " ");
          String[] operands = displayText.split(" ");
          fullExpression += ".";
          String lastOperand = operands[operands.length - 1];
          try {
            lastOperand += ".";
            Float.parseFloat(lastOperand);
            display.setText(display.getText() + ".");
          } catch (NumberFormatException nfe) {
          }
          break;
        } else { break; }
      case "OPEN_PARENTHESIS":
        if (display.getText().length() == 0) {
          display.setText(display.getText() + "(");
          parenPresent = true;
          fullExpression += "(";
          break;
        }
        break;
      case "CLOSED_PARENTHESIS":
        String dispText = display.getText();
        if (dispText.length() == 0) { break; }
        char lastVal = dispText.charAt(dispText.length() - 1);
        if (parenPresent && (Character.isDigit(lastVal) || !checkDigitPlacement(display))) {
          display.setText(display.getText().replace("(", ""));
          parenPresent = false;
          fullExpression += ")";
          break;
        } else { break; }
      case "CLEAR":
        display.setText("");
        break;
      case "RESET":
        topDisplay.setText("");
        display.setText("");
        equalsPresent = false;
        fullExpression = "";
        previousAnswer = "";
        break;
      case "SIGN":
        // This is a HYPHEN, not a DASH
        if (display.getText().charAt(0) == '-')
          display.setText(display.getText().substring(1));
        else
          display.setText("-" + display.getText());
        fullExpression = fullExpression.substring(0, fullExpression.length() - display.getText().length())
          + "-1*(" + fullExpression.substring(fullExpression.length() - display.getText().length()) + ")";
        break;
      case "ADD":
        if (display.getText().length() == 0 && !runningCalc) { break; }
        else { setOperator(display, topDisplay, "+"); fullExpression += "+"; break; }
      case "SUBTRACT":
        // This sign is a DASH, not a HYPHEN
        if (display.getText().length() == 0 && !runningCalc) { break; }
        else { setOperator(display, topDisplay, "—"); fullExpression += "—"; break; }
      case "MULTIPLY":
        if (display.getText().length() == 0 && !runningCalc) { break; }
        else { setOperator(display, topDisplay, "×"); fullExpression += "*"; break; }
      case "DIVIDE":
        if (display.getText().length() == 0 && !runningCalc) { break; }
        else { setOperator(display, topDisplay, "÷"); fullExpression += "/"; break; }
      case "ACTION_EXIT":
        System.exit(0);
        break;
      case "EQUALS":
        try {
          String expr;
          if (!previousAnswer.isBlank() && runningCalc) {
            fullExpression = previousAnswer + fullExpression;
          }
          expr = fullExpression;
          
          double[] result = evaluate(expr);
          // Format the result for display.
          String resultStr;
          if (result[1] == 0) {
            resultStr = Double.toString(result[0]);
            previousAnswer = Double.toString(result[0]);
          } else {
            String imagPart = (result[1] > 0 ? "+" : "") + result[1] + "i";
            resultStr = result[0] + imagPart;
            previousAnswer = result[0] + imagPart;
          }
          String formattedExpr = new String(fullExpression);
          formattedExpr = formattedExpr.replace("*", "×");
          formattedExpr = formattedExpr.replace("/", "÷");
          formattedExpr = formattedExpr.replace("-", "-");
          formattedExpr = formattedExpr.replace("+", "+");
          topDisplay.setText(formattedExpr + " = " + resultStr);
          display.setText("");
          equalsPresent = true;
          if (!runningCalc) {
            fullExpression = "";
            newStarted = false;
          }
          fullExpression = "";
        } catch (Exception ex) {
          display.setText("Error");
          JOptionPane.showMessageDialog(null, "Calculation error: " + ex.getMessage(),
            "Error", JOptionPane.ERROR_MESSAGE);
        }
        break;
      case "UNIT":
        if (display.getText().length() != 0 && Character.isDigit(lastChar())) {
          display.setText(display.getText() + "𝑖");
          fullExpression += "𝑖";
        }
        break;
      case "INVERT":
        runningCalc = !runningCalc;
      case "IMAGINARY_PART":
        break;
      case "REAL_PART":
        break;
      case "POLAR_FORM":
        break;
      case "CONJUGATE":
        break;
      case "SQUARE_ROOT":
        if (display.getText().length() == 0 && !runningCalc) break;
        display.setText("√(" + display.getText() + ")");
        break;
      case "EXPONENT":
        if (display.getText().length() == 0 && !runningCalc) break;
        setOperator(display, topDisplay, "^");
        fullExpression += "^";
        break;
      case "LOGARITHM":
        if (display.getText().length() == 0 && !runningCalc) break;
        display.setText("log(" + display.getText() + ")");
        break;
      default:
        break;
    }
  }

  public void setWindow(final RimpleXWindow window) {
    this.window = window;
  }

  public void setDisplays(final JLabel display, final JLabel topDisplay) {
    this.display = display;
    this.topDisplay = topDisplay;
  }

  private char lastChar() {
    String txt = display.getText();
    return txt.charAt(txt.length() - 1);
  }

  // Tokenizer: splits the expression string into tokens.
  private List<String> tokenize(String expr) {
    List<String> tokens = new ArrayList<>();
    StringTokenizer st = new StringTokenizer(expr, "+-*/()", true);
    while (st.hasMoreTokens()) {
      String token = st.nextToken().trim();
      if (!token.isEmpty()) {
        tokens.add(token);
      }
    }
    return tokens;
  }

  /**
   * Evaluation method for Equals button.
   * @param expr Expression to be evaluated.
   * @return The evaluated Expression.
   * @throws Exception
   */
  private double[] evaluate(String expr) throws Exception {
    List<String> tokens = tokenize(expr);
    currentTokenIndex = 0;
    double[] result = parseExpression(tokens);
    return result;
  }

  /**
   * Parser dealing with addition and subtraction of expression.
   * @param tokens List of "Tokens"
   * @return evaluated expression.
   * @throws Exception
   */
  private double[] parseExpression(List<String> tokens) throws Exception {
    double[] value = parseTerm(tokens);
    while (currentTokenIndex < tokens.size()) {
      String op = tokens.get(currentTokenIndex);
      if (op.equals("+")) {
        currentTokenIndex++;
        value = addComplex(value, parseTerm(tokens));
      } else if (op.equals("-")) {
        currentTokenIndex++;
        value = subtractComplex(value, parseTerm(tokens));
      } else break;
    }
    return value;
  }

  /**
   * Parser dealing with multiplication and division of expression.
   * @param tokens List of "Tokens"
   * @return evaluated expression.
   * @throws Exception
   */
  private double[] parseTerm(List<String> tokens) throws Exception {
    double[] value = parsePrimary(tokens);
    while (currentTokenIndex < tokens.size()) {
      String op = tokens.get(currentTokenIndex);
      if (op.equals("*")) {
        currentTokenIndex++;
        value = multiplyComplex(value, parsePrimary(tokens));
      } else if (op.equals("/")) {
        currentTokenIndex++;
        double[] divisor = parsePrimary(tokens);
        value = divideComplex(value, divisor);
      } else break;
    }
    return value;
  }

  /**
   * Parser dealing with expression in parenthesis.
   * @param tokens List of "Tokens"
   * @return evaluated expression.
   * @throws Exception
   */
  private double[] parsePrimary(List<String> tokens) throws Exception {
    if (currentTokenIndex >= tokens.size()) throw new IllegalArgumentException("Unexpected end of expression");
    String token = tokens.get(currentTokenIndex);
    if (token.equals("(")) {
      currentTokenIndex++;
      double[] value = parseExpression(tokens);
      if (currentTokenIndex >= tokens.size() || !tokens.get(currentTokenIndex).equals(")"))
        throw new IllegalArgumentException("Missing closing parenthesis");
      currentTokenIndex++;
      return value;
    } else {
      currentTokenIndex++;
      if (token.contains("𝑖")) {
        token = token.replace("𝑖", "");
        double imag;
        if (token.isEmpty() || token.equals("+")) imag = 1.0;
        else if (token.equals("-")) imag = -1.0;
        else imag = Double.parseDouble(token);
        return new double[]{0.0, imag};
      } else {
        double real = Double.parseDouble(token);
        return new double[]{real, 0.0};
      }
    }
  }

  private double[] addComplex(double[] a, double[] b) {
    return new double[]{a[0] + b[0], a[1] + b[1]};
  }

  private double[] subtractComplex(double[] a, double[] b) {
    return new double[]{a[0] - b[0], a[1] - b[1]};
  }

  private double[] multiplyComplex(double[] a, double[] b) {
    return new double[]{a[0] * b[0] - a[1] * b[1], a[0] * b[1] + a[1] * b[0]};
  }

  private double[] divideComplex(double[] a, double[] b) {
    double denom = b[0] * b[0] + b[1] * b[1];
    if (denom == 0) throw new ArithmeticException("Division by zero");
    return new double[]{(a[0] * b[0] + a[1] * b[1]) / denom, (a[1] * b[0] - a[0] * b[1]) / denom};
  }

  /**
   * Check if a digit can be placed based on display length and presence of imaginary unit.
   *
   * @param display The display to extract the text from.
   * @return True if digit can be placed.
   */
  private boolean checkDigitPlacement(final JLabel display) {
    if (display.getText().length() != 0) {
      if (display.getText().endsWith("\uD835\uDC56")) {
        return false;
      }
    }
    return true;
  }

  /**
   * Set the operator onto the display depending on presence of parentheses and other operators
   * within the current expression.
   *
   * @param display  The display that holds the current operand.
   * @param topDisplay The display that holds the left operand.
   * @param operator The operator to place.
   */
  private void setOperator(final JLabel display, final JLabel topDisplay, final String operator) {
    if (equalsPresent) {
      if (display.getText().length() == 0) // Combining previous equation with new one.
      {
        display.setText(operator);
        return;
      } else {
        if (display.getText().contains("+") || display.getText().contains("-")
            || display.getText().contains("×") || display.getText().contains("÷")) { // Allows for multiple operators when using a previous equation.
          topDisplay.setText(previousResult.toString() + display.getText() + operator);
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
    } else {
      if ((lastChar() != '.' && lastChar() != '+' && lastChar() != '-' && lastChar() != '×'
          && lastChar() != '÷') && !parenPresent) {
        topDisplay.setText(topDisplay.getText() + display.getText() + operator);
        display.setText("");
        return;
      } else {
        if (!display.getText().contains("+") && !display.getText().contains("-")
            && !display.getText().contains("×") && !display.getText().contains("÷")) {
          display.setText(display.getText() + operator);
          return;
        }
        // This is a test push
        // Test 2
      }
    }
  }
}
