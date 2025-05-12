package utilities;


import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 * Helper class for the intermediate steps handling.
 *
 * @author John Paul
 * @version 1
 */
public class IntermediateStepsHelper
{
  private static JTextArea IntermediateBox;
  private static StringBuilder historyContent = new StringBuilder();
  
  private IntermediateStepsHelper()
  {
    
  }
  /**
   * Sets the JLabel to act upon.
   * @param label JLabel to add session history to.
   */
  public static void setLabel(final JTextArea label)
  {
    IntermediateBox = label;
    IntermediateBox.setTabSize(4);
    historyContent.setLength(0);
    SwingUtilities.invokeLater(() -> IntermediateBox.setText(""));
    
  }
  
  /**
   * Adder method to add calculations to the session history box and file.
   * @param calculation Calculation to be added.
   */
  public static void add(final String calculation)
  {
    if (IntermediateBox != null) 
    {
      historyContent.append(calculation).append("\n");
      String html = historyContent.toString();
      SwingUtilities.invokeLater(() -> IntermediateBox.setText(html));
      System.out.println("added");
    }
  }
}
