package rimplex.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.StringReader;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.TransferHandler;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import utilities.Complex;
import utilities.SessionHistory;

import javax.swing.AbstractAction;


import static rimplex.RimpleX.*;

/**
 * The main window for the RimpleX application.
 * 
 * General structure taken from Dr. Bernstein's Serialization Lab:
 * (https://w3.cs.jmu.edu/bernstdh/web/common/labs/experience_serialization/tempz/index.php)
 * 
 * @author Joseph Pogoretskiy, Benjamin Bonnell, Kalani Johnson, John Paul, Sofia Miller
 * 
 *         This work complies with JMU Honor Code.
 */
public class RimpleXWindow extends JFrame implements KeyListener
{
  private static final long serialVersionUID = 1L;
  private static final int WINDOW_WIDTH = 400;
  private static final int WINDOW_HEIGHT = 460;
  //due to integer division, history width must be even.
 	private static final int HISTORY_WIDTH = 224; 
 	private static final int EXPANDED_WIDTH = WINDOW_WIDTH + HISTORY_WIDTH;
 	private static final int ANIMATION_DURATION = 200;
 	private static final int ANIMATION_STEPS = 20;
 	
 	private static final String HELP = "Help";
 	private static final String COPYPLAINTEXT = "copyPlainText";
 	private static final String SERIF = "Serif";
 	private static final String ONE = "ONE";
 	private static final String TWO = "TWO";
 	private static final String THREE = "THREE";
 	private static final String FOUR = "FOUR";
 	private static final String FIVE = "FIVE";
 	private static final String SIX = "SIX";
 	private static final String SEVEN = "SEVEN";
 	private static final String EIGHT = "EIGHT";
 	private static final String NINE = "NINE";
 	private static final String ZERO = "ZERO";
 	private static final String DECIMAL = "DECIMAL";
 	private static final String EQUALS = "EQUALS";
 	private static final String UNIT = "UNIT";
 	private static final String ADD = "ADD";
 	private static final String SUBTRACT = "SUBTRACT";
 	private static final String MULTIPLY = "MULTIPLY";
 	private static final String DIVIDE = "DIVIDE";
 	    
 	
  private boolean isExpanded = false;
  private SessionHistoryDropoutBar dropoutBar;

  private Timer animationTimer;
  private int targetWidth = WINDOW_WIDTH;
  private int targetButtonX = 365;
  
  private JLabel sessionHistory;

  private RimpleXController controller;
  // private ResourceBundle rb;

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
    // rb = ResourceBundle.getBundle("rimplex.gui.languages.Strings", locale);

    // Set the controller to refer to this instance.
    this.controller = controller;
    this.controller.setWindow(this);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    Container contentPane = this.getContentPane();
    contentPane.setLayout(null);
    
    // sets the window icon.
    ImageIcon img = new ImageIcon(getClass().getResource("/icons/iconRimplex.png"));
    setIconImage(img.getImage());

    setupAnimationTimer();
    setupSessionHistoryDropout();
    setupSoftKeyboard();
    setupSessionHistoryDisplay();
    setupDisplay();
    sessionHistory.setVisible(isExpanded);

    BufferedImage myPicture = ImageIO.read(getClass().getResource("/icons/logoRimplex.png"));

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

    this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    this.setResizable(false);

    // Adding menu bar
    // File menu
    JMenuBar menuBar = new JMenuBar();
    
