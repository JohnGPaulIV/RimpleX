package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import utilities.Evaluator;

/**
 * Tests to ensure the Evaluator utility parses and evaluates expressions accurately and
 * in logical order (PEMDAS).
 * 
 * @author Joseph Pogoretskiy
 * 
 * This work complies with JMU Honor Code.
 */
class EvaluatorTest
{
  static final String SUBTRACTION = "—";
  static final String ADDITION = "+";
  static final String MULTIPLICATION = "×";
  static final String DIVISION = "÷";
  static final String NEGATIVE = "-";
  static final String POWER = "^";
  static final String CONJUGATE = "Conjugate";
  static final String SQUARE_ROOT = "Square root";
  static final String LOG = "Log";
  static final String INVERT = "Invert";
  static final String GREATER_THAN = "≥";
  static final String LESS_THAN = "≤";

  @Test
  void testEvaluatingBasicSubtraction()
  {
    assertEquals("5.0", Evaluator.evaluate("4", SUBTRACTION, "-1", false));
  }
  
  @Test
  void testEvaluatingNonExistentOperators()
  {
    assertEquals("8.0", Evaluator.evaluate("8", "Bruh", "2", false));
  }
  
  @Test
  void testEvaluatingBasicAddition()
  {
    assertEquals("6.0", Evaluator.evaluate("8", ADDITION, "-2", false));
    assertEquals("-10.0", Evaluator.evaluate("-8", ADDITION, "-2", false));
  }
  
  @Test
  void testEvaluatingBasicDivision()
  {
    assertEquals("2.400", Evaluator.evaluate("12", DIVISION, "5", false));
  }

  @Test
  void testEvaluatingBasicMultiplication()
  {
    assertEquals("15.0", Evaluator.evaluate("5", MULTIPLICATION, "3", false));
  }
  
  @Test
  void testEvaluatingBasicExponentiation()
  {
    assertEquals("64.0", Evaluator.evaluate("8", POWER, "2", false));
    assertEquals("0.016", Evaluator.evaluate("8", POWER, "-2", false));
  }
  
  @Test
  void testEvaluatingParenthesizedOperands()
  {
    assertEquals("13.0", Evaluator.evaluate("7+11", SUBTRACTION, "5", false));
    assertEquals("-9.0", Evaluator.evaluate("-2+8", SUBTRACTION, "15", false));
    assertEquals("42.0", Evaluator.evaluate("4—1", MULTIPLICATION, "7×2", false));
    assertEquals("10.0", Evaluator.evaluate("2+2+2+2", ADDITION, "4×0.5", false));
    assertEquals("484.0", Evaluator.evaluate("3^3—5", POWER, "-8+10", false));
  }
  
  @Test
  void testEvaluatingPEMDASOrder()
  {
    assertEquals("12.0", Evaluator.evaluate("4+2×3—2", ADDITION, "2+8÷4", false));
    assertEquals("12.0", Evaluator.evaluate("9—3+4", ADDITION, "3—2+1", false));
    assertEquals("18.0", Evaluator.evaluate("20÷4×3", ADDITION, "4×3÷4", false));
  }
  
  @Test
  void testEvaluatingBasicImaginaryUnits()
  {
    assertEquals("3.0𝑖", Evaluator.evaluate("-5𝑖", ADDITION, "8𝑖", false));
    assertEquals("5.0𝑖", Evaluator.evaluate("2𝑖", ADDITION, "3𝑖", false));
    assertEquals("4.0𝑖", Evaluator.evaluate("6𝑖", SUBTRACTION, "2𝑖", false));
    assertEquals("-4.0𝑖", Evaluator.evaluate("-7𝑖", SUBTRACTION, "-3𝑖", false));
    assertEquals("-12.0", Evaluator.evaluate("6𝑖", MULTIPLICATION, "2𝑖", false));
    assertEquals("12.0𝑖", Evaluator.evaluate("6𝑖", MULTIPLICATION, "2", false));
    assertEquals("-3.0", Evaluator.evaluate("6𝑖", DIVISION, "2𝑖", false));
    assertEquals("3.0", Evaluator.evaluate("6𝑖", DIVISION, "-2𝑖", false));
    assertEquals("-3.0𝑖", Evaluator.evaluate("-6𝑖", DIVISION, "2", false));
    assertEquals("0.333𝑖", Evaluator.evaluate("2𝑖", DIVISION, "6", false));
  }
  
