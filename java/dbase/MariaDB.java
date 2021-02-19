/**
 * This class connects to a Maria Database.
 * 
 * MariaDB - problems with Eclipse DB development perspective (showing results table!)
 * jdbc:mariadb://localhost:3306/database
 *
 * mariadb-java-client-2.1.0.jar
 * 
 * @author James Soper
 * @version 1.1
 */
public final class MariaDB extends ConnectDB
{
  public MariaDB()
  {
    driverClass = "org.mariadb.jdbc.Driver";
    url         = "jdbc:mariadb://";

    host = "127.0.0.1"; // localhost
    port = 3306; // Default port for MariaDB
    database = "mysql";
    userID = "root";
    pwd = "password";

    output = "usage: java MariaDB <host> <port> <database> <userID> <password>";
  }

  public static void main(String[] args)
  {
    MariaDB db = new MariaDB();

    db.connect(args.length, args);
  }
}
