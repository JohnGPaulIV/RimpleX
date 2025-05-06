package utilities;

import static rimplex.RimpleX.rb;

import java.util.Locale;

import javax.swing.JOptionPane;

/**
 * Represents a number in complex form consisting of a real part and an imaginary part.
 * 
 * @author Joseph Pogoretskiy, Sofia Miller
 */
public class Complex
{
  private static final String ADDITION = "+";
  private static final String IMAGINARY_UNIT = "𝑖";
  private static final String SUBTRACTION = "—";
  private static final String NEGATIVE = "-";
  private double real;

  private double imaginary;

  private boolean imaginaryUnitPresent = true;

  /**
   * Explicit constructor of a new complex number.
   * 
   * @param real
   *          The real part of the complex number.
   * @param imaginary
   *          The imaginary part of the complex number.
   */
  public Complex(final double real, final double imaginary)
  {
    this.real = real;
    this.imaginary = imaginary;
  }

  /**
   * Add two complex numbers together.
   * 
   * @param other
   *          The summand.
   * @return Return the sum of two complex numbers.
   */
  public Complex add(final Complex other)
  {
    double realNum = this.real + other.real;
    double imaginaryNum = this.imaginary + other.imaginary;
    return new Complex(realNum, imaginaryNum);
  }

  /**
   * Calculate whether one complex number is greater than the other.
   * 
   * @return Return whether the complex number is greater than the other.
   */
  public boolean greaterThan(final Complex other)
  {
    boolean result;
    if (this.real > other.real)
    {
      result = true;
    }
    else if (this.real == other.real)
    {
      if (this.imaginary >= other.imaginary)
      {
        result = true;
      }
      else
      {
        result = false;
      }
    }
    else
    {
      result = false;
    }
    return result;
  }

  /**
   * Calculate whether one complex number is less than the other.
   * 
   * @return Return whether the complex number is less than the other.
   */
  public boolean lessThan(final Complex other)
  {
    boolean result;
    if (this.real < other.real)
    {
      result = true;
    }
    else if (this.real == other.real)
    {
      if (this.imaginary <= other.imaginary)
      {
        result = true;
      }
      else
      {
        result = false;
      }
    }
    else
    {
      result = false;
    }
    return result;
  }

  /**
   * Conjugate the complex number.
   */
  public void conjugate()
  {
    if (this.imaginary != 0.0)
    {
      this.imaginary = -(this.imaginary);
    }
  }

  /**
   * Divide two complex numbers.
   * 
   * @param other
   *          The dividend
   * @return Return the quotient of two complex numbers.
   */
  public Complex divide(final Complex other)
  {
    double denominator = other.real * other.real + other.imaginary * other.imaginary;
    if (denominator == 0)
    {
      JOptionPane.showMessageDialog(null, rb.getString("Divide_By_Zero_Error"),
          rb.getString("Error"), JOptionPane.ERROR_MESSAGE);
      // throw new ArithmeticException("Cannot divide by zero");
    }
    double realPart = ((this.real * other.real + this.imaginary * -(other.imaginary))
        / denominator);
    double imaginaryPart = ((this.imaginary * other.real - this.real * other.imaginary)
        / denominator);
    return new Complex(realPart, imaginaryPart);
  }

  /**
   * Raise one complex number to the other complex number.
   * 
   * @param other
   *          The power to raise to.
   * @return Return the result of the calculation in complex form.
   */
  public Complex exponentiate(final Complex other)
  {
    Complex result = null;
    if (this.real != 0.0 && this.imaginary == 0.0)
    {
      if (other.imaginary == 0.0)
      {
        result = new Complex((Math.pow(this.real, other.real)), 0.0);
      }
    }
    else if (this.real == 0.0 && this.imaginary != 0.0)
    {
      if (other.imaginary == 0.0)
      {
        int remainder = (int) other.real % 4;
        switch (remainder)
        {
          case 0:
            result = new Complex(this.imaginary, 0.0);
            break;
          case 1:
            result = new Complex(0.0, this.imaginary);
            break;
          case 2:
            result = new Complex(-(this.imaginary), 0.0);
            break;
          case 3:
            result = new Complex(0.0, -(this.imaginary));
            break;
          default:
            result = new Complex(0.0, 0.0);
            break;
        }
      }
    }
    else
    {
      if (other.imaginary == 0.0)
      {
        if (other.real == 0.0)
        {
          result = new Complex(1.0, 0.0);
        }
        else if (other.real % 1 == 0)
        {
          result = new Complex(this.real, this.imaginary);
          for (int i = 1; i < (int) Math.abs(other.real); i++)
          {
            result = result.multiply(this);
          }
          if (other.real < 0.0)
          {
            result.inverse();
          }
        }
      }
    }
    return result;
  }

