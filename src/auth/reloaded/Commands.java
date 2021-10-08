package auth.reloaded;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class Commands implements CommandExecutor {
  private final Plugin plugin = AuthReloaded.getPlugin(AuthReloaded.class);
  public static String[] cmds = {};

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
    return true;
  }
}
