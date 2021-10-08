package auth.reloaded.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import auth.reloaded.AuthReloaded;

public class MySqlFunctions {
  private static MySql mysql = AuthReloaded.mysql;
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

  public static void registerPlayer(Player p, String password_hash, String password_salt, String ip_hash, String ip_salt) {
    UUID uuid = p.getUniqueId();

    PreparedStatement hasEntry;
    try {
      hasEntry = mysql.getConnection().prepareStatement("SELECT * FROM `" + table + "` WHERE uuid=?");
    
      hasEntry.setString(1, uuid.toString());

      ResultSet results = hasEntry.executeQuery();
      results.next();

      if (playerHasEntry(p)) {
        p.sendMessage(ChatColor.RED + "You are already registered.\nUse " + ChatColor.BOLD + "/login <password>" + ChatColor.RED + " instead.");
        return;
      }

      PreparedStatement create = mysql.getConnection().prepareStatement("INSERT INTO `" + table + "` VALUES (?,?,?,?,?,?)");
      
      create.setString(1, uuid.toString());
      create.setString(2, p.getDisplayName());
      create.setString(3, password_hash);
      create.setString(4, password_salt);
      create.setString(5, ip_hash);
      create.setString(6, ip_salt);

      create.executeUpdate();

      p.sendMessage(ChatColor.GREEN + "You have been successfully registered.");

      // TODO: Remove user from unauthenticated
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static Integer getIps(Player p) {
    PreparedStatement ips;
    try {
      ips = mysql.getConnection().prepareStatement("SELECT name FROM `" + table + "` WHERE ip_hash=?");
      ips.setString(1, "poop");
      ResultSet results = ips.executeQuery();

      if (results.next())
        return results.getInt(1);
      else return 0;
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return -1;
  }
}
