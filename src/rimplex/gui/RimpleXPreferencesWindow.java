package rimplex.gui;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import utilities.HotkeyTextFieldFilter;
import utilities.RimpleXPreferences;

import static rimplex.RimpleX.*;

/**
 * The window used to edit preferences and hotkeys.
 * 
 * All work complies with JMU Honor Code.
 * 
 * @author Joseph Pogoretskiy
 */
public class RimpleXPreferencesWindow extends JDialog implements ItemListener, ChangeListener
{
  private static final long serialVersionUID = 1L;
  private static JCheckBox displaySeparatorsCheckBox;

  private static SpinnerNumberModel trailingZeroesSpinner;
  private static SpinnerNumberModel numDecimalsSpinner;
  private static JSpinner numDecimalsInput;
  private static JSpinner trailingZeroesInput;
  private static JTextField aboutField;
  private static JTextField helpField;
  private static JTextField exitField;
  private static JTextField complexField;
  private static JTextField newCalcField;
  private static JTextField printField;
  private static JTextField openRecordingField;
  private static JTextField saveRecordingField;
  private static JTextField editPreferencesField;
  private static JTextField savePreferencesField;
  private static String COLON = ":";

  /**
   * Constructs "About" Window for Rimplex.
   */
  public RimpleXPreferencesWindow()
  {
    setTitle(rb.getString("Preferences"));
    setSize(400, 480);
    setLocationRelativeTo(null);
    setLayout(null);
    setResizable(false);
    setModal(true);

    // Sets the Window icon.
    ImageIcon img = new ImageIcon(getClass().getResource("/icons/iconRimplex.png"));
    setIconImage(img.getImage());

    makePreferencesInputsAndLabels();

  }

  /**
   * Update displaying separators based on the user's input.
   */
  @Override
  public void itemStateChanged(final ItemEvent e)
  {
    if (e.getSource() == displaySeparatorsCheckBox)
    {
      boolean boxChecked = (e.getStateChange() == 1);
      RimpleXPreferences.setDisplaySeparators(boxChecked);
      RimpleXPreferences.savePreferences();
    }
  }

  /**
   * Create labels and editable inputs for the preferences/hot-keys and add them to the window.
   */
  private void makePreferencesInputsAndLabels()
  {
    // Set input for trailing zeroes, implementing the JSpinner object:
    // https://docs.oracle.com/javase/8/docs/api/javax/swing/SpinnerNumberModel.html
    JLabel trailingZeroesLabel = new JLabel(rb.getString("Trailing_Zeroes"));
    trailingZeroesSpinner = new SpinnerNumberModel(RimpleXPreferences.getTrailingZeroes(), 0, 6, 1);
    trailingZeroesInput = new JSpinner(trailingZeroesSpinner);
    trailingZeroesInput.addChangeListener(this);

    // Disable keyboard entry (except arrow keys), credit to Oleg Estekhin for the general solution:
    // https://stackoverflow.com/a/7151715
    JSpinner.DefaultEditor trailingZeroesEditor = (JSpinner.DefaultEditor) trailingZeroesInput
        .getEditor();
    trailingZeroesEditor.getTextField().setEditable(false);

    // Set input for number of decimals
    JLabel numDecimalsLabel = new JLabel(rb.getString("Num_Decimals"));
    numDecimalsSpinner = new SpinnerNumberModel(RimpleXPreferences.getNumOfDecimals(), 0, 6, 1);
    numDecimalsInput = new JSpinner(numDecimalsSpinner);
    numDecimalsInput.addChangeListener(this);

    JSpinner.DefaultEditor numDecimalsEditor = (JSpinner.DefaultEditor) trailingZeroesInput
        .getEditor();
    numDecimalsEditor.getTextField().setEditable(false);

    // Set checkbox for display thousands separators
    JLabel displaySeparatorsLabel = new JLabel(rb.getString("Display_Separators"));
    displaySeparatorsCheckBox = new JCheckBox();
    displaySeparatorsCheckBox.setSelected(RimpleXPreferences.getDisplaySeparators());
    displaySeparatorsCheckBox.addItemListener(this);

    setHotkeyLabels();
    setHotkeyInputs();

    // Set absolute positioning within component.
    trailingZeroesInput.setBounds(275, 30, 100, 25);
    trailingZeroesLabel.setBounds(25, 30, 200, 25);
    numDecimalsInput.setBounds(275, 60, 100, 25);
    numDecimalsLabel.setBounds(25, 60, 200, 25);
    displaySeparatorsLabel.setBounds(25, 90, 200, 25);
    displaySeparatorsCheckBox.setBounds(350, 90, 25, 25);

    // Add component to this window.
    add(displaySeparatorsLabel);
    add(displaySeparatorsCheckBox);
    add(numDecimalsInput);
    add(numDecimalsLabel);
    add(trailingZeroesInput);
    add(trailingZeroesLabel);
  }

