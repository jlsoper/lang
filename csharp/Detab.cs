using System;
using System.IO;

namespace csharp
{
  //
  // This class replaces tabs with spaces.
  // @author James Soper
  // @version 1.0
  //
  class Detab
  {
    public const int TAB   =  9;
    public const int LF    = 10;
    public const int CR    = 13;
    public const int SPACE = 32;
    public const int EOF   = -1;  // End of File character

    private String inputFile;
    private String outputFile;

    private StreamReader streamReader;
    private StreamWriter streamWriter;

    private int tab;
    private int tabsize;

    //
    // Sets default size for tabs (2), unless specified.
    //
    public Detab()
    {
      tabsize = 2;
    }

    //
    // The Main method for the Detab program.
    // @param args array of strings
    //
    static void Main(string[] args)
    {
      Detab d = new Detab();

      d.setFileNames(args.Length, args);
      d.processFile();
    }

    //
    // Sets the tab size & input/output filenames.
    // @param argc number of arguments passed to main()
    // @param arguments array of strings
    //
    public void setFileNames(int argc, String[] arguments)
    {
      switch (argc)
      {
        case 1:
          inputFile = arguments[0];
          break;

        case 2:
          tabsize   = Convert.ToInt32(arguments[0]);
          inputFile = arguments[1];
          break;

        default:
          Console.WriteLine("usage: dotnet DetabNS.dll <tab_size> input_file");
          Environment.Exit(-1);
          break;
      }

      outputFile = inputFile + ".detab";
    }

    //
    // Process the input file.
    //
    public void processFile()
    {
      int i, j;

      try
      {
        streamReader = new StreamReader(inputFile);
        streamWriter = new StreamWriter(outputFile);

        i = streamReader.Read();

        while (i != EOF)
        {
          if (i == TAB)  // if tab found, output appropriate # of spaces
          {
            for (j = tab; j < tabsize; j++)
            {
              streamWriter.Write(Convert.ToChar(SPACE));
            }

            tab = 0;
          }
          else
          {
            streamWriter.Write(Convert.ToChar(i));

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

          i = streamReader.Read();
        }
      }
      catch(Exception e)
      {
        Console.WriteLine("Exception: " + e.Message);
      }
      finally 
      {
        if (streamWriter != null)
          ((IDisposable) streamWriter).Dispose();

        if (streamReader != null)
          ((IDisposable) streamReader).Dispose();
      }
    }
  }
}
