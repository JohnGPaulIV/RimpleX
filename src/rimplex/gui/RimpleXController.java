package rimplex.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * The observer of all GUI components of the RimpleX application.
 * 
 * General structure taken from Dr. Bernstein's Serialization Lab:
 * (https://w3.cs.jmu.edu/bernstdh/web/common/labs/experience_serialization/tempz/index.php)
 * 
 * This work complies with JMU Honor Code.
 */
public class RimpleXController implements ActionListener
{
  private RimpleXWindow window;
  
  public RimpleXController()
  {
    super();
  }

  @Override
  /**
   * Action handler when buttons or clicked.
   * 
   * @param ae The ActionEvent that generated the message.
   */
  public void actionPerformed(ActionEvent ae)
  {
    String ac = ae.getActionCommand();
    
    // General structure of handling actions:
    // if (ac.equals(NAME_OF_BUTTON)) { do stuff...}.
  }

  /**
   * Set the TempzWindow that this object is controlling.
   * 
   * @param window The window
   */
  public void setWindow(RimpleXWindow window)
  {
    this.window = window;
  }
}
