package utilities;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class IntermediateStepsHelper
{
  private static JTextArea IntermediateBox;
  private static StringBuilder historyContent = new StringBuilder();
  private static int numOp = 1;
  
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
    }
  }
}
