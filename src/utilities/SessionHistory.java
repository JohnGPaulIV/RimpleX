package utilities;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
/**
* Helper class for the Session History.
*
* @author John Paul, Sofia Miller
* @version 1
*/
public class SessionHistory
{
  private static JTextArea historyBox;
  private static StringBuilder historyContent = new StringBuilder();
  private static int numOp = 1;
  private static String FILE = "Session_History.txt";
  private static String stringPeriod = ". ";
  private SessionHistory()
  {
  
  }
  /**
  * Sets the JLabel to act upon.
  * @param label JLabel to add session history to.
  */
  public static void setLabel(final JTextArea label)
  {
    historyBox = label;
    historyContent.setLength(0);
    SwingUtilities.invokeLater(() -> historyBox.setText(""));
    Path sessionHistory = Paths.get(FILE);
    try (BufferedWriter writer = Files.newBufferedWriter(sessionHistory,
        StandardOpenOption.CREATE,
        StandardOpenOption.TRUNCATE_EXISTING))
    {
      writer.write("Session History: ");
      writer.newLine();
    } 
    catch (IOException e)
    {
      e.printStackTrace();
    }
  
  }
  /**
  * Adder method to add calculations to the session history box and file.
  * @param calculation Calculation to be added.
  */
  public static void add(final String calculation)
  {
    Path sessionHistory = Paths.get(FILE);
    try (BufferedWriter writer = Files.newBufferedWriter(sessionHistory,
           StandardOpenOption.CREATE,
           StandardOpenOption.APPEND))
    {
      writer.write(String.valueOf(numOp) + stringPeriod + calculation);
      writer.newLine();
      writer.newLine();
      numOp++;
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    if (historyBox != null)
    {
      historyContent.append(calculation).append("\n");
      String html = historyContent.toString();
      SwingUtilities.invokeLater(() -> historyBox.setText(html));
    }
  
    RecordingManager.recordCalculation(numOp - 1 + stringPeriod + calculation);
  }
}
