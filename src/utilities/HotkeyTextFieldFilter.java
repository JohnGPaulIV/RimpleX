package utilities;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class HotkeyTextFieldFilter extends PlainDocument
{
  private static final long serialVersionUID = 1L;
  private static boolean initialized = false;

  public HotkeyTextFieldFilter()
  {
    super();
  }

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

  public void insertString(int offset, String str, AttributeSet attr)
  {
    if (str == null || !isInputValid(str))
    {
      return;
    }
    
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

  public boolean isInputValid(String str)
  {
    char input = str.charAt(0);
    if (!Character.isDigit(input) && RimpleXPreferences.getActionCommand(str) == null)
    {
      return true;
    }
    return false;
  }
}
