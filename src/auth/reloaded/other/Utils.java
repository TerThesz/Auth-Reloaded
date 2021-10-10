package auth.reloaded.other;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import auth.reloaded.AuthReloaded;
import auth.reloaded.Events;

public class Utils {
  public static void authenticated(Player p) {
    if (!AuthReloaded.unauthenticated_players.contains(p.getUniqueId())) return;

    AuthReloaded.unauthenticated_players.remove(p.getUniqueId());
    p.removePotionEffect(PotionEffectType.BLINDNESS);
    Events.protect(p, AuthReloaded.getPlugin(AuthReloaded.class).getConfig().getInt("immunity-duration-after-authentication"));
  }
}
