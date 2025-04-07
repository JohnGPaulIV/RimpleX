package rimplex.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

/**
 * The main window for the RimpleX application.
 * 
 * General structure taken from Dr. Bernstein's Serialization Lab:
 * (https://w3.cs.jmu.edu/bernstdh/web/common/labs/experience_serialization/tempz/index.php)
 * 
 * This work complies with JMU Honor Code.
 */
public class RimpleXWindow extends JFrame
{
  private static final long serialVersionUID = 1L;

  private RimpleXController controller;

  /**
   * Explicit window constructor.
   * 
   * @param controller
   *          The observer for all GUI components.
   * @throws IOException 
   */
  public RimpleXWindow(final RimpleXController controller) throws IOException
  {
    super();

    // Set the controller to refer to this instance.
    this.controller = controller;
    this.controller.setWindow(this);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    Container contentPane = this.getContentPane();
    contentPane.setLayout(null);
    


    setupSoftKeyboard();
    setupDisplay();
    
    BufferedImage myPicture = ImageIO.read(new File("logoRimplex.png"));

    // Calculate scaled dimensions
    int scaledWidth = myPicture.getWidth() / 2;
    int scaledHeight = myPicture.getHeight() / 2;

    // Create scaled image
    BufferedImage scaledImage = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = scaledImage.createGraphics();
    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    g2d.drawImage(myPicture, 0, 0, scaledWidth, scaledHeight, null);
    g2d.dispose();

    JLabel picLabel = new JLabel(new ImageIcon(scaledImage));
    picLabel.setBounds(10, 10, scaledWidth, scaledHeight);
    getContentPane().add(picLabel);

    this.setSize(275, 420);
    this.setResizable(false);
    
    //Adding menu bar
    JMenuBar menuBar = new JMenuBar();
    
    JMenu fileMenu = new JMenu("File");
    JMenuItem exitItem = new JMenuItem("Exit");
    exitItem.setActionCommand("ACTION_EXIT");
    exitItem.addActionListener(controller);
    fileMenu.add(exitItem);
    menuBar.add(fileMenu);
    setJMenuBar(menuBar);
    
    // TODO: Add Java GUI Components to the main window here
    // TODO: Set size, layout, all those goodies. Helper functions can be utilized.
  }

  /**
   * Setup the buttons on the application.
   */
  private void setupSoftKeyboard()
  {
    getContentPane().add(new RimpleXButton("SEVEN", "7", controller, 10, 160, 45, 45));
    getContentPane().add(new RimpleXButton("EIGHT", "8", controller, 60, 160, 45, 45));
    getContentPane().add(new RimpleXButton("NINE", "9", controller, 110, 160, 45, 45));
    getContentPane().add(new RimpleXButton("FOUR", "4", controller, 10, 210, 45, 45));
    getContentPane().add(new RimpleXButton("FIVE", "5", controller, 60, 210, 45, 45));
    getContentPane().add(new RimpleXButton("SIX", "6", controller, 110, 210, 45, 45));
    getContentPane().add(new RimpleXButton("ONE", "1", controller, 10, 260, 45, 45));
    getContentPane().add(new RimpleXButton("TWO", "2", controller, 60, 260, 45, 45));
    getContentPane().add(new RimpleXButton("THREE", "3", controller, 110, 260, 45, 45));
    getContentPane().add(new RimpleXButton("ZERO", "0", controller, 10, 310, 95, 45));
    getContentPane().add(new RimpleXButton("DECIMAL", ".", controller, 210, 310, 45, 45));
    
    getContentPane().add(new RimpleXButton("BACKSPACE", "\u2190", controller, 110, 110, 45, 45));

    // Add more buttons as new capabilities are added.
    
    // Adding parenthesis to GUI - John

    getContentPane().add(new RimpleXButton("OPEN_PARENTHESIS", "(", controller, 210, 210, 45, 45));
    getContentPane().add(new RimpleXButton("CLOSED_PARENTHESIS", ")", controller, 210, 260, 45, 45));
    
    // Adding Clear button to GUI - Ben
    getContentPane().add(new RimpleXButton("CLEAR", "C", controller, 60, 110, 45, 45));
    getContentPane().add(new RimpleXButton("RESET", "R", controller, 210, 110, 45, 45));
    getContentPane().add(new RimpleXButton("SIGN", "±", controller, 10, 110, 45, 45));
    
    //Adding operator buttons
    getContentPane().add(new RimpleXButton("ADD", "+", controller, 160, 110, 45, 45));
    getContentPane().add(new RimpleXButton("SUBTRACT", "-", controller, 160, 160, 45, 45));
    getContentPane().add(new RimpleXButton("MULTIPLY", "×", controller, 160, 210, 45, 45));
    getContentPane().add(new RimpleXButton("DIVIDE", "÷", controller, 160, 260, 45, 45));
  }

  /**
   * Setup the display on the application.
   */
  private void setupDisplay()
  {
    JLabel display = new JLabel("");
    this.controller.setDisplay(display);

    display.setBounds(10, 55, 245, 40);
    
    // Using compound border to set padding of the text in the display:
    // https://docs.oracle.com/javase/7/docs/api/javax/swing/border/CompoundBorder.html
    Border padding = BorderFactory.createEmptyBorder(5, 5, 5, 5);
    Border border = BorderFactory.createLineBorder(Color.BLACK);
    Border compound = new CompoundBorder(border, padding);
    
    display.setBorder(compound);
    getContentPane().add(display);
  }
}
