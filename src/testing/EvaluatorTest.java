package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import utilities.Evaluator;

class EvaluatorTest
{
  static final String SUBTRACTION = "—";
  static final String ADDITION = "+";
  static final String MULTIPLICATION = "×";
  static final String DIVISION = "÷";
  static final String NEGATIVE = "-";
  static final String POWER = "^";

  @Test
  void testEvaluatingBasicSubtraction()
  {
    assertEquals("5.0", Evaluator.evaluate("4", SUBTRACTION, "-1"));
  }
  
  @Test
  void testEvaluatingBasicAddition()
  {
    assertEquals("10.0", Evaluator.evaluate("8", ADDITION, "2"));
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
  void testEvaluatingBasicImaginaryUnits()
  {
    assertEquals("5.0𝑖", Evaluator.evaluate("2𝑖", ADDITION, "3𝑖"));
    assertEquals("4.0𝑖", Evaluator.evaluate("6𝑖", SUBTRACTION, "2𝑖"));
    assertEquals("-12.0", Evaluator.evaluate("6𝑖", MULTIPLICATION, "2𝑖"));
    assertEquals("12.0𝑖", Evaluator.evaluate("6𝑖", MULTIPLICATION, "2"));
    assertEquals("-3.0", Evaluator.evaluate("6𝑖", DIVISION, "2𝑖"));
    assertEquals("3.0", Evaluator.evaluate("6𝑖", DIVISION, "-2𝑖"));
    assertEquals("-3.0𝑖", Evaluator.evaluate("-6𝑖", DIVISION, "2"));
    assertEquals("0.3333333333333333𝑖", Evaluator.evaluate("2𝑖", DIVISION, "6"));
  }
  
  @Test
  void testEvaluatingParenthesizedImaginaryUnits()
  {
    assertEquals("4+5𝑖", Evaluator.evaluate("2+2𝑖", ADDITION, "2+3𝑖"));
    assertEquals("4+5𝑖", Evaluator.evaluate("2+4𝑖", ADDITION, "2𝑖"));
    assertEquals("4+5𝑖", Evaluator.evaluate("8+6𝑖", SUBTRACTION, "2—3𝑖+1"));
  }

}