  /**
   * Set the hot-key input components.
   */
  private void setHotkeyInputs()
  {
    // Limit text input into field, inspired by:
    // https://www.rgagnon.com/javadetails/java-0198.html
    aboutField = new JTextField();
    aboutField.setDocument(new HotkeyTextFieldFilter());
    ((HotkeyTextFieldFilter) aboutField.getDocument())
        .setInitialText(RimpleXPreferences.getAboutShortcut());

    helpField = new JTextField(RimpleXPreferences.getHelpShortcut());
    helpField.setDocument(new HotkeyTextFieldFilter());
    ((HotkeyTextFieldFilter) helpField.getDocument())
        .setInitialText(RimpleXPreferences.getHelpShortcut());

    complexField = new JTextField(RimpleXPreferences.getComplexPlaneShortcut());
    complexField.setDocument(new HotkeyTextFieldFilter());
    ((HotkeyTextFieldFilter) complexField.getDocument())
        .setInitialText(RimpleXPreferences.getComplexPlaneShortcut());

    newCalcField = new JTextField(RimpleXPreferences.getNewCalculatorShortcut());
    newCalcField.setDocument(new HotkeyTextFieldFilter());
    ((HotkeyTextFieldFilter) newCalcField.getDocument())
        .setInitialText(RimpleXPreferences.getNewCalculatorShortcut());

    printField = new JTextField(RimpleXPreferences.getPrintSessionShortcut());
    printField.setDocument(new HotkeyTextFieldFilter());
    ((HotkeyTextFieldFilter) printField.getDocument())
        .setInitialText(RimpleXPreferences.getPrintSessionShortcut());

    openRecordingField = new JTextField(RimpleXPreferences.getOpenRecordingShortcut());
    openRecordingField.setDocument(new HotkeyTextFieldFilter());
    ((HotkeyTextFieldFilter) openRecordingField.getDocument())
        .setInitialText(RimpleXPreferences.getOpenRecordingShortcut());

    saveRecordingField = new JTextField(RimpleXPreferences.getSaveRecordingShortcut());
    saveRecordingField.setDocument(new HotkeyTextFieldFilter());
    ((HotkeyTextFieldFilter) saveRecordingField.getDocument())
        .setInitialText(RimpleXPreferences.getSaveRecordingShortcut());

    editPreferencesField = new JTextField(RimpleXPreferences.getEditPreferencesShortcut());
    editPreferencesField.setDocument(new HotkeyTextFieldFilter());
    ((HotkeyTextFieldFilter) editPreferencesField.getDocument())
        .setInitialText(RimpleXPreferences.getEditPreferencesShortcut());

    savePreferencesField = new JTextField(RimpleXPreferences.getSavePreferencesShortcut());
    savePreferencesField.setDocument(new HotkeyTextFieldFilter());
    ((HotkeyTextFieldFilter) savePreferencesField.getDocument())
        .setInitialText(RimpleXPreferences.getSavePreferencesShortcut());

    exitField = new JTextField(RimpleXPreferences.getExitShortcut());
    exitField.setDocument(new HotkeyTextFieldFilter());
    ((HotkeyTextFieldFilter) exitField.getDocument())
        .setInitialText(RimpleXPreferences.getExitShortcut());

    setHotkeyListeners();

    aboutField.setBounds(335, 120, 40, 25);
    helpField.setBounds(335, 150, 40, 25);
    complexField.setBounds(335, 180, 40, 25);
    newCalcField.setBounds(335, 210, 40, 25);
    printField.setBounds(335, 240, 40, 25);
    openRecordingField.setBounds(335, 270, 40, 25);
    saveRecordingField.setBounds(335, 300, 40, 25);
    editPreferencesField.setBounds(335, 330, 40, 25);
    savePreferencesField.setBounds(335, 360, 40, 25);
    exitField.setBounds(335, 390, 40, 25);

    add(aboutField);
    add(helpField);
    add(complexField);
    add(newCalcField);
    add(printField);
    add(openRecordingField);
    add(saveRecordingField);
    add(editPreferencesField);
    add(savePreferencesField);
    add(exitField);
  }

