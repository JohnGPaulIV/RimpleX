package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
  private static final String ACTION_PRINT = "ACTION_PRINT";
  private static final String ACTION_NEW_CALC = "ACTION_NEW_CALC";
  private static final String ACTION_ABOUT = "ACTION_ABOUT";
  private static final String ACTION_HELP = "ACTION_HELP";
  private static final String ACTION_EXIT = "ACTION_EXIT";
  private static final String COMPLEX_PLANE = "COMPLEX_PLANE";
  private static final String EDIT_PREFERENCES = "EDIT_PREFERENCES";
  private static final String SAVE_PREFERENCES = "SAVE_PREFERENCES";
  private static final String OPEN_PREFERENCES = "OPEN_PREFERENCES";
  private static final String OPEN_RECORDING = "OPEN_RECORDING";
  private static final String SAVE_RECORDING = "SAVE_RECORDING";
      
  
  private static int numOfDecimals = 3;
  private static int trailingZeroes = 1;
  private static boolean displaySeparators = false;

  private static String openRecordingShortcut;
  private static String saveRecordingShortcut;
  private static String printSessionShortcut;
  private static String newCalculatorShortcut;
  private static String aboutShortcut;
  private static String helpShortcut;
  private static String exitShortcut;
  private static String complexPlaneShortcut;
  private static String editPreferencesShortcut;
  private static String openPreferencesShortcut;
  private static String savePreferencesShortcut;

  private static final Map<String, String> actionCommandMap = new HashMap<>();
  private static List<String> actionCommands = Arrays.asList(ACTION_PRINT, ACTION_NEW_CALC,
      ACTION_ABOUT, ACTION_HELP, ACTION_EXIT, EDIT_PREFERENCES, SAVE_PREFERENCES,
      OPEN_PREFERENCES, OPEN_RECORDING, SAVE_RECORDING, COMPLEX_PLANE);
  static
  {
    for (String action : actionCommands)
    {
      actionCommandMap.put(action, null);
    }
  }

  private static String preferencesFilePath;

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

  public static String getActionCommand(final String keyPressed)
  {
    for (Map.Entry<String, String> entry : actionCommandMap.entrySet())
    {
      if (Objects.equals(entry.getValue(), keyPressed))
      {
        return entry.getKey();
      }
    }
    return null;
  }

  public static String getHelpShortcut()
  {
    return helpShortcut;
  }
  
  public static String getExitShortcut()
  {
    return exitShortcut;
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

  public static String getPreferencesFile()
  {
    return preferencesFilePath;
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
    if (getActionCommand(shortcut) == null || actionCommandMap.get(OPEN_RECORDING) != shortcut)
    {
      actionCommandMap.put(OPEN_RECORDING, shortcut);
    }
  }

  public static void setSaveRecordingShortcut(String shortcut)
  {
    saveRecordingShortcut = shortcut;
    if (getActionCommand(shortcut) == null || actionCommandMap.get(SAVE_RECORDING) != shortcut)
    {
      actionCommandMap.put(SAVE_RECORDING, shortcut);
    }
  }

  public static void setPrintSessionShortcut(String shortcut)
  {
    printSessionShortcut = shortcut;
    if (getActionCommand(shortcut) == null || actionCommandMap.get(ACTION_PRINT) != shortcut)
    {
      actionCommandMap.put(ACTION_PRINT, shortcut);
    }
  }

  public static void setNewCalculatorShortcut(String shortcut)
  {
    newCalculatorShortcut = shortcut;
    if (getActionCommand(shortcut) == null || actionCommandMap.get(ACTION_NEW_CALC) != shortcut)
    {
      actionCommandMap.put(ACTION_NEW_CALC, shortcut);
    }
  }

  public static void setAboutShortcut(String shortcut)
  {
    aboutShortcut = shortcut;
    if (getActionCommand(shortcut) == null || actionCommandMap.get(ACTION_ABOUT) != shortcut)
    {
      actionCommandMap.put(ACTION_ABOUT, shortcut);
    }
  }

  public static void setHelpShortcut(String shortcut)
  {
    helpShortcut = shortcut;
    if (getActionCommand(shortcut) == null || actionCommandMap.get(ACTION_HELP) != shortcut)
    {
      actionCommandMap.put(ACTION_HELP, shortcut);
    }
  }

  public static void setComplexPlaneShortcut(String shortcut)
  {
    complexPlaneShortcut = shortcut;
    if (getActionCommand(shortcut) == null || actionCommandMap.get(COMPLEX_PLANE) != shortcut)
    {
      actionCommandMap.put(COMPLEX_PLANE, shortcut);
    }
  }

  public static void setEditPreferencesShortcut(String shortcut)
  {
    editPreferencesShortcut = shortcut;
    if (getActionCommand(shortcut) == null || actionCommandMap.get(EDIT_PREFERENCES) != shortcut)
    {
      actionCommandMap.put(EDIT_PREFERENCES, shortcut);
    }
  }

  public static void setOpenPreferencesShortcut(String shortcut)
  {
    openPreferencesShortcut = shortcut;
    if (getActionCommand(shortcut) == null || actionCommandMap.get(OPEN_PREFERENCES) != shortcut)
    {
      actionCommandMap.put(OPEN_PREFERENCES, shortcut);
    }
  }

  public static void setSavePreferencesShortcut(String shortcut)
  {
    savePreferencesShortcut = shortcut;
    if (getActionCommand(shortcut) == null || actionCommandMap.get(SAVE_PREFERENCES) != shortcut)
    {
      actionCommandMap.put(SAVE_PREFERENCES, shortcut);
    }
  }
  
  public static void setExitShortcut(String shortcut)
  {
    exitShortcut = shortcut;
    if (getActionCommand(shortcut) == null || actionCommandMap.get(ACTION_EXIT) != shortcut)
    {
      actionCommandMap.put(ACTION_EXIT, shortcut);
    }
  }

  public static void setPreferencesFile(String filePath)
  {
    preferencesFilePath = filePath;
  }
  
  public static void savePreferencesFilePath(String filePath)
  {
    FileInputStream prefInput;
    try
    {
      prefInput = new FileInputStream("src/rimplex/preferenceFilePath.properties");
      Properties preferences = new Properties();
      try
      {
        preferences.load(prefInput);
        prefInput.close();
        
        preferences.setProperty("PREFERENCE_FILE_PATH", preferencesFilePath);
        FileOutputStream prefOutput = new FileOutputStream("src/rimplex/preferenceFilePath.properties");
        preferences.store(prefOutput, null);
        prefOutput.close();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
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
      
      preferences.setProperty(OPEN_RECORDING, openRecordingShortcut);
      preferences.setProperty(SAVE_RECORDING, saveRecordingShortcut);
      preferences.setProperty(ACTION_PRINT, printSessionShortcut);
      preferences.setProperty(ACTION_NEW_CALC, newCalculatorShortcut);
      preferences.setProperty(ACTION_ABOUT, aboutShortcut);
      preferences.setProperty(ACTION_HELP, helpShortcut);
      preferences.setProperty(ACTION_EXIT, exitShortcut);
      preferences.setProperty(COMPLEX_PLANE, complexPlaneShortcut);
      preferences.setProperty(EDIT_PREFERENCES, editPreferencesShortcut);
      preferences.setProperty(OPEN_PREFERENCES, openPreferencesShortcut);
      preferences.setProperty(SAVE_PREFERENCES, savePreferencesShortcut);

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
      FileInputStream prefPathInput = new FileInputStream("src/rimplex/preferenceFilePath.properties");
      Properties preferencePath = new Properties();
      preferencePath.load(prefPathInput);
      preferencesFilePath = preferencePath.getProperty("PREFERENCE_FILE_PATH");
      
      FileInputStream prefInput = new FileInputStream(preferencesFilePath);
      Properties preferences = new Properties();
      preferences.load(prefInput);

      numOfDecimals = Integer.parseInt(preferences.getProperty("Num_Decimals"));
      trailingZeroes = Integer.parseInt(preferences.getProperty("Trailing_Zeroes"));
      displaySeparators = Boolean.parseBoolean(preferences.getProperty("Thousands_Separator"));

      setOpenRecordingShortcut(String.valueOf(preferences.getProperty(OPEN_RECORDING)));
      setSaveRecordingShortcut(String.valueOf(preferences.getProperty(SAVE_RECORDING)));
      setPrintSessionShortcut(String.valueOf(preferences.getProperty(ACTION_PRINT)));
      setNewCalculatorShortcut(String.valueOf(preferences.getProperty(ACTION_NEW_CALC)));
      setAboutShortcut(String.valueOf(preferences.getProperty(ACTION_ABOUT)));
      setHelpShortcut(String.valueOf(preferences.getProperty(ACTION_HELP)));
      setExitShortcut(String.valueOf(preferences.getProperty(ACTION_EXIT)));
      setComplexPlaneShortcut(String.valueOf(preferences.getProperty(COMPLEX_PLANE)));
      setEditPreferencesShortcut(String.valueOf(preferences.getProperty(EDIT_PREFERENCES)));
      setOpenPreferencesShortcut(String.valueOf(preferences.getProperty(OPEN_PREFERENCES)));
      setSavePreferencesShortcut(String.valueOf(preferences.getProperty(SAVE_PREFERENCES)));
      
      prefInput.close();
      System.out.println(actionCommandMap);
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
    
    String openRec = "Open Recording Shortcut: " + RimpleXPreferences.openRecordingShortcut;
    String about = "About Shortcut: " + RimpleXPreferences.aboutShortcut;

    return numOfDecimals + '\n' + trailingZeroes + '\n' + displaySeparators + '\n' + openRec + '\n' + about;
  }
}
