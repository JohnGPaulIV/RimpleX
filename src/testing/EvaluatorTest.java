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

  @Test
  void testEvaluatingBasicSubtraction()
  {
    assertEquals("5.0", Evaluator.evaluate("4", SUBTRACTION, "-1"));
  }
  
  @Test
  void testEvaluatingNonExistentOperators()
  {
    assertEquals("8.0", Evaluator.evaluate("8", "Bruh", "2"));
  }
  
  @Test
  void testEvaluatingBasicAddition()
  {
    assertEquals("10.0", Evaluator.evaluate("8", ADDITION, "2"));
    assertEquals("6.0", Evaluator.evaluate("8", ADDITION, "-2"));
    assertEquals("-10.0", Evaluator.evaluate("-8", ADDITION, "-2"));
  }
  
  @Test
  void testEvaluatingBasicDivision()
  {
    assertEquals("2.4", Evaluator.evaluate("12", DIVISION, "5"));
  }

  @Test
  void testEvaluatingBasicMultiplication()
  {
    assertEquals("15.0", Evaluator.evaluate("5", MULTIPLICATION, "3"));
  }
  
  @Test
  void testEvaluatingBasicExponentiation()
  {
    assertEquals("64.0", Evaluator.evaluate("8", POWER, "2"));
  }
  
  @Test
  void testEvaluatingParenthesizedOperands()
  {
    assertEquals("13.0", Evaluator.evaluate("7+11", SUBTRACTION, "5"));
    assertEquals("-9.0", Evaluator.evaluate("-2+8", SUBTRACTION, "15"));
    assertEquals("42.0", Evaluator.evaluate("4—1", MULTIPLICATION, "7×2"));
    assertEquals("10.0", Evaluator.evaluate("2+2+2+2", ADDITION, "4×0.5"));
    assertEquals("484.0", Evaluator.evaluate("3^3—5", POWER, "-8+10"));
  }
  
  @Test
  void testEvaluatingPEMDASOrder()
  {
    assertEquals("12.0", Evaluator.evaluate("4+2×3—2", ADDITION, "2+8÷4"));
    assertEquals("12.0", Evaluator.evaluate("9—3+4", ADDITION, "3—2+1"));
    assertEquals("18.0", Evaluator.evaluate("20÷4×3", ADDITION, "4×3÷4"));
  }
  
  @Test
  void testEvaluatingBasicImaginaryUnits()
  {
    assertEquals("3.0𝑖", Evaluator.evaluate("-5𝑖", ADDITION, "8𝑖"));
    assertEquals("5.0𝑖", Evaluator.evaluate("2𝑖", ADDITION, "3𝑖"));
    assertEquals("4.0𝑖", Evaluator.evaluate("6𝑖", SUBTRACTION, "2𝑖"));
    assertEquals("-4.0𝑖", Evaluator.evaluate("-7𝑖", SUBTRACTION, "-3𝑖"));
    assertEquals("-12.0", Evaluator.evaluate("6𝑖", MULTIPLICATION, "2𝑖"));
    assertEquals("12.0𝑖", Evaluator.evaluate("6𝑖", MULTIPLICATION, "2"));
    assertEquals("-3.0", Evaluator.evaluate("6𝑖", DIVISION, "2𝑖"));
    assertEquals("3.0", Evaluator.evaluate("6𝑖", DIVISION, "-2𝑖"));
    assertEquals("-3.0𝑖", Evaluator.evaluate("-6𝑖", DIVISION, "2"));
    assertEquals("0.333𝑖", Evaluator.evaluate("2𝑖", DIVISION, "6"));
  }
  
  @Test
  void testEvaluatingSquareRoots()
  {
    assertEquals("9.0", Evaluator.evaluate("50+31", SQUARE_ROOT, ""));
    assertEquals("9.0𝑖", Evaluator.evaluate("-50—31", SQUARE_ROOT, ""));
    assertEquals("2.121+2.121𝑖", Evaluator.evaluate("9𝑖", SQUARE_ROOT, ""));
    assertEquals("2.121—2.122𝑖", Evaluator.evaluate("-9𝑖", SQUARE_ROOT, ""));
    assertEquals("3.466+1.009𝑖", Evaluator.evaluate("3+8+9𝑖—2𝑖", SQUARE_ROOT, ""));
  }
  
  @Test
  void testEvaluatingLogarithms()
  {
    assertEquals("1.908", Evaluator.evaluate("50+31", LOG, ""));
    assertEquals("1.908", Evaluator.evaluate("-50—31", LOG, ""));
    assertEquals("0.954+0.682𝑖", Evaluator.evaluate("9𝑖", LOG, ""));
    assertEquals("0.954—0.683𝑖", Evaluator.evaluate("-9𝑖", LOG, ""));
    assertEquals("1.115+0.246𝑖", Evaluator.evaluate("3+8+9𝑖—2𝑖", LOG, ""));
  }
  
  @Test
  void testEvaluatingConjugate()
  {
    assertEquals("2.0—2.0𝑖", Evaluator.evaluate("2+2𝑖", CONJUGATE, ""));
    assertEquals("2.0+2.0𝑖", Evaluator.evaluate("2—2𝑖", CONJUGATE, ""));
    assertEquals("-3.0𝑖", Evaluator.evaluate("3𝑖", CONJUGATE, ""));
    assertEquals("3.0", Evaluator.evaluate("3", CONJUGATE, ""));
  }
 
  @Test
  void testEvaluatingInverse()
  {
    assertEquals("0.25—0.25𝑖", Evaluator.evaluate("2+2𝑖", INVERT, ""));
    assertEquals("0.25+0.25𝑖", Evaluator.evaluate("2—2𝑖", INVERT, ""));
    assertEquals("-0.334𝑖", Evaluator.evaluate("3𝑖", INVERT, ""));
    assertEquals("0.333", Evaluator.evaluate("3", INVERT, ""));
  }
  
  @Test
  void testEvaluatingParenthesizedImaginaryUnits()
  {
    assertEquals("4.0+5.0𝑖", Evaluator.evaluate("2+2𝑖", ADDITION, "2+3𝑖"));
    assertEquals("-2.0+10.0𝑖", Evaluator.evaluate("2+2𝑖", MULTIPLICATION, "2+3𝑖"));
    assertEquals("2.0+6.0𝑖", Evaluator.evaluate("2+4𝑖", ADDITION, "2𝑖"));
    assertEquals("8.0+3.0𝑖", Evaluator.evaluate("8+6𝑖", SUBTRACTION, "3𝑖"));
    assertEquals("3.0—9.0𝑖", Evaluator.evaluate("3—6𝑖", SUBTRACTION, "3𝑖"));
    assertEquals("11.0+3.0𝑖", Evaluator.evaluate("8+6𝑖", ADDITION, "2—3𝑖+1"));
    assertEquals("8.0+2.599𝑖", Evaluator.evaluate("8—6𝑖", ADDITION, "4×2𝑖+3𝑖÷5"));
    assertEquals("-0.075+0.15𝑖", Evaluator.evaluate("-3÷4𝑖", DIVISION, "2𝑖+4"));
  }

  @Test
  void testEvaluatingComplexExponentiation()
  {
    assertEquals("64.0^𝑖", Evaluator.evaluate("4", POWER, "3𝑖")); // Should return simplified.
    assertEquals("-64.0^-𝑖", Evaluator.evaluate("-4", POWER, "-3𝑖")); // Should return simplified.
    assertEquals("-64.0^𝑖", Evaluator.evaluate("-4", POWER, "3𝑖")); // Should return simplified.
    assertEquals("2.0^𝑖", Evaluator.evaluate("4", POWER, "0.5𝑖")); // Should return simplified.
    assertEquals("3.0+4.0𝑖^3.0𝑖", Evaluator.evaluate("3+4𝑖", POWER, "3𝑖")); // Should return as is.
    assertEquals("-3.0𝑖", Evaluator.evaluate("3𝑖", POWER, "3"));
    assertEquals("3.0", Evaluator.evaluate("3𝑖", POWER, "4"));
    assertEquals("3.0𝑖", Evaluator.evaluate("3𝑖", POWER, "5"));
    assertEquals("-3.0", Evaluator.evaluate("3𝑖", POWER, "6"));
    assertEquals("4.0𝑖^2.0+3.0𝑖", Evaluator.evaluate("4𝑖", POWER, "3𝑖+2")); 
    assertEquals("4.0^2.0+3.0𝑖", Evaluator.evaluate("4", POWER, "3𝑖+2"));// Should return as is.
    assertEquals("-6.75—9.0𝑖", Evaluator.evaluate("1.5—3.0𝑖", POWER, "2"));
    assertEquals("0.011—0.039𝑖", Evaluator.evaluate("4+3𝑖", POWER, "-2"));
  }
}
