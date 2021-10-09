package auth.reloaded.commands;

import org.bukkit.command.CommandSender;

public interface ExecutableCommand {
  public void executeCommand (CommandSender sender, String[] args);
}