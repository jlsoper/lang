import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * This class embeds a text file within an HTML file.
 * @author James Soper
 * @version 1.1
 */
public class Text2Html
{
  // Win -> "\r\n" , Unix -> "\n"
  public static final String LINESEPARATOR = "\n";

  private File inputFile;
  private File outputFile;

  private FileWriter  fileWriter;

  private PrintWriter printWriter;

  private Scanner scanner;

  private String fileName;

  private String[] header;
  private String[] footer;

  /**
   * Creates the header & footer sections.
   */
  public Text2Html()
  {
    String emptyString = "";  // newline in final output

    header = new String[11];
    footer = new String[3];

    header[0] = "<!doctype html>";
    header[1] = emptyString;
    header[2] = "<html lang=\"en\">";  // <html lang="en">
    header[3] = "<head>";
    header[4] = "<meta charset=\"utf-8\">";  // <meta charset="utf-8">
    header[5] = emptyString;
    // header[6] = "<title>text2html</title>";
    header[7] = "</head>";
    header[8] = emptyString;
    header[9] = "<body>";
    header[10] = "<pre>  <!-- preformatted text (fixed width font, preserve whitespace) -->";

    footer[0] = "</pre>";
    footer[1] = "</body>";
    footer[2] = "</html>";
  }

  /**
   * The main method for the Text2Html program.
   * @param args array of strings
   */
  public static void main(String[] args)
  {
    Text2Html text2Html = new Text2Html();

    text2Html.setFileName(args.length, args);
    text2Html.createFileObjects();
    text2Html.createStreams();
    text2Html.processFile();
    text2Html.closeStreams();
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
        fileName  = arguments[0];
        header[6] = "<title>" + fileName + "</title>";
        break;

      default:
        System.out.println("usage: java Text2Html <input_file>");
        System.exit(-1);
    }
  }

  /**
   * Creates input & output File objects.
   */
  public void createFileObjects()
  {
    inputFile  = new File(fileName);
    outputFile = new File(fileName + ".html");
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

      for (String line : header)
      {
        printWriter.write(line);
        printWriter.write(LINESEPARATOR);
      }

      printWriter.write(fileContents.toString());

      for (int i = 0; i < footer.length; i++)
      {
        printWriter.write(footer[i]);
        printWriter.write(LINESEPARATOR);
      }
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
