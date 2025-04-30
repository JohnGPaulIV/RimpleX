package utilities;

/**
 * This class evaluates given operands and operators recursively given a string of input.
 * 
 * @author Joseph Pogoretskiy
 * 
 * This work complies with JMU Honor Code.
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
  private static final String GREATER_THAN = "Greater than";
  private static final String LESS_THAN = "Less than";

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
    if (operand.contains(SUBTRACTION) || operand.contains(ADDITION))
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
   * Recursively evaluate the given expression.
   * 
   * @param leftOperand
   *          The left operand of the expression.
   * @param operator
   *          The main operator of the expression.
   * @param rightOperand
   *          The right operand of the expression.
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
      leftResult = evaluate(leftOperand.substring(0, leftOperand.indexOf(leftOperator)),
          leftOperator, leftOperand.substring(leftOperand.indexOf(leftOperator) + 1));
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
        result = evaluatePower(leftComplex, rightComplex);
        break;
      case CONJUGATE:
        leftComplex.conjugate();
        result = leftComplex.toString();
        break;
      case INVERT:
        leftComplex.inverse();
        result = leftComplex.toString();
        break;
      case SQUARE_ROOT:
        leftComplex.squareRoot();
        result = leftComplex.toString();
        break;
      case LOG:
        leftComplex.logarithm();
        result = leftComplex.toString();
        break;
      case GREATER_THAN:
        result = Boolean.toString(leftComplex.greaterThan(rightComplex));
        break;
      case LESS_THAN:
        result = Boolean.toString(leftComplex.lessThan(rightComplex));
        break;
      default:
        result = leftComplex.toString();
        break;
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
      }
      else if (leftOperand.getReal() != 0.0)
      {
        String sign = (rightOperand.getImaginary() < 0.0) ? NEGATIVE : "";
        result = String
            .valueOf(Math.pow(leftOperand.getReal(), Math.abs(rightOperand.getImaginary()))) + POWER
            + sign + IMAGINARY_UNIT;
      }
    }
    else if (rightOperand.getImaginary() == 0.0 && rightOperand.getReal() != 0.0)
    {
      result = leftOperand.exponentiate(rightOperand).toString();
    }
    else
    {
      result = leftOperand.toString() + POWER + rightOperand.toString();
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
}
