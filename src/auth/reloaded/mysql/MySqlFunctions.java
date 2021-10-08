package auth.reloaded.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.entity.Player;

public class MySqlFunctions {
  private static MySql mysql = new MySql();
  private static String table = mysql.table;

  public static boolean playerHasEntry(Player p) {
    UUID uuid = p.getUniqueId();

    try {
      PreparedStatement hasEntry = mysql.getConnection().prepareStatement("SELECT * FROM `" + table + "` WHERE uuid=?");
      hasEntry.setString(1, uuid.toString());

      ResultSet results = hasEntry.executeQuery();
      if (results.next())
        return true;
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return false;
  }
}
