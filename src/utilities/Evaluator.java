package utilities;

import java.util.ArrayList;
import java.util.List;

/**
 * This class evaluates given operands and operators recursively given a string of input. It also
 * formats the expression without evaluating, using similar recursive logic.
 * 
 * Enhanced to include intermediate steps in the evaluation process.
 * 
 * @author Joseph Pogoretskiy (original)
 */
public final class Evaluator
{
  private static final String CONJUGATE = "Conjugate";
  private static final String INVERT = "Invert";
  private static final String SUBTRACTION = "—";
  private static final String ADDITION = "+";
  private static final String MULTIPLICATION = "×";
  private static final String DIVISION = "÷";
  private static final String NEGATIVE = "-";
  private static final String POWER = "^";
  private static final String IMAGINARY_UNIT = "𝑖";
  private static final String SQUARE_ROOT = "Square root";
  private static final String LOG = "Log";
  private static final String GREATER_THAN = "≥";
  private static final String LESS_THAN = "≤";
  private static final String OPEN_PARENTHESIS = "(";
  private static final String CLOSED_PARENTHESIS = ")";
  private static final String FINAL_RESULT = "Final result: ";
  private static final String FURTHER = "Further evaluation needed with operator: ";
  private static final String COMPARING = "Comparing: ";
  private static final String SPACE = " ";
  private static final String EQUALS = "=";
  
  private static boolean toFormat;
  private static List<String> steps; // List to store intermediate steps

  /**
   * Initialize static Evaluator.
   */
  private Evaluator()
  {
  };

  /**
   * Check if an operator is within a given operand in PEMDAS order.
   * 
   * @param operand
   *          The operand to check.
   * @return The operator is present, null is none are present.
   */
  private static String checkOperators(final String operand)
  {
    String result;
    if (operand.contains(OPEN_PARENTHESIS) && !toFormat)
    {
      result = OPEN_PARENTHESIS;
    }
    else if (operand.contains(SUBTRACTION) || operand.contains(ADDITION))
    {
      boolean hasSubtraction = operand.contains(SUBTRACTION);
      boolean hasAddition = operand.contains(ADDITION);
      if (hasAddition && hasSubtraction)
      {
        int indexOfSubtraction = operand.indexOf(SUBTRACTION);
        int indexOfAddition = operand.indexOf(ADDITION);
        if (indexOfSubtraction > indexOfAddition)
        {
          result = SUBTRACTION;
        }
        else
        {
          result = ADDITION;
        }
      }
      else if (hasAddition)
      {
        result = ADDITION;
      }
      else
      {
        result = SUBTRACTION;
      }
    }
    else if (operand.contains(DIVISION) || operand.contains(MULTIPLICATION))
    {
      boolean hasMultiplication = operand.contains(MULTIPLICATION);
      boolean hasDivision = operand.contains(DIVISION);
      if (hasMultiplication && hasDivision)
      {
        int indexOfMultiplication = operand.indexOf(MULTIPLICATION);
        int indexOfDivision = operand.indexOf(DIVISION);
        if (indexOfMultiplication > indexOfDivision)
        {
          result = MULTIPLICATION;
        }
        else
        {
          result = DIVISION;
        }
      }
      else if (hasMultiplication)
      {
        result = MULTIPLICATION;
      }
      else
      {
        result = DIVISION;
      }
    }
    else if (operand.contains(POWER))
    {
      result = POWER;
    }
    else
    {
      result = null;
    }
    return result;
  }

  /**
   * Recursively evaluate the given expression with intermediate steps.
   * 
   * @param expression
   *          The full expression to evaluate.
   * @return List of strings containing intermediate steps and final result.
   */
  public static List<String> evaluateWithSteps(final String expression)
  {
    steps = new ArrayList<>();
    toFormat = false;
    
    // Add the original expression as the first step
    steps.add("Original expression: " + expression);
    
    // Start the evaluation process
    String operator = checkOperators(expression);
    if (operator != null) 
    {
      String result = evaluateFurther(expression, operator);
      // Final result is already added to steps in the evaluation process
      return steps;
    } else 
    {
      // If there are no operators, just return the expression as is
      String result = Complex.parse(expression).toString();
      steps.add(FINAL_RESULT + result);
      return steps;
    }
  }

