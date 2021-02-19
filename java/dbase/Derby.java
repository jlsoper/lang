/**
 * This class connects to a Apache Derby Database.
 *
 * Derby
 * jdbc:derby://server[:port]/databaseName[;URLAttributes=value[;...]]
 *
 * jdbc:derby://localhost:1527/database;create=true
 *
 * derbyclient.jar
 *
 * @author James Soper
 * @version 1.0
 */
public final class Derby extends ConnectDB
{
  public Derby()
  {
    driverClass = "org.apache.derby.jdbc.ClientDriver";
    url         = "jdbc:derby://";

    host = "127.0.0.1"; // localhost
    port = 1527; // Default port for Derby
    database = "database";
    userID = "root";
    pwd = "password";

    output = "usage: java Derby <host> <port> <database> <userID> <password>";
  }

  public static void main(String[] args)
  {
    Derby db = new Derby();

    db.connect(args.length, args);
  }
}
