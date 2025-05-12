package utilities;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

import rimplex.gui.RimpleXController;

public class RecordingManager
{
  private static BufferedWriter writer;
  private static boolean isRecording = false;
  private static boolean isPaused = false;
  private static String currentFilePath;

  public static void setFilePath(String path, boolean isForRecording)
  {
    try
    {
      currentFilePath = path;
      if (isForRecording)
      {
        writer = new BufferedWriter(new FileWriter(path, true));
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  public static void startRecording()
  {
    if (writer == null || currentFilePath == null)
    {
      System.err.println("Error: No file path set for recording.");
      return;
    }
    isRecording = true;
    isPaused = false;
    System.out.println("Recording started");
  }

  public static void pauseRecording()
  {
    isPaused = true;
    System.out.println("Recording paused");
  }

  public static void resumeRecording()
  {
    if (isRecording && isPaused)
    {
      isPaused = false;
      System.out.println("Recording resumed");
    }
  }

  public static void stopRecording()
  {
    try
    {
      if (writer != null)
      {
        writer.close();
        System.out.println("Recording saved to file: " + currentFilePath);
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    finally
    {
      isRecording = false;
      isPaused = false;
      writer = null;
    }
  }

  public static void log(String calculation)
  {
    if (isRecording && !isPaused && writer != null)
    {
      try
      {
        writer.write(calculation);
        writer.newLine();
        writer.flush();
        System.out.println("Recorded: " + calculation);
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
    else
    {
      System.err.println("Warning: Recording is not active.");
    }
  }

  public static String readRecording()
  {
    if (currentFilePath == null)
    {
      System.err.println("Error: No recording file to read.");
      return "";
    }
    try
    {
      System.out.println("Attempting to read file: " + currentFilePath);
      Path path = Path.of(currentFilePath);
      return Files.readString(path);
    }
    catch (IOException e)
    {
      e.printStackTrace();
      return "";
    }
  }

  public static void playFromFile(String filePath, RimpleXController controller, int delayMillis)
  {
    try
    {
      Path path = Paths.get(filePath);
      List<String> lines = Files.readAllLines(path);
      System.out.println("Playing file: " + filePath);
      System.out.println("Total lines read: " + lines.size());

      if (lines.isEmpty())
      {
        System.err.println("Error: Recording file is empty or couldn't be read.");
        return;
      }

      Timer timer = new Timer(delayMillis, (ActionListener) new ActionListener()
      {
        int index = 0;

        @Override
        public void actionPerformed(ActionEvent e)
        {
          if (index < lines.size())
          {
            String line = lines.get(index);
            System.out.println("Replaying calculation: " + line);
            controller.parseAndApplyCalculation(line);
            index++;
          }
          else
          {
            ((Timer) e.getSource()).stop();
          }
        }
      });

      timer.setInitialDelay(0);
      timer.start();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  public static void recordCalculation(String calculation)
  {
    if (isRecording && !isPaused && writer != null)
    {
      try
      {
        writer.write(calculation);
        writer.newLine();
        writer.flush();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
  }

  public static String getFilePath()
  {
    System.out.println("Retrieving file path for playback: " + currentFilePath);
    return currentFilePath;
  }

  public static List<String> readRecordingAsList()
  {
    List<String> recordedLines = new ArrayList<>();
    if (currentFilePath == null)
    {
      System.err.println("Error: No recording file to read.");
      return recordedLines;
    }
    try
    {
      System.out.println("Reading recording as list from file: " + currentFilePath);
      Path path = Paths.get(currentFilePath);
      recordedLines = Files.readAllLines(path);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }

    return recordedLines;
  }
}
