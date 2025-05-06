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

import utilities.Preferences;

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
  private static JSpinner numDecimalsInput;
  private static JSpinner trailingZeroesInput;

  /**
   * Constructs "About" Window for Rimplex.
   */
  public RimpleXPreferencesWindow()
  {
    setTitle(rb.getString("Preferences"));
    setSize(400, 150);
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
    SpinnerNumberModel trailingZeroesSpinner = new SpinnerNumberModel(
        Preferences.getTrailingZeroes(), 0, 6, 1);
    trailingZeroesInput = new JSpinner(trailingZeroesSpinner);
    trailingZeroesInput.addChangeListener(this);

    // Disable keyboard entry (except arrow keys), credit to Oleg Estekhin for the general solution:
    // https://stackoverflow.com/a/7151715
    JSpinner.DefaultEditor trailingZeroesEditor = (JSpinner.DefaultEditor) trailingZeroesInput
        .getEditor();
    trailingZeroesEditor.getTextField().setEditable(false);

    // Set input for number of decimals
    JLabel numDecimalsLabel = new JLabel("Number of Decimal Places:");
    SpinnerNumberModel numDecimalsSpinner = new SpinnerNumberModel(Preferences.getNumOfDecimals(),
        0, 6, 1);
    numDecimalsInput = new JSpinner(numDecimalsSpinner);
    numDecimalsInput.addChangeListener(this);

    JSpinner.DefaultEditor numDecimalsEditor = (JSpinner.DefaultEditor) trailingZeroesInput
        .getEditor();
    numDecimalsEditor.getTextField().setEditable(false);

    // Set checkbox for display thousands separators
    JLabel displaySeparatorsLabel = new JLabel("Display Separators:");
    displaySeparatorsCheckBox = new JCheckBox();
    displaySeparatorsCheckBox.setSelected(Preferences.getDisplaySeparators());
    displaySeparatorsCheckBox.addItemListener(this);
    
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

  @Override
  public void itemStateChanged(ItemEvent e)
  {
    if (e.getSource() == displaySeparatorsCheckBox)
    {
      boolean boxChecked = (e.getStateChange() == 1) ? true : false;
      Preferences.setDisplaySeparators(boxChecked);
    }
  }

  @Override
  public void stateChanged(ChangeEvent e)
  {
    if (e.getSource() == numDecimalsInput)
    {
      Preferences.setNumOfDecimals((int) numDecimalsInput.getValue());
    }
    else if (e.getSource() == trailingZeroesInput)
    {
      Preferences.setTrailingZeroes((int) trailingZeroesInput.getValue());
    }
  }
}