  /**
   * Set the hot-key label components.
   */
  private void setHotkeyLabels()
  {
    JLabel openRecordingLabel = new JLabel(rb.getString("Open_Recording"));
    JLabel saveRecordingLabel = new JLabel(rb.getString("Save_Recording"));
    JLabel printSessionLabel = new JLabel(rb.getString("Print_Session") + COLON);
    JLabel newCalcLabel = new JLabel(rb.getString("New_Calculator") + COLON);
    JLabel aboutLabel = new JLabel(rb.getString("About") + COLON);
    JLabel helpLabel = new JLabel(rb.getString("Help") + COLON);
    JLabel complexPlaneLabel = new JLabel(rb.getString("Complex_Plane_Hotkey"));
    JLabel editPreferencesLabel = new JLabel(rb.getString("Edit_Preferences"));
    JLabel savePreferencesLabel = new JLabel(rb.getString("Save_Preferences"));
    JLabel exitLabel = new JLabel(rb.getString("Exit_Hotkey"));

    aboutLabel.setBounds(25, 120, 200, 25);
    helpLabel.setBounds(25, 150, 200, 25);
    complexPlaneLabel.setBounds(25, 180, 200, 25);
    newCalcLabel.setBounds(25, 210, 200, 25);
    printSessionLabel.setBounds(25, 240, 200, 25);
    openRecordingLabel.setBounds(25, 270, 200, 25);
    saveRecordingLabel.setBounds(25, 300, 200, 25);
    editPreferencesLabel.setBounds(25, 330, 200, 25);
    savePreferencesLabel.setBounds(25, 360, 200, 25);
    exitLabel.setBounds(25, 390, 200, 25);

    add(aboutLabel);
    add(helpLabel);
    add(complexPlaneLabel);
    add(newCalcLabel);
    add(printSessionLabel);
    add(openRecordingLabel);
    add(saveRecordingLabel);
    add(editPreferencesLabel);
    add(savePreferencesLabel);
    add(exitLabel);
  }

  /**
   * Set Document listeners to all hot-keys to update shortcut preferences as they are changed.
   */
  public void setHotkeyListeners()
  {
    aboutField.getDocument().addDocumentListener(new DocumentListener()
    {
      @Override
      public void changedUpdate(final DocumentEvent e)
      {
      }

      @Override
      public void insertUpdate(final DocumentEvent e)
      {
        RimpleXPreferences.setAboutShortcut(aboutField.getText());
        RimpleXPreferences.savePreferences();
      }

      @Override
      public void removeUpdate(final DocumentEvent e)
      {
        RimpleXPreferences.setAboutShortcut(aboutField.getText());
        RimpleXPreferences.savePreferences();
      }
    });
    helpField.getDocument().addDocumentListener(new DocumentListener()
    {
      @Override
      public void changedUpdate(final DocumentEvent e)
      {
      }

      @Override
      public void insertUpdate(final DocumentEvent e)
      {
        RimpleXPreferences.setHelpShortcut(helpField.getText());
        RimpleXPreferences.savePreferences();
      }

      @Override
      public void removeUpdate(final DocumentEvent e)
      {
        RimpleXPreferences.setHelpShortcut(helpField.getText());
        RimpleXPreferences.savePreferences();
      }
    });
    complexField.getDocument().addDocumentListener(new DocumentListener()
    {
      @Override
      public void changedUpdate(final DocumentEvent e)
      {
      }

      @Override
      public void insertUpdate(final DocumentEvent e)
      {
        RimpleXPreferences.setComplexPlaneShortcut(complexField.getText());
        RimpleXPreferences.savePreferences();
      }

      @Override
      public void removeUpdate(final DocumentEvent e)
      {
        RimpleXPreferences.setComplexPlaneShortcut(complexField.getText());
        RimpleXPreferences.savePreferences();
      }
    });
    newCalcField.getDocument().addDocumentListener(new DocumentListener()
    {
      @Override
      public void changedUpdate(final DocumentEvent e)
      {
      }

      @Override
      public void insertUpdate(final DocumentEvent e)
      {
        RimpleXPreferences.setNewCalculatorShortcut(newCalcField.getText());
        RimpleXPreferences.savePreferences();
      }

      @Override
      public void removeUpdate(final DocumentEvent e)
      {
        RimpleXPreferences.setNewCalculatorShortcut(newCalcField.getText());
        RimpleXPreferences.savePreferences();
      }
    });
    printField.getDocument().addDocumentListener(new DocumentListener()
    {
      @Override
      public void changedUpdate(final DocumentEvent e)
      {
      }

      @Override
      public void insertUpdate(final DocumentEvent e)
      {
        RimpleXPreferences.setPrintSessionShortcut(printField.getText());
        RimpleXPreferences.savePreferences();
      }

      @Override
      public void removeUpdate(final DocumentEvent e)
      {
        RimpleXPreferences.setPrintSessionShortcut(printField.getText());
        RimpleXPreferences.savePreferences();
      }
    });
    openRecordingField.getDocument().addDocumentListener(new DocumentListener()
    {
      @Override
      public void changedUpdate(final DocumentEvent e)
      {
      }

      @Override
      public void insertUpdate(final DocumentEvent e)
      {
        RimpleXPreferences.setOpenRecordingShortcut(openRecordingField.getText());
        RimpleXPreferences.savePreferences();
      }

      @Override
      public void removeUpdate(final DocumentEvent e)
      {
        RimpleXPreferences.setOpenRecordingShortcut(openRecordingField.getText());
        RimpleXPreferences.savePreferences();
      }
    });
    saveRecordingField.getDocument().addDocumentListener(new DocumentListener()
    {
      @Override
      public void changedUpdate(final DocumentEvent e)
      {
      }

      @Override
      public void insertUpdate(final DocumentEvent e)
      {
        RimpleXPreferences.setSaveRecordingShortcut(saveRecordingField.getText());
        RimpleXPreferences.savePreferences();
      }

      @Override
      public void removeUpdate(final DocumentEvent e)
      {
        RimpleXPreferences.setSaveRecordingShortcut(saveRecordingField.getText());
        RimpleXPreferences.savePreferences();
      }
    });
    editPreferencesField.getDocument().addDocumentListener(new DocumentListener()
    {
      @Override
      public void changedUpdate(final DocumentEvent e)
      {
      }

      @Override
      public void insertUpdate(final DocumentEvent e)
      {
        RimpleXPreferences.setEditPreferencesShortcut(editPreferencesField.getText());
        RimpleXPreferences.savePreferences();
      }

      @Override
      public void removeUpdate(final DocumentEvent e)
      {
        RimpleXPreferences.setEditPreferencesShortcut(editPreferencesField.getText());
        RimpleXPreferences.savePreferences();
      }
    });
    savePreferencesField.getDocument().addDocumentListener(new DocumentListener()
    {
      @Override
      public void changedUpdate(final DocumentEvent e)
      {
      }

      @Override
      public void insertUpdate(final DocumentEvent e)
      {
        RimpleXPreferences.setSavePreferencesShortcut(savePreferencesField.getText());
        RimpleXPreferences.savePreferences();
      }

      @Override
      public void removeUpdate(final DocumentEvent e)
      {
        RimpleXPreferences.setSavePreferencesShortcut(savePreferencesField.getText());
        RimpleXPreferences.savePreferences();
      }
    });
    exitField.getDocument().addDocumentListener(new DocumentListener()
    {
      @Override
      public void changedUpdate(final DocumentEvent e)
      {
      }

      @Override
      public void insertUpdate(final DocumentEvent e)
      {
        RimpleXPreferences.setExitShortcut(exitField.getText());
        RimpleXPreferences.savePreferences();
      }

      @Override
      public void removeUpdate(final DocumentEvent e)
      {
        RimpleXPreferences.setExitShortcut(exitField.getText());
        RimpleXPreferences.savePreferences();
      }
    });
  }

