package auth.reloaded;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.Plugin;

public class MySql {
  private Connection connection;
  private String username, host, database, password, table;
  private Integer port;

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