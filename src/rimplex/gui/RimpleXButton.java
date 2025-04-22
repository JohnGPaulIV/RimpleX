package rimplex.gui;

import java.awt.Font;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.SwingConstants;

/**
 * Represents a button on the RimpleX application with a set text, action command, and bounds.
 * 
 * @author Joseph Pogoretskiy, Benjamin Bonnell
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
   * @param widthArial
   *          The width of the button.
   * @param height
   *          The height of the button.
   */
  public RimpleXButton(final String name, final String text, final RimpleXController controller,
      final int xPos, final int yPos, final int width, final int height)
  {
    this.setActionCommand(name);
    this.addActionListener(controller);
    this.setBounds(xPos, yPos, width, height);
    this.setMargin(new Insets(2, 2, 2, 2));  // Smaller than default
    
    if (text.length() < 3) {
      this.setFont(new Font("Sans-Serif", Font.PLAIN, 24));
    }
    else {
      this.setFont(new Font("Sans-Serif", Font.PLAIN, 14));
    }
    
    // parse multiple phrases, if too long
    if (text.length() > 10) {
      String[] phrases = text.split(" ");
      if (phrases.length > 1) {
        String buttonText = new String();
        buttonText = buttonText + "<html><div style='text-align: center;'>";
        for (String phrase : phrases) {
          buttonText = buttonText + phrase + "<br>";
        }
        buttonText = buttonText + "</div></html>";
        this.setText(buttonText);
      }
      else {
        this.setText(text);
      }
    }
    else {
      this.setText(text);
    }

    

  }

}
