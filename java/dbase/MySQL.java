/**
 * This class connects to a MySQL Database.
 * 
 * MySQL
 * jdbc:mysql://localhost:3306/database
 * 
 * mysql-connector-java-5.1.43-bin.jar
 * 
 * @author James Soper
 * @version 1.1
 */
public final class MySQL extends ConnectDB
{
  public MySQL()
  {
    driverClass = "com.mysql.jdbc.Driver";
    url         = "jdbc:mysql://";

    host = "127.0.0.1"; // localhost
    port = 3306; // Default port for MySQL
    database = "mysql";
    userID = "root";
    pwd = "password";

    output = "usage: java MySQL <host> <port> <database> <userID> <password>";
  }

  public static void main(String[] args)
  {
    MySQL db = new MySQL();

    db.connect(args.length, args);
  }
}
