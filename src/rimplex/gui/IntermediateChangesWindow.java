package rimplex.gui;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;

/**
 * The window for the Intermediate Changes that follows the main calculator.
 * 
 * @author Benjamin Bonnell
 * 
 * This work complies with JMU Honor Code.
 */
public class IntermediateChangesWindow extends DropoutWindow
{
  private static final long serialVersionUID = 1L;

  /**
   * Explicit Intermediate Changes Window constructor
   * 
   * @param mainWindow the window it shall follow behind
   * @param IntermediateChanges the Intermediate Changes it will contain.
   */
  public IntermediateChangesWindow(final RimpleXWindow mainWindow, final JTextArea IntermediateChanges)
  {
    super(mainWindow, IntermediateChanges, "left", -30);
  }
}
