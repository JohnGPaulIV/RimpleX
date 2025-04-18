package utilities;

/**
 * This class evaluates given operands and operators recursively given a string of input.
 * 
 * This work complies with JMU Honor Code.
 */
public final class Evaluator
{
  private static final String SUBTRACTION = "—";
  private static final String ADDITION = "+";
  private static final String MULTIPLICATION = "×";
  private static final String DIVISION = "÷";
  private static final String NEGATIVE = "-";
  private static final String POWER = "^";
  private static final String IMAGINARY_UNIT = "𝑖";

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
    
    // Create new Complex numbers based on presence of imaginary units.
    Complex leftComplex;
    Complex rightComplex;
//    if (leftResult.contains(IMAGINARY_UNIT))
//    {
//      leftComplex = new Complex(0.0, Double.parseDouble(leftResult.replace(IMAGINARY_UNIT, "")));
//    }
//    else
//    {
//      leftComplex = new Complex(Double.parseDouble(leftResult), 0.0);
//    }
//    if (rightResult.contains(IMAGINARY_UNIT))
//    {
//      rightComplex = new Complex(0.0, Double.parseDouble(rightResult.replace(IMAGINARY_UNIT, "")));
//    }
//    else
//    {
//      rightComplex = new Complex(Double.parseDouble(rightResult), 0.0);
//    }
    leftComplex = Complex.parse(leftResult);
    rightComplex = Complex.parse(rightResult);
    
    switch (operator)
    {
      case ADDITION:
        result = leftComplex.add(rightComplex).toString();
        break;
      case SUBTRACTION:
        result = leftComplex.subtract(rightComplex).toString();
        break;
      case DIVISION:
        result = leftComplex.divide(rightComplex).toString();
        break;
      case MULTIPLICATION:
        result = leftComplex.multiply(rightComplex).toString();
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
