package utilities;

public class Complex
{
  private final double real;
  private final double imaginary;
  private static final String IMAGINARY_UNIT = "𝑖";
  static final String SUBTRACTION = "—";
  static final String NEGATIVE = "-";
  private boolean imaginaryUnitPresent = true;

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
    if (this.imaginary != 0.0 && other.imaginary != 0.0)
    {
      imaginaryUnitPresent = !imaginaryUnitPresent;
    }
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
    double realPart = (this.real * other.real + this.imaginary * -(other.imaginary)) / denominator;
    double imaginaryPart = (this.imaginary * other.real - this.real * other.imaginary)
        / denominator;
    return new Complex(realPart, imaginaryPart);
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
        if (sign == " - ")
        {
          return "-" + String.valueOf(this.imaginary) + unit;
        }
        return String.valueOf(this.imaginary) + unit;
      } 
      else if (sign == "+")
      {
        return String.valueOf(this.real) + sign + String.valueOf(this.imaginary) + unit;
      }
      else
      {
        return String.valueOf(this.real) + SUBTRACTION + String.valueOf(Math.abs(this.imaginary)) + unit;
      }
    } 
    return String.valueOf(this.real);
  }

  public static Complex parse(String input)
  {
//    input = input.replace(" ", "").replace("𝑖", "");
    if (input.contains("+"))
    {
      String[] parts = input.split("\\+");
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
    else if (input.contains(SUBTRACTION))
    {
      int index = input.lastIndexOf(SUBTRACTION);
      if (index == 0)
      {
        input = input.substring(1);
        index = input.indexOf(SUBTRACTION, 1);
        if (index == -1)
        {
          input = input.replace("𝑖", "");
          return new Complex(-Double.parseDouble(input), 0);
        }
        else
        {
          input = input.replace("𝑖", "");
          return new Complex(-Double.parseDouble(input.substring(0, index)),
              -Double.parseDouble(input.substring(index + 1)));
        }
      }
      input = input.replace("𝑖", "");
      return new Complex(Double.parseDouble(input.substring(0, index)),
          -Double.parseDouble(input.substring(index + 1)));
    }
    else if (input.contains("𝑖"))
    {
      input = input.replace("𝑖", "");
      return new Complex(0.0, Double.parseDouble(input));
    }
    else
    {
      return new Complex(Double.parseDouble(input), 0.0);
    }
  }

  public static Complex fromReal(double real)
  {
    return new Complex(real, 0.0);
  }
}
