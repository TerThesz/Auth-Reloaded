package auth.reloaded.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class CommandObject {
  private static ConsoleCommandSender console = Bukkit.getConsoleSender();

  private String name;
    private Integer minArgs;
  private Class<? extends ExecutableCommand> command;
  private String permission;

  // TODO: status messages

  public CommandObject(String name, Integer minArgs, Class<? extends ExecutableCommand> command, String permission) {
    this.name = name;
    this.minArgs = minArgs;
    this.command = command;
    this.permission = permission;
  }

  public String getName() {
    return name;
  }

  public Integer getMinArgs() {
    return minArgs;
  }

  public Class<? extends ExecutableCommand> getCommand() {
    return command;
  }

  public String getPermission() {
    return permission;
  }

  public static CommandBuilder builder() {
    return new CommandBuilder();
  }

  public static final class CommandBuilder {
    private String name;
    private Integer minArgs;
    private Class<? extends ExecutableCommand> command;
    private String permission;

    public CommandObject register() {
      if (name == null || command == null) {
        console.sendMessage(ChatColor.RED + "[AuthReloaded] ERROR -> Invalid command.");
      }

      return new CommandObject(name, minArgs, command, permission);
    }

    public CommandBuilder name(String name) {
      this.name = name;
      return this;
    }

    public CommandBuilder minArgs(Integer minArgs) {
      this.minArgs = minArgs;
      return this;
    }

    public CommandBuilder command(Class<? extends ExecutableCommand> command) {
      this.command = command;
      return this;
    }

    public CommandBuilder permission(String permission) {
      this.permission = permission;
      return this;
    }
  }
}
