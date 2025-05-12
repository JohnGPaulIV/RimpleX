package rimplex.gui;

import static rimplex.RimpleX.rb;

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
  private static RimpleXPlaybackWindow instance;
  private String filePath;
  private RimpleXController controller;
  private JSlider slider;

  public static RimpleXPlaybackWindow getInstance(RimpleXController controller, String filePath)
  {
    if (instance == null)
    {
      instance = new RimpleXPlaybackWindow(controller, filePath);
    }
    else
    {
      instance.setFilePath(filePath);
    }
    return instance;
  }

  /**
   * Constructs Playback Controller Window for RimpleX.
   */

  private RimpleXPlaybackWindow(RimpleXController controller, String filePath)
  {
    this.controller = controller;
    this.filePath = filePath;

    setTitle("Playback");
    setSize(350, 100);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    setLocationRelativeTo(null);
    setLayout(null);
    setResizable(false);

    RimpleXButton playback = new RimpleXButton("RECORDING_PLAY", "▶", controller, 10, 10, 45, 45);
    add(playback);
    RimpleXButton pause = new RimpleXButton("RECORDING_PAUSE", "⏸", controller, 60, 10, 45, 45);
    add(pause);
    RimpleXButton stop = new RimpleXButton("RECORDING_STOP", "◼", controller, 110, 10, 45, 45);
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

  @Override
  public void dispose()
  {
    super.dispose();
    instance = null; // Clear the instance when the window is closed
  }

  public void setFilePath(String path)
  {
    this.filePath = path;
  }

  public String getFilePath()
  {
    return this.filePath;
  }

  private void adjustPlaybackSpeed(int speed)
  {
    System.out.println("Playback speed set to: " + speed + "ms");
  }

  private class PlayActionListener implements ActionListener
  {
    @Override
    public void actionPerformed(ActionEvent e)
    {
      controller
          .actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "RECORDING_PLAY"));
    }
  }

  private class PauseActionListener implements ActionListener
  {
    @Override
    public void actionPerformed(ActionEvent e)
    {
      controller
          .actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "RECORDING_PAUSE"));
    }
  }

  private class StopActionListener implements ActionListener
  {
    @Override
    public void actionPerformed(ActionEvent e)
    {
      controller
          .actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "RECORDING_STOP"));
    }
  }

  private class PlaybackSpeedChangeListener implements ChangeListener
  {
    @Override
    public void stateChanged(ChangeEvent e)
    {
      adjustPlaybackSpeed(slider.getValue());
    }
  }

  public int getSliderValue()
  {
    return slider.getValue();
  }

}
