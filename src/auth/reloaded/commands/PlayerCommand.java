package auth.reloaded.commands;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public abstract class PlayerCommand implements ExecutableCommand {
  @Override
  public void executeCommand(CommandSender sender, String[] args) {
    if (sender instanceof Player) {
      runCommand((Player) sender, args);
    } else {
      sender.sendMessage(ChatColor.RED + "This command is only for players.");
      return;
    }
  }

  protected abstract void runCommand(Player player, String[] arguments);
}