    JMenu fileMenu = new JMenu(rb.getString("File"));
    JMenuItem printItem = new JMenuItem(rb.getString("Print_Session"));
    printItem.setActionCommand("ACTION_PRINT");
    printItem.addActionListener(controller);
    fileMenu.add(printItem);
    JMenuItem newCalcItem = new JMenuItem(rb.getString("New_Calculator"));
    newCalcItem.setActionCommand("ACTION_NEWCALC");
    newCalcItem.addActionListener(controller);
    fileMenu.add(newCalcItem);
    JMenuItem exitItem = new JMenuItem(rb.getString("Exit"));
    exitItem.setActionCommand("ACTION_EXIT");
    exitItem.addActionListener(controller);
    fileMenu.add(exitItem);
    menuBar.add(fileMenu);
    // View menu
    JMenu viewMenu = new JMenu(rb.getString("View"));
    JMenuItem complexPlaneItem = new JMenuItem(rb.getString("Complex_Plane"));
    complexPlaneItem.addActionListener(e ->
    {
      Complex resultComplex = controller.getResult();
      if (resultComplex != null)
      {
        new ComplexPlaneWindow(resultComplex);
      }
      else
      {
        JOptionPane.showMessageDialog(this, rb.getString("No_Result"), rb.getString("Error"),
            JOptionPane.ERROR_MESSAGE);
      }
    });
    viewMenu.add(complexPlaneItem);
    menuBar.add(viewMenu);
    // Help menu
    JMenu helpMenu = new JMenu(rb.getString(HELP));
    JMenuItem helpItem = new JMenuItem(rb.getString(HELP));
    helpItem.setActionCommand("ACTION_HELP");
    helpItem.addActionListener(controller);
    JMenuItem aboutItem = new JMenuItem(rb.getString("About"));
    aboutItem.setActionCommand("ACTION_ABOUT");
    aboutItem.addActionListener(controller);
    helpMenu.add(aboutItem);
    helpMenu.add(helpItem);
    menuBar.add(helpMenu);
    
    // Preferences menu
    JMenu preferencesMenu = new JMenu(rb.getString("Preferences"));
    JMenuItem editItem = new JMenuItem(rb.getString("Edit"));
    editItem.setActionCommand("EDIT_PREFERENCES");
    editItem.addActionListener(controller);
    JMenuItem openItem = new JMenuItem(rb.getString("Open"));
    openItem.setActionCommand("OPEN_PREFERENCES");
    openItem.addActionListener(controller);
    JMenuItem saveItem = new JMenuItem(rb.getString("Save"));
    saveItem.setActionCommand("SAVE_PREFERENCES");
    saveItem.addActionListener(controller);
    preferencesMenu.add(editItem);
    preferencesMenu.add(openItem);
    preferencesMenu.add(saveItem);
    menuBar.add(preferencesMenu);
    
    setJMenuBar(menuBar);

