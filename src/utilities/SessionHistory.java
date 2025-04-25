package utilities;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class SessionHistory
{
  private static JLabel historyBox;
  private static StringBuilder historyContent = new StringBuilder();
  private static int numOp = 1;
  
  private SessionHistory()
  {
    
  }
  
  public static void setLabel(JLabel label)
  {
    historyBox = label;
    historyContent.setLength(0);
    SwingUtilities.invokeLater(() -> historyBox.setText("<html></html>"));
    Path sessionHistory = Paths.get("Session_History");
    try (BufferedWriter writer = Files.newBufferedWriter(sessionHistory,
        StandardOpenOption.CREATE,
        StandardOpenOption.TRUNCATE_EXISTING)) {
    } catch (IOException e) {
    e.printStackTrace();
    }
    
  }
  
  public static void add(String calculation)
  {
    Path sessionHistory = Paths.get("Session_History");
    try (BufferedWriter writer = Files.newBufferedWriter(sessionHistory,
            StandardOpenOption.CREATE,
            StandardOpenOption.APPEND)) {
        writer.write(String.valueOf(numOp) + ". " + calculation);
        writer.newLine();
        writer.newLine();
        numOp++;
    } catch (IOException e) {
        e.printStackTrace();
    }
    if (historyBox != null) {
      historyContent.append(calculation).append("<br>");
      String html = "<html>" + historyContent.toString() + "</html>";
      SwingUtilities.invokeLater(() -> historyBox.setText(html));
  }
  }
  
}
