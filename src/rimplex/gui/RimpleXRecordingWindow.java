package rimplex.gui;

import java.awt.Color;
import java.awt.Font;

import static rimplex.RimpleX.rb;

import javax.swing.*;

/**
 * Represents the Recording controller window.
 * 
 * @author Benjamin Bonnell
 * 
 *         This work complies with JMU Honor Code.
 */
public class RimpleXRecordingWindow extends JFrame
{
  private static RimpleXRecordingWindow instance;

  public static RimpleXRecordingWindow getInstance(RimpleXController controller)
  {
    if (instance == null)
    {
      instance = new RimpleXRecordingWindow(controller);
    }
    return instance;
  }

  /**
   * Constructs Playback Controller Window for RimpleX.
   */
  private RimpleXRecordingWindow(RimpleXController controller)
  {
    setTitle("Recording to: ");
    setSize(350, 100);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    setLocationRelativeTo(null);
    setLayout(null);
    setResizable(false);

    RimpleXButton record = new RimpleXButton("RECORDING_START", "⏺", controller, 10, 10, 45, 45);
    record.setForeground(new Color(163, 0, 0));
    record.setFont(new Font("Sans-Serif", Font.PLAIN, 42));
    add(record);
    RimpleXButton pause = new RimpleXButton("RECORDING_PAUSE", "⏸", controller, 60, 10, 45, 45);
    add(pause);
    RimpleXButton stop = new RimpleXButton("RECORDING_STOP", "◼", controller, 110, 10, 45, 45);
    add(stop);

  }

  @Override
  public void dispose()
  {
    super.dispose();
    instance = null; // Clear the instance when the window is closed

  }

  public static boolean isWindowVisible()
  {
    return instance != null && instance.isVisible();
  }
}
