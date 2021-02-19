import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * This class word wraps text files.
 *   similar to Unix 'fold -s' command.
 * @author James Soper
 * @version 1.3
 */
public class Fold
{
  public static final char SPACE = ' ';
  public static final char TAB   = '\t';

  // Win -> "\r\n" , Unix -> "\n"
  public static final String LINESEPARATOR = System.getProperty("line.separator");
  public static final String LF = "\n";

  private File inputFile;
  private File outputFile;

  private PrintWriter printWriter;
  private FileWriter  fileWriter;

  private Scanner scanner;

  private int maxLength;

  private String fileName;

  /**
   * Set the default size for maximum line length. ( 80 )
   */
  public Fold()
  {
    maxLength = 80;
  }

  /**
   * The main method for the Fold program.
   * @param args array of strings
   */
  public static void main(String[] args)
  {
    Fold fold = new Fold();

    fold.setFileName(args.length, args);
    fold.createFileObjects();
    fold.createStreams();
    fold.processFile();
    fold.closeStreams();
  }

  /**
   * This method word wraps each line according to maximum line length.
   * @param input string to be analyzed, possibly broken up
   * @return string of the 1st line and any possible lines thereafter (recursively calls itself)
   */
  public String wordWrap(String input)
  {
    while(input.length() > 0 && (input.charAt(0) == TAB || input.charAt(0) == SPACE))
      input = input.substring(1);

    if (input.length() < maxLength)  // string is less than max length
      return input;

    if (input.substring(0, maxLength).contains(LF))  // string contains LF character before max length
    {
      String firstLine = input.substring(0, input.indexOf(LF));

      String restOfString = input.substring(input.indexOf(LF) + 1);

      return firstLine + LF + wordWrap(restOfString);
    }

    // string does not contain LF character before max length, so determine where to break
    int place = Math.max(input.lastIndexOf(SPACE, maxLength), input.lastIndexOf(TAB, maxLength));

    if (place == -1)
      place = maxLength;  // no SPACE or TAB found, then break line at max length

    String substr1 = input.substring(0, place);
    String substr2 = input.substring(place);

    return substr1 + LINESEPARATOR + wordWrap(substr2);
  }

  /**
   * Sets the name of the input file.
   * @param argc number of arguments passed to main()
   * @param arguments array of strings
   */
  public void setFileName(int argc, String[] arguments)
  {
    switch (argc)
    {
      case 1:
        fileName = arguments[0];
        break;

      case 2:
        try
        {
          maxLength = Integer.parseInt(arguments[0]);
        }
        catch (NumberFormatException nfe)
        {
          System.err.println("Enter a valid argument for max line length!");
          System.exit(-1);
        }

        fileName = arguments[1];
        break;

      default:
        System.out.println("usage: java Fold <line_length> input_file");
        System.exit(-1);
    }
  }

  /**
   * Creates input & output File objects.
   */
  public void createFileObjects()
  {
    inputFile  = new File(fileName);
    outputFile = new File(fileName + ".text");
  }

  /**
   * Creates the output stream.
   */
  public void createStreams()
  {
    try
    {
      fileWriter = new FileWriter(outputFile);
    }
    catch (IOException e)
    {
      System.err.println("IOException opening output file " + outputFile);
    }

    printWriter = new PrintWriter(fileWriter);
  }

  /**
   * Process the input file and writes to output file.
   */
  public void processFile()
  {
    StringBuilder fileContents = new StringBuilder((int) inputFile.length());

    try
    {
      scanner = new Scanner(inputFile);

      while (scanner.hasNextLine())
      {
        fileContents.append(scanner.nextLine());
        fileContents.append(LINESEPARATOR);
      }

      printWriter.write(wordWrap(fileContents.toString()));
    }
    catch (FileNotFoundException e)
    {
      System.err.println("FileNotFoundException reading file!");
    }
    finally
    {
      scanner.close();
    }
  }

  /**
   * Close the output stream.
   */
  public void closeStreams()
  {
    try
    {
      printWriter.close();
      fileWriter.close();
    }
    catch (IOException e)
    {
      System.err.println("IOException closing output file " + outputFile);
    }
  }
}
