package auth.reloaded.mysql;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;

import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.Plugin;

public class MySql {
  private Connection connection;
  private String username, host, database, password;
  private Integer port;
  public String table;

  public void start(Plugin plugin) {
    Configuration config = plugin.getConfig();

    username = config.getString("username");
    password = config.getString("password");
    database = config.getString("database");
    host = config.getString("host");
    table = config.getString("table");
    port = config.getInt("port");

    try {
      connect();
      createTable();
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    } catch (SQLException e) {
        e.printStackTrace();
    }
  }

  private void connect() throws SQLException, ClassNotFoundException {
    Class.forName("com.mysql.jdbc.Driver");
    connection = DriverManager.getConnection("jdbc:mysql://" + host+ ":" + port + "/" + database, username, password);
  }

  private void createTable() throws SQLException {
    String statement = "CREATE TABLE IF NOT EXISTS `" + table + "` (" +
      "`uuid` CHAR(36) NOT NULL," +
      "`name` VARCHAR(100) NOT NULL," +
      "`password_hash` VARCHAR(64) NOT NULL," +
      "`password_salt` CHAR(5) NOT NULL," +
      "`ip_hash` VARCHAR(64) NOT NULL," +
      "PRIMARY KEY (`uuid`));";
    PreparedStatement create_table = getConnection().prepareStatement(statement);

    create_table.executeUpdate();
  }

  public void end() {
    if (isConnected()) {
      try {
        connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  public Connection getConnection() {
    return connection;
  }

  public Boolean isConnected() {
    return (connection == null ? false : true);
  }
}