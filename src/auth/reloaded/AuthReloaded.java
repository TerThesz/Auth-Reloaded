package auth.reloaded;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class AuthReloaded extends JavaPlugin {
  private ConsoleCommandSender console = Bukkit.getConsoleSender();
  public static MySql mysql;

  @Override
  public void onEnable() {
    console.sendMessage(ChatColor.GREEN + "[Auth-Reloaded] Enabling plugin.");
    for (String cmd : Commands.cmds)
      this.getCommand(cmd).setExecutor(new Commands());

    Bukkit.getServer().getPluginManager().registerEvents(new Events(), this);

    getConfig().options().copyDefaults(true);
    saveDefaultConfig();
    console.sendMessage(ChatColor.GREEN + "[Auth-Reloaded] Plugin setup finished.");

    mysql.start();
    if (mysql.isConnected())
      console.sendMessage(ChatColor.GREEN + "[Auth-Reloaded] MySQL connected.");

    console.sendMessage(ChatColor.GREEN + "[Auth-Reloaded] Plugin is enabled.");
  }

  @Override
  public void onDisable() {
    console.sendMessage(ChatColor.GREEN + "[Auth-Reloaded] Disabling plugin.");
    saveDefaultConfig();

    mysql.end();
    if (!mysql.isConnected())
      console.sendMessage(ChatColor.GREEN + "[Auth-Reloaded] MySQL disconnected.");

    console.sendMessage(ChatColor.GREEN + "[Auth-Reloaded] Plugin is disabled.");
  }
}
