package rimplex;

public class Complex
{
  private final double real;
  private final double imaginary;

  public Complex(double real, double imaginary)
  {
    this.real = real;
    this.imaginary = imaginary;
  }

  public double getReal()
  {
    return real;
  }

  public double getImaginary()
  {
    return imaginary;
  }

  public Complex add(Complex other)
  {
    return new Complex(this.real + other.real, this.imaginary + other.imaginary);
  }

  public Complex subtract(Complex other)
  {
    return new Complex(this.real - other.real, this.imaginary - other.imaginary);
  }

  public Complex multiply(Complex other)
  {
    double realPart = this.real * other.real - this.imaginary * other.imaginary;
    double imaginaryPart = this.real * other.imaginary + this.imaginary * other.real;
    return new Complex(realPart, imaginaryPart);
  }

  public Complex divide(Complex other)
  {
    double denominator = other.real * other.real + other.imaginary * other.imaginary;
    if (denominator == 0)
    {
      throw new ArithmeticException("Cannot divide by zero");
    }
    double realPart = (this.real * other.real + this.imaginary * other.imaginary) / denominator;
    double imaginaryPart = (this.imaginary * other.real - this.real * other.imaginary)
        / denominator;
    return new Complex(realPart, imaginaryPart);
  }

  @Override
  public String toString()
  {
    String realStr = String.format("%.2f", real);
    String imaginaryStr = String.format("%.2f", Math.abs(imaginary)) + "𝑖";
    String sign = (imaginary >= 0) ? " + " : " - ";
    return realStr + sign + imaginaryStr;
  }

  public static Complex parse(String input)
  {
    input = input.replace(" ", "").replace("i", "");
    if (input.contains("+"))
    {
      String[] parts = input.split("\\+");
      return new Complex(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]));
    }
    else if (input.contains("-"))
    {
      int index = input.lastIndexOf("-");
      if (index == 0)
      {
        input = input.substring(1);
        index = input.indexOf("-", 1);
        if (index == -1)
        {
          return new Complex(-Double.parseDouble(input), 0);
        }
        else
        {
          return new Complex(-Double.parseDouble(input.substring(0, index)),
              -Double.parseDouble(input.substring(index + 1)));
        }
      }
      return new Complex(Double.parseDouble(input.substring(0, index)),
          -Double.parseDouble(input.substring(index + 1)));
    }
    else
    {
      return new Complex(Double.parseDouble(input), 0);
    }
  }

  public static Complex fromReal(double real)
  {
    return new Complex(real, 0);
  }
}
