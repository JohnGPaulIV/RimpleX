package rimplex.gui;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;

public class SessionHistoryWindow extends JFrame
{
  private static final long serialVersionUID = 1L;
  private JFrame mainWindow;
  private int offsetX = 142;
  private int offsetY = 50;
  private int targetX = 372;
  private SessionHistoryDropoutBar dropoutBar;
  private final int ANIMATION_DURATION = 200;
  private final int ANIMATION_STEPS = 20;
  private Timer animationTimer;
  private boolean isExpanded = false;
  private DropoutWindowController controller;

  public SessionHistoryWindow(final RimpleXWindow mainWindow, final JTextArea sessionHistory)
  {
    this.setUndecorated(true);
    this.setFocusableWindowState(false);
    setSize(260, 400);
    setLocationRelativeTo(null);
    setLayout(null);
    setResizable(false);

    this.mainWindow = mainWindow;
    this.controller = new DropoutWindowController(this);

    // Create and add the dropout bar
    dropoutBar = new SessionHistoryDropoutBar(controller);
    dropoutBar.setBounds(233, 2, 25, 395);
    dropoutBar.setText(">");
    add(dropoutBar);

    // display.setBounds(10, 10, 220, 380);
    // add(display);

    // System.out.println(display);

    JScrollPane display2 = new JScrollPane(sessionHistory, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    display2.setBounds(10, 10, 220, 380);
    add(display2);

    setupMainWindowTracking();
    updateLocation();
    setupAnimationTimer();
  }

  private void setupAnimationTimer()
  {
    animationTimer = new Timer(ANIMATION_DURATION / ANIMATION_STEPS, e -> {
      int stepSize = (targetX - offsetX) / ANIMATION_STEPS;
      int currentX = getX();
      int newX;

      if (isExpanded)
      {
        // Animate back to original position
        newX = currentX - stepSize;
        if (newX <= mainWindow.getX() + offsetX)
        {
          newX = mainWindow.getX() + offsetX;
          isExpanded = false;
          animationTimer.stop();
          dropoutBar.setText(">");
        }
      }
      else
      {
        // Animate to target position
        newX = currentX + stepSize;
        if (newX >= mainWindow.getX() + targetX)
        {
          newX = mainWindow.getX() + targetX;
          isExpanded = true;
          animationTimer.stop();
          dropoutBar.setText("<");
        }
      }

      setLocation(newX, getY());
    });
  }

  private void setupMainWindowTracking()
  {
    mainWindow.addComponentListener(new ComponentAdapter()
    {
      @Override
      public void componentMoved(ComponentEvent e)
      {
        updateLocation();
        mainWindow.toFront();
      }
    });
  }

  public void updateLocation()
  {
    if (mainWindow != null && mainWindow != null)
    {
      this.setLocation(mainWindow.getX() + offsetX, mainWindow.getY() + offsetY);
      isExpanded = false;
      dropoutBar.setText(">");
    }
  }

  public void toggleAnimation()
  {
    if (!animationTimer.isRunning())
    {
      animationTimer.start();
    }
  }
}
