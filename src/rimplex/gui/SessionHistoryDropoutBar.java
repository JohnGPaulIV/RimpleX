package rimplex.gui;

import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * Represents the button to extend the session history.
 * 
 * @author Joseph Pogoretskiy, Benjamin Bonnell
 * 
 * This work complies with JMU Honor Code.
 */
public class SessionHistoryDropoutBar extends JButton
{

  private static final long serialVersionUID = 1L;


  /**
   * Session History Drop-out Bar constructor.
   * @param controller Controller object that the drop-out uses.
   */
  public SessionHistoryDropoutBar(final ActionListener controller)
  {
    this.setActionCommand("S_HISTORY_DROPOUT");
    this.addActionListener(controller);
    this.setBounds(365, 2, 25, 400);
    this.setMargin(new Insets(0,0,0,0));  // Smaller than default
    this.setText(">");
    this.setFont(new Font("Serif", Font.BOLD, 14));
    

  }

}
