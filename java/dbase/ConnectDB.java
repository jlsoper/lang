import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

/**
 * This class contains the plumbing for connecting to a Database.
 * @author James Soper
 * @version 1.7
 */
public abstract class ConnectDB
{
  String driverClass;
  String url;

  String host;
  int    port;
  String database;
  String userID;
  String pwd;

  String connectionURL;

  private Connection        connection = null;
  private Statement         statement  = null;
  private ResultSet         resultSet  = null;
  private ResultSetMetaData rsMetaData = null;

  String output;

  void connect(int argc, String[] arguments)
  {
    boolean rows = false;

    switch (argc)
    {
      case 0:
        System.out.println("Connecting to DB as root!");
        break;

      case 3:
        database = arguments[0];
        userID   = arguments[1];
        pwd      = arguments[2];
        break;

      case 5:
        host     = arguments[0];
        port     = Integer.parseInt(arguments[1]);
        database = arguments[2];
        userID   = arguments[3];
        pwd      = arguments[4];
        break;

      default:
        System.out.println(output);
        System.out.println("(Enter last 3 or 5 arguments)");
        System.exit(-1);
    }

    try
    {
      String sql = readFromCommandLine("Enter your DB query");

      // load JDBC driver to connect to database
      Class.forName(driverClass);

      setConnectionURL();

      connection = DriverManager.getConnection(connectionURL, userID, pwd);

      statement = connection.createStatement();

      resultSet = statement.executeQuery(sql);

      rsMetaData = resultSet.getMetaData();

      System.out.println();

      while (resultSet.next())
      {
        rows = true;

        for (int i = 1; i <= rsMetaData.getColumnCount(); i++)
        {
          System.out.println(rsMetaData.getColumnName(i) + ": " + resultSet.getString(i));
        }

        System.out.println();
      }

      if (rows == false)
        System.out.println("(no records)");
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      disconnect();
    }
  }

  private void disconnect()
  {
    try
    {
      if (resultSet != null)
        resultSet.close();

      if (statement != null)
        statement.close();

      if (connection  != null)
        connection.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  void setConnectionURL()
  {
    connectionURL = url + host + ":" + port + "/" + database;
  }

  public static String readFromCommandLine(String prompt) throws IOException
  {
    System.out.print(prompt + ": ");

    //  open up standard input
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    return br.readLine();
  }
}
