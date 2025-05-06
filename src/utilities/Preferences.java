package utilities;

import java.io.File;
import java.io.FileInputStream;
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
public final class Preferences
{
  private static int numOfDecimals;
  private static int trailingZeroes;
  private static boolean displaySeparators;

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

  private Preferences()
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

  public void savePreferences()
  {
  }
  
  
  public void getPreferences()
  {
    // Fetching properties using the Properties class, thanks to the following article:
    // https://sybernix.medium.com/how-to-add-a-config-file-to-a-java-project-99fd9b6cebca.
    try
    {
      String preferencesFilePath = "src/rimplex.gui.preferences/Preferences.properties";
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
}
