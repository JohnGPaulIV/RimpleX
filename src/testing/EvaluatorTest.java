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
  void testEvaluatingParenthesizedOperands()
  {
    assertEquals("13.0", Evaluator.evaluate("7+11", SUBTRACTION, "5"));
    assertEquals("-9.0", Evaluator.evaluate("-2+8", SUBTRACTION, "15"));
    assertEquals("42.0", Evaluator.evaluate("4—1", MULTIPLICATION, "7×2"));
    assertEquals("10.0", Evaluator.evaluate("2+2+2+2", ADDITION, "4×0.5"));
  }

}
