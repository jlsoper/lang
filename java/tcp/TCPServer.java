package tcp;

// usage : java tcp.TCPServer <port number>
// default port is 1500
// connection to be closed by client

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * This is the 'server' class.
 * @author James Soper
 * @version 1.1
 */
public class TCPServer implements Runnable
{
  private int          port;
  private ServerSocket serverSocket;
  private Socket       socket;

  public static void main(String[] args)
  {
    TCPServer server = new TCPServer();

    server.start(args);
  }

  public void start(String[] arguments)
  {
    try
    {
      port = Integer.parseInt(arguments[0]);
    }
    catch (Exception e)
    {
      System.out.println("port = 1500 (default)");
      port = 1500;
    }

    try
    {
      serverSocket = new ServerSocket(port);
      System.out.println("Server waiting for client on port " + serverSocket.getLocalPort());

      // server infinite loop
      while (true)
      {
        socket = serverSocket.accept();
        System.out.println("New connection accepted " + socket.getInetAddress() + ":" + socket.getPort());

        Thread t = new Thread(this);
        t.start();
      }
    }
    catch (IOException e)
    {
      System.out.println(e);
    }
  }

  public void run()
  {
    try
    {
      BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

      // print received data
      try
      {
        while (true)
        {
          String message = input.readLine();

          if (message == null)
          {
            break;
          }

          System.out.println(message);
        }
      }
      catch (IOException e)
      {
        System.out.println(e);
      }

      // connection closed by client
      try
      {
        socket.close();
        System.out.println("Connection closed by client");
      }
      catch (IOException e)
      {
        System.out.println(e);
      }
    }
    catch (IOException e)
    {
      System.out.println(e);
    }
  }
}
