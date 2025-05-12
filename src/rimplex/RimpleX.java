package rimplex;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.ResourceBundle;

import rimplex.gui.*;
import utilities.RimpleXPreferences;

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
  private static String en = "en";
  private static String es = "es";
  private static String ru = "ru";
  private static String US = "US";
  private static String ES = "ES";
  private static String RU = "RU";
  private static String English = "English";
  private static String Russian = "русский язык";
  private static String Spanish = "Español";
  /**
   * Main driver.
   * 
   * @param args
   *          Command line arguments.
   * @throws InterruptedException
   * @throws InvocationTargetException
   */
  public static void main(final String[] args)
      throws InterruptedException, java.lang.reflect.InvocationTargetException
  {
    // Perform all of the setup activities in the event dispatch thread
    SwingUtilities.invokeAndWait(new RimpleX());
  }

  @Override
  public void run()
  {
    /* Locale locale = Locale.of(en, US);
    String[] languageOptions = {English, Russian, Spanish};
    String selection = (String) JOptionPane.showInputDialog(
        null,
        "Pick a language:",
        "Language selector",
        JOptionPane.QUESTION_MESSAGE,
        null,
        languageOptions,
        languageOptions[0]);
    if (selection.equals(English))
    {
      locale = Locale.of(en, US);
    } else if (selection.equals(Russian))
    {
      locale = Locale.of(ru, RU);
    } else if (selection.equals(Spanish))
    {
      locale = Locale.of(es, ES);
    } */
    Locale locale = Locale.getDefault();
    rb = ResourceBundle.getBundle("rimplex.gui.languages.Strings", locale);
    RimpleXPreferences.getPreferences();
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
