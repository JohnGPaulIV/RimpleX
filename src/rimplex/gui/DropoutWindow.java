package rimplex.gui;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;

/**
 * Abstract Dropout Window class for session history and intermediate changes.
 * 
 * Author: Benjamin Bonnell
 * 
 * This work complies with JMU honor code.
 */
public abstract class DropoutWindow extends JFrame
{
  private static final long serialVersionUID = 1L;
  private JFrame mainWindow;
  private int offsetX;
  private int offsetY = 50;
  private int targetX;
  private int slideRange = 230;
  private DropoutBar dropoutBar;
  private final int ANIMATION_DURATION = 200;
  private final int ANIMATION_STEPS = 20;
  private Timer animationTimer;
  private boolean isExpanded = false;
  private DropoutWindowController controller;
  private String closedIcon;
  private String openIcon;

  /**
   * Generic Dropout Window constructor.
   * 
   * @param mainWindow The main window to follow.
   * @param contents The JTextArea contents.
   * @param orientation either "right" or "left" of the calculator.
   * @param offsetX the physical place it will stay relative to the main window.
   */
  public DropoutWindow(final RimpleXWindow mainWindow, final JTextArea contents,
      final String orientation, final int offsetX)
  {
    this.setUndecorated(true);
    this.setFocusableWindowState(false);
    setSize(260, 400);
    setLocationRelativeTo(null);
    setLayout(null);
    setResizable(false);
    this.mainWindow = mainWindow;
    this.controller = new DropoutWindowController(this);
    this.offsetX = offsetX;
    
    // Create the dropout bar
    dropoutBar = new DropoutBar(controller);
    
    // Create the display
    JScrollPane display = new JScrollPane(contents, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    if (orientation.equals("right"))
    {
      this.targetX = offsetX + slideRange;
      this.closedIcon = ">";
      this.openIcon = "<";
      dropoutBar.setBounds(233, 2, 25, 395);
      display.setBounds(10, 10, 220, 380);
    }
    else if (orientation.equals("left")) 
    {
      this.targetX = offsetX - slideRange;
      this.closedIcon = "<";
      this.openIcon = ">";  
      dropoutBar.setBounds(2, 2, 25, 395);
      display.setBounds(35, 10, 220, 380);
    }
    else 
    {
      throw new UnsupportedOperationException("window must be either 'left' or 'right'");
    }
    
    add(dropoutBar);
    add(display);
    
    setupMainWindowTracking();
    updateLocation();
    setupAnimationTimer();
  }
  
  /**
   * Set up the timer that controls the smooth sliding animation.
   */
  private void setupAnimationTimer()
  {
    animationTimer = new Timer(ANIMATION_DURATION / ANIMATION_STEPS, e ->
    {
      int stepSize = (targetX - offsetX) / ANIMATION_STEPS;
      int currentX = getX();
      int newX;
      
      boolean isRightOriented = targetX > offsetX;
      if (isExpanded)
      {
        // Animate back to original position
        newX = currentX - stepSize;
        if ((isRightOriented && newX <= mainWindow.getX() + offsetX)
            || (!isRightOriented && newX >= mainWindow.getX() + offsetX))
        {
          newX = mainWindow.getX() + offsetX;
          isExpanded = false;
          animationTimer.stop();
          dropoutBar.setText(closedIcon);
        }
      }
      else
      {
        // Animate to target position
        newX = currentX + stepSize;
        if ((isRightOriented && newX >= mainWindow.getX() + targetX)
            || (!isRightOriented && newX <= mainWindow.getX() + targetX))
        {
          newX = mainWindow.getX() + targetX;
          isExpanded = true;
          animationTimer.stop();
          dropoutBar.setText(openIcon);
        }
      }

      setLocation(newX, getY());
    });
  }
  
  /**
   * Setup the tracking of the window it shall follow.
   */
  private void setupMainWindowTracking()
  {
    mainWindow.addComponentListener(new ComponentAdapter()
    {
      @Override
      public void componentMoved(final ComponentEvent e)
      {
        updateLocation();
        mainWindow.toFront();
      }
    });
  }

  /**
   * Update the location of the window to be where it is supposed to be.
   */
  public void updateLocation()
  {
    if (mainWindow != null && mainWindow != null)
    {
      this.setLocation(mainWindow.getX() + offsetX, mainWindow.getY() + offsetY);
      isExpanded = false;
      dropoutBar.setText(closedIcon);
    }
  }

  /**
   * Either open or close the timer.
   */
  public void toggleAnimation()
  {
    if (!animationTimer.isRunning())
    {
      animationTimer.start();
    }
  }
}
