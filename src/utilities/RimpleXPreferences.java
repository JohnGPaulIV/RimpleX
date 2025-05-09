package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
  // Properties
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
  private static final String NUM_OF_DECIMALS = "Num_Decimals";
  private static final String TRAILING_ZEROES = "Trailing_Zeroes";
  private static final String DISPLAY_SEPARATORS = "Thousands_Separators";

  private static final String PREF_FILE_PATH = "PREFERENCE_FILE_PATH";
  private static final String PREF_FILE_PATH_CONFIG = "src/rimplex/preferenceFilePath.properties";

  // Preferences
  private static int numOfDecimals = 3;
  private static int trailingZeroes = 1;
  private static boolean displaySeparators = false;

  // Hot-keys
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

  // Setup initial map of action commands.
  private static final Map<String, String> HOTKEY_MAPPING = new HashMap<>();
  private static List<String> actionCommands = Arrays.asList(ACTION_PRINT, ACTION_NEW_CALC,
      ACTION_ABOUT, ACTION_HELP, ACTION_EXIT, EDIT_PREFERENCES, SAVE_PREFERENCES, OPEN_PREFERENCES,
      OPEN_RECORDING, SAVE_RECORDING, COMPLEX_PLANE);
  static
  {
    for (String action : actionCommands)
    {
      HOTKEY_MAPPING.put(action, null);
    }
  }

  private static String preferencesFilePath;

  /**
   * Explicit constructor.
   */
  private RimpleXPreferences()
  {
  };

  /**
   * Get number of decimals.
   * 
   * @return Return number of decimals.
   */
  public static int getNumOfDecimals()
  {
    return numOfDecimals;
  }

  /**
   * Get number of trailing zeroes.
   * 
   * @return Return number of trailing zeroes.
   */
  public static int getTrailingZeroes()
  {
    return trailingZeroes;
  }

  /**
   * Get whether to display separators.
   * 
   * @return Return whether to display separators.
   */
  public static boolean getDisplaySeparators()
  {
    return displaySeparators;
  }

  /**
   * Get the shortcut for opening a recording.
   * 
   * @return Return the shortcut for opening a recording.
   */
  public static String getOpenRecordingShortcut()
  {
    return openRecordingShortcut;
  }

  /**
   * Get the shortcut for saving a recording.
   * 
   * @return Return the shortcut for saving a recording.
   */
  public static String getSaveRecordingShortcut()
  {
    return saveRecordingShortcut;
  }

  /**
   * Get the shortcut for printing the session history.
   * 
   * @return Return the shortcut for printing the session history.
   */
  public static String getPrintSessionShortcut()
  {
    return printSessionShortcut;
  }

  /**
   * Get the shortcut for opening up a new calculator window.
   * 
   * @return Return the shortcut opening up a new calculator window.
   */
  public static String getNewCalculatorShortcut()
  {
    return newCalculatorShortcut;
  }

  /**
   * Get the shortcut for opening the about window.
   * 
   * @return Return the shortcut for opening the about window.
   */
  public static String getAboutShortcut()
  {
    return aboutShortcut;
  }

  /**
   * Get the action command based off of the key pressed.
   * 
   * @param keyPressed
   *          The key press to process.
   * @return Return the action command, null if no mapping exists.
   */
  public static String getActionCommand(final String keyPressed)
  {
    for (Map.Entry<String, String> entry : HOTKEY_MAPPING.entrySet())
    {
      if (Objects.equals(entry.getValue(), keyPressed))
      {
        return entry.getKey();
      }
    }
    return null;
  }

  /**
   * Get the shortcut for opening the help page.
   * 
   * @return Return the shortcut for opening the help page.
   */
  public static String getHelpShortcut()
  {
    return helpShortcut;
  }

  /**
   * Get the shortcut for exiting the application.
   * 
   * @return Return the shortcut for exiting the application.
   */
  public static String getExitShortcut()
  {
    return exitShortcut;
  }

  /**
   * Get the shortcut for viewing the complex plane.
   * 
   * @return Return the shortcut for viewing the complex plane.
   */
  public static String getComplexPlaneShortcut()
  {
    return complexPlaneShortcut;
  }

  /**
   * Get the shortcut for opening up the preference editor.
   * 
   * @return Return the shortcut for opening up the preference editor.
   */
  public static String getEditPreferencesShortcut()
  {
    return editPreferencesShortcut;
  }

  /**
   * Get the shortcut for opening up the file chooser to select a preference file.
   * 
   * @return Return the shortcut for opening up the file chooser to select a preference file.
   */
  public static String getOpenPreferencesShortcut()
  {
    return openPreferencesShortcut;
  }

  /**
   * Get the shortcut for saving a preference configuration.
   * 
   * @return Return the shortcut for saving a preference configuration.
   */
  public static String getSavePreferencesShortcut()
  {
    return savePreferencesShortcut;
  }

  /**
   * Get the file path of the currently used preference configuration.
   * 
   * @return Return the file path to the current preferences configuration.
   */
  public static String getPreferencesFile()
  {
    return preferencesFilePath;
  }

  /**
   * Set the decimal precision setting.
   * 
   * @param num
   *          The decimal precision.
   */
  public static void setNumOfDecimals(final int num)
  {
    numOfDecimals = num;
  }

  /**
   * Set the number of trailing zeroes setting.
   * 
   * @param zeroes
   *          The number of trailing zeroes.
   */
  public static void setTrailingZeroes(final int zeroes)
  {
    trailingZeroes = zeroes;
  }

  /**
   * Set whether to display thousands separators.
   * 
   * @param separators
   *          True if to display thousands separators, false otherwise.
   */
  public static void setDisplaySeparators(final boolean separators)
  {
    displaySeparators = separators;
  }

  /**
   * Set the shortcut for opening a recording.
   * 
   * @param shortcut
   *          The shortcut for opening a recording.
   */
  public static void setOpenRecordingShortcut(final String shortcut)
  {
    openRecordingShortcut = shortcut;
    if (getActionCommand(shortcut) == null || HOTKEY_MAPPING.get(OPEN_RECORDING) != shortcut)
    {
      HOTKEY_MAPPING.put(OPEN_RECORDING, shortcut);
    }
  }

  /**
   * Set the shortcut for saving a recording.
   * 
   * @param shortcut
   *          The shortcut for saving a recording.
   */
  public static void setSaveRecordingShortcut(final String shortcut)
  {
    saveRecordingShortcut = shortcut;
    if (getActionCommand(shortcut) == null || HOTKEY_MAPPING.get(SAVE_RECORDING) != shortcut)
    {
      HOTKEY_MAPPING.put(SAVE_RECORDING, shortcut);
    }
  }

  /**
   * Set the shortcut for printing the session history.
   * 
   * @param shortcut
   *          The shortcut for printing the session history.
   */
  public static void setPrintSessionShortcut(final String shortcut)
  {
    printSessionShortcut = shortcut;
    if (getActionCommand(shortcut) == null || HOTKEY_MAPPING.get(ACTION_PRINT) != shortcut)
    {
      HOTKEY_MAPPING.put(ACTION_PRINT, shortcut);
    }
  }

  /**
   * Set the shortcut to open a new instance of RimpleX.
   * 
   * @param shortcut
   *          The shortcut to open a new instance of RimpleX.
   */
  public static void setNewCalculatorShortcut(final String shortcut)
  {
    newCalculatorShortcut = shortcut;
    if (getActionCommand(shortcut) == null || HOTKEY_MAPPING.get(ACTION_NEW_CALC) != shortcut)
    {
      HOTKEY_MAPPING.put(ACTION_NEW_CALC, shortcut);
    }
  }

  /**
   * Set the shortcut to open the about window.
   * 
   * @param shortcut
   *          The shortcut to open the about window.
   */
  public static void setAboutShortcut(final String shortcut)
  {
    aboutShortcut = shortcut;
    if (getActionCommand(shortcut) == null || HOTKEY_MAPPING.get(ACTION_ABOUT) != shortcut)
    {
      HOTKEY_MAPPING.put(ACTION_ABOUT, shortcut);
    }
  }

  /**
   * Set the shortcut to open the help page.
   * 
   * @param shortcut
   *          The shortcut to open the help page.
   */
  public static void setHelpShortcut(final String shortcut)
  {
    helpShortcut = shortcut;
    if (getActionCommand(shortcut) == null || HOTKEY_MAPPING.get(ACTION_HELP) != shortcut)
    {
      HOTKEY_MAPPING.put(ACTION_HELP, shortcut);
    }
  }

  /**
   * Set the shortcut to view the complex plane.
   * 
   * @param shortcut
   *          The shortcut to view the complex plane.
   */
  public static void setComplexPlaneShortcut(final String shortcut)
  {
    complexPlaneShortcut = shortcut;
    if (getActionCommand(shortcut) == null || HOTKEY_MAPPING.get(COMPLEX_PLANE) != shortcut)
    {
      HOTKEY_MAPPING.put(COMPLEX_PLANE, shortcut);
    }
  }

  /**
   * Set the shortcut for opening the preferences editor.
   * 
   * @param shortcut
   *          The shortcut for opening the preferences editor.
   */
  public static void setEditPreferencesShortcut(final String shortcut)
  {
    editPreferencesShortcut = shortcut;
    if (getActionCommand(shortcut) == null || HOTKEY_MAPPING.get(EDIT_PREFERENCES) != shortcut)
    {
      HOTKEY_MAPPING.put(EDIT_PREFERENCES, shortcut);
    }
  }

  /**
   * Set the shortcut for opening the file chooser to select preferences.
   * 
   * @param shortcut
   *          The shortcut for opening the file chooser to select preferences.
   */
  public static void setOpenPreferencesShortcut(final String shortcut)
  {
    openPreferencesShortcut = shortcut;
    if (getActionCommand(shortcut) == null || HOTKEY_MAPPING.get(OPEN_PREFERENCES) != shortcut)
    {
      HOTKEY_MAPPING.put(OPEN_PREFERENCES, shortcut);
    }
  }

  /**
   * Set the shortcut for saving the current preferences to a new file.
   * 
   * @param shortcut
   *          The shortcut for saving the current preferences to a new file.
   */
  public static void setSavePreferencesShortcut(final String shortcut)
  {
    savePreferencesShortcut = shortcut;
    if (getActionCommand(shortcut) == null || HOTKEY_MAPPING.get(SAVE_PREFERENCES) != shortcut)
    {
      HOTKEY_MAPPING.put(SAVE_PREFERENCES, shortcut);
    }
  }

  /**
   * Set the shortcut to exit the application.
   * 
   * @param shortcut
   *          The shortcut to exit the application.
   */
  public static void setExitShortcut(final String shortcut)
  {
    exitShortcut = shortcut;
    if (getActionCommand(shortcut) == null || HOTKEY_MAPPING.get(ACTION_EXIT) != shortcut)
    {
      HOTKEY_MAPPING.put(ACTION_EXIT, shortcut);
    }
  }

  /**
   * Set the file path to the new preferences file.
   * 
   * @param filePath
   *          The file path to the new preferences file.
   */
  public static void setPreferencesFile(final String filePath)
  {
    preferencesFilePath = filePath;
  }

  /**
   * Save the file path to the file path configuration file for persistence across sessions.
   * 
   * @param filePath
   *          The file path to the file path configuration file.
   */
  public static void savePreferencesFilePath(final String filePath)
  {
    FileInputStream prefInput;
    try
    {
      prefInput = new FileInputStream(PREF_FILE_PATH_CONFIG);
      Properties preferences = new Properties();
      try
      {
        preferences.load(prefInput);
        prefInput.close();

        preferences.setProperty(PREF_FILE_PATH, preferencesFilePath);
        FileOutputStream prefOutput = new FileOutputStream(PREF_FILE_PATH_CONFIG);
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

  /**
   * Save the current preferences to the preferences/hot-key configuration file.
   */
  public static void savePreferences()
  {
    try
    {
      FileInputStream prefInput = new FileInputStream(preferencesFilePath);
      Properties preferences = new Properties();
      preferences.load(prefInput);
      prefInput.close();

      preferences.setProperty(NUM_OF_DECIMALS, String.valueOf(numOfDecimals));
      preferences.setProperty(TRAILING_ZEROES, String.valueOf(trailingZeroes));
      preferences.setProperty(DISPLAY_SEPARATORS, String.valueOf(displaySeparators));

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

  /**
   * Fetch and parse the preferences/hot-keys from the preferences configuration file.
   */
  public static void getPreferences()
  {
    // Fetching properties using the Properties class, thanks to the following article:
    // https://sybernix.medium.com/how-to-add-a-config-file-to-a-java-project-99fd9b6cebca.
    try
    {
      // Load the preferences file path to save into.
      FileInputStream prefPathInput = new FileInputStream(PREF_FILE_PATH_CONFIG);
      Properties preferencePath = new Properties();
      preferencePath.load(prefPathInput);
      preferencesFilePath = preferencePath.getProperty(PREF_FILE_PATH);

      // Load the properties from the received file path.
      FileInputStream prefInput = new FileInputStream(preferencesFilePath);
      Properties preferences = new Properties();
      preferences.load(prefInput);

      // Parse and populate the preferences from the configuration file into the application.
      numOfDecimals = Integer.parseInt(preferences.getProperty(NUM_OF_DECIMALS));
      trailingZeroes = Integer.parseInt(preferences.getProperty(TRAILING_ZEROES));
      displaySeparators = Boolean.parseBoolean(preferences.getProperty(DISPLAY_SEPARATORS));

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
      System.out.println(HOTKEY_MAPPING);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
}