  /**
   * Recursively evaluate the given expression.
   * 
   * @param leftOperand
   *          The left operand of the expression.
   * @param operator
   *          The main operator of the expression.
   * @param rightOperand
   *          The right operand of the expression.
   * @param format
   *          Whether to format the expression.
   * @return The result of the evaluation.
   */
  public static String evaluate(final String leftOperand, final String operator,
      final String rightOperand, final boolean format)
  {
    String result;
    toFormat = format;

    // Clone the operands since they will be reassigned later if further evaluation is needed.
    String leftResult = new String(leftOperand);
    String rightResult = new String(rightOperand);

    // Check if the operand has an operator
    String leftOperator = checkOperators(leftOperand);
    String rightOperator = checkOperators(rightOperand);

    // Add a step showing the current operation we're evaluating
    if (!format && steps != null) 
    {
      steps.add("Evaluating: " + leftOperand + SPACE + operator + SPACE + rightOperand);
    }

    // If there is an operator in the left operand, evaluate it.
    if (leftOperator != null)
    {
      if (!format && steps != null) 
      {
        steps.add("Processing left operand: " + leftOperand);
      }
      leftResult = evaluateFurther(leftResult, leftOperator);
      if (!format && steps != null) 
      {
        steps.add("Left operand evaluates to: " + leftResult);
      }
    }

    // If there is an operator in the right operand, evaluate it.
    if (rightOperator != null)
    {
      if (!format && steps != null) 
      {
        steps.add("Processing right operand: " + rightOperand);
      }
      rightResult = evaluateFurther(rightResult, rightOperator);
      if (!format && steps != null) 
      {
        steps.add("Right operand evaluates to: " + rightResult);
      }
    }

    // Replace the negative symbols with subtractions symbols so the Double parser can parse
    // negative numbers.
    leftResult = leftResult.replace(NEGATIVE, SUBTRACTION);
    rightResult = rightResult.replace(NEGATIVE, SUBTRACTION);

    // Create new Complex numbers based on presence of imaginary units.
    Complex leftComplex = null;
    Complex rightComplex = null;

    if (!format)
    {
      leftComplex = Complex.parse(leftResult);
      rightComplex = Complex.parse(rightResult);
      
      if (steps != null) 
      {
        steps.add("Left as complex number: " + leftComplex.toString());
        steps.add("Right as complex number: " + rightComplex.toString());
      }
    }

    switch (operator)
    {
      case ADDITION:
        if (format)
        {
          result = formatOperand(leftResult, rightResult, ADDITION);
        }
        else
        {
          result = leftComplex.add(rightComplex).toString();
          if (steps != null) 
          {
            steps.add("Adding: " + leftComplex.toString() + " + "
                  + rightComplex.toString() + SPACE + EQUALS 
                  + SPACE + result);
          }
        }
        break;
      case SUBTRACTION:
        if (format)
        {
          result = formatOperand(leftResult, rightResult, SUBTRACTION);
        }
        else
        {
          result = leftComplex.subtract(rightComplex).toString();
          if (steps != null) 
          {
            steps.add("Subtracting: " + leftComplex.toString()
                + " - " + rightComplex.toString() + SPACE + EQUALS
                + SPACE + result);
          }
        }
        break;
      case DIVISION:
        if (format)
        {
          result = formatOperand(leftResult, rightResult, DIVISION);
        }
        else
        {
          result = leftComplex.divide(rightComplex).toString();
          if (steps != null) 
          {
            steps.add("Dividing: " + leftComplex.toString() + " ÷ " + rightComplex.toString()
                + SPACE + EQUALS + SPACE + result);
          }
        }
        break;
      case MULTIPLICATION:
        if (format)
        {
          result = formatOperand(leftResult, rightResult, MULTIPLICATION);
        }
        else
        {
          result = leftComplex.multiply(rightComplex).toString();
          if (steps != null) 
          {
            steps.add("Multiplying: " + leftComplex.toString()
                + " × " + rightComplex.toString() + SPACE + EQUALS + SPACE + result);
          }
        }
        break;
      case POWER:
        if (format)
        {
          result = formatOperand(leftResult, rightResult, POWER);
        }
        else
        {
          result = evaluatePower(leftComplex, rightComplex);
          if (steps != null) 
          {
            steps.add("Exponentiating: " + leftComplex.toString() + " ^ "
                + rightComplex.toString() + SPACE + EQUALS + SPACE + result);
          }
        }
        break;
      case CONJUGATE:
        leftComplex.conjugate();
        result = leftComplex.toString();
        if (!format && steps != null) 
        {
          steps.add("Conjugating: " + leftOperand + SPACE + EQUALS + SPACE + result);
        }
        break;
      case INVERT:
        leftComplex.inverse();
        result = leftComplex.toString();
        if (!format && steps != null) 
        {
          steps.add("Inverting: " + leftOperand + SPACE + EQUALS + SPACE + result);
        }
        break;
      case SQUARE_ROOT:
        leftComplex.squareRoot();
        result = leftComplex.toString();
        if (!format && steps != null) 
        {
          steps.add("Square root of: " + leftOperand + SPACE + EQUALS + SPACE + result);
        }
        break;
      case LOG:
        leftComplex.logarithm();
        result = leftComplex.toString();
        if (!format && steps != null) 
        {
          steps.add("Log of: " + leftOperand + SPACE + EQUALS + SPACE + result);
        }
        break;
      case GREATER_THAN:
        result = Boolean.toString(leftComplex.greaterThan(rightComplex));
        if (!format && steps != null) 
        {
          steps.add(COMPARING + leftComplex.toString()
              + " ≥ " + rightComplex.toString() + SPACE + EQUALS + SPACE + result);
        }
        break;
      case LESS_THAN:
        result = Boolean.toString(leftComplex.lessThan(rightComplex));
        if (!format && steps != null) 
        {
          steps.add(COMPARING + leftComplex.toString() + " ≤ "
              + rightComplex.toString() + SPACE + EQUALS + SPACE + result);
        }
        break;
      default:
        if (checkOperators(leftResult) == null)
        {
          result = Complex.parse(leftResult).toString();
        }
        else
        {
          result = leftResult;
        }
        break;
    }
    
    // Add the final step for this evaluation if we're in the outer scope
    if (!format && steps != null && leftOperator == null && rightOperator == null) 
    {
      steps.add(FINAL_RESULT + result);
    }
    
    return result;
  }

