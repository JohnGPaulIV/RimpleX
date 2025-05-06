package rimplex.gui;

import java.awt.FlowLayout;
import java.awt.Font;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import utilities.Preferences;

import static rimplex.RimpleX.*;

/**
 * The window used to edit preferences
 * 
 * @author Joseph Pogoretskiy
 */
public class RimpleXPreferencesWindow extends JDialog
{
  private static final long serialVersionUID = 1L;

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
  
  private void makePreferencesInputsAndLabels()
  {
    
    // Set input for trailing zeroes, implementing the JSpinner object:
    // https://docs.oracle.com/javase/8/docs/api/javax/swing/SpinnerNumberModel.html
    JLabel trailingZeroesLabel = new JLabel("Trailing Zeroes:");
    SpinnerNumberModel trailingZeroesSpinner = new SpinnerNumberModel(Preferences.getTrailingZeroes(), 0, 6, 1);
    JSpinner trailingZeroesInput = new JSpinner(trailingZeroesSpinner);
    
    // Disable keyboard entry (except arrow keys), credit to Oleg Estekhin for the general solution:
    // https://stackoverflow.com/a/7151715
    JSpinner.DefaultEditor trailingZeroesEditor = ( JSpinner.DefaultEditor ) trailingZeroesInput.getEditor();
    trailingZeroesEditor.getTextField().setEditable(false);
    trailingZeroesInput.setBounds(275, 30, 100, 25);
    trailingZeroesLabel.setBounds(25, 30, 200, 25);
    add(trailingZeroesInput);
    add(trailingZeroesLabel);
    
    // Set input for number of decimals
    JLabel numDecimalsLabel = new JLabel("Number of Decimal Places:");
    SpinnerNumberModel numDecimalsSpinner = new SpinnerNumberModel(Preferences.getNumOfDecimals(), 0, 6, 1);
    JSpinner numDecimalsInput = new JSpinner(numDecimalsSpinner);
    JSpinner.DefaultEditor numDecimalsEditor = ( JSpinner.DefaultEditor ) trailingZeroesInput.getEditor();
    numDecimalsEditor.getTextField().setEditable(false);
    numDecimalsInput.setBounds(275, 60, 100, 25);
    numDecimalsLabel.setBounds(25, 60, 200, 25);
    add(numDecimalsInput);
    add(numDecimalsLabel);
  }
}
