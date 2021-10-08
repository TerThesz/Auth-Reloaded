package auth.reloaded;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class AuthReloaded extends JavaPlugin {
  private ConsoleCommandSender console = Bukkit.getConsoleSender();
  
  @Override
  public void onEnable() {
    for (String cmd : Commands.cmds)
      this.getCommand(cmd).setExecutor(new Commands());

    Bukkit.getServer().getPluginManager().registerEvents(new Events(), this);
  }

  @Override
  public void onDisable() {

  }
}
