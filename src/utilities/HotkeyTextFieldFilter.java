package utilities;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * The hot-key input filter to ensure that users can not input anything more than a single
 * character. 
 * 
 * This work complies with JMU Honor Code.
 * 
 * @author Joseph Pogoretskiy
 */
public class HotkeyTextFieldFilter extends PlainDocument
{
  private static final long serialVersionUID = 1L;
  private static boolean initialized = false;

  /**
   * Explicit constructor.
   */
  public HotkeyTextFieldFilter()
  {
    super();
  }

  /**
   * Insert keyboard input only if the current text is no more than one in length.
   */
  public void insertString(final int offset, final String str, final AttributeSet attr)
  {
    if (str == null || !isInputValid(str))
    {
      return;
    }
    
    // For the initial text.
    if (initialized) {
      try
      {
        super.insertString(offset, str, attr);
      }
      catch (BadLocationException e)
      {
        e.printStackTrace();
      }
      return;
    }
    
    if ((getLength() + str.length() <= 1))
    {
      try
      {
        super.insertString(offset, str, attr);
      }
      catch (BadLocationException e)
      {
        e.printStackTrace();
      }
    }
  }

  /**
   * Check whether the inputed character is neither a digit nor an already existing hot-key.
   * @param str The inputed character.
   * @return Whether the character is a valid hot-key.
   */
  private boolean isInputValid(final String str)
  {
    char input = str.charAt(0);
    if (!Character.isDigit(input) && RimpleXPreferences.getActionCommand(str) == null)
    {
      return true;
    }
    return false;
  }

  /**
   * Set the initial text of the field that bypasses the insertString() filter.
   * 
   * @param str The hot-key loaded in from the preferences configuration file.
   */
  public void setInitialText(final String str)
  {
    try {
      initialized = true;
      remove(0, getLength());
      super.insertString(0, str, null);
    } catch (BadLocationException e) {
      e.printStackTrace();
    } finally {
      initialized = false;
    }
  }
}
