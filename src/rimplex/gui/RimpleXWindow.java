package rimplex.gui;

import javax.swing.JFrame;

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
   * @param controller The observer for all GUI components.
   */
  public RimpleXWindow(RimpleXController controller)
  {
    super();
    
    // Set the controller to refer to this instance.
    this.controller = controller;
    this.controller.setWindow(this);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    this.setSize(400,400);
    // TODO: Add Java GUI Components to the main window here
    // TODO: Set size, layout, all those goodies. Helper functions can be utilized.
  }
}
