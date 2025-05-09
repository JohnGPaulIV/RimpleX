package rimplex.gui;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import static rimplex.RimpleX.*;

/**
 * The 'About' Window for RimpleX.
 * 
 * @author Kalani Johnson
 */
public class RimpleXAboutWindow extends JFrame
{
  private static final long serialVersionUID = 1L;
  private final String sansSerif = "Sans-Serif";
  private final String about = "About";
  
  /**
   * Constructs "About" Window for Rimplex.
   */
  public RimpleXAboutWindow() throws IOException
  {
    setTitle(rb.getString(about) + " RimpleX");
    setSize(400, 275);
    setLocationRelativeTo(null);
    setLayout(null);
    setResizable(false);

    try
    {
      // Add title and RimpleX icon to the About Window.
      JLabel titleLabel = new JLabel(rb.getString(about), JLabel.CENTER);
      titleLabel.setFont(new Font(sansSerif, Font.PLAIN, 16));
      titleLabel.setBounds(0, 10, 400, 20);
      add(titleLabel);

      BufferedImage icon = ImageIO.read(getClass().getResource("/icons/iconRimplex.png"));
      
      // sets the window icon.
      ImageIcon img = new ImageIcon(getClass().getResource("/icons/iconRimplex.png"));
      setIconImage(img.getImage());

      int iconWidth = icon.getWidth() / 2;
      int iconHeight = icon.getHeight() / 2;

      BufferedImage scaledicon = new BufferedImage(iconWidth, iconHeight,
          BufferedImage.TYPE_INT_ARGB);
      Graphics2D g2d = scaledicon.createGraphics();
      g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
          RenderingHints.VALUE_INTERPOLATION_BILINEAR);
      g2d.drawImage(icon, 0, 0, iconWidth, iconHeight, null);
      g2d.dispose();

      JLabel iconLabel = new JLabel(new ImageIcon(scaledicon));
      iconLabel.setBounds((400 - iconWidth) / 2, 35, iconWidth, iconHeight);
      add(iconLabel);

      // Add version label
      JLabel versionLabel = new JLabel("Rimplex v1.0", JLabel.CENTER);
      versionLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
      versionLabel.setBounds(0, 35 + iconHeight, 400, 20);
      add(versionLabel);

      // About description
      String description = rb.getString("RimpleX_About");

      JLabel descLabel = new JLabel(description, JLabel.CENTER);
      descLabel.setFont(new Font(sansSerif, Font.PLAIN, 14));
      descLabel.setBounds(10, 35 + iconHeight + 25, 360, 130);
      add(descLabel);

    }
    catch (IOException e)
    {
      e.printStackTrace();
    }

  }
}
