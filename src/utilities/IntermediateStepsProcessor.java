package utilities;

import java.util.ArrayList;
import java.util.List;

/**
 * Steps for the intermediate steps.
 * 
 * This work complies with JMU Honor Code.
 */
public class IntermediateStepsProcessor
{
  public static List<String> getIntermediateSteps(String equation)
  {
    List<String> steps = new ArrayList<>();
    int stepCount = 0;
    String counter = "Step " + ++stepCount + ": ";

    // addition
    if (equation.contains("+"))
    {
      steps.add(counter + "Identify real and imaginary components in " + equation);
      steps.add(counter + "Add real parts separately.");
      steps.add(counter + "Add imaginary parts separately.");
    }

    // subtraction
    if (equation.contains("—"))
    {
      steps.add(counter + "Identify real and imaginary components in " + equation);
      steps.add(counter + "Subtract real parts separately.");
      steps.add(counter + "Subtract imaginary parts separately");
    }

    // multiplication
    if (equation.contains("*"))
    {
      steps.add(counter + "Expand distributively for " + equation);
      steps.add(counter + "Multiply real parts separately.");
      steps.add(counter + "Multiply imaginary parts separately.");
      steps.add(counter + "Multiply cross terms involving 'i'.");
      steps.add(counter + "Combine like terms and simplify.");
    }

    // division
    if (equation.contains("/"))
    {
      steps.add(counter + "Multiply numerator and denominator by the conjugates of " + equation);
      steps.add(counter + "Expand using distributive property.");
      steps.add(counter + "Compute real and imaginary parts separately.");
      steps.add(counter + "Divide by the squared magnitude of the denominator.");
    }

    // conjugates
    if (equation.contains("conj"))
    {
      steps.add(counter + "Identify complex conjugate.");
      steps.add(counter + "Reverse the sign of the imaginary component.");
    }

    // exponents
    if (equation.contains("^"))
    {
      steps.add(counter + "Convert complex number to polar form.");
      steps.add(counter + "Apply De Moivre's theorem.");
      steps.add(counter + "Expand if required.");
    }

    // logarithms
    if (equation.contains("Log"))
    {
      steps.add(counter + "Convert complex number to polar form.");
      steps.add(counter + "Apply logarithm properties.");
      steps.add(counter + "Compute θ using tan^-1(b/a).");
    }

    // square root
    if (equation.contains("Sqrt"))
    {
      steps.add(counter + "Convert complex number to polar form.");
      steps.add(counter + "Compute square root using √r and halving θ");
      steps.add(counter + "Convert back to rectangular form if required.");
    }

    // polar form
    if (equation.contains("Plr"))
    {
      steps.add(counter + "Compute modulus r = sqrt(a^2 + b^2).");
      steps.add(counter + "Compute angle θ using tan^-1(b/a).");
      steps.add(counter + "Express result as r ∠ θ.");
    }

    // inverting
    if (equation.contains("1/"))
    {
      steps.add(counter + "Multiply by conjugate (a - bi) in numerator & denominator.");
      steps.add(counter + "Expand using distributive property.");
      steps.add(counter + "Divide by squared modulus a^2 + b^2.");
    }

    // greater than
    if (equation.contains(">"))
    {
      steps.add(counter + "If |a + bi| > |c + di|, then (a + bi) is greater.");
    }

    if (equation.contains("<"))
    {
      steps.add(counter + "If |a + bi| < |c + di|, then (a + bi) is smaller.");
    }

    // sign
    if (equation.contains("sign"))
    {
      steps.add(counter + "Flip signs for real and imaginary components.");
      steps.add(counter + "Preserve formatting for correctness.");
    }

    return steps;
  }
}
