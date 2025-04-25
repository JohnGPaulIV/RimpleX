package utilities;
import javax.swing.JEditorPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import java.awt.print.PrinterException;

public class PrintHelper {
    /**
     * Opens a print dialog for the given HTML file.
     */
    public static void printHtmlFile() {
      // SessionHistory.print();
      SwingUtilities.invokeLater(() -> {
        try {
            // Read the entire text file
            String content = Files.readString(Path.of("Session_History"));

            // Load into a JTextArea
            JTextArea ta = new JTextArea(content);
            ta.setEditable(false);
            ta.setLineWrap(true);
            ta.setWrapStyleWord(true);

            // Optional headers/footers
            MessageFormat header = new MessageFormat("Session History");
            MessageFormat footer = new MessageFormat("Page {0}");

            // Show dialog and print
            boolean showDialog = true;
            ta.print(header, footer, showDialog, null, null, true);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (PrinterException e) {
            e.printStackTrace();
        }
    });
    }
}