  /**
   * Evaluate exponentiating between two operands taking into account their possible complex forms.
   * 
   * @param leftOperand
   *          The base of the exponentiation.
   * @param rightOperand
   *          The power of the exponentiation.
   * @return Return the calculation in string form to display on the calculator.
   */
  private static String evaluatePower(final Complex leftOperand, final Complex rightOperand)
  {
    String result = null;
    if (rightOperand.getImaginary() != 0.0 && rightOperand.getReal() == 0.0)
    {
      if (isComplex(leftOperand) || leftOperand.getImaginary() != 0.0)
      {
        result = leftOperand.toString() + POWER + rightOperand.toString();
        if (steps != null) 
        {
          steps.add("Cannot evaluate complex with imaginary exponent: " + result);
        }
      }
      else if (leftOperand.getReal() != 0.0)
      {
        String sign = (rightOperand.getImaginary() < 0.0) ? NEGATIVE : "";
        result = String
            .valueOf(Math.pow(leftOperand.getReal(), Math.abs(rightOperand.getImaginary()))) + POWER
            + sign + IMAGINARY_UNIT;
        if (steps != null) 
        {
          steps.add("Partial evaluation of power with imaginary exponent: " + result);
        }
      }
    }
    else if (rightOperand.getImaginary() == 0.0 && rightOperand.getReal() != 0.0)
    {
      result = leftOperand.exponentiate(rightOperand).toString();
      if (steps != null) 
      {
        steps.add("Power evaluation: " + leftOperand.toString() 
            + POWER + rightOperand.toString() 
            + SPACE + EQUALS + SPACE + result);
      }
    }
    else
    {
      result = leftOperand.toString() + POWER + rightOperand.toString();
      if (steps != null) 
      {
        steps.add("Cannot evaluate complex power: " + result);
      }
    }
    return result;
  }

