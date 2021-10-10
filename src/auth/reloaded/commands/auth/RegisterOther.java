package auth.reloaded.commands.auth;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.configuration.Configuration;

import auth.reloaded.AuthReloaded;
import auth.reloaded.Hash;
import auth.reloaded.commands.ExecutableCommand;
import auth.reloaded.mysql.MySqlFunctions;
import auth.reloaded.other.Utils;

public class RegisterOther implements ExecutableCommand {
  private final Configuration config = AuthReloaded.getPlugin(AuthReloaded.class).getConfig();

  @Override
  public void executeCommand(CommandSender sender, String[] args) {
    Player player = Bukkit.getPlayer(args[0]);

    String password = args[1],
      confirm_password = args[2];

      Integer min_length = config.getInt("min-password-length") < 5 ? 5 : config.getInt("min-password-length"),
        max_length = config.getInt("max-password-length") > 30 ? 30 : config.getInt("max-password-length");

      if (password.length() < min_length) {
        sender.sendMessage(ChatColor.RED + "Invalid password length. Password must be at least " + min_length + " characters long.");
        return;
      }
  
      if (password.length() > max_length) {
        sender.sendMessage(ChatColor.RED + "Invalid password length. Password can't be longer than " + min_length + " characters long.");
        return;
      }

    if (player == null) {
      sender.sendMessage(ChatColor.RED + "This player does not exist.");
      return;
    }

    if (!password.equals(confirm_password)) {
      sender.sendMessage(ChatColor.RED + "Passwords don't match!");
      return;
    }

    String password_salt = Hash.salt();
    String password_hash = Hash.hash(password + password_salt);
    String ip_hash = player.isOnline() ? Hash.hash(player.getAddress().toString().replace("/", "").split(":")[0]) : null;

    Boolean isRegistered = MySqlFunctions.registerPlayer(player, password_hash, password_salt, ip_hash, sender);

    if (isRegistered) Utils.authenticated(player);
  }
}