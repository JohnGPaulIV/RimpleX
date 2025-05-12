package rimplex.gui;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import utilities.Complex;
import utilities.Evaluator;
import utilities.PrintHelper;
import utilities.RecordingManager;
import utilities.SessionHistory;
import utilities.RimpleXPreferences;
import static rimplex.RimpleX.*;
/**
* The observer of all GUI components of the RimpleX application.
*
* General structure taken from Dr. Bernstein's Serialization Lab:
* (https://w3.cs.jmu.edu/bernstdh/web/common/labs/experience_serialization/tempz/index.php)
*
* @author Joseph Pogoretskiy, Benjamin Bonnell, Kalani Johnson, John Paul, Sofia Miller
*
*         This work complies with JMU Honor Code.
*/
/**
*
*/
public class RimpleXController implements ActionListener
{
 private static final String ADD = "+";
 private static final String SUBTRACTION = "—";
 private static final String MULTIPLICATION = "×";
 private static final String DIVIDE = "÷";
 private static final String NEGATIVE = "-";
 private static final String POWER = "^";
 private static final String EQUALS = "=";
 private static final String IMAGINARY_UNIT = "\uD835\uDC56";
 private static final String OPEN_PAREN = "(";
 private static final String CLOSED_PAREN = ")";
 private static final String OPEN_BRACKET = "[";
 private static final String SPACE = " ";
 private static final String DECIMAL = ".";
 private static final String GREATER_THAN = "≥";
 private static final String LESSER_THAN = "≤";
 private static final String IM = "IM";
 private static final String LOG = "Log";
 private static final String CONJ = "Conj";
 private static final String PROPERTIES = ".properties";
 private static final String PLR = "Plr";
 private static final String INV = "Inv(";
 private static final String RE = "RE";
 private static final String SQRT = "sqrt";
 private static final String ERROR = "Error";
 private static final String HELP_FILE_PATH = "Help_File_Path";
 private static final String EVALUATE = " = ";
 private RimpleXRelationalOperation relationalWindow = new RimpleXRelationalOperation();
 private RimpleXPreferencesWindow prefWindow = new RimpleXPreferencesWindow();
 FileNameExtensionFilter filter = new FileNameExtensionFilter("Preferences", "properties");
 private RimpleXWindow window;
 private JLabel topDisplay;
 private JLabel bottomDisplay;
 private boolean bracketPresent;
 private boolean bracketClosed = false;
 private boolean equalsPresent = false;
 private boolean relationalOpPresent = false;
 private boolean runningCalc = true;
 private int openParenCount = 0;
 private int closedParenCount = 0;
 private Complex result;
private boolean polarFormEnabled = false;
 private Complex polarizedComplex;
 private SessionHistoryWindow sessionHistoryWindow;
 private String[] tempFileResources = new String[] {"help_en_US.html", "help_es_ES.html",
     "help_ru_RU.html",
     "ComplexPlaneWindow_en_US.png", "Equals_en_US.png", "IntermediateSteps_en_US.png",
   "NumberEntry_en_US.png", "OperationEntry_en_US.png", "Playback_en_US.png",
     "PrintSessionHistory_en_US.png", "Recording_en_US.png", "SessionHistory_en_US.png",
     "ComplexPlaneWindow_ru_RU.png", "Equals_ru_RU.png", "IntermediateSteps_ru_RU.png",
     "NumberEntry_ru_RU.png", "OperationEntry_ru_RU.png", "Playback_ru_RU.png",
    "PrintSessionHistory_ru_RU.png", "Recording_ru_RU.png", "SessionHistory_ru_RU.png",};
 /**
  * Constructor for a RimpleXController.
  */
 public RimpleXController()
 {
   super();
 }
 @Override
 /**
  * Action handler when buttons are clicked.
  *
  * @param ae
  *          The ActionEvent that generated the message.
  */
 public void actionPerformed(final ActionEvent ae)
 {
   String ac = ae.getActionCommand();
   // General structure: if (ac.equals(NAME_OF_BUTTON)) { do stuff... }.
   // For testing if buttons are linked with actions.
   // System.out.println(ac + " was pressed.");
   if (bottomDisplay.getText().equals(ERROR))
   {
     bottomDisplay.setText("");
     topDisplay.setText("");
   }
   switch (ac)
   {
     case "ONE":
       if (updateDisplayingDigit())
       {
        bottomDisplay.setText(bottomDisplay.getText() + "1");
       }
      break;
     case "TWO":
      if (updateDisplayingDigit())
       {
         bottomDisplay.setText(bottomDisplay.getText() + "2");
       }
      break;
     case "THREE":
      if (updateDisplayingDigit())
       {
         bottomDisplay.setText(bottomDisplay.getText() + "3");
       }
      break;
     case "FOUR":
      if (updateDisplayingDigit())
       {
         bottomDisplay.setText(bottomDisplay.getText() + "4");
       }
      break;
     case "FIVE":
      if (updateDisplayingDigit())
       {
         bottomDisplay.setText(bottomDisplay.getText() + "5");
       }
      break;
     case "SIX":
      if (updateDisplayingDigit())
       {
         bottomDisplay.setText(bottomDisplay.getText() + "6");
       }
      break;
     case "SEVEN":
      if (updateDisplayingDigit())
       {
         bottomDisplay.setText(bottomDisplay.getText() + "7");
       }
      break;
     case "EIGHT":
      if (updateDisplayingDigit())
       {
         bottomDisplay.setText(bottomDisplay.getText() + "8");
       }
      break;
     case "NINE":
      if (updateDisplayingDigit())
       {
         bottomDisplay.setText(bottomDisplay.getText() + "9");
       }
      break;
     case "ZERO":
      if (updateDisplayingDigit())
       {
         bottomDisplay.setText(bottomDisplay.getText() + "0");
       }
      break;
     case "BACKSPACE":
       if (bottomDisplay.getText().length() != 0)
       {
         if (bottomDisplay.getText().endsWith(IMAGINARY_UNIT))
         {
           bottomDisplay.setText(
             bottomDisplay.getText().substring(0, bottomDisplay.getText().length() - 2));
         }
         else if (bracketClosed)
         {
           bottomDisplay.setText(OPEN_PAREN
               + bottomDisplay.getText().substring(0, bottomDisplay.getText().length()));
           bracketClosed = false;
         }
         else
         {
           if (bottomDisplay.getText().endsWith(CLOSED_PAREN))
           {
           closedParenCount--;
           }
           if (bottomDisplay.getText().endsWith(OPEN_PAREN))
           {
             openParenCount--;
          }
          bottomDisplay.setText(
               bottomDisplay.getText().substring(0, bottomDisplay.getText().length() - 1));
           if (bottomDisplay.getText().length() == 0 && bracketPresent)
           {
             bracketPresent = false;
           }
         }
       }
       break;
     case "DECIMAL":
       // This is a temporary solution since this won't work when operators are in the
       // current
       // expression.
       boolean canPlace = !(bottomDisplay.getText().length() == 0) && Character
           .isDigit(bottomDisplay.getText().charAt(bottomDisplay.getText().length() - 1));
       if (canPlace)
       {
         // Remove the parentheses so as not to set the parser off.
         String displayText = new String(bottomDisplay.getText());
         displayText = displayText.replace(OPEN_PAREN, "").replace(CLOSED_PAREN, "")
             .replace(ADD, SPACE).replace(NEGATIVE, SPACE).replace(MULTIPLICATION, SPACE)
             .replace(DIVIDE, SPACE);
         String[] operands = displayText.split(SPACE);
         String lastOperand = operands[operands.length - 1];
       try
         {
           lastOperand += DECIMAL;
           Float.parseFloat(lastOperand);
           bottomDisplay.setText(bottomDisplay.getText() + DECIMAL);
         }
         catch (NumberFormatException nfe)
         {
         }
         break;
       }
       else
     {
       break;
       }
     case "OPEN_PARENTHESIS":
       if (!bracketPresent)
      {
         break;
       }
       if (Character.isDigit(lastChar()) || !checkDigitPlacement(bottomDisplay)
          || lastChar() == ')')
       {
         bottomDisplay.setText(bottomDisplay.getText() + MULTIPLICATION + OPEN_PAREN);
         openParenCount++;
       }
       else
       {
         bottomDisplay.setText(bottomDisplay.getText() + OPEN_PAREN);
         openParenCount++;
      }
       break;
     case "CLOSED_PARENTHESIS":
       if (!bracketPresent)
       {
         break;
       }
       if ((Character.isDigit(lastChar()) || !checkDigitPlacement(bottomDisplay))
          && (openParenCount > closedParenCount))
       {
         bottomDisplay.setText(bottomDisplay.getText() + CLOSED_PAREN);
         closedParenCount++;
       }
       break;
     case "CLEAR":
       bottomDisplay.setText("");
       bracketPresent = false;
       bracketClosed = false;
      openParenCount = 0;
       closedParenCount = 0;
     break;
     case "RESET":
       topDisplay.setText("");
       bottomDisplay.setText("");
       equalsPresent = false;
      polarFormEnabled = false;
       polarizedComplex = null;
       bracketPresent = false;
       bracketClosed = false;
       relationalOpPresent = false;
       openParenCount = 0;
       closedParenCount = 0;
       break;
     case "SIGN":
       // This is a HYPHEN, not a DASH
       if (bracketClosed
           || (!bracketClosed && !bracketPresent && !bottomDisplay.getText().isBlank()))
       {
         String temp = "TEMP";
         String displayText = bottomDisplay.getText();
         // displayText = displayText.replace(ADD, SUBTRACTION).replace(SUBTRACTION, ADD)
         // .replace(MULTIPLICATION, MULTIPLICATION + NEGATIVE).replace(DIVIDE, DIVIDE +
         // NEGATIVE)
         // .replace(POWER, POWER + NEGATIVE);
         displayText = displayText.replace(ADD, temp);
         displayText = displayText.replace(SUBTRACTION, ADD);
        displayText = displayText.replace(temp, SUBTRACTION);
         displayText = displayText.replace(MULTIPLICATION + NEGATIVE, temp);
         displayText = displayText.replace(MULTIPLICATION, MULTIPLICATION + NEGATIVE);
         displayText = displayText.replace(temp, MULTIPLICATION);
         displayText = displayText.replace(DIVIDE + NEGATIVE, temp);
         displayText = displayText.replace(DIVIDE, DIVIDE + NEGATIVE);
         displayText = displayText.replace(temp, DIVIDE);
         displayText = displayText.replace(POWER + NEGATIVE, temp);
         displayText = displayText.replace(POWER, POWER + NEGATIVE);
         displayText = displayText.replace(temp, POWER);
         if (displayText.charAt(0) == '-')
         {
         displayText = displayText.substring(1);
         }
       else
         {
           displayText = NEGATIVE + displayText;
         }
         bottomDisplay.setText(displayText);
       }
      break;
     case "ADD":
       setOperator(bottomDisplay, topDisplay, ADD);
       break;
     case "SUBTRACT":
       setOperator(bottomDisplay, topDisplay, SUBTRACTION);
       break;
     case "MULTIPLY":
      setOperator(bottomDisplay, topDisplay, MULTIPLICATION);
       break;
     case "DIVIDE":
       setOperator(bottomDisplay, topDisplay, DIVIDE);
       break;
     case "GREATER_THAN":
      setRelationalOperator(bottomDisplay, topDisplay, GREATER_THAN);
       break;
     case "LESS_THAN":
       setRelationalOperator(bottomDisplay, topDisplay, LESSER_THAN);
       break;
     case "ACTION_EXIT":
       RimpleXPreferences.savePreferences();
       System.exit(0);
       break;
     case "ACTION_HELP":
       try
      {
         // Create a temporary directory
         Path tempDir = Files.createTempDirectory("rimplex_help");
         tempDir.toFile().deleteOnExit();
        // Get the base resource path
         String basePath = rb.getString(HELP_FILE_PATH);
         System.out.println(rb.getString(HELP_FILE_PATH));
         basePath = basePath.substring(0, basePath.lastIndexOf('/') + 1);
       // Copy all files
         for (String file : tempFileResources)
         {
           try (InputStream in = getClass().getResourceAsStream(basePath + file))
           {
            if (in != null)
             {
             Path targetFile = tempDir.resolve(file);
               Files.createDirectories(targetFile.getParent());
             Files.copy(in, targetFile, StandardCopyOption.REPLACE_EXISTING);
               System.out.println(targetFile);
             }
         }
         }
         // Open main help file
         Path mainHelpFile = tempDir.resolve(rb.getString("Help_File"));
         System.out.println(tempFileResources[0]);
         try
         {
           Desktop.getDesktop().browse(mainHelpFile.toUri());
         }
         catch (UnsupportedOperationException e)
         {
           Runtime.getRuntime().exec(new String[] {"xdg-open", mainHelpFile.toString()});
         }
       }
     catch (IOException e)
       {
         System.err.println("Error loading help files: " + e.getMessage());
       }
       break;
     case "ACTION_ABOUT":
      try
       {
         RimpleXAboutWindow aboutWindow = new RimpleXAboutWindow();
         aboutWindow.setVisible(true);
       }
       catch (IOException e)
       {
        e.printStackTrace();
       }
       break;
     case "OPEN_RECORDING":
       JFileChooser openFileChooser = new JFileChooser();
       openFileChooser.setDialogTitle("Open Recording File");
      int openResult = openFileChooser.showOpenDialog(null);
       if (openResult == JFileChooser.APPROVE_OPTION)
       {
         String selectedFilePath = openFileChooser.getSelectedFile().getAbsolutePath();
         System.out.println("Selected recording file: " + selectedFilePath);
         RecordingManager.setFilePath(selectedFilePath, false);
         // Close recording window if open
         if (RimpleXRecordingWindow.isWindowVisible())
        {
           RimpleXRecordingWindow.getInstance(this).dispose();
         }
         RimpleXPlaybackWindow playbackWindow = RimpleXPlaybackWindow.getInstance(this,
            selectedFilePath);
         playbackWindow.setVisible(true);
      }
       break;
     case "SAVE_RECORDING":
       // Close playback window if open
       // if (RimpleXPlaybackWindow.isWindowVisible())
       // {
      // RimpleXPlaybackWindow.getInstance(this).dispose();
       // }
       JFileChooser saveFileChooser = new JFileChooser();
       saveFileChooser.setDialogTitle("Select Recording Save Location");
       int userSelection = saveFileChooser.showSaveDialog(null);
       // RimpleXRecordingWindow recordingWindow = RimpleXRecordingWindow.getInstance(this);
     // recordingWindow.setVisible(true);
       if (userSelection == JFileChooser.APPROVE_OPTION)
       {
         String filePath = saveFileChooser.getSelectedFile().getAbsolutePath();
         RecordingManager.setFilePath(filePath, true);
         RimpleXRecordingWindow recordingWindow = RimpleXRecordingWindow.getInstance(this,
             filePath);
         recordingWindow.setVisible(true);
     }
       break;
     case "EQUALS":
       if (!bracketPresent && checkOperatorPlacement(bottomDisplay))
      {
         String leftOperand;
         if (polarFormEnabled)
        {
           leftOperand = polarizedComplex.toString();
         }
         else
        {
           leftOperand = topDisplay.getText().substring(0, topDisplay.getText().length() - 1);
         }
         String operator = topDisplay.getText().substring(topDisplay.getText().length() - 1);
         String rightOperand = bottomDisplay.getText();
        // For debugging:
         // System.out.println("leftOperand: " + leftOperand);
         // System.out.println("operator: " + operator);
         // System.out.println("rightOperand: " + rightOperand);
         String evaluation = Evaluator.evaluate(leftOperand, operator, rightOperand, false);
         if (!relationalOpPresent)
         {
           result = Complex.parse(evaluation);
         }
         else
         {
           relationalWindow.setResult(Evaluator.evaluate(leftOperand, "", "", true) + SPACE
               + operator + SPACE + Evaluator.evaluate(rightOperand, "", "", true) + SPACE + EQUALS
               + SPACE + rb.getString(evaluation));
           relationalWindow.setVisible(true);
           topDisplay.setText("");
           bottomDisplay.setText("");
           polarFormEnabled = false;
         polarizedComplex = null;
           bracketPresent = false;
           bracketClosed = false;
           relationalOpPresent = false;
           openParenCount = 0;
           closedParenCount = 0;
           break;
       }
         topDisplay.setText(
             leftOperand + operator + SPACE + rightOperand + SPACE + EQUALS + SPACE + evaluation);
       topDisplay.setText(Evaluator.evaluate(leftOperand, "", "", true) + SPACE + operator
             + SPACE + Evaluator.evaluate(rightOperand, "", "", true) + SPACE + EQUALS + SPACE
             + evaluation);
         SessionHistory.add(topDisplay.getText());
         if (polarFormEnabled)
         {
           Complex evaluated = Complex.parse(evaluation);
           String polarForm = evaluated.getPolarForm();
         topDisplay.setText(polarizedComplex.getPolarForm() + SPACE + operator + SPACE
               + Evaluator.evaluate(rightOperand, "", "", true) + SPACE + EQUALS + SPACE
               + polarForm);
           polarizedComplex = evaluated;
       }
         else
         {
           topDisplay.setText(Evaluator.evaluate(leftOperand, "", "", true) + SPACE + operator
               + SPACE + Evaluator.evaluate(rightOperand, "", "", true) + SPACE + EQUALS + SPACE
               + evaluation);
         }
         bottomDisplay.setText("");
         equalsPresent = true;
         bracketClosed = false;
         openParenCount = 0;
         closedParenCount = 0;
      }
       break;
     case "UNIT":
       if (bottomDisplay.getText().length() != 0 && Character.isDigit(lastChar()))
      {
         bottomDisplay.setText(bottomDisplay.getText() + "𝑖");
       }
       break;
     case "INVERT":
       if (bracketClosed
           || (!bracketPresent && !bracketClosed && !bottomDisplay.getText().isEmpty()))
      {
        String evaluated = Evaluator.evaluate(bottomDisplay.getText(), "Invert", "", false);
         topDisplay.setText(bottomDisplay.getText() + SPACE + EQUALS + SPACE + evaluated);
         SessionHistory
             .add(INV + topDisplay.getText().substring(0, topDisplay.getText().indexOf(EQUALS) - 1)
                 + CLOSED_PAREN + SPACE
                + topDisplay.getText().substring(topDisplay.getText().indexOf(EQUALS)));
         bottomDisplay.setText("");
       bracketClosed = false;
         equalsPresent = true;
       }
       else if (equalsPresent)
       {
         Complex complexNum;
         if (polarFormEnabled)
         {
         complexNum = polarizedComplex;
        }
         else
         {
          complexNum = Complex
              .parse(topDisplay.getText().substring(topDisplay.getText().indexOf(EQUALS) + 1));
        }
         complexNum.inverse();
         if (polarFormEnabled)
         {
           polarizedComplex = complexNum;
          topDisplay
               .setText(topDisplay.getText().substring(topDisplay.getText().indexOf(EQUALS) + 2)
                 + SPACE + EQUALS + SPACE + polarizedComplex.getPolarForm());
           SessionHistory.add(
               INV + topDisplay.getText().substring(0, topDisplay.getText().indexOf(EQUALS) - 1)
                 + CLOSED_PAREN + SPACE
                 + topDisplay.getText().substring(topDisplay.getText().indexOf(EQUALS)));
       }
         else
         {
           topDisplay
               .setText(topDisplay.getText().substring(topDisplay.getText().indexOf(EQUALS) + 2)
                 + SPACE + EQUALS + SPACE + complexNum.toString());
           SessionHistory.add(
              INV + topDisplay.getText().substring(0, topDisplay.getText().indexOf(EQUALS) - 1)
                 + CLOSED_PAREN + SPACE
                   + topDisplay.getText().substring(topDisplay.getText().indexOf(EQUALS)));
         }
       }
       break;
     case "IMAGINARY_PART":
      String top;
      if (bracketClosed
           || (!bracketPresent && !bracketClosed && !bottomDisplay.getText().isEmpty()))
       {
         String evaluated = Evaluator.evaluate(bottomDisplay.getText(), "", "", false);
         Complex imaginary = new Complex(0.0, Complex.parse(evaluated).getImaginary());
         topDisplay
             .setText(bottomDisplay.getText() + SPACE + EQUALS + SPACE + imaginary.toString());
         top = OPEN_PAREN
             + topDisplay.getText().substring(0, topDisplay.getText().indexOf(EQUALS) - 1)
             + CLOSED_PAREN;
         SessionHistory.add(IM + top + SPACE
             + topDisplay.getText().substring(topDisplay.getText().indexOf(EQUALS)));
         bottomDisplay.setText("");
       bracketClosed = false;
       equalsPresent = true;
       }
       else if (equalsPresent)
       {
         Complex complexNum;
         if (polarFormEnabled)
        {
           complexNum = polarizedComplex;
         }
         else
         {
         complexNum = Complex
              .parse(topDisplay.getText().substring(topDisplay.getText().indexOf(EQUALS) + 1));
         }
         Complex imaginary = new Complex(0.0, complexNum.getImaginary());
         if (polarFormEnabled)
       {
           polarizedComplex = imaginary;
           topDisplay
               .setText(topDisplay.getText().substring(topDisplay.getText().indexOf(EQUALS) + 2)
                   + SPACE + EQUALS + SPACE + polarizedComplex.toString());
           top = OPEN_PAREN
             + topDisplay.getText().substring(0, topDisplay.getText().indexOf(EQUALS) - 1)
               + CLOSED_PAREN;
          SessionHistory.add(IM + top + SPACE
             + topDisplay.getText().substring(topDisplay.getText().indexOf(EQUALS)));
       }
        else
         {
          topDisplay
             .setText(topDisplay.getText().substring(topDisplay.getText().indexOf(EQUALS) + 2)
                 + SPACE + EQUALS + SPACE + imaginary.toString());
         top = OPEN_PAREN
               + topDisplay.getText().substring(0, topDisplay.getText().indexOf(EQUALS) - 1)
              + CLOSED_PAREN;
           SessionHistory.add(IM + top + SPACE
               + topDisplay.getText().substring(topDisplay.getText().indexOf(EQUALS)));
         }
      }
       break;
    case "REAL_PART":
     if (bracketClosed
           || (!bracketPresent && !bracketClosed && !bottomDisplay.getText().isEmpty()))
       {
         String evaluated = Evaluator.evaluate(bottomDisplay.getText(), "", "", false);
         Complex real = new Complex(Complex.parse(evaluated).getReal(), 0.0);
         topDisplay.setText(bottomDisplay.getText() + SPACE + EQUALS + SPACE + real.toString());
         top = OPEN_PAREN
             + topDisplay.getText().substring(0, topDisplay.getText().indexOf(EQUALS) - 1)
             + CLOSED_PAREN;
         SessionHistory.add(RE + top + SPACE
             + topDisplay.getText().substring(topDisplay.getText().indexOf(EQUALS)));
         bottomDisplay.setText("");
         bracketClosed = false;
         equalsPresent = true;
       }
       else if (equalsPresent)
       {
        Complex complexNum;
         if (polarFormEnabled)
       {
          complexNum = polarizedComplex;
         }
       else
         {
           complexNum = Complex
               .parse(topDisplay.getText().substring(topDisplay.getText().indexOf(EQUALS) + 1));
         }
         Complex real = new Complex(complexNum.getReal(), 0.0);
         if (polarFormEnabled)
         {
         polarizedComplex = real;
           topDisplay
               .setText(topDisplay.getText().substring(topDisplay.getText().indexOf(EQUALS) + 2)
                   + SPACE + EQUALS + SPACE + polarizedComplex.toString());
           top = OPEN_PAREN
               + topDisplay.getText().substring(0, topDisplay.getText().indexOf(EQUALS) - 1)
               + CLOSED_PAREN;
           SessionHistory.add(RE + top + SPACE
            + topDisplay.getText().substring(topDisplay.getText().indexOf(EQUALS)));
         }
         else
         {
           topDisplay
             .setText(topDisplay.getText().substring(topDisplay.getText().indexOf(EQUALS) + 2)
                   + SPACE + EQUALS + SPACE + real.toString());
           top = OPEN_PAREN
              + topDisplay.getText().substring(0, topDisplay.getText().indexOf(EQUALS) - 1)
               + CLOSED_PAREN;
           SessionHistory.add(RE + top + SPACE
              + topDisplay.getText().substring(topDisplay.getText().indexOf(EQUALS)));
         }
       }
       break;
     case "POLAR_FORM":
       if (bracketClosed
           || (!bracketPresent && !bracketClosed && !bottomDisplay.getText().isEmpty()))
       {
         if (!polarFormEnabled)
         {
           String evaluated = Evaluator.evaluate(bottomDisplay.getText(), "", "", false);
           polarizedComplex = Complex.parse(evaluated);
           topDisplay.setText(
               bottomDisplay.getText() + SPACE + EQUALS + SPACE + polarizedComplex.getPolarForm());
          top = OPEN_PAREN
               + topDisplay.getText().substring(0, topDisplay.getText().indexOf(EQUALS) - 1)
               + CLOSED_PAREN;
           SessionHistory.add(PLR + top + SPACE
             + topDisplay.getText().substring(topDisplay.getText().indexOf(EQUALS)));
          bottomDisplay.setText("");
          bracketClosed = false;
           equalsPresent = true;
           polarFormEnabled = true;
         }
       }
       else if (equalsPresent)
       {
         if (!polarFormEnabled)
         {
           polarizedComplex = Complex
             .parse(topDisplay.getText().substring(topDisplay.getText().indexOf(EQUALS) + 1));
           String polarForm = polarizedComplex.getPolarForm();
           topDisplay
             .setText(topDisplay.getText().substring(topDisplay.getText().indexOf(EQUALS) + 2)
                  + SPACE + EQUALS + SPACE + polarForm);
           top = OPEN_PAREN
               + topDisplay.getText().substring(0, topDisplay.getText().indexOf(EQUALS) - 1)
               + CLOSED_PAREN;
          SessionHistory.add(PLR + top + SPACE
             + topDisplay.getText().substring(topDisplay.getText().indexOf(EQUALS)));
           polarFormEnabled = true;
         }
         else
         {
           topDisplay
               .setText(topDisplay.getText().substring(topDisplay.getText().indexOf(EQUALS) + 2)
                  + SPACE + EQUALS + SPACE + polarizedComplex.toString());
           top = OPEN_PAREN
               + topDisplay.getText().substring(0, topDisplay.getText().indexOf(EQUALS) - 1)
               + CLOSED_PAREN;
           SessionHistory.add(PLR + top + SPACE
              + topDisplay.getText().substring(topDisplay.getText().indexOf(EQUALS)));
          polarFormEnabled = false;
         }
       }
       break;
     case "CONJUGATE":
       if (bracketClosed
           || (!bracketPresent && !bracketClosed && !bottomDisplay.getText().isEmpty()))
       {
         String evaluated = Evaluator.evaluate(bottomDisplay.getText(), "Conjugate", "", false);
         topDisplay.setText(bottomDisplay.getText() + SPACE + EQUALS + SPACE + evaluated);
         top = OPEN_PAREN
            + topDisplay.getText().substring(0, topDisplay.getText().indexOf(EQUALS) - 1)
             + CLOSED_PAREN;
       SessionHistory.add(CONJ + top + SPACE
             + topDisplay.getText().substring(topDisplay.getText().indexOf(EQUALS)));
         bottomDisplay.setText("");
        bracketClosed = false;
         equalsPresent = true;
       }
       else if (equalsPresent)
       {
         Complex complexNum;
         if (polarFormEnabled)
         {
           complexNum = polarizedComplex;
        }
        else
         {
           complexNum = Complex
             .parse(topDisplay.getText().substring(topDisplay.getText().indexOf(EQUALS) + 1));
         }
         complexNum.conjugate();
         if (polarFormEnabled)
         {
         polarizedComplex = complexNum;
           topDisplay
               .setText(topDisplay.getText().substring(topDisplay.getText().indexOf(EQUALS) + 2)
                 + SPACE + EQUALS + SPACE + polarizedComplex.getPolarForm());
           top = OPEN_PAREN
              + topDisplay.getText().substring(0, topDisplay.getText().indexOf(EQUALS) - 1)
               + CLOSED_PAREN;
           SessionHistory.add(CONJ + top + SPACE
               + topDisplay.getText().substring(topDisplay.getText().indexOf(EQUALS)));
         }
         else
         {
           topDisplay
               .setText(topDisplay.getText().substring(topDisplay.getText().indexOf(EQUALS) + 2)
                   + SPACE + EQUALS + SPACE + complexNum.toString());
          top = OPEN_PAREN
               + topDisplay.getText().substring(0, topDisplay.getText().indexOf(EQUALS) - 1)
               + CLOSED_PAREN;
          SessionHistory.add(CONJ + top + SPACE
               + topDisplay.getText().substring(topDisplay.getText().indexOf(EQUALS)));
         }
       }
       break;
     case "SQUARE_ROOT":
       if (bracketClosed
           || (!bracketPresent && !bracketClosed && !bottomDisplay.getText().isEmpty()))
       {
        String evaluated = Evaluator.evaluate(bottomDisplay.getText(), "Square root", "", false);
         topDisplay.setText(bottomDisplay.getText() + SPACE + EQUALS + SPACE + evaluated);
         top = OPEN_PAREN
             + topDisplay.getText().substring(0, topDisplay.getText().indexOf(EQUALS) - 1)
            + CLOSED_PAREN;
         SessionHistory.add(SQRT + top + SPACE
             + topDisplay.getText().substring(topDisplay.getText().indexOf(EQUALS)));
       bottomDisplay.setText("");
         bracketClosed = false;
         equalsPresent = true;
      }
      else if (equalsPresent)
     {
         Complex complexNum;
         if (polarFormEnabled)
         {
         complexNum = polarizedComplex;
         }
         else
         {
           complexNum = Complex
              .parse(topDisplay.getText().substring(topDisplay.getText().indexOf(EQUALS) + 1));
         }
         complexNum.squareRoot();
         if (polarFormEnabled)
         {
          polarizedComplex = complexNum;
           topDisplay
             .setText(topDisplay.getText().substring(topDisplay.getText().indexOf(EQUALS) + 2)
                + SPACE + EQUALS + SPACE + polarizedComplex.getPolarForm());
           top = OPEN_PAREN
               + topDisplay.getText().substring(0, topDisplay.getText().indexOf(EQUALS) - 1)
               + CLOSED_PAREN;
          SessionHistory.add(SQRT + top + SPACE
               + topDisplay.getText().substring(topDisplay.getText().indexOf(EQUALS)));
         }
         else
       {
           topDisplay
               .setText(topDisplay.getText().substring(topDisplay.getText().indexOf(EQUALS) + 2)
                   + SPACE + EQUALS + SPACE + complexNum.toString());
           top = OPEN_PAREN
             + topDisplay.getText().substring(0, topDisplay.getText().indexOf(EQUALS) - 1)
              + CLOSED_PAREN;
         SessionHistory.add(SQRT + top + SPACE
               + topDisplay.getText().substring(topDisplay.getText().indexOf(EQUALS)));
         }
       }
       break;
     case "EXPONENT":
       if (bottomDisplay.getText().length() == 0 && !runningCalc)
         break;
     setOperator(bottomDisplay, topDisplay, POWER);
       break;
     case "LOGARITHM":
      if (bracketClosed
          || (!bracketPresent && !bracketClosed && !bottomDisplay.getText().isEmpty()))
       {
         String evaluated = Evaluator.evaluate(bottomDisplay.getText(), LOG, "", false);
         topDisplay.setText(bottomDisplay.getText() + SPACE + EQUALS + SPACE + evaluated);
         top = OPEN_PAREN
            + topDisplay.getText().substring(0, topDisplay.getText().indexOf(EQUALS) - 1)
           + CLOSED_PAREN;
         SessionHistory.add(LOG + top + SPACE
            + topDisplay.getText().substring(topDisplay.getText().indexOf(EQUALS)));
         bottomDisplay.setText("");
         bracketClosed = false;
         equalsPresent = true;
       }
       else if (equalsPresent)
      {
         Complex complexNum;
         if (polarFormEnabled)
         {
           complexNum = polarizedComplex;
         }
         else
         {
           complexNum = Complex
             .parse(topDisplay.getText().substring(topDisplay.getText().indexOf(EQUALS) + 1));
        }
         complexNum.logarithm();
         if (polarFormEnabled)
         {
           polarizedComplex = complexNum;
           topDisplay
             .setText(topDisplay.getText().substring(topDisplay.getText().indexOf(EQUALS) + 2)
                   + SPACE + EQUALS + SPACE + polarizedComplex.getPolarForm());
           top = OPEN_PAREN
               + topDisplay.getText().substring(0, topDisplay.getText().indexOf(EQUALS) - 1)
               + CLOSED_PAREN;
          SessionHistory.add(LOG + top + SPACE
               + topDisplay.getText().substring(topDisplay.getText().indexOf(EQUALS)));
         }
         else
        {
           topDisplay
              .setText(topDisplay.getText().substring(topDisplay.getText().indexOf(EQUALS) + 2)
                   + SPACE + EQUALS + SPACE + complexNum.toString());
           top = OPEN_PAREN
              + topDisplay.getText().substring(0, topDisplay.getText().indexOf(EQUALS) - 1)
               + CLOSED_PAREN;
          SessionHistory.add(LOG + top + SPACE
              + topDisplay.getText().substring(topDisplay.getText().indexOf(EQUALS)));
        }
      }
      break;
    case "OPEN_BRACKET":
       if (bottomDisplay.getText().length() == 0)
       {
         bottomDisplay.setText(bottomDisplay.getText() + OPEN_BRACKET);
       if (equalsPresent)
         {
           topDisplay.setText("");
          equalsPresent = false;
         }
         bracketPresent = true;
       }
     break;
     case "CLOSED_BRACKET":
       String dispText = bottomDisplay.getText();
       if (dispText.length() == 0)
       {
         break;
       }
       char lastVal = dispText.charAt(dispText.length() - 1);
       if (bracketPresent && (Character.isDigit(lastVal) || !checkDigitPlacement(bottomDisplay)
          || lastChar() == ')') && (openParenCount == closedParenCount))
       {
         bottomDisplay.setText(bottomDisplay.getText().replace(OPEN_BRACKET, ""));
         bracketPresent = false;
         bracketClosed = true;
       }
       break;
    case "S_HISTORY_DROPOUT":
      if (sessionHistoryWindow != null)
       {
        sessionHistoryWindow.toggleAnimation();
       }
       break;
     case "ACTION_PRINT":
       PrintHelper.printHtmlFile();
     break;
     case "ACTION_NEWCALC":
       try
       {
         ProcessBuilder pb = new ProcessBuilder("java", "-cp",
             System.getProperty("java.class.path"), "rimplex.RimpleX");
         pb.start();
       }
       catch (IOException e)
       {
         e.printStackTrace();
       }
       break;
     case "EDIT_PREFERENCES":
       prefWindow.setVisible(true);
       break;
     case "SAVE_PREFERENCES":
       JFileChooser fileSaver = new JFileChooser();
       fileSaver.addChoosableFileFilter(filter);
       fileSaver.setFileFilter(filter);
       fileSaver.setAcceptAllFileFilterUsed(false);
      if (fileSaver.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
       {
       File fileToSave = fileSaver.getSelectedFile();
        String fileToSavePath = fileToSave.getAbsolutePath();
         if (!fileToSavePath.toLowerCase().endsWith(PROPERTIES))
         {
           fileToSavePath += PROPERTIES;
           fileToSave = new File(fileToSavePath);
         }
         RimpleXPreferences.setPreferencesFile(fileToSavePath);
         if (!fileToSave.exists())
         {
           try
           {
            fileToSave.createNewFile();
           }
          catch (IOException e)
           {
           e.printStackTrace();
           }
         }
       // <<<<<<< HEAD
         //
         // =======
         // RimpleXPreferences.savePreferencesFilePath(fileToSavePath);
        // >>>>>>> branch 'main' of https://github.com/bernstdh/s25team2b
         RimpleXPreferences.savePreferencesFilePath(fileToSavePath);
         RimpleXPreferences.savePreferences();
       }
       break;
     case "OPEN_PREFERENCES":
       JFileChooser fileChooser = new JFileChooser();
       fileChooser.addChoosableFileFilter(filter);
       fileChooser.setAcceptAllFileFilterUsed(false);
      if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
       {
         RimpleXPreferences.setPreferencesFile(fileChooser.getSelectedFile().getAbsolutePath());
         RimpleXPreferences
            .savePreferencesFilePath(fileChooser.getSelectedFile().getAbsolutePath());
         RimpleXPreferences.getPreferences();
         prefWindow.updatePreferenceValues();
       }
     break;
    case "COMPLEX_PLANE":
      if (result != null)
       {
         new ComplexPlaneWindow(result);
       }
       else
       {
        JOptionPane.showMessageDialog(null, rb.getString("No_Result_To_Display"),
             rb.getString(ERROR), JOptionPane.ERROR_MESSAGE);
       }
       break;
     case "RECORDING_START":
       RecordingManager.startRecording();
       break;
     case "RECORDING_PAUSE":
      RecordingManager.pauseRecording();
       break;
     case "RECORDING_RESUME":
       RecordingManager.resumeRecording();
       break;
     case "RECORDING_STOP":
     RecordingManager.pauseRecording();
      RimpleXRecordingWindow.getInstance(this).dispose();
       break;
   case "RECORDING_PLAY":
      String filePath = RecordingManager.getFilePath();
       System.out.println("Playback triggered for file: " + filePath);
       if (filePath != null && !filePath.isEmpty())
      {
         RimpleXPlaybackWindow playbackWindow = RimpleXPlaybackWindow.getInstance(this, filePath);
         int sliderValue = playbackWindow.getSliderValue();
         int delayMillis = 4100 - sliderValue;
         RecordingManager.playFromFile(filePath, this, delayMillis);
      }
       else
       {
        System.err.println("Error: No recording file selected for playback");
       }
       break;
     default:
       break;
   }
   window.requestFocusInWindow();
 }
 /**
  * Check if a digit can be placed based on display length and presence of imaginary unit.
  *
  * @param display
  *          The display to extract the text from.
  * @return True if digit can be placed.
  */
 private boolean checkDigitPlacement(final JLabel display)
 {
  if (display.getText().length() != 0)
   {
     if (display.getText().endsWith(IMAGINARY_UNIT))
     {
       return false;
    }
   }
   return true;
 }
 /**
  * Check if an operator can be inputed onto the display.
  *
  * @param display
  *          The display to reference.
  * @return Return true if operator can be placed.
  */
 private boolean checkOperatorPlacement(final JLabel display)
 {
   char lastChar = display.getText().charAt(display.getText().length() - 1);
   if (lastChar == '+' || lastChar == '—' || lastChar == '^' || lastChar == '×' || lastChar == '÷'
       || lastChar == '÷')
   {
     return false;
   }
   return true;
 }
 /**
  * Get the result of the current complex number for complex plane.
  *
  * @return Return the complex number.
  */
 public Complex getResult()
 {
   return result;
 }
 /**
  * Get the last character of the bottom display.
  *
  * @return Return the last character.
  */
 private char lastChar()
 {
   String txt = bottomDisplay.getText();
   return txt.charAt(txt.length() - 1);
 }
 /**
  * Set the calculator displays to reference and control.
  *
  * @param display
  *          The bottom display of the calculator.
  * @param upperDisplay
  *          The top display of the calculator.
  */
 public void setDisplays(final JLabel display, final JLabel upperDisplay)
 {
   this.bottomDisplay = display;
   this.topDisplay = upperDisplay;
 }
 /**
 * Set the operator onto the display depending on presence of parentheses and other operators
  * within the current expression.
  *
  * @param display
  *          The display that holds the current operand.
  * @param upperDisplay
  *        The display that holds the left operand.
  * @param op
  *       The operator to place.
  */
 private void setOperator(final JLabel display, final JLabel upperDisplay, final String op)
 {
   String topDisplayValue = upperDisplay.getText();
   if (topDisplayValue.isBlank() && display.getText().isBlank())
   {
    return;
   }
   if (equalsPresent)
   {
     upperDisplay.setText(
         Evaluator.evaluate(topDisplayValue.substring(topDisplayValue.indexOf(EQUALS) + 2), "", "",
           true) + SPACE + op);
     equalsPresent = false;
   }
   else
   {
     if (!bracketPresent && !relationalOpPresent)
     {
       // Evaluate when doing running calculations
       if (!upperDisplay.getText().isBlank())
       {
         String leftOperand;
         if (polarFormEnabled)
         {
         leftOperand = polarizedComplex.toString();
         }
         else
         {
           leftOperand = upperDisplay.getText().replace(SPACE, "").substring(0,
               upperDisplay.getText().length() - 2);
         }
         String prevOperator = upperDisplay.getText()
             .substring(upperDisplay.getText().length() - 1);
         String rightOperand = display.getText();
         String evaluation = Evaluator.evaluate(leftOperand, prevOperator, rightOperand, false);
         if (polarFormEnabled)
         {
           Complex evaluated = Complex.parse(evaluation);
           String polarForm = evaluated.getPolarForm();
           upperDisplay.setText(polarForm + SPACE + op);
          if (!display.getText().isBlank())
           {
             SessionHistory
                 .add(topDisplayValue + SPACE + display.getText() + EVALUATE + evaluation);
           }
         polarizedComplex = evaluated;
         }
         else
        {
          upperDisplay.setText(evaluation + SPACE + op);
           if (!display.getText().isBlank())
           {
             SessionHistory
                 .add(topDisplayValue + SPACE + display.getText() + EVALUATE + evaluation);
           }
       }
       }
       else
      {
         upperDisplay.setText(Evaluator.evaluate(display.getText(), "", "", true) + SPACE + op);
       }
       display.setText("");
       bracketClosed = false;
     }
     else
     {
       if (checkOperatorPlacement(display))
       {
         display.setText(display.getText() + op);
       }
     }
   }
 }
 /**
  * Update the display when digit is entered.
  *
  * @return if the display is updated.
  */
 private boolean updateDisplayingDigit()
 {
   if (!checkDigitPlacement(bottomDisplay))
   {
     return false;
   }
   if (equalsPresent)
   {
     topDisplay.setText("");
     equalsPresent = false;
   }
   return true;
 }
 /**
 * Set relational the operator onto the display depending on presence of parentheses and other
  * operators within the current expression.
  *
  * @param display
  *         Lower display of the calculator.
  * @param upperDisplay
  *          Upper display of the calculator.
  * @param op
  *          The relational operator inputed.
  */
 private void setRelationalOperator(final JLabel display, final JLabel upperDisplay,
     final String op)
 {
   String topDisplayValue = upperDisplay.getText();
 if (topDisplayValue.isBlank() && display.getText().isBlank())
   {
     return;
   }
   if (topDisplayValue.isBlank())
   {
     if (bracketClosed
         || (!bracketPresent && !bracketClosed && !bottomDisplay.getText().isEmpty()))
     {
       upperDisplay.setText(Evaluator.evaluate(display.getText(), "", "", true) + SPACE + op);
     }
   }
   else if (equalsPresent)
   {
   upperDisplay.setText(
         Evaluator.evaluate(topDisplayValue.substring(topDisplayValue.indexOf(EQUALS) + 2), "", "",
             true) + SPACE + op);
   equalsPresent = false;
   }
   else if (!relationalOpPresent && !bracketPresent)
   {
     // Evaluate when doing running calculations
   if (!upperDisplay.getText().isBlank())
     {
       String leftOperand;
       if (polarFormEnabled)
       {
         leftOperand = polarizedComplex.toString();
       }
       else
       {
       leftOperand = upperDisplay.getText().replace(SPACE, "").substring(0,
             upperDisplay.getText().length() - 2);
       }
       String prevOperator = upperDisplay.getText().substring(upperDisplay.getText().length() - 1);
       String rightOperand = display.getText();
     String evaluation = Evaluator.evaluate(leftOperand, prevOperator, rightOperand, false);
       if (polarFormEnabled)
       {
         Complex evaluated = Complex.parse(evaluation);
         String polarForm = evaluated.getPolarForm();
      upperDisplay.setText(polarForm + SPACE + op);
         if (!display.getText().isBlank())
         {
           SessionHistory.add(topDisplayValue + SPACE + display.getText() + EVALUATE + evaluation);
        }
         polarizedComplex = evaluated;
       }
       else
       {
         upperDisplay.setText(evaluation + SPACE + op);
         if (!display.getText().isBlank())
         {
         SessionHistory.add(topDisplayValue + SPACE + display.getText() + EVALUATE + evaluation);
         }
       }
     }
   }
  relationalOpPresent = true;
   display.setText("");
 }
 /**
  * Set the window to reference and control.
  *
  * @param window
  *          The GUI window to control.
  */
 public void setWindow(final RimpleXWindow window)
 {
   this.window = window;
 }
 /**
  * Sets the session history window to be controlled.
*
  * @param controlledWindow
  *          The current window.
  */
 public void setSessionHistoryWindow(final SessionHistoryWindow controlledWindow)
 {
   this.sessionHistoryWindow = controlledWindow;
 }
 /**
  * Parses line for calculation.
 *
  * @param line
  *          The line being parsed.
  */
 public void parseAndApplyCalculation(final String line)
 {
   String parseLine = line;
   System.out.println("parseAndApplyCalculation received: " + parseLine);
   if (parseLine.contains(EQUALS))
   {
     parseLine = parseLine.replaceFirst("^\\d+\\.\\s*", "");
     String[] parts = parseLine.split(EQUALS);
     if (parts.length == 2)
     {
       String equation = parts[0].trim();
       String trimResult = parts[1].trim();
       topDisplay.setText(equation + " =");
       bottomDisplay.setText(trimResult);
       topDisplay.repaint();
       bottomDisplay.repaint();
       System.out.println("Displayed playback equation: " + equation + EVALUATE + trimResult);
     }
     else
     {
       System.err.println("Invalid playback format: " + parseLine);
     }
   }
   else
   {
     System.err.println("Playback entry does not contain '=': " + parseLine);
   }
 }
}

