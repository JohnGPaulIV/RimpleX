package rimplex.gui;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;

/**
 * A JTextArea that only allows selecting and copying the text after the last "= " on each line.
 *
 * @author John Paul
 * @version 1
 */
public class NumberOnlyTextArea extends JTextArea
{
  private static final long serialVersionUID = 1L;
  private static final String DELIM = "= ";

  /**
   * Creates an instance of a NumberOnlyTextArea.
   */
  public NumberOnlyTextArea() 
  {
    super();
    init();
  }

  private void init() 
  {
    DefaultCaret invisibleCaret = new DefaultCaret() 
    {
      private static final long serialVersionUID = 1L;
      @Override
      public void paint(final Graphics g) 
      {
      }
      @Override
      protected synchronized void damage(final Rectangle r) 
      {
      }
    };
    invisibleCaret.setBlinkRate(0);
    setCaret(invisibleCaret);

    addMouseListener(new MouseAdapter()
    {
      @Override
      public void mousePressed(final MouseEvent e) 
      {
        int pos = viewToModel2D(e.getPoint());
        int resultStart = findResultStart(pos);
        setCaretPosition(resultStart);
      }
    });

    addMouseMotionListener(new MouseMotionAdapter() 
    {
      @Override
      public void mouseDragged(final MouseEvent e) 
      {
        int pos = viewToModel2D(e.getPoint());
        int resultStart = findResultStart(pos);
        int clamped = Math.max(pos, resultStart);
        moveCaretPosition(clamped);
      }
    });

    KeyStroke copyKs = KeyStroke.getKeyStroke(KeyEvent.VK_C,
                            Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx());
    getInputMap().put(copyKs, DefaultEditorKit.copyAction);
    getActionMap().put(DefaultEditorKit.copyAction, new AbstractAction() 
    {
      private static final long serialVersionUID = 1L;

      @Override
      public void actionPerformed(final ActionEvent e) 
      {
        String sel = getSelectedText();
        if (sel != null && !sel.isEmpty()) 
        {
          Toolkit.getDefaultToolkit()
                   .getSystemClipboard()
                   .setContents(new StringSelection(sel.trim()), null);
        }
      }
    });
  }

    /**
     * Finds the offset immediately after the last "= " on the line containing the given pos.
     * If no delimiter is found, returns the original pos.
     * 
     * @param pos Position
     * @return where the result from equation starts.
     */
  private int findResultStart(final int pos) 
  {
    try 
    {
      int line = getLineOfOffset(pos);
      int startOffset = getLineStartOffset(line);
      int endOffset = getLineEndOffset(line);
      String lineText = getText(startOffset, endOffset - startOffset);
      int idx = lineText.lastIndexOf(DELIM);
      if (idx >= 0) 
      {
        return startOffset + idx + DELIM.length();
      }
    } catch (BadLocationException ex) 
    {
        // ignore
    }
    return pos;
  }
}
