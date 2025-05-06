package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 * Represents the current configuration of the RimpleX application
 * 
 * This work complies with JMU Honor Code.
 * 
 * @author Joseph Pogoretskiy
 */
public final class RimpleXPreferences
{
  private static int numOfDecimals = 3;
  private static int trailingZeroes = 1;
  private static boolean displaySeparators = false;

  private static String openRecordingShortcut;
  private static String saveRecordingShortcut;
  private static String printSessionShortcut;
  private static String newCalculatorShortcut;
  private static String aboutShortcut;
  private static String helpShortcut;
  private static String complexPlaneShortcut;
  private static String editPreferencesShortcut;
  private static String openPreferencesShortcut;
  private static String savePreferencesShortcut;
  private static String startStopRecordingShortcut;
  private static String pauseRecordingShortcut;
  
  private static String preferencesFilePath = "src/rimplex/gui/preferences/Preferences.properties";

  private RimpleXPreferences()
  {
  };

  public static int getNumOfDecimals()
  {
    return numOfDecimals;
  }

  public static int getTrailingZeroes()
  {
    return trailingZeroes;
  }

  public static boolean getDisplaySeparators()
  {
    return displaySeparators;
  }

  public static String getOpenRecordingShortcut()
  {
    return openRecordingShortcut;
  }

  public static String getSaveRecordingShortcut()
  {
    return saveRecordingShortcut;
  }

  public static String getPrintSessionShortcut()
  {
    return printSessionShortcut;
  }

  public static String getNewCalculatorShortcut()
  {
    return newCalculatorShortcut;
  }

  public static String getAboutShortcut()
  {
    return aboutShortcut;
  }

  public static String getHelpShortcut()
  {
    return helpShortcut;
  }

  public static String getComplexPlaneShortcut()
  {
    return complexPlaneShortcut;
  }

  public static String getEditPreferencesShortcut()
  {
    return editPreferencesShortcut;
  }

  public static String getOpenPreferencesShortcut()
  {
    return openPreferencesShortcut;
  }

  public static String getSavePreferencesShortcut()
  {
    return savePreferencesShortcut;
  }

  public static String getStartStopRecordingShortcut()
  {
    return startStopRecordingShortcut;
  }

  public static String getPauseRecordingShortcut()
  {
    return pauseRecordingShortcut;
  }

  public static void setNumOfDecimals(int num)
  {
    numOfDecimals = num;
  }

  public static void setTrailingZeroes(int zeroes)
  {
    trailingZeroes = zeroes;
  }

  public static void setDisplaySeparators(boolean separators)
  {
    displaySeparators = separators;
  }

  public static void setOpenRecordingShortcut(String shortcut)
  {
    openRecordingShortcut = shortcut;
  }

  public static void setSaveRecordingShortcut(String shortcut)
  {
    saveRecordingShortcut = shortcut;
  }

  public static void setPrintSessionShortcut(String shortcut)
  {
    printSessionShortcut = shortcut;
  }

  public static void setNewCalculatorShortcut(String shortcut)
  {
    newCalculatorShortcut = shortcut;
  }

  public static void setAboutShortcut(String shortcut)
  {
    aboutShortcut = shortcut;
  }

  public static void setHelpShortcut(String shortcut)
  {
    helpShortcut = shortcut;
  }

  public static void setComplexPlaneShortcut(String shortcut)
  {
    complexPlaneShortcut = shortcut;
  }

  public static void setEditPreferencesShortcut(String shortcut)
  {
    editPreferencesShortcut = shortcut;
  }

  public static void setOpenPreferencesShortcut(String shortcut)
  {
    openPreferencesShortcut = shortcut;
  }

  public static void setSavePreferencesShortcut(String shortcut)
  {
    savePreferencesShortcut = shortcut;
  }

  public static void savePreferences()
  {
    try
    {
      FileInputStream prefInput = new FileInputStream(preferencesFilePath);
      Properties preferences = new Properties();
      preferences.load(prefInput);
      prefInput.close();

      preferences.setProperty("Num_Decimals", String.valueOf(numOfDecimals));
      preferences.setProperty("Trailing_Zeroes", String.valueOf(trailingZeroes));
      preferences.setProperty("Thousands_Separator", String.valueOf(displaySeparators));

      FileOutputStream prefOutput = new FileOutputStream(preferencesFilePath);
      preferences.store(prefOutput, null);
      prefOutput.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  public static void getPreferences()
  {
    // Fetching properties using the Properties class, thanks to the following article:
    // https://sybernix.medium.com/how-to-add-a-config-file-to-a-java-project-99fd9b6cebca.
    try
    {
      FileInputStream prefInput = new FileInputStream(preferencesFilePath);
      Properties preferences = new Properties();
      preferences.load(prefInput);

      numOfDecimals = Integer.parseInt(preferences.getProperty("Num_Decimals"));
      trailingZeroes = Integer.parseInt(preferences.getProperty("Trailing_Zeroes"));
      displaySeparators = Boolean.parseBoolean(preferences.getProperty("Thousands_Separator"));

      prefInput.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * Format the state of the preferences for debugging.
   * 
   * @return The current state of preferences.
   */
  public static String toStrings()
  {
    String numOfDecimals = "Number of decimals: " + RimpleXPreferences.numOfDecimals;
    String trailingZeroes = "Trailing zeroes: " + RimpleXPreferences.trailingZeroes;
    String displaySeparators = "Display separators: "
        + String.valueOf(RimpleXPreferences.displaySeparators);

    return numOfDecimals + '\n' + trailingZeroes + '\n' + displaySeparators;
  }
}
