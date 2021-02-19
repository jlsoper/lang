// java JC mySource.java
// java JC mySource.java > output.txt

import java.io.*;

public class JC
{
  public static void main(String args[]) throws IOException, InterruptedException
  {
    String header  = null;
    String footer  = null;
    String marker  = null;
    String command = null;

    String parameters = "";

    if (args.length == 0)
    {
      command = "javac";
      header = "";
      footer = "";
      marker = "";
    }
    else
    {
      command = "javac -verbose ";

      for (int i = 0; i < args.length; i++)
      {
        parameters += (args[i] + " ");
      }

      header = "BEGIN ( " + parameters + ")";
      marker = " : ";
    }

    System.out.println(header);

    Process        process = Runtime.getRuntime().exec(command + parameters);
    String         buffer;
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

    while ((buffer = bufferedReader.readLine()) != null)
    {
      System.out.println(marker + buffer);
    }

    if (args.length != 0)
      footer = "END   (Process return value:" + process.waitFor() + ")";

    System.out.println(footer);
  }
}
