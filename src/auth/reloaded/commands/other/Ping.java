package auth.reloaded.commands.other;

import org.bukkit.entity.Player;

import auth.reloaded.commands.PlayerCommand;

public class Ping extends PlayerCommand {
  @Override
  protected void runCommand(Player player, String[] arguments) {
    player.sendMessage("Pong!");
  }
  
}
