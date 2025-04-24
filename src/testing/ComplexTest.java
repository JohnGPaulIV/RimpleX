package testing;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utilities.Complex;

/**
 * Tests complex class to ensure both real and imaginary numbers are displayed properly.
 * 
 * @author Kalani Johnson
 * 
 *         This work complies with JMU Honor Code.
 */

public class ComplexTest
{
  private Complex a;
  private Complex b;
  private Complex cImagZero;
  private Complex cRealZero;

  @BeforeEach
  public void setUp()
  {
    a = new Complex(2, 3);
    b = new Complex(4, 8);
    cImagZero = new Complex(1.0, 0.0);
    cRealZero = new Complex(0.0, 1.0);
  }

  @Test
  public void testConstructorAndGetters()
  {
    assertEquals(2, a.getReal());
    assertEquals(3, a.getImaginary());
    assertEquals(4, b.getReal());
    assertEquals(8, b.getImaginary());
  }

  @Test
  public void testAdd()
  {
    Complex result = a.add(b);
    assertEquals(6, result.getReal());
    assertEquals(11, result.getImaginary());
  }

  @Test
  public void testSubtract()
  {
    Complex result = a.subtract(b);
    assertEquals(-2, result.getReal());
    assertEquals(-5, result.getImaginary());
  }

  @Test
  public void testMultiply()
  {
    Complex result = a.multiply(b);
    assertEquals(-16, result.getReal());
    assertEquals(28, result.getImaginary());
  }

  @Test
  public void testDivide()
  {
    Complex result = a.multiply(b);
    assertEquals(-16, result.getReal());
    assertEquals(28, result.getImaginary());

    Complex zero = new Complex(0.0, 0.0);
    assertThrows(NullPointerException.class, () -> a.divide(zero));
  }

  @Test
  public void testConjugate()
  {
    a.conjugate();
    assertEquals(2, a.getReal());
    assertEquals(-3, a.getImaginary());
  }

  @Test
  public void testToString()
  {
    assertEquals("2.0+3.0𝑖", a.toString());
    assertEquals("4.0+8.0𝑖", b.toString());
    Complex aNegative = new Complex(2, -3);
    assertEquals("2.0—3.0𝑖", aNegative.toString());
    Complex c = new Complex(0, 7);
    assertEquals("7.0𝑖", c.toString());
    Complex cNegative = new Complex(0, -7.);
    assertEquals("-7.0𝑖", cNegative.toString());
    Complex zeroImaginary = new Complex(4, 0);
    assertEquals("4.0", zeroImaginary.toString());
  }

  @Test
  public void testParse()
  {
    Complex c1 = Complex.parse("3.0+4.0𝑖");
    assertEquals(3.0, c1.getReal(), 0.001);
    assertEquals(4.0, c1.getImaginary(), 0.001);

    Complex c2 = Complex.parse("4.0𝑖+3.0");
    assertEquals(3.0, c2.getReal(), 0.001);
    assertEquals(4.0, c2.getImaginary(), 0.001);

    Complex c3 = Complex.parse("3.0—4.0𝑖");
    assertEquals(3.0, c3.getReal(), 0.001);
    assertEquals(-4.0, c3.getImaginary(), 0.001);

    Complex c4 = Complex.parse("-3.0—4.0𝑖");
    assertEquals(-3.0, c4.getReal(), 0.001);
    assertEquals(-4.0, c4.getImaginary(), 0.001);

    Complex cImagOnly = Complex.parse("6.0𝑖");
    assertEquals(0.0, cImagOnly.getReal(), 0.001);
    assertEquals(6.0, cImagOnly.getImaginary(), 0.001);

    Complex cNegImagOnly = Complex.parse("-7.0𝑖");
    assertEquals(0.0, cNegImagOnly.getReal(), 0.001);
    assertEquals(-7.0, cNegImagOnly.getImaginary(), 0.001);

    Complex cRealOnly = Complex.parse("8.0");
    assertEquals(8.0, cRealOnly.getReal(), 0.001);
    assertEquals(0.0, cRealOnly.getImaginary(), 0.001);

    Complex cNull = Complex.parse("");
    assertEquals(0.0, cNull.getReal(), 0.001);
    assertEquals(0.0, cNull.getImaginary(), 0.001);

    Complex cSubtractSymbolImagOnly = Complex.parse("—7.0𝑖");
    assertEquals(-7.0, cSubtractSymbolImagOnly.getReal(), 0.001);
    assertEquals(0.0, cSubtractSymbolImagOnly.getImaginary(), 0.001);

  }

