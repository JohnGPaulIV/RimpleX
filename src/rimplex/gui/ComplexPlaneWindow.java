package rimplex.gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import utilities.Complex;

public class ComplexPlaneWindow extends JFrame
{
  private static final long serialVersionUID = 1L;
  private Complex number;

  public ComplexPlaneWindow(Complex number)
  {
    super("Complex Plane");
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
      double scale = calculateScale(number);
      int x = (int) (realPart * scale);
      int y = (int) (imaginaryPart * scale);
      g.setColor(Color.BLACK);
      g.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
      g.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
      g.drawString("Re", getWidth() - 30, getHeight() / 2 - 5);
      g.drawString("Im", getWidth() / 2 + 5, 15);
      g.setColor(Color.RED);
      g.fillOval(getWidth() / 2 + x - 5, getHeight() / 2 - y - 5, 10, 10);
      g.setColor(Color.BLACK);
      g.drawString(number.toString(), getWidth() / 2 + x + 10, getHeight() / 2 - y);
    }

    public static double calculateScale(Complex c)
    {
      double maxAbs = Math.max(Math.abs(c.getReal()), Math.abs(c.getImaginary()));
      int scale;
      if (maxAbs < 10)
      {
        scale = 2;
      }
      else if (maxAbs < 100)
      {
        scale = 10;
      }
      else if (maxAbs < 500)
      {
        scale = 50;
      }
      else
      {
        scale = 100;
      }
      return scale;
      // if (maxAbs == 0)
      // {
      // return 2;
      // }
      // int scale = (int) (maxAbs / 10);
      // scale = ((scale + 4) / 5) * 5;
      // scale = Math.max(2, Math.min(scale, 1000));
      // return scale;
    }
  }
}