  @Test
  void testEvaluatingSquareRoots()
  {
    assertEquals("9.0", Evaluator.evaluate("50+31", SQUARE_ROOT, "", false));
    assertEquals("9.0𝑖", Evaluator.evaluate("-50—31", SQUARE_ROOT, "", false));
    assertEquals("2.121+2.121𝑖", Evaluator.evaluate("9𝑖", SQUARE_ROOT, "", false));
    assertEquals("2.121—2.121𝑖", Evaluator.evaluate("-9𝑖", SQUARE_ROOT, "", false));
    assertEquals("3.467+1.010𝑖", Evaluator.evaluate("3+8+9𝑖—2𝑖", SQUARE_ROOT, "", false));
  }
  
  @Test
  void testEvaluatingLogarithms()
  {
    assertEquals("1.908", Evaluator.evaluate("50+31", LOG, "", false));
    assertEquals("1.908", Evaluator.evaluate("-50—31", LOG, "", false));
    assertEquals("0.954+0.682𝑖", Evaluator.evaluate("9𝑖", LOG, "", false));
    assertEquals("0.954—0.682𝑖", Evaluator.evaluate("-9𝑖", LOG, "", false));
    assertEquals("1.115+0.246𝑖", Evaluator.evaluate("3+8+9𝑖—2𝑖", LOG, "", false));
  }
  
  @Test
  void testEvaluatingConjugate()
  {
    assertEquals("2.0—2.0𝑖", Evaluator.evaluate("2+2𝑖", CONJUGATE, "", false));
    assertEquals("2.0+2.0𝑖", Evaluator.evaluate("2—2𝑖", CONJUGATE, "", false));
    assertEquals("-3.0𝑖", Evaluator.evaluate("3𝑖", CONJUGATE, "", false));
    assertEquals("3.0", Evaluator.evaluate("3", CONJUGATE, "", false));
  }
 
  @Test
  void testEvaluatingInverse()
  {
    assertEquals("0.250—0.250𝑖", Evaluator.evaluate("2+2𝑖", INVERT, "", false));
    assertEquals("0.250+0.250𝑖", Evaluator.evaluate("2—2𝑖", INVERT, "", false));
    assertEquals("-0.333𝑖", Evaluator.evaluate("3𝑖", INVERT, "", false));
    assertEquals("0.333", Evaluator.evaluate("3", INVERT, "", false));
  }
  
  @Test
  void testEvaluatingGreaterThanComplexes()
  {
    assertEquals("true", Evaluator.evaluate("3.001", GREATER_THAN, "3", false));
    assertEquals("false", Evaluator.evaluate("3", GREATER_THAN, "3.001", false));
    assertEquals("true", Evaluator.evaluate("3+2𝑖", GREATER_THAN, "3", false));
    assertEquals("true", Evaluator.evaluate("3+2𝑖", GREATER_THAN, "3+2𝑖", false));
    assertEquals("true", Evaluator.evaluate("3+2𝑖", GREATER_THAN, "3—2𝑖", false));
    assertEquals("false", Evaluator.evaluate("3—2𝑖", GREATER_THAN, "3+2𝑖", false));
    assertEquals("false", Evaluator.evaluate("3", GREATER_THAN, "3+2𝑖", false));
    assertEquals("true", Evaluator.evaluate("3𝑖", GREATER_THAN, "2𝑖", false));
    assertEquals("true", Evaluator.evaluate("3𝑖", GREATER_THAN, "3𝑖", false));
    assertEquals("true", Evaluator.evaluate("3𝑖", GREATER_THAN, "-3𝑖", false));
    assertEquals("false", Evaluator.evaluate("3𝑖", GREATER_THAN, "4", false));
    assertEquals("false", Evaluator.evaluate("3𝑖", GREATER_THAN, "3", false));
  }
  
