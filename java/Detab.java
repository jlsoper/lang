import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This class replaces tabs with spaces.
 * @author James Soper
 * @version 1.8
 */
public class Detab
{
  public static final int TAB   =  9;
  public static final int LF    = 10;
  public static final int CR    = 13;
  public static final int SPACE = 32;
  public static final int EOF   = -1;  // End of File character

  private File            inputFile;
  private File            outputFile;

  private FileReader      fileReader;
  private FileWriter      fileWriter;

  private BufferedReader  bufferedReader;
  private PrintWriter     printWriter;

  private int             tab;
  private int             tabsize;
  private String          fileName;

  /**
   * Sets default size for tabs (2), unless specified.
   */
  public Detab()
  {
    tabsize = 2;
  }

  /**
   * The main method for the Detab program.
   * @param args array of strings
   */
  public static void main(String[] args)
  {
    Detab d = new Detab();

    d.setFileName(args.length, args);
    d.createFileNames();
    d.createStreams();
    d.processFile();
    d.closeStreams();
  }

  /**
   * Sets the tab size & input filename.
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
          tabsize = Integer.parseInt(arguments[0]);
        }
        catch (NumberFormatException nfe)
        {
          System.err.println("Enter a valid argument for tabsize!");
          System.exit(-1);
        }

        fileName = arguments[1];
        break;

      default:
        System.out.println("usage: java Detab <tab_size> input_file");
        System.exit(-1);
    }
  }

  /**
   * Creates input & output File objects.
   */
  public void createFileNames()
  {
    inputFile  = new File(fileName);
    outputFile = new File(fileName + ".detab");
  }

  /**
   * Creates the input & output streams.
   */
  public void createStreams()
  {
    try
    {
      fileReader = new FileReader(inputFile);
    }
    catch (IOException e)
    {
      System.err.println("IO exception opening input file " + inputFile);
    }

    bufferedReader = new BufferedReader(fileReader);

    try
    {
      fileWriter = new FileWriter(outputFile);
    }
    catch (IOException e)
    {
      System.err.println("IO exception opening output file " + outputFile);
    }

    printWriter = new PrintWriter(fileWriter);
  }

  /**
   * Process the input file.
   */
  public void processFile()
  {
    int i, j;

    try
    {
      i = bufferedReader.read();

      while (i != EOF)
      {
        if (i == TAB)  // if tab found, output appropriate # of spaces
        {
          for (j = tab; j < tabsize; j++)
          {
            printWriter.write(SPACE);
          }

          tab = 0;
        }
        else
        {
          printWriter.write(i);

          tab++;

          if (tab == tabsize)
          {
            tab = 0;
          }

          if (i == CR || i == LF)
          {
            tab = 0;
          }
        }

        i = bufferedReader.read();
      }
    }
    catch (IOException e)
    {
      System.err.println("IO exception reading/writing file!");
    }
  }

  /**
   * Close the input & output streams.
   */
  public void closeStreams()
  {
    try
    {
      bufferedReader.close();
      fileReader.close();
    }
    catch (IOException e)
    {
      System.err.println("IO exception closing input file " + inputFile);
    }

    try
    {
      printWriter.close();
      fileWriter.close();
    }
    catch (IOException e)
    {
      System.err.println("IO exception closing output file " + outputFile);
    }
  }
}
