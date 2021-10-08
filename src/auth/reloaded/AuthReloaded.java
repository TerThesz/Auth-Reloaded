package auth.reloaded;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import auth.reloaded.mysql.MySql;
import auth.reloaded.mysql.MySqlFunctions;

public class AuthReloaded extends JavaPlugin {
  private ConsoleCommandSender console = Bukkit.getConsoleSender();
  public static MySql mysql = new MySql();

  @Override
  public void onEnable() {
    console.sendMessage(ChatColor.GREEN + "[Auth-Reloaded] Enabling plugin.");

    for (String cmd : Commands.cmds)
      this.getCommand(cmd).setExecutor(new Commands());

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

    console.sendMessage("" + MySqlFunctions.playerHasEntry(Bukkit.getPlayer("4a86e8d3")));

    console.sendMessage(ChatColor.GREEN + "[Auth-Reloaded] Plugin is enabled.");
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
}