  /**
   * Check if the operand has an open parenthesis recursively.
   * 
   * @param operand
   *          The operand to check
   * @param operator
   *          The operand's operator
   * @return The innermost parenthesized expression
   */
  private static String evaluateFurther(final String operand, final String operator)
  {
    String operandCopy = new String(operand);
    String operatorCopy = new String(operator);
    String result;

    // Add step for this evaluation phase
    if (!toFormat && steps != null) 
    {
      steps.add("Evaluating expression: " + operand);
    }

    // If operand contains parenthesis, fetch it and evaluate it first.
    if (operand.contains(OPEN_PARENTHESIS) && !toFormat)
    {
      String parenthesizedExpr = fetchParenthesizedExpression(operandCopy);
      String unparenthesizedExpr = new String(parenthesizedExpr).substring(1,
          parenthesizedExpr.length() - 1);
      
      if (!toFormat && steps != null) 
      {
        steps.add("Found parenthesized expression: " + parenthesizedExpr);
        steps.add("Without parentheses: " + unparenthesizedExpr);
      }
      
      operatorCopy = checkOperators(unparenthesizedExpr);
      if (operatorCopy != null)
      {
        // Get result and replace parenthesized operand with its result.
        result = evaluateFurther(unparenthesizedExpr, operatorCopy);
        
        if (!toFormat && steps != null) 
        {
          steps.add("Evaluated parenthesized expression: " + result);
          steps.add("Replacing " + parenthesizedExpr + " with " + result + " in " + operandCopy);
        }
        
        operandCopy = new String(operandCopy.replace(parenthesizedExpr, result));
        
        if (!toFormat && steps != null) 
        {
          steps.add("Expression after replacement: " + operandCopy);
        }
        
        operatorCopy = checkOperators(operandCopy);
        // Keep evaluating as long as there is a operator.
        if (operatorCopy != null)
        {
          if (!toFormat && steps != null) 
          {
            steps.add(FURTHER + operatorCopy);
          }
          result = evaluateFurther(operandCopy, operatorCopy);
        }
        else
        {
          result = operandCopy;
        }
      }
      else
      {
        operandCopy = new String(operandCopy.replace(parenthesizedExpr, unparenthesizedExpr));
        
        if (!toFormat && steps != null) 
        {
          steps.add("Removed parentheses, new expression: " + operandCopy);
        }
        
        operatorCopy = checkOperators(operandCopy);
        // Keep evaluating as long as there is a operator.
        if (operatorCopy != null)
        {
          if (!toFormat && steps != null) 
          {
            steps.add(FURTHER + operatorCopy);
          }
          result = evaluateFurther(operandCopy, operatorCopy);
        }
        else
        {
          result = operandCopy;
        }
      }
    }
    else
    {
      // Evaluate based off of present operator.
      operatorCopy = checkOperators(operandCopy);
      
      if (!toFormat && steps != null)
      {
        steps.add("Splitting expression at operator " + operatorCopy + ": " 
                  +
                  operandCopy.substring(0, operandCopy.indexOf(operatorCopy)) + SPACE 
                  + 
                  operatorCopy + SPACE 
                  +
                  operandCopy.substring(operandCopy.indexOf(operatorCopy) + 1));
      }
      
      if (toFormat)
      {
        result = evaluate(operandCopy.substring(0, operandCopy.indexOf(operatorCopy)), operatorCopy,
            operandCopy.substring(operandCopy.indexOf(operatorCopy) + 1), true);
      }
      else
      {
        result = evaluate(operandCopy.substring(0, operandCopy.indexOf(operatorCopy)), operatorCopy,
            operandCopy.substring(operandCopy.indexOf(operatorCopy) + 1), false);
      }
      return result;
    }
    return result;
  }

