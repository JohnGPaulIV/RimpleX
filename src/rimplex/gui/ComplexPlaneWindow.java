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
  private Complex number;

  public ComplexPlaneWindow(Complex number)
  {
    super(rb.getString("Complex_Plane"));
    this.number = number;
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setSize(400, 400);
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

    public ComplexPlanePanel(Complex number)
    {
      this.number = number;
    }

    @Override
    protected void paintComponent(Graphics g)
    {
      super.paintComponent(g);
      double realPart = number.getReal();
      double imaginaryPart = number.getImaginary();
      int width = getWidth();
      int height = getHeight();
      int centerX = width / 2;
      int centerY = height / 2;
      int[] scaleAndInterval = calculateScaleAndInterval(number);
      int scale = scaleAndInterval[0];
      int tickInterval = scaleAndInterval[1];
      int tickSize = 4;
      g.setColor(Color.BLACK);
      g.drawLine(centerX, 0, centerX, height); // y-axis
      g.drawLine(0, centerY, width, centerY); // x-axis
      g.drawString("Re", width - 30, centerY - 5);
      g.drawString("Im", centerX + 5, 15);
      // x-axis
      double realStart = -((double) width / (2 * scale));
      double realEnd = (double) width / (2 * scale);
      int startXTick = (int) Math.floor(realStart / tickInterval) * tickInterval;
      int endXTick = (int) Math.ceil(realEnd / tickInterval) * tickInterval;
      for (int i = startXTick; i <= endXTick; i += tickInterval)
      {
        int screenX = centerX + i * scale;
        g.drawLine(screenX, centerY - tickSize, screenX, centerY + tickSize);
        if (i != 0)
        {
          g.drawString(Integer.toString(i), screenX - 5, centerY + 15);
        }
      }
      // y-axis
      double imaginaryStart = -((double) height / (2 * scale));
      double imaginaryEnd = (double) height / (2 * scale);
      int startYTick = (int) Math.floor(imaginaryStart / tickInterval) * tickInterval;
      int endYTick = (int) Math.ceil(imaginaryEnd / tickInterval) * tickInterval;
      for (int j = startYTick; j <= endYTick; j += tickInterval)
      {
        int screenY = centerY - j * scale;
        g.drawLine(centerX - tickSize, screenY, centerX + tickSize, screenY);
        if (j != 0)
        {
          g.drawString(Integer.toString(j), centerX + 5, screenY + 5);
        }
      }

      int x = (int) (realPart * scale);
      int y = (int) (imaginaryPart * scale);
      g.setColor(Color.RED);
      g.fillOval(centerX + x - 5, centerY - y - 5, 10, 10);
      g.setColor(Color.BLACK);
      g.drawString(number.toString(), centerX + x + 10, centerY - y);
    }

    /**
     * Calculating the scale and tick intervals for the graph.
     * 
     * @param number
     *          The complex number result
     * @return the scale and interval
     */
    private int[] calculateScaleAndInterval(Complex number)
    {
      double real = Math.abs(number.getReal());
      double imaginary = Math.abs(number.getImaginary());
      double maxVal = Math.max(real, imaginary);
      int baseSize = Math.min(getWidth(), getHeight()) / 2;
      if (maxVal == 0)
        return new int[] {50, 1};
      double scale = baseSize / (maxVal * 1.2);
      int roundedScale = (int) Math.floor(scale);
      int tickInterval;
      if (maxVal > 1000)
      {
        tickInterval = 500;
      }
      else if (maxVal > 500)
      {
        tickInterval = 200;
      }
      else if (maxVal > 200)
      {
        tickInterval = 100;
      }
      else if (maxVal > 100)
      {
        tickInterval = 50;
      }
      else if (maxVal > 50)
      {
        tickInterval = 20;
      }
      else if (maxVal > 20)
      {
        tickInterval = 10;
      }
      else if (maxVal > 10)
      {
        tickInterval = 5;
      }
      else if (maxVal > 5)
      {
        tickInterval = 2;
      }
      else
      {
        tickInterval = 1;
      }
      return new int[] {roundedScale, tickInterval};
    }
  }
}
