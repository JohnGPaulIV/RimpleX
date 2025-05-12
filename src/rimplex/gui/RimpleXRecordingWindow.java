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
  private static final String RECORDING_TO = "Recording to: ";
  private String filePath;

  /**
   * Gets the instance of the window for to ensure no duplicates.
   *
   * @param controller
   *          The control button
   * @param filePath
   *          The file path.
   * @return Returns window
   */
  public static RimpleXRecordingWindow getInstance(final RimpleXController controller,
      final String filePath)
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

  /**
   * Creates instance of a new recording window.
   *
   * @param controller
   *          The control button.
   * @return Returns a new recording window
   */
  public static RimpleXRecordingWindow getInstance(final RimpleXController controller)
  {
    return getInstance(controller, null);
  }

  /**
   * Creates new recording window.
   *
   * @param controller
   *          The control button.
   * @param filePath
   *          The file path.
   */
  private RimpleXRecordingWindow(final RimpleXController controller, final String filePath)
  {
    this.filePath = filePath;
    initializeWindow(controller);
  }

  private void initializeWindow(final RimpleXController controller)
  {
    setTitle(RECORDING_TO + filePath);
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

  /**
   * Changes the file path of the window.
   *
   * @param newPath
   *          the new file path.
   */
  public void updateFilePath(final String newPath)
  {
    this.filePath = newPath;
    setTitle(RECORDING_TO + newPath);
  }

  @Override
  public void dispose()
  {
    super.dispose();
    instance = null;
  }

  /**
   * Checks if window is visible.
   *
   * @return If window is visible.
   */
  public static boolean isWindowVisible()
  {
    return instance != null && instance.isVisible();
  }
}
