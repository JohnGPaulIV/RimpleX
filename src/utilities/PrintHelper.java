package utilities;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;
import javax.print.*;
import javax.print.attribute.*;
import javax.print.attribute.standard.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.List;

/**
 * Printer helper for Session History.
 *
 * @author John Paul
 * @version 1
 */
public class PrintHelper 
{
  private static final String FILE = "Session_History.txt";
  private static final String DIALOG = "dialog.title";
  private static final String LABEL = "label.landscapeOption";

  /**
   * Printer for session history.
   */
  public static void printHtmlFile() {
    String lang    = System.getProperty("user.language");
    String country = System.getProperty("user.country");
    Locale locale  = (lang != null && !lang.isEmpty())
                   ? (country != null && !country.isEmpty()
                        ? new Locale(lang, country)
                        : new Locale(lang))
                   : Locale.getDefault();
    Locale.setDefault(locale);
    JComponent.setDefaultLocale(locale);
    UIManager.getDefaults().setDefaultLocale(locale);
    ResourceBundle b = ResourceBundle.getBundle("rimplex.gui.languages.Strings", locale);

    final List<String> lines = new ArrayList<>();
    try (BufferedReader rdr = new BufferedReader(new FileReader(FILE))) 
    {
      String ln;
      while ((ln = rdr.readLine()) != null) 
      {
        lines.add(ln);
      }
    } catch (IOException ex) 
    {
      JOptionPane.showMessageDialog(null,
          MessageFormat.format(b.getString("error.readingFile"), ex.getMessage()),
          b.getString(DIALOG),
          JOptionPane.ERROR_MESSAGE);
      return;
    }

    PrinterJob job = PrinterJob.getPrinterJob();
    job.setJobName(FILE);
    PageFormat pf = job.defaultPage();

    BufferedImage buf = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = buf.createGraphics();
    final float lineHeight = g2d.getFontMetrics().getHeight();
    g2d.dispose();

    final int linesPerPage = Math.max(1, (int)(pf.getImageableHeight() / lineHeight));
    final int totalPages = (lines.size() + linesPerPage - 1) / linesPerPage;

    JSpinner copiesSpin = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
    JComboBox<String> orientBox = new JComboBox<>(new String[]{
        b.getString("label.portraitOption"),
        b.getString(LABEL)
    });
    orientBox.setSelectedIndex(0);

    JSpinner fromSpin = new JSpinner(new SpinnerNumberModel(1, 1, totalPages, 1));
    JSpinner toSpin = new JSpinner(new SpinnerNumberModel(totalPages, 1, totalPages, 1));

    fromSpin.addChangeListener(new ChangeListener() 
    {
      public void stateChanged(final ChangeEvent e) 
      {
        int from = (Integer)fromSpin.getValue();
        SpinnerNumberModel toModel = (SpinnerNumberModel)toSpin.getModel();
        toModel.setMinimum(from);
        if ((Integer)toSpin.getValue() < from) 
        {
          toSpin.setValue(from);
        }
      }
    });

    JPanel panel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(4,4,4,4);
    gbc.anchor = GridBagConstraints.WEST;

    gbc.gridx=0;
    gbc.gridy=0;
    panel.add(new JLabel(b.getString("label.copies")), gbc);
    gbc.gridx=1;
    panel.add(copiesSpin, gbc);
    gbc.gridx=0;
    gbc.gridy=1;
    panel.add(new JLabel(b.getString("label.orientation")), gbc);
    gbc.gridx=1;
    panel.add(orientBox, gbc);

    gbc.gridx=0;
    gbc.gridy=2;
    panel.add(new JLabel(b.getString("label.pageRange")), gbc);
    JPanel rangeP = new JPanel(new FlowLayout(FlowLayout.LEFT,2,0));
    rangeP.add(new JLabel(b.getString("label.from")));
    rangeP.add(fromSpin);
    rangeP.add(new JLabel(b.getString("label.to")));
    rangeP.add(toSpin);
    gbc.gridx=1;
    panel.add(rangeP, gbc);

    int choice = JOptionPane.showConfirmDialog(
        null, panel,
        b.getString(DIALOG),
        JOptionPane.OK_CANCEL_OPTION,
        JOptionPane.PLAIN_MESSAGE
    );
    if (choice != JOptionPane.OK_OPTION) 
    {
      return;
    }

    final int copies = (Integer) copiesSpin.getValue();
    final boolean landscape = orientBox.getSelectedItem().equals(b.getString(
        LABEL));
    final int pageFrom = (Integer) fromSpin.getValue();
    final int pageTo = (Integer) toSpin.getValue();

    DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PAGEABLE;
    PrintService[] services = PrintServiceLookup.lookupPrintServices(flavor, null);
    PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
    if (services.length == 0) 
    {
      JOptionPane.showMessageDialog(null,
          b.getString("error.noPrintServices"),
          b.getString(DIALOG),
          JOptionPane.ERROR_MESSAGE);
      return;
    }

    PrintRequestAttributeSet attrs = new HashPrintRequestAttributeSet();
    attrs.add(new Copies(copies));
    attrs.add(landscape
        ? OrientationRequested.LANDSCAPE
        : OrientationRequested.PORTRAIT
    );
    attrs.add(new PageRanges(pageFrom, pageTo));

    PrintService chosen = ServiceUI.printDialog(
         null,
         200, 
         200,
         services,
         defaultService,
         flavor,
         attrs
    );
    if (chosen == null) return;

    try 
    {
      job.setPrintable((Graphics gr, PageFormat fmt, int pageIndex) -> 
      {
        if (pageIndex < pageFrom - 1 || pageIndex > pageTo - 1) 
        {
          return Printable.NO_SUCH_PAGE;
        }
        Graphics2D g = (Graphics2D) gr;
        g.translate(fmt.getImageableX(), fmt.getImageableY());

        float y = 0;
        int start = pageIndex * linesPerPage;
        int end = Math.min(start + linesPerPage, lines.size());
        for (int i = start; i < end; i++) 
        {
          y += lineHeight;
          g.drawString(lines.get(i), 0, y);
        }
        return Printable.PAGE_EXISTS;
      });
      job.setPrintService(chosen);
      
    } 
    catch (PrinterException e) 
    {
      JOptionPane.showMessageDialog(null,
          MessageFormat.format(b.getString("error.failedSetPrinter"), e.getMessage()),
          b.getString(DIALOG),
          JOptionPane.ERROR_MESSAGE);
      return;
    }

    try
    {
      job.print(attrs);
      JOptionPane.showMessageDialog(null,
          b.getString("message.printJobSent"),
          b.getString(DIALOG),
          JOptionPane.INFORMATION_MESSAGE);
    } 
    catch (PrinterException e) 
    {
      JOptionPane.showMessageDialog(null,
          MessageFormat.format(b.getString("error.duringPrint"), e.getMessage()),
          b.getString(DIALOG),
          JOptionPane.ERROR_MESSAGE);
    }
  }
}
