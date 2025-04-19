package utilities;

/**
 * Represents a number in complex form consisting of a real part and an imaginary part.
 * 
 * @author Joseph Pogoretskiy, Sofia Miller
 */
public class Complex
{
  private static final String IMAGINARY_UNIT = "𝑖";
  private static final String SUBTRACTION = "—";
  private static final String NEGATIVE = "-";
  private double real;
  private double imaginary;
  private boolean imaginaryUnitPresent = true;
  
  /**
   * Explicit constructor of a new complex number.
   * 
   * @param real The real part of the complex number.
   * @param imaginary The imaginary part of the complex number.
   */
  public Complex(final double real, final double imaginary)
  {
    this.real = real;
    this.imaginary = imaginary;
  }

  /**
   * Add two complex numbers together.
   * 
   * @param other The summand.
   * @return Return the sum of two complex numbers.
   */
  public Complex add(final Complex other)
  {
    return new Complex(this.real + other.real, this.imaginary + other.imaginary);
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
   * @param other The dividend
   * @return Return the quotient of two complex numbers.
   */
  public Complex divide(final Complex other)
  {
    double denominator = other.real * other.real + other.imaginary * other.imaginary;
    if (denominator == 0)
    {
      throw new ArithmeticException("Cannot divide by zero");
    }
    double realPart = (this.real * other.real + this.imaginary * -(other.imaginary)) / denominator;
    double imaginaryPart = (this.imaginary * other.real - this.real * other.imaginary)
        / denominator;
    return new Complex(realPart, imaginaryPart);
  }
  
  /**
   * Raise one complex number to the other complex number.
   * 
   * @param other The power to raise to.
   * @return Return the result of the calculation in complex form.
   */
  public Complex exponentiate(final Complex other)
  {
    Complex result = null;
    if (this.real != 0.0 && this.imaginary == 0.0)
    {
      if (other.imaginary == 0.0)
      {
        result = new Complex(Math.pow(this.real, other.real), 0.0);
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
        }
      }
    }
    return result;
  }

  /**
   * Create a real number in complex form.
   * 
   * @param real The real number to use.
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
   * Get the real part of the complex number.
   * 
   * @return Return real number.
   */
  public double getReal()
  {
    return real;
  }

  /**
   * Multiply two complex numbers together.
   * 
   * @param other The multiplicand.
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
   * @param input The string to parse.
   * @return Return a new Complex number.
   */
  public static Complex parse(final String input)
  {
    // If in complex form.
    String copy = new String(input);
    if (copy.contains("+"))
    {
      String[] parts = copy.split("\\+");
      if (parts[0].contains("𝑖"))
      {
        parts[0] = parts[0].replace("𝑖", "");
        return new Complex(Double.parseDouble(parts[1]), Double.parseDouble(parts[0]));
      }
      else
      {
        parts[1] = parts[1].replace("𝑖", "");
        return new Complex(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]));
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
        if (index == -1)
        {
          copy = copy.replace("𝑖", "");
          return new Complex(-Double.parseDouble(copy), 0);
        }
        else
        {
          copy = copy.replace("𝑖", "");
          return new Complex(-Double.parseDouble(copy.substring(0, index)),
              -Double.parseDouble(copy.substring(index + 1)));
        }
      }
      copy = copy.replace("𝑖", "");
      return new Complex(Double.parseDouble(copy.substring(0, index)),
          -Double.parseDouble(copy.substring(index + 1)));
    }
    else if (copy.contains("𝑖"))
    {
      copy = copy.replace("𝑖", "");
      return new Complex(0.0, Double.parseDouble(copy));
    }
    else
    {
      return new Complex(Double.parseDouble(copy), 0.0);
    }
  }

  /**
   * Subtract two complex numbers together.
   * 
   * @param other The subtrahend.
   * @return Return the difference of two complex numbers.
   */
  public Complex subtract(final Complex other)
  {
    return new Complex(this.real - other.real, this.imaginary - other.imaginary);
  }

  @Override
  public String toString()
  {
//    String realStr = String.format("%.2f", real);
//    String imaginaryStr = String.format("%.2f", Math.abs(imaginary)) + "𝑖";
    String sign = (imaginary >= 0) ? "+" : "-";
    String unit = (imaginaryUnitPresent) ? IMAGINARY_UNIT : "";
    if (imaginary != 0.0) 
    {
      if (real == 0.0)
      {
        // If there is only a negative imaginary number.
        if (sign == " - ")
        {
          return "-" + String.valueOf(this.imaginary) + unit;
        }
        // If there is only a positive imaginary number
        return String.valueOf(this.imaginary) + unit;
      } 
      // If both parts are present, making it in complex number form.
      else if (sign == "+")
      {
        return String.valueOf(this.real) + sign + String.valueOf(this.imaginary) + unit;
      }
      else
      {
        return String.valueOf(this.real) + SUBTRACTION + String.valueOf(Math.abs(this.imaginary))
          + unit;
      }
    } 
    return String.valueOf(this.real);
  }
}
