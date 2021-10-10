package auth.reloaded.commands.auth;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import auth.reloaded.AuthReloaded;
import auth.reloaded.commands.PlayerCommand;
import auth.reloaded.mysql.MySqlFunctions;

public class Login extends PlayerCommand {
  @Override
  protected void runCommand(Player player, String[] args) {
    String password = args[0];

    if (!AuthReloaded.isUnauthenticated(player.getUniqueId())) {
      player.sendMessage(ChatColor.GREEN + "You are already logged in.");
      return;
    }

    MySqlFunctions.loginPlayer(player, password);
    // TODO: I ended here (It works as intended)
  }
}
