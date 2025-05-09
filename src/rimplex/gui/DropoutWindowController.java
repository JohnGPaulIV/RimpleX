package rimplex.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller for windows that drop out behind the calculator. 
 * 
 * This work complies with JMU Honor Code
 */
public class DropoutWindowController implements ActionListener
{
  private final DropoutWindow dropoutWindow;
  
  /**
   * Constructs the controller to control appropriate windows
   * 
   * @param dropoutWindow The SessionHistoryWindow to be controlled.
   */
  public DropoutWindowController(DropoutWindow dropoutWindow) {
    this.dropoutWindow = dropoutWindow;
  }

  /**
   * Handler for each action that a dropout window can take.
   */
  @Override
  public void actionPerformed(final ActionEvent e)
  {
    String ac = e.getActionCommand();    
    switch (ac) {
      case "DROPOUT":
        dropoutWindow.toggleAnimation();
        break;
      default:
        break;
    }
      
  }

}
