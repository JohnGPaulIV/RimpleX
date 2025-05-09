package rimplex.gui;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;

import static rimplex.RimpleX.*;

/**
 * The pop-up window when a relational operation is evaluated.
 * 
 * @author Joseph Pogoretskiy
 */
public class RimpleXRelationalOperation extends JDialog
{
  private static final long serialVersionUID = 1L;
  private static final String RESULT = "Relational_Operation_Result";
  private final String sansSerif = "Sans-Serif";
  
  private JLabel descLabel;

  /**
   * Constructs "About" Window for Rimplex.
   */
  public RimpleXRelationalOperation()
  {
    setTitle(rb.getString(RESULT));
    setSize(400, 150);
    setLocationRelativeTo(null);
    setLayout(null);
    setResizable(false);
    setModal(true);

    // Add title and RimpleX icon to the About Window.
    JLabel titleLabel = new JLabel(rb.getString(RESULT), JLabel.CENTER);
    titleLabel.setFont(new Font(sansSerif, Font.PLAIN, 16));
    titleLabel.setBounds(0, 10, 400, 20);
    add(titleLabel);

    // sets the window icon.
    ImageIcon img = new ImageIcon(getClass().getResource("/icons/iconRimplex.png"));
    setIconImage(img.getImage());
    
    descLabel = new JLabel("", JLabel.CENTER);
    descLabel.setFont(new Font(sansSerif, Font.PLAIN, 14));
    descLabel.setBounds(10, 35 + 25, 360, 60);
    add(descLabel);
  }
  
  /**
   * Set the result of the relational operation to the window.
   * 
   * @param result The result of the evaluation.
   */
  public void setResult(final String result)
  {
    descLabel.setText(result);
  }
}