  /**
   * Create a real number in complex form.
   * 
   * @param real
   *          The real number to use.
   * @return Return a new Complex number with only the real part non-zero.
   */
  public static Complex fromReal(final double real)
  {
    return new Complex(real, 0.0);
  }

  /**
   * Get the imaginary part of the complex number.
   * 
   * @return Return imaginary number.
   */
  public double getImaginary()
  {
    return imaginary;
  }

  /**
   * Get polar form of the complex number.
   * 
   * @return A string representation of the polar form of the number.
   */
  public String getPolarForm()
  {
    String doubleClosed = "))";
    String result = null;
    if (this.real != 0.0 && this.imaginary != 0.0)
    {
      Double modulus = Math.sqrt((this.real * this.real) + (this.imaginary * this.imaginary));
      Double argument = Math.atan(this.imaginary / this.real);
      result = String.valueOf(modulus) + "((cos" + String.valueOf(argument) + ") + " + "i sin("
          + String.valueOf(argument) + doubleClosed;
    }
    else if (this.real != 0.0 && this.imaginary == 0.0)
    {
      if (this.real > 0)
      {
        result = String.valueOf(this.real) + "(cos(0)" + " + i sin(0))";
      }

      else
      {
        result = String.valueOf(this.real) + "(cos(π)" + " + i sin(π))";
      }
    }
    else if (this.real == 0.0 && this.imaginary != 0.0)
    {
      String argument;
      Double modulus = Math.sqrt(this.imaginary + this.imaginary);
      if (this.imaginary > 0.0)
      {
        argument = "π/2";
      }
      else
      {
        argument = "3π/2";
      }
      result = String.valueOf(modulus) + "(cos(" + argument + ")" + " + i sin(" + argument
          + doubleClosed;
    }
    return result;
  }

  /**
   * Get the real part of the complex number.
   * 
   * @return Return real number.
   */
  public double getReal()
  {
    return real;
  }

  /**
   * Inverse the complex number (multiplicative).
   */
  public void inverse()
  {
    Complex numerator = new Complex(this.real, this.imaginary);
    numerator.conjugate();

    Complex denominator = new Complex(this.real, this.imaginary).multiply(numerator);
    Complex result = numerator.divide(denominator);

    this.real = result.real;
    this.imaginary = result.imaginary;
  }

  /**
   * Calculate the logarithm (base 10) of the current complex number.
   */
  public void logarithm()
  {
    if (this.real == 0 && this.imaginary == 0)
    {
      throw new ArithmeticException("Cannot log by zero.");
    }
    double magnitude = Math.sqrt(this.real * this.real + this.imaginary * this.imaginary);
    double argument = (Math.atan(this.imaginary / this.real));

    double naturalLogReal = Math.log(magnitude);
    double naturalLogImaginary = argument;

    this.real = naturalLogReal / Math.log(10);
    this.imaginary = naturalLogImaginary / Math.log(10);
  }

  /**
   * Multiply two complex numbers together.
   * 
   * @param other
   *          The multiplicand.
   * @return Return the product of two complex numbers.
   */
  public Complex multiply(final Complex other)
  {
    if (this.imaginary != 0.0 && other.imaginary != 0.0)
    {
      imaginaryUnitPresent = !imaginaryUnitPresent;
    }
    double realPart = this.real * other.real - this.imaginary * other.imaginary;
    double imaginaryPart = this.real * other.imaginary + this.imaginary * other.real;
    return new Complex(realPart, imaginaryPart);
  }

