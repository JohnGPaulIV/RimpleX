package rimplex.gui;

import java.awt.Color;
import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * The main window for the RimpleX application.
 * 
 * General structure taken from Dr. Bernstein's Serialization Lab:
 * (https://w3.cs.jmu.edu/bernstdh/web/common/labs/experience_serialization/tempz/index.php)
 * 
 * This work complies with JMU Honor Code.
 */
public class RimpleXWindow extends JFrame
{
  private static final long serialVersionUID = 1L;

  private RimpleXController controller;

  /**
   * Explicit window constructor.
   * 
   * @param controller
   *          The observer for all GUI components.
   */
  public RimpleXWindow(RimpleXController controller)
  {
    super();

    // Set the controller to refer to this instance.
    this.controller = controller;
    this.controller.setWindow(this);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    Container contentPane = this.getContentPane();
    contentPane.setLayout(null);

    setupNumberPad();
    setupDisplay();

    this.setSize(400, 400);
    this.setResizable(false);
    // TODO: Add Java GUI Components to the main window here
    // TODO: Set size, layout, all those goodies. Helper functions can be utilized.
  }

  /**
   * Setup the buttons on the application.
   */
  private void setupNumberPad()
  {
    getContentPane().add(new RimpleXButton("SEVEN", "7", controller, 10, 60, 40, 40));
    getContentPane().add(new RimpleXButton("EIGHT", "8", controller, 60, 60, 40, 40));
    getContentPane().add(new RimpleXButton("NINE", "9", controller, 110, 60, 40, 40));
    getContentPane().add(new RimpleXButton("FOUR", "4", controller, 10, 110, 40, 40));
    getContentPane().add(new RimpleXButton("FIVE", "5", controller, 60, 110, 40, 40));
    getContentPane().add(new RimpleXButton("SIX", "6", controller, 110, 110, 40, 40));
    getContentPane().add(new RimpleXButton("ONE", "1", controller, 10, 160, 40, 40));
    getContentPane().add(new RimpleXButton("TWO", "2", controller, 60, 160, 40, 40));
    getContentPane().add(new RimpleXButton("THREE", "3", controller, 110, 160, 40, 40));
    getContentPane().add(new RimpleXButton("ZERO", "0", controller, 10, 210, 90, 40));

    // Add more buttons as new capabilities are added.
  }

  /**
   * Setup the display on the application.
   */
  private void setupDisplay()
  {
    JLabel display = new JLabel("");
    this.controller.setDisplay(display);

    display.setBounds(10, 10, 210, 40);
    display.setBorder(BorderFactory.createEtchedBorder());
    display.setBackground(Color.BLACK);
    getContentPane().add(display);
  }
}
