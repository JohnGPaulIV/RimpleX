package rimplex.gui;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Represents the playback controller window.
 *
 * @author Benjamin Bonnell, Sofia Miller
 *
 *         This work complies with JMU Honor Code.
 */
public class RimpleXPlaybackWindow extends JFrame
{
  private static final long serialVersionUID = 1L;
  private static RimpleXPlaybackWindow instance;
  private static final String RECORDING_PLAY = "RECORDING_PLAY";
  private static final String RECORDING_PAUSE = "RECORDING_PAUSE";
  private static final String RECORDING_STOP = "RECORDING_STOP";
  private String filePath;
  private RimpleXController controller;
  private JSlider slider;

  /**
   * Constructs Playback Controller Window for RimpleX.
   *
   * @param controller
   *          The control button.
   * @param filePath
   *          The file path.
   */
  private RimpleXPlaybackWindow(final String filePath, final RimpleXController controller)
  {
    this.filePath = filePath;
    this.controller = controller;
    setTitle("Playback");
    setSize(350, 100);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(null);
    setResizable(false);
    RimpleXButton playback = new RimpleXButton(RECORDING_PLAY, "▶", controller, 10, 10, 45, 45);
    add(playback);
    RimpleXButton pause = new RimpleXButton(RECORDING_PAUSE, "⏸", controller, 60, 10, 45, 45);
    add(pause);
    RimpleXButton stop = new RimpleXButton(RECORDING_STOP, "◼", controller, 110, 10, 45, 45);
    add(stop);
    slider = new JSlider(JSlider.HORIZONTAL, 100, 4000, 2050);
    slider.setBounds(160, 10, 180, 45);
    slider.setMajorTickSpacing(500);
    slider.setMinorTickSpacing(100);
    add(slider);
    playback.addActionListener(new PlayActionListener());
    pause.addActionListener(new PauseActionListener());
    stop.addActionListener(new StopActionListener());
    slider.addChangeListener(new PlaybackSpeedChangeListener());
  }
  
  /**
   * Creates playback window.
   *
   * @param controller
   *          The control button
   * @param filePath
   *          The file path
   * @return Returns a playback window.
   */
  public static RimpleXPlaybackWindow getInstance(final RimpleXController controller,
      final String filePath)
  {
    if (instance == null)
    {
      instance = new RimpleXPlaybackWindow(filePath, controller);
    }
    else
    {
      instance.setFilePath(filePath);
    }
    return instance;
  }

  @Override
  public void dispose()
  {
    super.dispose();
    instance = null; // Clear the instance when the window is closed
  }

  /**
   *
   * @param path
   */
  public void setFilePath(final String path)
  {
    this.filePath = path;
  }

  /**
   * Gets file path.
   *
   * @return Returns the file path.
   */
  public String getFilePath()
  {
    return this.filePath;
  }

  /**
   * Adjusts playback speed.
   *
   * @param speed
   *          Speed of playback.
   */
  private void adjustPlaybackSpeed(final int speed)
  {
    System.out.println("Playback speed set to: " + speed + "ms");
  }

  private class PlayActionListener implements ActionListener
  {
    @Override
    public void actionPerformed(final ActionEvent e)
    {
      controller
          .actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, RECORDING_PLAY));
    }
  }

  private class PauseActionListener implements ActionListener
  {
    @Override
    public void actionPerformed(final ActionEvent e)
    {
      controller
          .actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, RECORDING_PAUSE));
    }
  }

  private class StopActionListener implements ActionListener
  {
    @Override
    public void actionPerformed(final ActionEvent e)
    {
      controller
          .actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, RECORDING_STOP));
    }
  }

  private class PlaybackSpeedChangeListener implements ChangeListener
  {
    @Override
    public void stateChanged(final ChangeEvent e)
    {
      adjustPlaybackSpeed(slider.getValue());
    }
  }

  /**
   * Gets slider value.
   *
   * @return Returns the slider value.
   */
  public int getSliderValue()
  {
    return slider.getValue();
  }
}
