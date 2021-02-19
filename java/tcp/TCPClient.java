package tcp;

// usage : java tcp.TCPClient <server> <port>
// default connection is localhost:1500

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.Socket;
import java.net.UnknownHostException;

/**
 * This is the 'client' class.
 * @author James Soper
 * @version 1.1
 */
public class TCPClient
{
  public static void main(String[] args)
  {
    int            port = 1500;
    String         server = "localhost";
    Socket         socket = null;
    String         lineToBeSent;
    BufferedReader input;
    PrintWriter    output;
    int            ERROR = 1;

    // read arguments
    if (args.length >= 1)
    {
      server = args[0];

      try
      {
        port = Integer.parseInt(args[1]);
      }
      catch (Exception e)
      {
        System.out.println("server port = 1500 (default)");
        port = 1500;
      }
    }

    // connect to server
    try
    {
      socket = new Socket(server, port);
      System.out.println("Connected with server " + socket.getInetAddress() + ":" + socket.getPort());
    }
    catch (UnknownHostException e)
    {
      System.out.println(e);
      System.exit(ERROR);
    }
    catch (IOException e)
    {
      System.out.println(e);
      System.exit(ERROR);
    }

    try
    {
      input = new BufferedReader(new InputStreamReader(System.in));
      output = new PrintWriter(socket.getOutputStream(), true);

      // get user input and transmit it to server
      while (true)
      {
        lineToBeSent = input.readLine();

        // stop if input line is "."
        if (lineToBeSent.equals("."))
        {
          break;
        }

        output.println(lineToBeSent);
      }
    }
    catch (IOException e)
    {
      System.out.println(e);
    }

    try
    {
      socket.close();
    }
    catch (IOException e)
    {
      System.out.println(e);
    }
  }
}
