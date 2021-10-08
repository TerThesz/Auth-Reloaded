package auth.reloaded;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

public class Events implements Listener {
  private final Plugin plugin = AuthReloaded.getPlugin(AuthReloaded.class);
  private static ArrayList<UUID> unauthenticated = new ArrayList<UUID>();

  @EventHandler(priority = EventPriority.HIGH)
  public void onPlayerLogin(PlayerLoginEvent event) {
    setUnauthenticated(event.getPlayer().getUniqueId());
  }

  @EventHandler(priority = EventPriority.HIGH)
  public void onPlayerMove(PlayerMoveEvent event) {
    if (!isUnauthenticated(event.getPlayer().getUniqueId())) event.setCancelled(true);
  }

  public static boolean isUnauthenticated(UUID uuid) {
    return (unauthenticated.contains(uuid));
  }

  public static boolean setUnauthenticated(UUID uuid) {
    if (isUnauthenticated(uuid)) return false;

    unauthenticated.add(uuid);
    return true;
  }

  public static boolean setAuthenticated(UUID uuid) {
    if (!isUnauthenticated(uuid)) return false;

    unauthenticated.remove(uuid);
    return true;
  }
}
