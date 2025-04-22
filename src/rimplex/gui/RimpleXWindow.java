package rimplex.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

/**
 * The main window for the RimpleX application.
 * 
 * General structure taken from Dr. Bernstein's Serialization Lab:
 * (https://w3.cs.jmu.edu/bernstdh/web/common/labs/experience_serialization/tempz/index.php)
 * 
 * @author Joseph Pogoretskiy, Benjamin Bonnell, Kalani Johnson, John Paul, Sofia Miller
 * 
 * This work complies with JMU Honor Code.
 */
public class RimpleXWindow extends JFrame implements KeyListener
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

    this.setSize(375, 460);
    this.setResizable(false);

    // Adding menu bar
    // File menu
    JMenuBar menuBar = new JMenuBar();

    JMenu fileMenu = new JMenu("File");
    JMenuItem printItem = new JMenuItem("Print Session");
    printItem.setActionCommand("ACTION_PRINT");
    printItem.addActionListener(controller);
    fileMenu.add(printItem);
    menuBar.add(fileMenu);
    JMenuItem exitItem = new JMenuItem("Exit");
    exitItem.setActionCommand("ACTION_EXIT");
    exitItem.addActionListener(controller);
    fileMenu.add(exitItem);
    // Help menu
    JMenu helpMenu = new JMenu("Help");
    JMenuItem helpItem = new JMenuItem("Help");
    helpItem.setActionCommand("ACTION_HELP");
    helpItem.addActionListener(controller);
    JMenuItem aboutItem = new JMenuItem("About");
    aboutItem.setActionCommand("ACTION_ABOUT");
    aboutItem.addActionListener(controller);
    helpMenu.add(aboutItem);
    helpMenu.add(helpItem);
    menuBar.add(helpMenu);
    setJMenuBar(menuBar);

    addKeyListener(this);
    setFocusable(true);
  }

  /**
   * Setup the buttons on the application.
   */
  private void setupSoftKeyboard()
  {
    getContentPane().add(new RimpleXButton("SEVEN", "7", controller, 10, 200, 45, 45));
    getContentPane().add(new RimpleXButton("EIGHT", "8", controller, 60, 200, 45, 45));
    getContentPane().add(new RimpleXButton("NINE", "9", controller, 110, 200, 45, 45));
    getContentPane().add(new RimpleXButton("FOUR", "4", controller, 10, 250, 45, 45));
    getContentPane().add(new RimpleXButton("FIVE", "5", controller, 60, 250, 45, 45));
    getContentPane().add(new RimpleXButton("SIX", "6", controller, 110, 250, 45, 45));
    getContentPane().add(new RimpleXButton("ONE", "1", controller, 10, 300, 45, 45));
    getContentPane().add(new RimpleXButton("TWO", "2", controller, 60, 300, 45, 45));
    getContentPane().add(new RimpleXButton("THREE", "3", controller, 110, 300, 45, 45));
    getContentPane().add(new RimpleXButton("ZERO", "0", controller, 10, 350, 95, 45));
    getContentPane().add(new RimpleXButton("DECIMAL", ".", controller, 210, 350, 45, 45));

    getContentPane().add(new RimpleXButton("BACKSPACE", "←", controller, 110, 150, 45, 45));

    // Add more buttons as new capabilities are added.

    // Adding parenthesis to GUI - John

    getContentPane().add(new RimpleXButton("OPEN_PARENTHESIS", "(", controller, 210, 250, 45, 45));
    getContentPane()
        .add(new RimpleXButton("CLOSED_PARENTHESIS", ")", controller, 210, 300, 45, 45));

    // Adding Clear button to GUI - Ben
    getContentPane().add(new RimpleXButton("EQUALS", "=", controller, 160, 350, 45, 45));

    getContentPane().add(new RimpleXButton("CLEAR", "C", controller, 60, 150, 45, 45));
    getContentPane().add(new RimpleXButton("RESET", "R", controller, 210, 150, 45, 45));
    getContentPane().add(new RimpleXButton("SIGN", "±", controller, 10, 150, 45, 45));
    getContentPane().add(new RimpleXButton("UNIT", "𝑖", controller, 110, 350, 45, 45));

    // Adding operator buttons
    getContentPane().add(new RimpleXButton("ADD", "+", controller, 160, 150, 45, 45));
    // NOTE! This "subtraction" sign is a MINUS character. Not a hyphen.
    getContentPane().add(new RimpleXButton("SUBTRACT", "−", controller, 160, 200, 45, 45));

    getContentPane().add(new RimpleXButton("INVERT", "Inv", controller, 210, 200, 45, 45));

    getContentPane().add(new RimpleXButton("MULTIPLY", "×", controller, 160, 250, 45, 45));
    getContentPane().add(new RimpleXButton("DIVIDE", "÷", controller, 160, 300, 45, 45));

    // Sprint 2 additional buttons.
    getContentPane()
        .add(new RimpleXButton("IMAGINARY_PART", "Imaginary Part", controller, 260, 150, 95, 45));
    getContentPane().add(new RimpleXButton("REAL_PART", "Real Part", controller, 260, 200, 95, 45));
    getContentPane()
        .add(new RimpleXButton("POLAR_FORM", "Polar Form", controller, 260, 250, 95, 45));

    getContentPane().add(new RimpleXButton("CONJUGATE", "z̄", controller, 260, 300, 45, 45));
    getContentPane().add(new RimpleXButton("SQUARE_ROOT", "√", controller, 310, 300, 45, 45));
    getContentPane().add(new RimpleXButton("EXPONENT", "^", controller, 260, 350, 45, 45));
    getContentPane().add(new RimpleXButton("LOGARITHM", "Log", controller, 310, 350, 45, 45));
  }

  /**
   * Setup the display on the application.
   */
  private void setupDisplay()
  {
    // Create new displays.
    JLabel display = new JLabel("", SwingConstants.RIGHT);
    JLabel topDisplay = new JLabel("");
    this.controller.setDisplays(display, topDisplay);

    // Set the font of the displayed text.
    display.setFont(new Font("Serif", Font.PLAIN, 18));
    topDisplay.setFont(new Font("Serif", Font.PLAIN, 18));

    // Set the size of the displays.
    display.setBounds(10, 65, 345, 50);
    topDisplay.setBounds(10, 65, 345, 30);

    // Using compound border to set padding of the text in the display:
    // https://docs.oracle.com/javase/7/docs/api/javax/swing/border/CompoundBorder.html
    Border padding = BorderFactory.createEmptyBorder(30, 5, 5, 5);
    Border border = BorderFactory.createLineBorder(Color.BLACK);
    Border compound = new CompoundBorder(border, padding);

    display.setBorder(compound);
    topDisplay.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

    getContentPane().add(display);
    getContentPane().add(topDisplay);
  }

  @Override
  public void keyTyped(KeyEvent e)
  {
    char keyChar = e.getKeyChar();
    String actionCommand = null;

    switch (keyChar)
    {
      case '1':
        actionCommand = "ONE";
        break;
      case '2':
        actionCommand = "TWO";
        break;
      case '3':
        actionCommand = "THREE";
        break;
      case '4':
        actionCommand = "FOUR";
        break;
      case '5':
        actionCommand = "FIVE";
        break;
      case '6':
        actionCommand = "SIX";
        break;
      case '7':
        actionCommand = "SEVEN";
        break;
      case '8':
        actionCommand = "EIGHT";
        break;
      case '9':
        actionCommand = "NINE";
        break;
      case '0':
        actionCommand = "ZERO";
        break;
      case '.':
        actionCommand = "DECIMAL";
        break;
      case '+':
        actionCommand = "ADD";
        break;
      case '-':
        actionCommand = "SUBTRACT";
        break;
      case '*':
        actionCommand = "MULTIPLY";
        break;
      case '/':
        actionCommand = "DIVIDE";
        break;
      case 'i':
        actionCommand = "UNIT";
        break;
    }
    if (e.getKeyChar() == KeyEvent.VK_ENTER)
    {
      actionCommand = "EQUALS";
    }
    if (actionCommand != null)
    {
      ActionEvent ae = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, actionCommand);
      controller.actionPerformed(ae);
    }
  }

  @Override
  public void keyPressed(KeyEvent e)
  {
    // Not needed
  }

  @Override
  public void keyReleased(KeyEvent e)
  {
    // Not needed
  }
}