  @Test
  public void testSquareRoot()
  {
    Complex realOnly = new Complex(9.0, 0.0);
    realOnly = Complex.fromReal(realOnly.getReal());
    realOnly.squareRoot();
    assertEquals(3.0, realOnly.getReal(), 0.001);
    assertEquals(0.0, realOnly.getImaginary(), 0.001);

    Complex negativeReal = new Complex(-16.0, 0.0);
    negativeReal.squareRoot();
    assertEquals(0.0, negativeReal.getReal(), 0.001);
    assertEquals(4.0, negativeReal.getImaginary(), 0.001);

    Complex sr1 = new Complex(3.0, 4.0);
    sr1.squareRoot();
    assertEquals(2.0, sr1.getReal(), 0.001);
    assertEquals(1.0, sr1.getImaginary(), 0.001);

    Complex sr2 = new Complex(-3.0, -4.0);
    sr2.squareRoot();
    assertEquals(1.0, sr2.getReal(), 0.001);
    assertEquals(-2.0, sr2.getImaginary(), 0.001);
  }

  @Test
  public void testInverse()
  {
    a.inverse();
    assertEquals(0.1538, a.getReal(), 0.001);
    assertEquals(-0.231, a.getImaginary(), 0.001);

    cImagZero.inverse();
    assertEquals(1.0, cImagZero.getReal(), 0.001);
    assertEquals(0.0, cImagZero.getImaginary(), 0.001);

    cRealZero.inverse();
    assertEquals(0.0, cRealZero.getReal(), 0.001);
    assertEquals(-1.0, cRealZero.getImaginary(), 0.001);
  }

  @Test
  public void testLogarithm()
  {
    a.logarithm();
    assertEquals(0.556, a.getReal(), 0.001);
    assertEquals(0.426, a.getImaginary(), 0.001);

    cImagZero.logarithm();
    assertEquals(0.0, cImagZero.getReal(), 0.001);
    assertEquals(0.0, cImagZero.getImaginary(), 0.001);

    cRealZero.logarithm();
    assertEquals(0.0, cRealZero.getReal(), 0.001);
    assertEquals(0.682, cRealZero.getImaginary(), 0.001);

    Complex zero = new Complex(0.0, 0.0);
    assertThrows(ArithmeticException.class, () -> zero.logarithm());
  }

  @Test
  public void testExponentiate()
  {
    Complex base1 = new Complex(2, 0);
    Complex exp1 = new Complex(3, 0);
    Complex result1 = base1.exponentiate(exp1);
    assertEquals(8.0, result1.getReal(), 0.001);
    assertEquals(0.0, result1.getImaginary(), 0.001);

    Complex base2 = new Complex(0, 1);
    Complex exp2 = new Complex(2, 0);
    Complex result2 = base2.exponentiate(exp2);
    assertEquals(-1.0, result2.getReal(), 0.001);
    assertEquals(0.0, result2.getImaginary(), 0.001);
    
    Complex base4 = new Complex(2, 0);
    Complex exp4 = new Complex(-3, 0);
    Complex result4 = base4.exponentiate(exp4);
    assertEquals(0.125, result4.getReal(), 0.001);
    assertEquals(0.0, result4.getImaginary(), 0.001);

    Complex baseCases = new Complex(0, 4);

    Complex expC0 = new Complex(4, 0);
    Complex resultC0 = baseCases.exponentiate(expC0);
    assertEquals(4.0, resultC0.getReal(), 0.001);
    
    Complex expC1 = new Complex(5, 0);
    Complex resultC1 = baseCases.exponentiate(expC1);
    assertEquals(0.0, resultC1.getReal(), 0.001);
    
    Complex expC3 = new Complex(7, 0);
    Complex resultC3 = baseCases.exponentiate(expC3);
    assertEquals(0.0, resultC3.getReal(), 0.001);
    
    Complex expCDefault = new Complex(-1, 0);
    Complex resultCDefault = baseCases.exponentiate(expCDefault);
    assertEquals(0.0, resultCDefault.getReal(), 0.001);  
    
    Complex baseZero = new Complex(0, 0);
    
    Complex expAllZero = new Complex(0, 0);
    Complex resultAllZero = baseZero.exponentiate(expAllZero);
    assertEquals(1.0, resultAllZero.getReal(), 0.001);
    
    Complex expZeroImag = new Complex(3, 0);
    Complex resultZeroImag = baseZero.exponentiate(expZeroImag);
    assertEquals(0.0, resultZeroImag.getImaginary(), 0.001);
    
    Complex baseNonZero = new Complex(2, 3);
    Complex expNegReal = new Complex(-1, 0);
    Complex resultZeroReal = baseNonZero.exponentiate(expNegReal);
    assertEquals(0.153, resultZeroReal.getReal(), 0.001);
    
  }

  @Test
  public void testGetPolarForm()
  {
    Complex z1 = new Complex(1, 1);
    String polar1 = z1.getPolarForm();
    assertTrue(polar1.contains("cos") && polar1.contains("sin"));

    Complex z2 = new Complex(3, 0);
    assertEquals("3.0(cos(0) + i sin(0))", z2.getPolarForm());

    Complex z3 = new Complex(-3, 0);
    assertEquals("-3.0(cos(π) + i sin(π))", z3.getPolarForm());

    Complex z4 = new Complex(0, 5);
    String polar4 = z4.getPolarForm();
    assertTrue(polar4.contains("π/2"));

    Complex z5 = new Complex(0, -5);
    String polar5 = z5.getPolarForm();
    assertTrue(polar5.contains("3π/2"));
  }

}