  /**
   * Update preferences based on the user's input.
   */
  @Override
  public void stateChanged(final ChangeEvent e)
  {
    if (e.getSource() == numDecimalsInput)
    {
      RimpleXPreferences.setNumOfDecimals((int) numDecimalsInput.getValue());
      RimpleXPreferences.savePreferences();
    }
    else if (e.getSource() == trailingZeroesInput)
    {
      RimpleXPreferences.setTrailingZeroes((int) trailingZeroesInput.getValue());
      RimpleXPreferences.savePreferences();
    }
  }

  /**
   * Update the internal preferences configuration based on the inputed values of the window.
   */
  public void updatePreferenceValues()
  {
    displaySeparatorsCheckBox.setSelected(RimpleXPreferences.getDisplaySeparators());
    trailingZeroesSpinner.setValue(RimpleXPreferences.getTrailingZeroes());
    numDecimalsSpinner.setValue(RimpleXPreferences.getNumOfDecimals());

    aboutField.setText(RimpleXPreferences.getAboutShortcut());
    helpField.setText(RimpleXPreferences.getHelpShortcut());
    complexField.setText(RimpleXPreferences.getComplexPlaneShortcut());
    newCalcField.setText(RimpleXPreferences.getNewCalculatorShortcut());
    printField.setText(RimpleXPreferences.getPrintSessionShortcut());
    exitField.setText(RimpleXPreferences.getExitShortcut());
    openRecordingField.setText(RimpleXPreferences.getOpenRecordingShortcut());
    saveRecordingField.setText(RimpleXPreferences.getSaveRecordingShortcut());
    editPreferencesField.setText(RimpleXPreferences.getEditPreferencesShortcut());
    savePreferencesField.setText(RimpleXPreferences.getSavePreferencesShortcut());
  }
}
