package auth.reloaded.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class CommandObject {
  private static ConsoleCommandSender console = Bukkit.getConsoleSender();

  private String name;
  private Integer minArgs;
  private Class<? extends ExecutableCommand> command;

  public CommandObject(String name, Integer minArgs, Class<? extends ExecutableCommand> command) {
    this.name = name;
    this.minArgs = minArgs;
  }

  public static CommandBuilder builder() {
    return new CommandBuilder();
  }

  public String getName() {
    return name;
  }

  public Integer minArgs() {
    return minArgs;
  }

  public Class<? extends ExecutableCommand> getCommand() {
    return command;
  }

  public Class<? extends ExecutableCommand> command() {
    return command;
  }

  public static final class CommandBuilder {
    private String name;
    private Integer minArgs;
    private Class<? extends ExecutableCommand> command;

    // TODO: add permissions, hasToBeOnline and make minArgs optional

    public CommandObject register() {
      if (name == null || minArgs == null || command == null) {
        console.sendMessage(ChatColor.RED + "[AuthReloaded] ERROR -> Invalid command.");
      }

      return new CommandObject(name, minArgs, command);
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
  }
}
