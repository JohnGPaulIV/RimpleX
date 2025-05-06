package rimplex.gui;

import java.awt.Font;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
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
  private final String sansSerif = "Sans-Serif";
  
  JLabel descLabel;

  /**
   * Constructs "About" Window for Rimplex.
   */
  public RimpleXRelationalOperation()
  {
    setTitle(rb.getString("Relational_Operation_Result"));
    setSize(400, 150);
    setLocationRelativeTo(null);
    setLayout(null);
    setResizable(false);
    setModal(true);

    // Add title and RimpleX icon to the About Window.
    JLabel titleLabel = new JLabel(rb.getString("Relational_Operation_Result"), JLabel.CENTER);
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
  
  public void setResult(final String result)
  {
    descLabel.setText(result);
  }
}
