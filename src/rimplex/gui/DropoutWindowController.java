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
  private final SessionHistoryWindow sessionHistory;
  
  /**
   * Constructs the controller to control appropriate windows
   * 
   * @param sessionHistory The SessionHistoryWindow to be controlled.
   */
  public DropoutWindowController(SessionHistoryWindow sessionHistory) {
    this.sessionHistory = sessionHistory;
  }

  /**
   * Handler for each action that a dropout window can take.
   */
  @Override
  public void actionPerformed(final ActionEvent e)
  {
    String ac = e.getActionCommand();    
    switch (ac) {
      case "S_HISTORY_DROPOUT":
        sessionHistory.toggleAnimation();
        break;
      default:
        break;
    }
      
  }

}