  @Test
  void testEvaluatingLesserThanComplexes()
  {
    assertEquals("false", Evaluator.evaluate("3.001", LESS_THAN, "3", false));
    assertEquals("true", Evaluator.evaluate("3", LESS_THAN, "3.001", false));
    assertEquals("false", Evaluator.evaluate("3+2𝑖", LESS_THAN, "3", false));
    assertEquals("true", Evaluator.evaluate("3+2𝑖", LESS_THAN, "3+2𝑖", false));
    assertEquals("false", Evaluator.evaluate("3+2𝑖", LESS_THAN, "3—2𝑖", false));
    assertEquals("true", Evaluator.evaluate("3—2𝑖", LESS_THAN, "3+2𝑖", false));
    assertEquals("true", Evaluator.evaluate("3", LESS_THAN, "3+2𝑖", false));
    assertEquals("false", Evaluator.evaluate("3𝑖", LESS_THAN, "2𝑖", false));
    assertEquals("true", Evaluator.evaluate("3𝑖", LESS_THAN, "3𝑖", false));
    assertEquals("false", Evaluator.evaluate("3𝑖", LESS_THAN, "-3𝑖", false));
    assertEquals("true", Evaluator.evaluate("3𝑖", LESS_THAN, "4", false));
    assertEquals("true", Evaluator.evaluate("3𝑖", LESS_THAN, "3", false));
  }
  
  @Test
  void testEvaluatingParenthesizedImaginaryUnits()
  {
    assertEquals("4.0+5.0𝑖", Evaluator.evaluate("2+2𝑖", ADDITION, "2+3𝑖", false));
    assertEquals("-2.0+10.0𝑖", Evaluator.evaluate("2+2𝑖", MULTIPLICATION, "2+3𝑖", false));
    assertEquals("2.0+6.0𝑖", Evaluator.evaluate("2+4𝑖", ADDITION, "2𝑖", false));
    assertEquals("8.0+3.0𝑖", Evaluator.evaluate("8+6𝑖", SUBTRACTION, "3𝑖", false));
    assertEquals("3.0—9.0𝑖", Evaluator.evaluate("3—6𝑖", SUBTRACTION, "3𝑖", false));
    assertEquals("11.0+3.0𝑖", Evaluator.evaluate("8+6𝑖", ADDITION, "2—3𝑖+1", false));
    assertEquals("8.0+2.600𝑖", Evaluator.evaluate("8—6𝑖", ADDITION, "4×2𝑖+3𝑖÷5", false));
    assertEquals("-0.075+0.150𝑖", Evaluator.evaluate("-3÷4𝑖", DIVISION, "2𝑖+4", false));
  }

  @Test
  void testEvaluatingComplexExponentiation()
  {
    assertEquals("64.0^𝑖", Evaluator.evaluate("4", POWER, "3𝑖", false)); // Should return simplified.
    assertEquals("-64.0^-𝑖", Evaluator.evaluate("-4", POWER, "-3𝑖", false)); // Should return simplified.
    assertEquals("-64.0^𝑖", Evaluator.evaluate("-4", POWER, "3𝑖", false)); // Should return simplified.
    assertEquals("2.0^𝑖", Evaluator.evaluate("4", POWER, "0.5𝑖", false)); // Should return simplified.
    assertEquals("3.0+4.0𝑖^3.0𝑖", Evaluator.evaluate("3+4𝑖", POWER, "3𝑖", false)); // Should return as is.
    assertEquals("-3.0𝑖", Evaluator.evaluate("3𝑖", POWER, "3", false));
    assertEquals("3.0", Evaluator.evaluate("3𝑖", POWER, "4", false));
    assertEquals("3.0𝑖", Evaluator.evaluate("3𝑖", POWER, "5", false));
    assertEquals("-3.0", Evaluator.evaluate("3𝑖", POWER, "6", false));
    assertEquals("4.0𝑖^2.0+3.0𝑖", Evaluator.evaluate("4𝑖", POWER, "3𝑖+2", false)); 
    assertEquals("4.0^2.0+3.0𝑖", Evaluator.evaluate("4", POWER, "3𝑖+2", false));// Should return as is.
    assertEquals("-6.750—9.0𝑖", Evaluator.evaluate("1.5—3.0𝑖", POWER, "2", false));
    assertEquals("0.011—0.038𝑖", Evaluator.evaluate("4+3𝑖", POWER, "-2", false));
  }
  
