/**
 * This class connects to a MS SQL Server Database.
 * 
 * MS SQL Server
 * jdbc:sqlserver://localhost:1433;databaseName=pubs
 * 
 * sqljdbc.jar
 * 
 * @author James Soper
 * @version 1.1
 */
public final class SQLServer extends ConnectDB
{
  public SQLServer()
  {
    driverClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    url         = "jdbc:sqlserver://";

    host = "127.0.0.1"; // localhost
    port = 1433;        // Default port for SQLServer
    database = "pubs";
    userID = "root";
    pwd = "password";

    output = "usage: java SQLServer <host> <port> <database> <userID> <password>";
  }

  public static void main(String[] args)
  {
    SQLServer db = new SQLServer();

    db.connect(args.length, args);
  }

  void setConnectionURL()
  {
    connectionURL = url + host + ":" + port + ";databaseName=" + database;
  }
}
