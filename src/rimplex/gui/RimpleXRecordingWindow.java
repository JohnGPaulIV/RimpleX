package rimplex.gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;

/**
 * Represents the recording controller window.
 * 
 * @author Benjamin Bonnell
 * 
 *         This work complies with JMU Honor Code.
 */
public class RimpleXRecordingWindow extends JFrame
{
  private static RimpleXRecordingWindow instance;
  private String filePath;

  /**
   * Gets the instance of the window for to ensure no duplicates.
   * 
   * @param controller
   * @param filePath
   * @return
   */
  public static RimpleXRecordingWindow getInstance(RimpleXController controller, String filePath)
  {
    if (instance == null)
    {
      instance = new RimpleXRecordingWindow(controller, filePath);
    }
    else
    {
      instance.updateFilePath(filePath);
    }
    return instance;
  }

  public static RimpleXRecordingWindow getInstance(RimpleXController controller)
  {
    return getInstance(controller, null);
  }

  /**
   * @param controller
   * @param filePath
   */
  private RimpleXRecordingWindow(RimpleXController controller, String filePath)
  {
    this.filePath = filePath;
    initializeWindow(controller);
  }

  private void initializeWindow(RimpleXController controller)
  {
    setTitle("Recording to: " + filePath);
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

  public void updateFilePath(String newPath)
  {
    this.filePath = newPath;
    setTitle("Recording to: " + newPath);
  }

  @Override
  public void dispose()
  {
    super.dispose();
    instance = null;
  }

  public static boolean isWindowVisible()
  {
    return instance != null && instance.isVisible();
  }
}
