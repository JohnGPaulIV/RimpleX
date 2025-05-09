package rimplex.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DropoutWindowController implements ActionListener
{
  private final SessionHistoryWindow sessionHistory;
  
  public DropoutWindowController(SessionHistoryWindow sessionHistory) {
    this.sessionHistory = sessionHistory;
  }

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
