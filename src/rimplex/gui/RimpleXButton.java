package rimplex.gui;

import java.awt.Font;
import java.awt.Insets;

import javax.swing.JButton;

/**
 * Represents a button on the RimpleX application with a set text, action command, and bounds.
 * 
 * This work complies with JMU Honor Code.
 */
public class RimpleXButton extends JButton
{

  private static final long serialVersionUID = 1L;

  /**
   * Explicit RimpleXButton constructor.
   * 
   * @param name
   *          The name of the button. Also the ActionCommand name.
   * @param text
   *          The text that appears on the button.
   * @param controller
   *          The observer of the button.
   * @param xPos
   *          The absolute position of the button on the x-axis.
   * @param yPos
   *          The absolute position of the button on the y-axis.
   * @param width
   *          The width of the button.
   * @param height
   *          The height of the button.
   */
  public RimpleXButton(final String name, final String text, final RimpleXController controller,
      final int xPos, final int yPos, final int width, final int height)
  {
    this.setText(text);
    this.setActionCommand(name);
    this.addActionListener(controller);
    this.setBounds(xPos, yPos, width, height);
    this.setMargin(new Insets(2, 2, 2, 2));  // Smaller than default
    this.setFont(new Font("Arial", Font.PLAIN, 24));

  }

}