  /**
   * Parse a string of inputs to create the appropriate representation of a complex number.
   * 
   * @param input
   *          The string to parse.
   * @return Return a new Complex number.
   */
  public static Complex parse(final String input)
  {
    // If in complex form.
    String copy = new String(input).replace(",", "");
    Complex result;
    if (copy.contains(ADDITION))
    {
      String[] parts = copy.split("\\+");
      if (parts[0].contains(IMAGINARY_UNIT))
      {
        parts[0] = parts[0].replace(IMAGINARY_UNIT, "");
        result = new Complex(Double.parseDouble(parts[1]), Double.parseDouble(parts[0]));
      }
      else
      {
        parts[1] = parts[1].replace(IMAGINARY_UNIT, "");
        result = new Complex(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]));
      }
    }
    else if (copy.contains(SUBTRACTION))
    {
      // Distinguish between negative numbers and subtraction operation.
      int index = copy.lastIndexOf(SUBTRACTION);
      if (index == 0)
      {
        copy = copy.substring(1);
        index = copy.indexOf(SUBTRACTION, 1);
        // if (index == -1)
        // {
        copy = copy.replace(IMAGINARY_UNIT, "");
        result = new Complex(-Double.parseDouble(copy), 0);
        // }
        // else
        // {
        // copy = copy.replace(IMAGINARY_UNIT, "");
        // result = new Complex(-Double.parseDouble(copy.substring(0, index)),
        // -Double.parseDouble(copy.substring(index + 1)));
        // }
      }
      else
      {
        copy = copy.replace(IMAGINARY_UNIT, "");
        result = new Complex(Double.parseDouble(copy.substring(0, index)),
            -Double.parseDouble(copy.substring(index + 1)));
      }
    }
    else if (copy.contains(IMAGINARY_UNIT))
    {
      copy = copy.replace(IMAGINARY_UNIT, "");
      result = new Complex(0.0, Double.parseDouble(copy));
    }
    else
    {
      if (copy.isBlank())
      {
        result = new Complex(0.0, 0.0);
      }
      else
      {
        result = new Complex(Double.parseDouble(copy), 0.0);
      }
    }
    return result;
  }

  /**
   * Square root the complex number.
   */
  public void squareRoot()
  {
    double modulus = Math.sqrt(this.real * this.real + this.imaginary * this.imaginary);
    double realPart = Math.sqrt((this.real + modulus) / 2);
    double imaginaryPart;
    if (this.imaginary != 0.0)
    {
      imaginaryPart = this.imaginary / (2 * realPart);
    }
    else
    {
      if (this.real >= 0.0)
      {
        imaginaryPart = 0.0;
      }
      else
      {
        realPart = 0.0;
        imaginaryPart = Math.sqrt(-this.real);
      }
    }
    this.real = realPart;
    this.imaginary = imaginaryPart;
  }

  /**
   * Subtract two complex numbers together.
   * 
   * @param other
   *          The subtrahend.
   * @return Return the difference of two complex numbers.
   */
  public Complex subtract(final Complex other)
  {
    double realNum = this.real - other.real;
    double imaginaryNum = this.imaginary - other.imaginary;
    return new Complex(realNum, imaginaryNum);
  }

  @Override
  public String toString()
  {
    String result;
    String sign = (imaginary >= 0) ? ADDITION : NEGATIVE;
    String unit = (imaginaryUnitPresent) ? IMAGINARY_UNIT : "";
    if (imaginary != 0.0)
    {
      if (real == 0.0)
      {
        // If there is only a negative imaginary number.
        if (sign.equals(NEGATIVE))
        {
          result = NEGATIVE + String.format(getDecimalFormat(Math.abs(this.imaginary)), Math.abs(this.imaginary)) + unit;
        }
        else
        {
          // If there is only a positive imaginary number
          result = String.format(getDecimalFormat(this.imaginary), this.imaginary) + unit;
        }
      }
      // If both parts are present, making it in complex number form.
      else if (sign.equals(ADDITION))
      {
        result = String.format(getDecimalFormat(this.real), this.real) + sign
            + String.format(getDecimalFormat(this.imaginary), this.imaginary) + unit;
      }
      else
      {
        result = String.format(getDecimalFormat(this.real), this.real) + SUBTRACTION
            + String.format(getDecimalFormat(Math.abs(this.imaginary)), Math.abs(this.imaginary)) + unit;
      }
    }
    else
    {
      result = String.format(getDecimalFormat(this.real), this.real);
    }
    return result;
  }

  /**
   * Get the decimal format based on current preferences.
   * 
   * @return The decimal format string for String formatting.
   */
  private String getDecimalFormat(final double num)
  {
    String format;
    String locale = Locale.getDefault().getLanguage();
    String thousandsSeparator;
    System.out.println(locale);
    if (!locale.equals("en"))
    {
      thousandsSeparator = " ";
    }
    else
    {
      thousandsSeparator = ",";
    }
    if (num % 1 == 0)
    {
      format = "%." + String.valueOf(RimpleXPreferences.getTrailingZeroes()) + "f";
    }
    else
    {
      format = "%." + String.valueOf(RimpleXPreferences.getNumOfDecimals()) + "f";
    }
    if (RimpleXPreferences.getDisplaySeparators())
    {
      format = format.substring(0, 1) + thousandsSeparator + format.substring(1, format.length());
      System.out.println(format);
    }
    return format;
  }
}
