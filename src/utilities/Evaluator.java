package utilities;

/**
 * This class evaluates given operands and operators recursively given a string of input.
 * 
 * This work complies with JMU Honor Code.
 */
public final class Evaluator
{
  static final String SUBTRACTION = "—";
  static final String ADDITION = "+";
  static final String MULTIPLICATION = "×";
  static final String DIVISION = "÷";
  static final String NEGATIVE = "-";
  static final String POWER = "^";

  /**
   * Initialize static Evaluator.
   */
  private Evaluator()
  {
  };

  /**
   * Recursively evaluate the given expression.
   * 
   * @param leftOperand The left operand of the expression.
   * @param operator The main operator of the expression.
   * @param rightOperand The right operand of the expression.
   * @return The result of the evaluation.
   */
  public static String evaluate(final String leftOperand, final String operator,
      final String rightOperand)
  {
    String result;
    
    // Clone the operands since they will be reassigned later if further evaluation is needed.
    String leftResult = new String(leftOperand);
    String rightResult = new String(rightOperand);
    
    // Check if the operand has an operator
    String leftOperator = checkOperators(leftOperand);
    String rightOperator = checkOperators(rightOperand);

    // If there is an operator in the left operand, evaluate it.
    if (leftOperator != null)
    {
       leftResult = evaluate(leftOperand.substring(0, leftOperand.indexOf(leftOperator)), leftOperator,
          leftOperand.substring(leftOperand.indexOf(leftOperator) + 1));
    }
    
    // If there is an operator in the right operand, evaluate it.
    if (rightOperator != null)
    {
        rightResult = evaluate(rightOperand.substring(0, rightOperand.indexOf(rightOperator)),
          rightOperator, rightOperand.substring(rightOperand.indexOf(rightOperator) + 1));
    }
    
    // Replace the negative symbols with subtractions symbols so the Double parser can parse
    // negative numbers.
    leftResult.replace(NEGATIVE, SUBTRACTION);
    rightResult.replace(NEGATIVE, SUBTRACTION);
    
    switch (operator)
    {
      case ADDITION:
        result = String.valueOf(Double.parseDouble(leftResult) + Double.parseDouble(rightResult));
        break;
      case SUBTRACTION:
        result = String.valueOf(Double.parseDouble(leftResult) - Double.parseDouble(rightResult));
        break;
      case DIVISION:
        result = String.valueOf(Double.parseDouble(leftResult) / Double.parseDouble(rightResult));
        break;
      case MULTIPLICATION:
        result = String.valueOf(Double.parseDouble(leftResult) * Double.parseDouble(rightResult));
        break;
      case POWER:
        result = String.valueOf(Math.pow(Double.parseDouble(leftResult), Double.parseDouble(rightResult)));
        break;
      default:
        result = "Error"; // For debugging
        break;
    }
    return result;
  }

  /**
   * Check if an operator is within a given operand in PEMDAS order.
   * 
   * @param operand The operand to check.
   * @return The operator is present, null is none are present.
   */
  private static String checkOperators(final String operand)
  {
    if (operand.contains(SUBTRACTION))
    {
      return SUBTRACTION;
    }
    else if (operand.contains(ADDITION))
    {
      return ADDITION;
    }
    else if (operand.contains(DIVISION))
    {
      return DIVISION;
    }
    else if (operand.contains(MULTIPLICATION))
    {
      return MULTIPLICATION;
    }
    else if (operand.contains(POWER))
    {
      return POWER;
    }
    else
    {
      return null;
    }
  }
}
