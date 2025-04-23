package rimplex;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.ResourceBundle;

import rimplex.gui.*;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * The main class for the RimpleX application. This is where the process begins.
 * 
 * General structure taken from Dr. Bernstein's Serialization Lab:
 * (https://w3.cs.jmu.edu/bernstdh/web/common/labs/experience_serialization/tempz/index.php)
 * 
 * @author Joseph Pogoretskiy
 * 
 * This work complies with JMU Honor Code.
 */
public class RimpleX implements Runnable
{
  public static ResourceBundle rb;
  /**
   * Main driver.
   * 
   * @param args
   *          Command line arguments.
   * @throws InterruptedException
   * @throws InvocationTargetException
   */
  public static void main(final String[] args)
      throws InterruptedException, InvocationTargetException
  {
    // Perform all of the setup activities in the event dispatch thread
    SwingUtilities.invokeAndWait(new RimpleX());
  }

  @Override
  public void run()
  {
    Locale locale = Locale.of("en", "US");
    String[] languageOptions = {"English", "русский язык", "Español"};
    String selection = (String) JOptionPane.showInputDialog(
        null,
        "Pick a language:",
        "Language selector",
        JOptionPane.QUESTION_MESSAGE,
        null,
        languageOptions,
        languageOptions[0]);
    if (selection.equals("English"))
    {
      locale = Locale.of("en", "US");
    } else if (selection.equals("русский язык"))
    {
      locale = Locale.of("ru", "RU");
    } else if (selection.equals("Español"))
    {
      locale = Locale.of("es", "ES");
    }
    rb = ResourceBundle.getBundle("rimplex.gui.languages.Strings", locale);
    RimpleXController controller = new RimpleXController();
    RimpleXWindow window = null;
    try
    {
      window = new RimpleXWindow(controller);
    }
    catch (IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    window.setVisible(true);
  }

}
