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

/**
 * The 'About' Window for RimpleX.
 * 
 * @author Kalani Johnson
 */
public class RimpleXAboutWindow extends JFrame
{
  private static final long serialVersionUID = 1L;

  public RimpleXAboutWindow() throws IOException
  {
    setTitle("About RimpleX");
    setSize(400, 250);
    setLocationRelativeTo(null);
    setLayout(null);
    setResizable(false);

    try
    {
      // Add RimpleX Logo to the About Window.
      BufferedImage myPicture = ImageIO.read(new File("logoRimplex.png"));

      int scaledWidth = myPicture.getWidth() / 2;
      int scaledHeight = myPicture.getHeight() / 2;

      BufferedImage scaledImage = new BufferedImage(scaledWidth, scaledHeight,
          BufferedImage.TYPE_INT_ARGB);
      Graphics2D g2d = scaledImage.createGraphics();
      g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
          RenderingHints.VALUE_INTERPOLATION_BILINEAR);
      g2d.drawImage(myPicture, 0, 0, scaledWidth, scaledHeight, null);
      g2d.dispose();

      JLabel picLabel = new JLabel(new ImageIcon(scaledImage));
      picLabel.setBounds(10, 10, scaledWidth, scaledHeight);
      getContentPane().add(picLabel);

      // About description
      String aboutText = "TODO: Add About Description";

      JLabel aboutLabel = new JLabel("<html>" + aboutText.replace("\n", "<br>") + "</html>");
      aboutLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 14));
      aboutLabel.setBounds(10, scaledHeight + 20, 365, 120);
      aboutLabel.setBackground(getContentPane().getBackground());

      getContentPane().add(aboutLabel);

    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

  }
}