  /**
   * Fetch the first and outermost parenthesized expression within the operand.
   * 
   * @param operand
   *          The operand with the parentheses
   * @return The outermost parenthesized expression
   */
  private static String fetchParenthesizedExpression(final String operand)
  {
    int openParenCount = 0;
    int closedParenCount = 0;
    int indexOfClosedParen = 0;
    int indexOfOpenParen = 0;
    boolean firstOpenParen = true;
    char[] operandCharArray = operand.toCharArray();
    for (int i = 0; i < operandCharArray.length; i++)
    {
      if (operandCharArray[i] == '(')
      {
        if (firstOpenParen)
        {
          indexOfOpenParen = i;
          firstOpenParen = false;
        }
        openParenCount++;
      }
      if (operandCharArray[i] == ')')
      {
        closedParenCount++;
      }
      if (openParenCount == closedParenCount && !firstOpenParen)
      {
        indexOfClosedParen = i;
        break;
      }
    }
    String result = operand.substring(indexOfOpenParen, indexOfClosedParen + 1);
    return result;
  }

  /**
   * Extract the parenthesis from the given number.
   * 
   * @param num
   *          The single number.
   * @return Any parentheses surrounding it.
   */
  private static String getParenthesis(final String num)
  {
    if (num.contains(OPEN_PARENTHESIS))
    {
      int count = 0;
      for (int i = 0; i < num.length(); i++)
      {
        if (num.charAt(i) == '(')
        {
          count++;
        }
      }
      String openParen = "";
      for (int j = 0; j < count; j++)
      {
        openParen = openParen + OPEN_PARENTHESIS;
      }
      return openParen;
    }
    else if (num.contains(CLOSED_PARENTHESIS))
    {
      int count = 0;
      for (int i = 0; i < num.length(); i++)
      {
        if (num.charAt(i) == ')')
        {
          count++;
        }
      }
      String closedParen = "";
      for (int j = 0; j < count; j++)
      {
        closedParen = closedParen + CLOSED_PARENTHESIS;
      }
      return closedParen;
    }
    else
    {
      return "";
    }
  }