  @Test
  void testEvaluatingOperandsContainingParentheses()
  {
    assertEquals("11.0", Evaluator.evaluate("4", ADDITION, "(4+3)", false));
    assertEquals("43.0", Evaluator.evaluate("(5+4^2—1)×2", ADDITION, "3", false));
    assertEquals("51.0", Evaluator.evaluate("2×4+2×(5+4^2—1)", ADDITION, "3", false));
    assertEquals("48.0", Evaluator.evaluate("(2+6)×(7—2)+1", ADDITION, "5+2", false));
    assertEquals("15.0", Evaluator.evaluate("(5+3)", ADDITION, "5+2", false));
    assertEquals("71.0", Evaluator.evaluate("(5+2×7)", ADDITION, "(4+6×8)", false));
    assertEquals("18.0+10.0𝑖", Evaluator.evaluate("4+(5+3𝑖)+2𝑖", ADDITION, "4+(5+3𝑖)+2𝑖", false));
  }
  
  @Test
  void testEvaluatingOperandsContainingMultiplieParentheses()
  {
    assertEquals("10.500", Evaluator.evaluate("4", ADDITION, "(4+3+(4+2))×0.5", false));
    assertEquals("30.0", Evaluator.evaluate("5+2+((2+8)×2)", ADDITION, "3", false));
    assertEquals("72.0", Evaluator.evaluate("2×(8÷4)+(4+8)", ADDITION, "4×(8×2—(1+1))", false));
    assertEquals("10.0+8.0𝑖", Evaluator.evaluate("2+3𝑖+(2𝑖+(4+2𝑖))—1", ADDITION, "(3𝑖^2)+(1+3𝑖+(5—2𝑖))+2", false));
  }
  
  @Test
  void testEvaluatingOperandsContainingParenthesizedDigits()
  {
    assertEquals("18.0+4.0𝑖", Evaluator.evaluate("(5+1)+4+(4𝑖)", ADDITION, "4+(4)", false));
  }
  
  @Test
  void testFormattingOperands()
  {
    assertEquals("3.0+8.500+9.0𝑖+2.0𝑖", Evaluator.evaluate("3+8.5+9𝑖+2𝑖", "", "", true));
    assertEquals("-3.0—8.500—9.0𝑖—2.0𝑖", Evaluator.evaluate("-3—8.5—9𝑖—2𝑖", "", "", true));
    assertEquals("3.0×8.500×9.0𝑖×2.0𝑖", Evaluator.evaluate("3×8.5×9𝑖×2𝑖", "", "", true));
    assertEquals("3.0÷8.500÷9.0𝑖÷2.0𝑖", Evaluator.evaluate("3÷8.5÷9𝑖÷2𝑖", "", "", true));
    assertEquals("3.0^8.500^9.0𝑖^2.0𝑖", Evaluator.evaluate("3^8.5^9𝑖^2𝑖", "", "", true));
    assertEquals("3.0^8.500+9.0𝑖÷2.0𝑖", Evaluator.evaluate("3^8.5+9𝑖÷2𝑖", "", "", true));
    assertEquals("2.0×(8.800÷4.0)+(4.0+8.0)", Evaluator.evaluate("2×(8.8÷4)+(4+8)", "", "", true));
    assertEquals("5.0+2.0+((2.0+8.0)×2.0)", Evaluator.evaluate("5+2+((2+8)×2)", "", "", true));
  }
}
