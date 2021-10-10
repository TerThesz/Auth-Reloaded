package auth.reloaded.commands.auth;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import auth.reloaded.Hash;
import auth.reloaded.commands.ExecutableCommand;
import auth.reloaded.mysql.MySqlFunctions;
import auth.reloaded.other.Utils;

public class RegisterOther implements ExecutableCommand {
  @Override
  public void executeCommand(CommandSender sender, String[] args) {
    Player player = Bukkit.getPlayer(args[0]);

    String password = args[1],
      confirm_password = args[2];

    // TODO: password length

    if (player == null) {
      sender.sendMessage(ChatColor.RED + "This player does not exist.");
      return;
    }

    if (!password.equals(confirm_password)) {
      player.sendMessage(ChatColor.RED + "Passwords don't match!");
      return;
    }

    String password_salt = Hash.salt();
    String password_hash = Hash.hash(password + password_salt);
    String ip_hash = player.isOnline() ? Hash.hash(player.getAddress().toString().replace("/", "").split(":")[0]) : null;

    Boolean isRegistered = MySqlFunctions.registerPlayer(player, password_hash, password_salt, ip_hash, sender);

    if (isRegistered) Utils.authenticated(player);
  }
}