package rimplex.gui;

import javax.swing.JTextArea;

/**
 * The window for the Intermediate Changes that follows the main calculator.
 *
 * @author Benjamin Bonnell
 *
 *         This work complies with JMU Honor Code.
 */
public class IntermediateChangesWindow extends DropoutWindow
{
  private static final long serialVersionUID = 1L;

  /**
   * Explicit Intermediate Changes Window constructor.
   *
   * @param mainWindow
   *          the window it shall follow behind
   * @param intermediateChanges
   *          the Intermediate Changes it will contain.
   */
  public IntermediateChangesWindow(final RimpleXWindow mainWindow,
      final JTextArea intermediateChanges)
  {
    super(mainWindow, intermediateChanges, "left", -30);
  }
}
