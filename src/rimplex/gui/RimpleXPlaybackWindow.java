package rimplex.gui;

import static rimplex.RimpleX.rb;

import javax.swing.*;

/**
 * Represents the playback controller window.
 * 
 * @author Benjamin Bonnell
 * 
 *         This work complies with JMU Honor Code.
 */
public class RimpleXPlaybackWindow extends JFrame
{
  private static RimpleXPlaybackWindow instance;

  public static RimpleXPlaybackWindow getInstance(RimpleXController controller)
  {
    if (instance == null)
    {
      instance = new RimpleXPlaybackWindow(controller);
    }
    return instance;
  }

  /**
   * Constructs Playback Controller Window for RimpleX.
   */
  private RimpleXPlaybackWindow(RimpleXController controller)
  {
    setTitle("Playback");
    setSize(350, 100);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    setLocationRelativeTo(null);
    setLayout(null);
    setResizable(false);

    RimpleXButton playback = new RimpleXButton("PLAYBACK_PLAY", "▶", controller, 10, 10, 45, 45);
    add(playback);
    RimpleXButton pause = new RimpleXButton("PLAYBACK_PAUSE", "⏸", controller, 60, 10, 45, 45);
    add(pause);
    RimpleXButton stop = new RimpleXButton("PLAYBACK_STOP", "◼", controller, 110, 10, 45, 45);
    add(stop);

    JSlider slider = new JSlider();
    slider.setBounds(160, 10, 180, 45);
    add(slider);

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
