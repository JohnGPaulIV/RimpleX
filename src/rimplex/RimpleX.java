package rimplex;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import rimplex.gui.*;

import javax.swing.SwingUtilities;

/**
 * The main class for the RimpleX application. This is where the process begins.
 * 
 * General structure taken from Dr. Bernstein's Serialization Lab:
 * (https://w3.cs.jmu.edu/bernstdh/web/common/labs/experience_serialization/tempz/index.php)
 * 
 * This work complies with JMU Honor Code.
 */
public class RimpleX implements Runnable
{
  /**
   * Main driver.
   * 
   * @param args
   *          Command line arguments.
   * @throws InterruptedException
   * @throws InvocationTargetException
   */
  public static void main(final String[] args)
      throws InterruptedException, InvocationTargetException
  {
    // Perform all of the setup activities in the event dispatch thread
    SwingUtilities.invokeAndWait(new RimpleX());
  }

  @Override
  public void run()
  {
    RimpleXController controller = new RimpleXController();
    RimpleXWindow window = null;
    try
    {
      window = new RimpleXWindow(controller);
    }
    catch (IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    window.setVisible(true);
  }

}
