package rimplex.gui;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import utilities.Complex;
import static rimplex.RimpleX.*;

/**
 * The Complex Plane Window.
 *
 * @author Sofia Miller
 *
 *         This work complies with JMU Honor Code.
 */
public class ComplexPlaneWindow extends JFrame
{
  private static final long serialVersionUID = 1L;
  private static final String DECIMAL_PLACEMENT = "%.0f";
  private Complex number;

  /**
   * Creates an instance of a complex plane window.
   *
   * @param number
   */
  public ComplexPlaneWindow(final Complex number)
  {
    super(rb.getString("Complex_Plane"));
    this.number = number;
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setSize(500, 500);
    setLocationRelativeTo(null);
    ComplexPlanePanel panel = new ComplexPlanePanel(number);
    add(panel);
    setVisible(true);
  }

  /**
   * Drawing the Complex Plane.
   */
  private static class ComplexPlanePanel extends JPanel
  {
    private static final long serialVersionUID = 1L;
    private Complex number;

    public ComplexPlanePanel(final Complex number)
    {
      this.number = number;
    }

    @Override
    protected void paintComponent(final Graphics g)
    {
      super.paintComponent(g);
      double realPart = number.getReal();
      double imaginaryPart = number.getImaginary();
      int width = getWidth();
      int height = getHeight();
      int centerX = width / 2;
      int centerY = height / 2;
      double[] scales = calculateScalesAndIntervals();
      double scaleX = scales[0];
      double scaleY = scales[1];
      double tickIntervalX = scales[2];
      double tickIntervalY = scales[3];
      int tickSize = 4;
      g.setColor(Color.BLACK);
      g.drawLine(centerX, 0, centerX, height); // y-axis
      g.drawLine(0, centerY, width, centerY); // x-axis
      g.drawString("Re", width - 30, centerY - 5);
      g.drawString("Im", centerX + 5, 15);
      // x-axis
      double realStart = -((double) width / (2 * scaleX));
      double realEnd = (double) width / (2 * scaleX);
      for (double i = Math.floor(realStart / tickIntervalX)
          * tickIntervalX; i <= realEnd; i += tickIntervalX)
      {
        int screenX = (int) (centerX + i * scaleX);
        g.drawLine(screenX, centerY - tickSize, screenX, centerY + tickSize);
        if (Math.abs(i) > 1e-6)
        {
          g.drawString(String.format(DECIMAL_PLACEMENT, i), screenX - 10, centerY + 15);
        }
      }
      // y-axis
      double imaginaryStart = -((double) height / (2 * scaleY));
      double imaginaryEnd = (double) height / (2 * scaleY);
      for (double j = Math.floor(imaginaryStart / tickIntervalY)
          * tickIntervalY; j <= imaginaryEnd; j += tickIntervalY)
      {
        int screenY = (int) (centerY - j * scaleY);
        g.drawLine(centerX - tickSize, screenY, centerX + tickSize, screenY);
        if (Math.abs(j) > 1e-6)
        {
          g.drawString(String.format(DECIMAL_PLACEMENT, j), centerX + 5, screenY + 5);
        }
      }
      int x = (int) (realPart * scaleX);
      int y = (int) (imaginaryPart * scaleY);
      g.setColor(Color.RED);
      g.fillOval(centerX + x - 5, centerY - y - 5, 10, 10);
      g.setColor(Color.BLACK);
      g.drawString(number.toString(), centerX + x + 10, centerY - y);
    }

    /**
     * Calculating the scale and tick intervals for the graph.
     *
     * @return the scale and interval
     */
    private double[] calculateScalesAndIntervals()
    {
      double real = Math.abs(number.getReal());
      double imaginary = Math.abs(number.getImaginary());
      double paddingFactor = 1.2;
      double minVisibleRange = 10.0;
      double realRange = Math.max(real * paddingFactor, minVisibleRange);
      double imaginaryRange = Math.max(imaginary * paddingFactor, minVisibleRange);
      double maxRange = Math.max(realRange, imaginaryRange);
      double scaleFactor = (0.45 * Math.min(getWidth(), getHeight())) / maxRange;
      double scale = Math.max(0.1, scaleFactor);
      double tickInterval = determineTickInterval(maxRange);
      return new double[] {scale, scale, tickInterval, tickInterval};
    }

    /**
     * Calculating the tick intervals.
     *
     * @param maxVal
     *          the max value for the axes.
     * @return the Tick interval.
     */
    private double determineTickInterval(final double maxVal)
    {
      if (maxVal == 0)
        return 1;
      int magnitude = (int) Math.floor(Math.log10(maxVal));
      double base = Math.pow(10, magnitude);
      double scaled = maxVal / base;
      if (scaled > 5)
      {
        base *= 2;
      }
      else if (scaled > 2.5)
      {
        base *= 1;
      }
      else
      {
        base /= 2;
      }
      return base;
    }
  }
}
