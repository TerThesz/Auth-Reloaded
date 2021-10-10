package auth.reloaded;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import auth.reloaded.commands.CommandHandler;
import auth.reloaded.mysql.MySql;

public class AuthReloaded extends JavaPlugin {
  private ConsoleCommandSender console = Bukkit.getConsoleSender();
  public static MySql mysql = new MySql();

  public static List<UUID> unauthenticated_players = new ArrayList<UUID>();

  private CommandHandler commandHandler = new CommandHandler();

  private static Plugin plugin;

  @Override
  public void onEnable() {
    console.sendMessage(ChatColor.GREEN + "[Auth-Reloaded] Enabling plugin.");

    plugin = this;

    Bukkit.getServer().getPluginManager().registerEvents(new Events(), this);

    getConfig().options().copyDefaults(true);
    saveDefaultConfig(); 
    console.sendMessage(ChatColor.GREEN + "[Auth-Reloaded] Plugin setup finished.");

    mysql.start(this);
    if (mysql.isConnected())
      console.sendMessage(ChatColor.GREEN + "[Auth-Reloaded] MySQL connected.");
    else {
      for (Player p : Bukkit.getOnlinePlayers()) {
        if (p.isOp())
          p.sendMessage(ChatColor.RED + "Check console for MySQL errors.");
      }

      if (getConfig().getBoolean("shut-down-server-on-mysql-error"))
        Bukkit.shutdown();
      else
        Bukkit.getServer().getPluginManager().disablePlugin(this);
    }

    console.sendMessage(ChatColor.GREEN + "[Auth-Reloaded] Plugin is enabled.");

    if (Bukkit.getOnlinePlayers().size() > 0) {
      for (Player p : Bukkit.getOnlinePlayers())
        addToUnauthenticated(p.getUniqueId());
    }
  }

  @Override
  public void onDisable() {
    console.sendMessage(ChatColor.GREEN + "[Auth-Reloaded] Disabling plugin.");
    saveDefaultConfig();

    if (mysql.isConnected())
      mysql.end();
    if (!mysql.isConnected())
      console.sendMessage(ChatColor.GREEN + "[Auth-Reloaded] MySQL disconnected.");

    console.sendMessage(ChatColor.GREEN + "[Auth-Reloaded] Plugin is disabled.");
  }

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (CommandHandler.getCommands().contains(cmd.getName().toLowerCase()))
      return commandHandler.handleCommand(sender, cmd, label, args);

    return false;
  }

  public static boolean isUnauthenticated(UUID uuid) {
    return (unauthenticated_players.contains(uuid));
  }

  public static boolean addToUnauthenticated(UUID uuid) {
    if (isUnauthenticated(uuid)) return false;

    unauthenticated_players.add(uuid);
    return true;
  }

  public static boolean removeFromUnauthenticated(UUID uuid) {
    if (!isUnauthenticated(uuid)) return false;

    unauthenticated_players.remove(uuid);
    return true;
  }

  public static Plugin getPluginInstance() {
    return plugin;
  }
}
