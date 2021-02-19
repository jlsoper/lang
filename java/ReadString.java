import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadString
{
  public static void main(String[] args)
  {
    String input = null;

    try
    {
      input = readFromCommandLine("Enter your name");
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }

    System.out.println(Integer.toBinaryString(5));
    
    System.out.println("The text you entered: \"" + input + "\"");
  }

  public static String readFromCommandLine(String prompt) throws IOException
  {
    System.out.print(prompt + ": ");

    //  open up standard input
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    return br.readLine();
  }
}
