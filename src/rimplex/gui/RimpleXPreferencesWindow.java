package rimplex.gui;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import utilities.RimpleXPreferences;

import static rimplex.RimpleX.*;

/**
 * The window used to edit preferences
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

    // sets the Window icon.
    ImageIcon img = new ImageIcon(getClass().getResource("/icons/iconRimplex.png"));
    setIconImage(img.getImage());

    makePreferencesInputsAndLabels();

  }

  /**
   * Create labels and editable inputs for the preferences and add them to the window.
   */
  private void makePreferencesInputsAndLabels()
  {

    // Set input for trailing zeroes, implementing the JSpinner object:
    // https://docs.oracle.com/javase/8/docs/api/javax/swing/SpinnerNumberModel.html
    JLabel trailingZeroesLabel = new JLabel("Trailing Zeroes:");
    trailingZeroesSpinner = new SpinnerNumberModel(
        RimpleXPreferences.getTrailingZeroes(), 0, 6, 1);
    trailingZeroesInput = new JSpinner(trailingZeroesSpinner);
    trailingZeroesInput.addChangeListener(this);

    // Disable keyboard entry (except arrow keys), credit to Oleg Estekhin for the general solution:
    // https://stackoverflow.com/a/7151715
    JSpinner.DefaultEditor trailingZeroesEditor = (JSpinner.DefaultEditor) trailingZeroesInput
        .getEditor();
    trailingZeroesEditor.getTextField().setEditable(false);

    // Set input for number of decimals
    JLabel numDecimalsLabel = new JLabel("Number of Decimal Places:");
    numDecimalsSpinner = new SpinnerNumberModel(RimpleXPreferences.getNumOfDecimals(),
        0, 6, 1);
    numDecimalsInput = new JSpinner(numDecimalsSpinner);
    numDecimalsInput.addChangeListener(this);

    JSpinner.DefaultEditor numDecimalsEditor = (JSpinner.DefaultEditor) trailingZeroesInput
        .getEditor();
    numDecimalsEditor.getTextField().setEditable(false);

    // Set checkbox for display thousands separators
    JLabel displaySeparatorsLabel = new JLabel("Display Separators:");
    displaySeparatorsCheckBox = new JCheckBox();
    displaySeparatorsCheckBox.setSelected(RimpleXPreferences.getDisplaySeparators());
    displaySeparatorsCheckBox.addItemListener(this);
    
    JLabel openRecordingLabel = new JLabel("Open Recording:");
    JLabel startStopRecordingLabel = new JLabel("Start/Stop Recording:");
    JLabel saveRecordingLabel = new JLabel("Save Recording:");
    JLabel printSessionLabel = new JLabel("Print Session:");
    JLabel newCalcLabel = new JLabel("New Calculator:");
    JLabel aboutLabel = new JLabel("About:");
    JLabel helpLabel = new JLabel("Help:");
    JLabel complexPlaneLabel = new JLabel("View Complex Plane:");
    JLabel editPreferencesLabel = new JLabel("Edit Preferences:");
    JLabel savePreferencesLabel = new JLabel("Save Preferences:");
    JLabel pauseRecordingLabel = new JLabel("Pause Recording:");
    
    aboutLabel.setBounds(25, 120, 200, 25);
    helpLabel.setBounds(25, 150, 200, 25);
    complexPlaneLabel.setBounds(25, 180, 200, 25);
    newCalcLabel.setBounds(25, 210, 200, 25);
    printSessionLabel.setBounds(25, 240, 200, 25);
    openRecordingLabel.setBounds(25, 270, 200, 25);
    startStopRecordingLabel.setBounds(25, 300, 200, 25);
    saveRecordingLabel.setBounds(25, 330, 200, 25);
    pauseRecordingLabel.setBounds(25, 360, 200, 25);
    editPreferencesLabel.setBounds(25, 390, 200, 25);
    savePreferencesLabel.setBounds(25, 420, 200, 25);
    
   
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
    
    add(aboutLabel);
    add(helpLabel);
    add(complexPlaneLabel);
    add(newCalcLabel);
    add(printSessionLabel);
    add(openRecordingLabel);
    add(startStopRecordingLabel);
    add(saveRecordingLabel);
    add(pauseRecordingLabel);
    add(editPreferencesLabel);
    add(savePreferencesLabel);

  }
  
  public void updatePreferenceValues()
  {
    displaySeparatorsCheckBox.setSelected(RimpleXPreferences.getDisplaySeparators());
    trailingZeroesSpinner.setValue(RimpleXPreferences.getTrailingZeroes());
    numDecimalsSpinner.setValue(RimpleXPreferences.getNumOfDecimals());
  }

  @Override
  public void itemStateChanged(ItemEvent e)
  {
    if (e.getSource() == displaySeparatorsCheckBox)
    {
      boolean boxChecked = (e.getStateChange() == 1) ? true : false;
      RimpleXPreferences.setDisplaySeparators(boxChecked);
    }
  }

  @Override
  public void stateChanged(ChangeEvent e)
  {
    if (e.getSource() == numDecimalsInput)
    {
      RimpleXPreferences.setNumOfDecimals((int) numDecimalsInput.getValue());
    }
    else if (e.getSource() == trailingZeroesInput)
    {
      RimpleXPreferences.setTrailingZeroes((int) trailingZeroesInput.getValue());
    }
  }
}
