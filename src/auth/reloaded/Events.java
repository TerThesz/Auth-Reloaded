package auth.reloaded;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import auth.reloaded.mysql.MySqlFunctions;

public class Events implements Listener {
  private final Plugin plugin = AuthReloaded.getPlugin(AuthReloaded.class);

  @EventHandler(priority = EventPriority.HIGH)
  public void onPlayerLogin(PlayerLoginEvent event) {
    AuthReloaded.addToUnauthenticated(event.getPlayer().getUniqueId());
  }

  @EventHandler(priority = EventPriority.HIGH)
  public void onPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
    Integer ips = MySqlFunctions.getIps(event.getAddress().toString().replace("/", "").split(":")[0]);
    if (ips >= plugin.getConfig().getInt("max-registers-per-ip"))
      event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ChatColor.RED + "Too many accounts have registered using this IP.");

    if (ips == -1)
      event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ChatColor.RED + "Something went wrong. Please contact the server administrator.");
  }

  @EventHandler(priority = EventPriority.HIGH)
  public void onPlayerMove(PlayerMoveEvent event) {
    if (AuthReloaded.isUnauthenticated(event.getPlayer().getUniqueId())) {
      Location from = event.getFrom();
      Location to = event.getTo();
      
      if (from.getX() != to.getX() && from.getZ() != to.getZ())
        event.setCancelled(true);
    }
  }

  @EventHandler(priority = EventPriority.HIGH)
  public void onChat(AsyncPlayerChatEvent event) {
    if (AuthReloaded.isUnauthenticated(event.getPlayer().getUniqueId())) event.setCancelled(true);
  }

  @EventHandler
  public void preCommand(PlayerCommandPreprocessEvent event) {
    if (AuthReloaded.isUnauthenticated(event.getPlayer().getUniqueId())) {
      if (!plugin.getConfig().getStringList("allowed-commands-for-unauthenticated").contains(event.getMessage().split(" ")[0].replace("/", "").toLowerCase()))
        event.setCancelled(true);
    }
  }

  @EventHandler
  public void onInteract(PlayerInteractEvent event) {
    if (AuthReloaded.isUnauthenticated(event.getPlayer().getUniqueId())) event.setCancelled(true);
  }

  @EventHandler
  public void onPlayerDamage(EntityDamageEvent event) {
    if (event.getEntity() instanceof Player) {
      if (AuthReloaded.isUnauthenticated(((Player) event.getEntity()).getUniqueId()))
        event.setCancelled(true);
      
      // TODO: 5second invincibility after authentication
    }
  }

  @EventHandler
  public void onPlayerLeave(PlayerQuitEvent event) {
    AuthReloaded.removeFromUnauthenticated(event.getPlayer().getUniqueId());
  }
}
