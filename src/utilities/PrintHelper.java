package utilities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;



public class PrintHelper {
    
    public static void printHtmlFile() {
      PrinterJob printJob = PrinterJob.getPrinterJob();
      printJob.setJobName("Session_History.txt");

      printJob.setPrintable(new Printable() {
          @Override
          public int print(Graphics graphics, PageFormat pf, int pageIndex)
                  throws PrinterException {
              if (pageIndex > 0) {
                  return NO_SUCH_PAGE;
              }

              Graphics2D g2 = (Graphics2D) graphics;
              g2.translate(pf.getImageableX(), pf.getImageableY());

              float y = 0;
              float lineHeight = g2.getFontMetrics().getHeight();

              try (BufferedReader reader = new BufferedReader(
                       new FileReader("Session_History.txt"))) {
                  String line;
                  while ((line = reader.readLine()) != null) {
                      y += lineHeight;
                      g2.drawString(line, 0, y);
                  }
              } catch (IOException e) {
                  throw new PrinterException("Error reading file: " + e.getMessage());
              }

              return PAGE_EXISTS;
          }
      });

      if (!printJob.printDialog()) {
          System.out.println("Printing canceled by user.");
          return;
      }

      try {
          printJob.print();
          System.out.println("Print job sent.");
      } catch (PrinterException exc) {
          exc.printStackTrace();
      }
  }
}