  /**
   * Formats the result of the operand based off the operator and presence of parentheses.
   * 
   * @param leftResult
   *          The left operand.
   * @param rightResult
   *          The right operand.
   * @param op
   *          The operator.
   * @return Return the formatted operand.
   */
  private static String formatOperand(final String leftResult, final String rightResult,
      final String op)
  {
    String result;
    if (checkOperators(leftResult) == null && checkOperators(rightResult) == null)
    {
      if (getParenthesis(leftResult).contains(OPEN_PARENTHESIS)
          && getParenthesis(rightResult).contains(CLOSED_PARENTHESIS))
      {
        result = getParenthesis(leftResult) + Complex.parse(leftResult).toString() + op
            + Complex.parse(rightResult).toString() + getParenthesis(rightResult);
      }
      else if (getParenthesis(leftResult).contains(OPEN_PARENTHESIS)
          && getParenthesis(rightResult).contains(OPEN_PARENTHESIS))
      {
        result = getParenthesis(leftResult) + Complex.parse(leftResult).toString() + op
            + getParenthesis(rightResult) + Complex.parse(rightResult).toString();
      }
      else if (getParenthesis(leftResult).contains(CLOSED_PARENTHESIS)
          && getParenthesis(rightResult).contains(OPEN_PARENTHESIS))
      {
        result = Complex.parse(leftResult).toString() + getParenthesis(leftResult) + op
            + getParenthesis(rightResult) + Complex.parse(rightResult).toString();
      }
      else if (getParenthesis(leftResult).contains(CLOSED_PARENTHESIS)
          && getParenthesis(rightResult).contains(CLOSED_PARENTHESIS))
      {
        result = Complex.parse(leftResult).toString() + getParenthesis(leftResult) + op
            + Complex.parse(rightResult).toString() + getParenthesis(rightResult);
      }
      else if (getParenthesis(leftResult).contains(CLOSED_PARENTHESIS))
      {
        result = Complex.parse(leftResult).toString() + getParenthesis(leftResult) + op
            + Complex.parse(rightResult).toString();
      }
      else if (getParenthesis(leftResult).contains(OPEN_PARENTHESIS))
      {
        result = getParenthesis(leftResult) + Complex.parse(leftResult).toString() + op
            + Complex.parse(rightResult).toString();
      }
      else if (getParenthesis(rightResult).contains(CLOSED_PARENTHESIS))
      {
        result = Complex.parse(leftResult).toString() + op + Complex.parse(rightResult).toString()
            + getParenthesis(rightResult);
      }
      else if (getParenthesis(rightResult).contains(OPEN_PARENTHESIS))
      {
        result = Complex.parse(leftResult).toString() + op + getParenthesis(rightResult)
            + Complex.parse(rightResult).toString();
      }
      else
      {
        result = Complex.parse(leftResult).toString() + op + Complex.parse(rightResult).toString();
      }

    }
    else if (checkOperators(leftResult) != null && checkOperators(rightResult) == null)
    {
      if (getParenthesis(rightResult).contains(OPEN_PARENTHESIS))
      {
        result = leftResult + op + getParenthesis(rightResult)
            + Complex.parse(rightResult).toString();
      }
      else if (getParenthesis(rightResult).contains(CLOSED_PARENTHESIS))
      {
        result = leftResult + op + Complex.parse(rightResult).toString()
            + getParenthesis(rightResult);
      }
      else
      {
        result = leftResult + op + Complex.parse(rightResult).toString();
      }
    }
    else if (checkOperators(leftResult) == null && checkOperators(rightResult) != null)
    {
      if (getParenthesis(leftResult).contains(OPEN_PARENTHESIS))
      {
        result = getParenthesis(leftResult) + Complex.parse(leftResult).toString() + op
            + rightResult;
      }
      else if (getParenthesis(leftResult).contains(CLOSED_PARENTHESIS))
      {
        result = Complex.parse(leftResult).toString() + getParenthesis(leftResult) + op
            + rightResult;
      }
      else
      {
        result = Complex.parse(leftResult).toString() + op + rightResult;
      }
    }
    else
    {
      result = leftResult + op + rightResult;
    }
    return result;
  }

  /**
   * Check if the complex number is actually complex (has a real and imaginary part).
   * 
   * @param number
   *          The "complex" number.
   * @return True if it is complex, false if it isn't.
   */
  private static boolean isComplex(final Complex number)
  {
    if (number.getReal() != 0.0 && number.getImaginary() != 0.0)
    {
      return true;
    }
    return false;
  }
  
  /**
   * Original evaluation method preserved for backward compatibility.
   * 
   * @param expression
   *          The full expression to evaluate.
   * @return The final result as a string.
   */
  public static String evaluate(final String expression)
  {
    toFormat = false;
    String operator = checkOperators(expression);
    if (operator != null) 
    {
      return evaluateFurther(expression, operator);
    } else 
    {
      return Complex.parse(expression).toString();
    }
  }
}
