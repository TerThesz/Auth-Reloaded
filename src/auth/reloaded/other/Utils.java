package auth.reloaded.other;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import auth.reloaded.AuthReloaded;

public class Utils {
  public static void authenticated(Player p, String message) {
    if (!AuthReloaded.unauthenticated_players.contains(p.getUniqueId())) return;

    AuthReloaded.unauthenticated_players.remove(p.getUniqueId());

    // TODO: All the other stuff

    p.sendMessage(ChatColor.GREEN + message);
  }
}