    addKeyListener(this);
    setFocusable(true);

  }

  /**
   * Setup the buttons on the application.
   */
  private void setupSoftKeyboard()
  {
    getContentPane().add(new RimpleXButton(SEVEN,
        rb.getString("Seven"), controller, 10, 200, 45, 45));
    getContentPane().add(new RimpleXButton(EIGHT,
        rb.getString("Eight"), controller, 60, 200, 45, 45));
    getContentPane().add(new RimpleXButton(NINE,
				rb.getString("Nine"), controller, 110, 200, 45, 45));
    getContentPane().add(new RimpleXButton(FOUR,
				rb.getString("Four"), controller, 10, 250, 45, 45));
    getContentPane().add(new RimpleXButton(FIVE,
				rb.getString("Five"), controller, 60, 250, 45, 45));
    getContentPane().add(new RimpleXButton(SIX,
				rb.getString("Six"), controller, 110, 250, 45, 45));
    getContentPane().add(new RimpleXButton(ONE,
				rb.getString("One"), controller, 10, 300, 45, 45));
    getContentPane().add(new RimpleXButton(TWO,
				rb.getString("Two"), controller, 60, 300, 45, 45));
    getContentPane().add(new RimpleXButton(THREE,
				rb.getString("Three"), controller, 110, 300, 45, 45));
    getContentPane().add(new RimpleXButton(ZERO,
				rb.getString("Zero"), controller, 10, 350, 95, 45));
    getContentPane().add(new RimpleXButton(DECIMAL,
				".", controller, 210, 350, 45, 45));

    getContentPane().add(new RimpleXButton("BACKSPACE", "←", controller, 110, 150, 45, 45));

    // Add more buttons as new capabilities are added.

    // Adding parenthesis to GUI - John

    getContentPane().add(new RimpleXButton("OPEN_PARENTHESIS",
        rb.getString("Open_Paren"), controller, 210, 250, 45, 45));
    getContentPane()
        .add(new RimpleXButton("CLOSED_PARENTHESIS",
				rb.getString("Closed_Paren"), controller, 210, 300, 45, 45));

    // Adding Clear button to GUI - Ben
    getContentPane().add(new RimpleXButton(EQUALS,
				rb.getString("Equals"), controller, 160, 350, 45, 45));

    getContentPane().add(new RimpleXButton("CLEAR",
				rb.getString("Clear"), controller, 60, 150, 45, 45));
    getContentPane().add(new RimpleXButton("RESET",
				rb.getString("Reset"), controller, 210, 150, 45, 45));
    getContentPane().add(new RimpleXButton("SIGN",
				rb.getString("Sign"), controller, 10, 150, 45, 45));
    getContentPane().add(new RimpleXButton(UNIT,
				rb.getString("Unit"), controller, 110, 350, 45, 45));

    // Adding operator buttons
    getContentPane().add(new RimpleXButton(ADD,
				rb.getString("Add"), controller, 160, 150, 45, 45));
    // NOTE! This "subtraction" sign is a MINUS character. Not a hyphen.
    getContentPane().add(new RimpleXButton(SUBTRACT,
				rb.getString("Subtract"), controller, 160, 200, 45, 45));

    getContentPane().add(new RimpleXButton("INVERT",
				rb.getString("Invert"), controller, 210, 200, 45, 45));

    getContentPane().add(new RimpleXButton(MULTIPLY,
				rb.getString("Multiply"), controller, 160, 250, 45, 45));
    getContentPane().add(new RimpleXButton(DIVIDE,
				rb.getString("Divide"), controller, 160, 300, 45, 45));

    // Sprint 2 additional buttons.
    getContentPane()
        .add(new RimpleXButton("IMAGINARY_PART",
				rb.getString("Imaginary_Part"), controller, 260, 150, 95, 45));
    getContentPane().add(new RimpleXButton("REAL_PART",
				rb.getString("Real_Part"), controller, 260, 200, 95, 45));
    getContentPane()
        .add(new RimpleXButton("POLAR_FORM",
				rb.getString("Polar_Form"), controller, 260, 250, 95, 45));

    getContentPane().add(new RimpleXButton("CONJUGATE",
				rb.getString("Conjugate"), controller, 260, 300, 45, 45));
    getContentPane().add(new RimpleXButton("SQUARE_ROOT",
				rb.getString("Square_Root"), controller, 310, 300, 45, 45));
    getContentPane().add(new RimpleXButton("EXPONENT",
				rb.getString("Exponent"), controller, 260, 350, 45, 45));
    getContentPane().add(new RimpleXButton("LOGARITHM",
				rb.getString("Logarithm"), controller, 310, 350, 45, 45));
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
    display.setFont(new Font(SERIF, Font.PLAIN, 18));
    topDisplay.setFont(new Font(SERIF, Font.PLAIN, 18));

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
  
  /**
   * Sets up the session History Display.
   */
  @SuppressWarnings("serial")
  public void setupSessionHistoryDisplay()
  {
    sessionHistory = new JLabel("Session History:");
    sessionHistory.setVerticalAlignment(SwingConstants.TOP);
    sessionHistory.setVerticalTextPosition(SwingConstants.BOTTOM);
    sessionHistory.setBounds(365, 10, 220, 390);
    Border padding = BorderFactory.createEmptyBorder(5, 5, 5, 5);
    Border border = BorderFactory.createLineBorder(Color.BLACK);
    Border compound = new CompoundBorder(border, padding);

    sessionHistory.setBorder(compound);
    sessionHistory.setTransferHandler(new TransferHandler("text"));

    //made session history copiable
    sessionHistory.setFocusable(true);

    int menuMask = Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx();
    KeyStroke copyKS = KeyStroke.getKeyStroke(KeyEvent.VK_COPY, menuMask);
    sessionHistory.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                  .put(copyKS, COPYPLAINTEXT);
    sessionHistory.getActionMap()
                  .put(COPYPLAINTEXT, new AbstractAction() 
                  {
                    @Override
                    public void actionPerformed(final ActionEvent e)
                    {
                      String html = sessionHistory.getText();
                      try 
                      {
                        HTMLEditorKit kit = new HTMLEditorKit();
                        HTMLDocument doc = (HTMLDocument) kit.createDefaultDocument();
                        doc.putProperty("IgnoreCharsetDirective", Boolean.TRUE);
                        kit.read(new StringReader(html), doc, 0);
          
                        String plain = doc.getText(0, doc.getLength());
          
                        StringSelection sel = new StringSelection(plain);
                        Toolkit.getDefaultToolkit()
                               .getSystemClipboard()
                               .setContents(sel, null);
                      } catch (HeadlessException | BadLocationException
                          | IOException ex)
                      {
                        ex.printStackTrace();
                      }
                    }
                  });

    JPopupMenu popup = new JPopupMenu();
    JMenuItem copyItem = new JMenuItem("Copy");
    copyItem.addActionListener(evt ->
        sessionHistory.getActionMap()
                      .get(COPYPLAINTEXT)
                      .actionPerformed(new ActionEvent(sessionHistory, 0, null))
    );
    popup.add(copyItem);
    sessionHistory.setComponentPopupMenu(popup);

    sessionHistory.addMouseListener(new MouseAdapter()
    {
      @Override
      public void mousePressed(final MouseEvent e)
      {
        sessionHistory.requestFocusInWindow();
      }
    });
    SessionHistory.setLabel(sessionHistory);
    getContentPane().add(sessionHistory);
    
  }

  @Override
  public void keyTyped(final KeyEvent e)
  {
    char keyChar = e.getKeyChar();
    String actionCommand = null;

    switch (keyChar)
    {
      case '1':
        actionCommand = ONE;
        break;
      case '2':
        actionCommand = TWO;
        break;
      case '3':
        actionCommand = THREE;
        break;
      case '4':
        actionCommand = FOUR;
        break;
      case '5':
        actionCommand = FIVE;
        break;
      case '6':
        actionCommand = SIX;
        break;
      case '7':
        actionCommand = SEVEN;
        break;
      case '8':
        actionCommand = EIGHT;
        break;
      case '9':
        actionCommand = NINE;
        break;
      case '0':
        actionCommand = ZERO;
        break;
      case '.':
        actionCommand = DECIMAL;
        break;
      case '+':
        actionCommand = ADD;
        break;
      case '-':
        actionCommand = SUBTRACT;
        break;
      case '*':
        actionCommand = MULTIPLY;
        break;
      case '/':
        actionCommand = DIVIDE;
        break;
      case 'i':
        actionCommand = UNIT;
        break;
      default:
        break;
    }
    if (e.getKeyChar() == KeyEvent.VK_ENTER)
    {
      actionCommand = EQUALS;
    }
    if (actionCommand != null)
    {
      ActionEvent ae = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, actionCommand);
      controller.actionPerformed(ae);
    }
  }

  private void setupSessionHistoryDropout()
  {
    dropoutBar = new SessionHistoryDropoutBar(controller);
    getContentPane().add(dropoutBar);
  }

  private void setupAnimationTimer()
  {
    // animation timer with function attached.
    animationTimer = new Timer(ANIMATION_DURATION / ANIMATION_STEPS, e ->
    {

      if (isExpanded)
      {
        sessionHistory.setVisible(true);
      }
      
      int currentWidth = getWidth();
      // this lets us move the button
      int currentButtonX = dropoutBar.getX();

      int widthStep = Integer.compare(targetWidth, currentWidth) * 2; // Fixed step of 2px
      int buttonStep = Integer.compare(targetButtonX, currentButtonX) * 2;

      setSize(currentWidth + widthStep, WINDOW_HEIGHT);
      dropoutBar.setLocation(currentButtonX + buttonStep, dropoutBar.getY());

      // stop it when its in place.
      // because targetWidth works both ways, it has to be equals.
      if (currentWidth == targetWidth && currentButtonX == targetButtonX)
      {
        if (!isExpanded)
        {
          sessionHistory.setVisible(false);
        }
        animationTimer.stop();
        // change the text of the bar accordingly.
        dropoutBar.setText(!isExpanded ? ">" : "<");
      }
    });
  }
  
  /**
   * Toggle method for the expansion of the session history.
   */
  public void toggleExpansion()
  {
    if (animationTimer.isRunning())
      return;

    // flip expansion logic
    isExpanded = !isExpanded;
    
    // if it isnt expanded, set target to expanded width.
    // if it IS expanded, set target to window width.
    if (!isExpanded)
    {
      targetWidth = WINDOW_WIDTH;
      targetButtonX = 365;
    }
    else
    {
      targetWidth = EXPANDED_WIDTH;
      targetButtonX = 365 + HISTORY_WIDTH;
    }

    animationTimer.start();
  }

  @Override
  public void keyPressed(final KeyEvent e)
  {
    // Not needed
  }

  @Override
  public void keyReleased(final KeyEvent e)
  {
    // Not needed
  }
}
