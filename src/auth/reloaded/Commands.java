package auth.reloaded;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import auth.reloaded.mysql.MySqlFunctions;

public class Commands implements CommandExecutor {
  private final Plugin plugin = AuthReloaded.getPlugin(AuthReloaded.class);
  public static String[] cmds = {"register"};

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
    if (sender instanceof Player) {
      Player p = (Player) sender;
      
      switch (cmd.getName().toLowerCase()) {
        case "register":
          MySqlFunctions.registerPlayer(p, "sdg", "12345", "dsgsdg", "12345");
          break;
      }
    }

    switch(cmd.getName().toLowerCase()) {

    }
    
    return true;
  }
}
