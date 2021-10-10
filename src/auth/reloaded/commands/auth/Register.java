package auth.reloaded.commands.auth;

import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import auth.reloaded.AuthReloaded;
import auth.reloaded.Hash;
import auth.reloaded.commands.PlayerCommand;
import auth.reloaded.mysql.MySqlFunctions;
import auth.reloaded.other.Utils;

public class Register extends PlayerCommand {
  private final Configuration config = AuthReloaded.getPlugin(AuthReloaded.class).getConfig();
  
  @Override
  public void runCommand(Player player, String[] args) {
    String password = args[0],
      confirm_password = args[1];

    Integer min_length = config.getInt("min-password-length") < 5 ? 5 : config.getInt("min-password-length"),
      max_length = config.getInt("max-password-length") > 30 ? 30 : config.getInt("max-password-length");

    if (password.length() < min_length) {
      player.sendMessage(ChatColor.RED + "Invalid password length. Password must be at least " + min_length + " characters long.");
      return;
    }

    if (password.length() > max_length) {
      player.sendMessage(ChatColor.RED + "Invalid password length. Password can't be longer than " + min_length + " characters long.");
      return;
    }

    if (!password.equals(confirm_password)) {
      player.sendMessage(ChatColor.RED + "Passwords don't match!");
      return;
    }

    String password_salt = Hash.salt();
    String password_hash = Hash.hash(password + password_salt);
    String ip_hash = Hash.hash(player.getAddress().toString().replace("/", "").split(":")[0]);

    Boolean isRegistered = MySqlFunctions.registerPlayer(player, password_hash, password_salt, ip_hash, player);

    if (isRegistered) Utils.authenticated(player);
  }
}
