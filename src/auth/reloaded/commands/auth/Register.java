package auth.reloaded.commands.auth;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import auth.reloaded.Hash;
import auth.reloaded.commands.PlayerCommand;
import auth.reloaded.mysql.MySqlFunctions;
import auth.reloaded.other.Utils;

public class Register extends PlayerCommand {
  @Override
  public void runCommand(Player player, String[] args) {
    String password = args[0],
      confirm_password = args[1];
  
    // TODO: Register others

    if (!password.equals(confirm_password)) {
      player.sendMessage(ChatColor.RED + "Passwords don't match!");
      return;
    }

    String password_salt = Hash.salt();
    String password_hash = Hash.hash(password + password_salt);
    String ip_hash = Hash.hash(player.getAddress().toString().replace("/", "").split(":")[0]);

    Boolean isRegistered = MySqlFunctions.registerPlayer(player, password_hash, password_salt, ip_hash);

    if (isRegistered) Utils.authenticated(player);
  }
}
