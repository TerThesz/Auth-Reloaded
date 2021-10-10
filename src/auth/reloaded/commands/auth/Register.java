package auth.reloaded.commands.auth;

import org.bukkit.Bukkit;
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

    // TODO: password length

    if (!password.equals(confirm_password)) {
      player.sendMessage(ChatColor.RED + "Passwords don't match!");
      return;
    }

    if (args.length >= 3) {
      if (!player.hasPermission("auth-reloaded.register-others")) return;

      Player other_player = Bukkit.getPlayer(args[2]);
      if (other_player == null || !other_player.isOnline()) {
        player.sendMessage(ChatColor.RED + "Specified player doesn't exist or is not online.");
        return;
      }

      String password_salt = Hash.salt();
      String password_hash = Hash.hash(password + password_salt);
      String ip_hash = Hash.hash(other_player.getAddress().toString().replace("/", "").split(":")[0]);

      Boolean isRegistered = MySqlFunctions.registerPlayer(other_player, password_hash, password_salt, ip_hash);

      if (isRegistered) {
        Utils.authenticated(other_player);

        player.sendMessage(ChatColor.GREEN + "Player has been successfully registered.");
      }
    }

    String password_salt = Hash.salt();
    String password_hash = Hash.hash(password + password_salt);
    String ip_hash = Hash.hash(player.getAddress().toString().replace("/", "").split(":")[0]);

    Boolean isRegistered = MySqlFunctions.registerPlayer(player, password_hash, password_salt, ip_hash);

    if (isRegistered) Utils.authenticated(player);
  }
}
