import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

/**
 * This class connects to a Database.
 * @author James Soper
 * @version 1.6
 */
public class ConnectDatabase
{
  // MariaDB - problems with Eclipse DB development perspective (showing results table!)
  //
  // jdbc:mysql://localhost:3306/database
  // org.mariadb.jdbc.Driver
  //
  // mariadb-java-client-2.1.0.jar

  // MySQL
  // jdbc:mysql://127.0.0.1:3306/database
  // com.mysql.jdbc.Driver
  //
  // mysql-connector-java-5.1.43-bin.jar

  public static final String DRIVER_CLASS = "org.mariadb.jdbc.Driver";
  public static final String URL = "jdbc:mariadb://";

  private String host;
  private int    port;
  private String database;
  private String userID;
  private String pwd;

  private Connection        connection = null;
  private Statement         statement  = null;
  private ResultSet         resultSet  = null;
  private ResultSetMetaData rsMetaData = null;

  public ConnectDatabase()
  {
    host = "127.0.0.1"; // localhost
    port = 3306; // Default port for MariaDB (MySQL)
    database = "mysql";
    userID = "root";
    pwd = "password";
  }

  public static void main(String[] args)
  {
    ConnectDatabase db = new ConnectDatabase();

    db.connect(args.length, args);
  }

  private void connect(int argc, String[] arguments)
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
        System.out.println("usage: java ConnectDatabase <host> <port> <database> <userID> <password>");
        System.out.println("(Enter last 3 or 5 arguments)");
        System.exit(-1);
    }

    try
    {
      String sql = readFromCommandLine("Enter your DB query");

      // load JDBC driver to connect to database
      Class.forName(DRIVER_CLASS);

      String connectionURL = URL + host + ":" + port + "/" + database;

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

  public static String readFromCommandLine(String prompt) throws IOException
  {
    System.out.print(prompt + ": ");
  
    //  open up standard input
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  
    return br.readLine();
  }
}
