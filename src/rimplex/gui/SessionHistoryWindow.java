package rimplex.gui;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;

/**
 * The window for the session history that follows the main calculator.
 * 
 * @author Benjamin Bonnell
 * 
 * This work complies with JMU Honor Code.
 */
public class SessionHistoryWindow extends DropoutWindow
{
  private static final long serialVersionUID = 1L;

  /**
   * Explicit Session History Window constructor
   * 
   * @param mainWindow the window it shall follow behind
   * @param sessionHistory the Session History it will contain.
   */
  public SessionHistoryWindow(final RimpleXWindow mainWindow, final JTextArea sessionHistory)
  {
    super(mainWindow, sessionHistory, "right", 142);
  }
}
