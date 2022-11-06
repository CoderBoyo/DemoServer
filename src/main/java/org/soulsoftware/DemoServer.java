package org.soulsoftware;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.*;
import org.bukkit.plugin.java.JavaPlugin;

public final class DemoServer extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);

    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.teleport(Bukkit.getWorld("world").getSpawnLocation());
        player.setGameMode(GameMode.CREATIVE);
    }

    @EventHandler
    public void onDamageTaken(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            if (e.getDamage() > 0.1) {
                e.getEntity().teleport(Bukkit.getWorld("world").getSpawnLocation());
                ((Player) e.getEntity()).setHealth(20);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void cmd(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if(!player.isOp()) {
            CommandBlocked cmdEvent = new CommandBlocked(event.getMessage());
            Bukkit.getServer().getPluginManager().callEvent(cmdEvent);
            if(!cmdEvent.isCancelled()) {
                event.setCancelled(true);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cSorry you can't do that!"));
            }
        }

    }



    @EventHandler(priority = EventPriority.HIGHEST)
    public void placeBlock(BlockPlaceEvent event) {
        if(!event.getPlayer().isOp())
            event.setCancelled(true);
    }

    @EventHandler
    public void chat(AsyncPlayerChatEvent event) {
        if(!event.getPlayer().isOp()) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cSorry you can't chat here!"));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerAction(PlayerInteractEvent event) {
        if(!event.getPlayer().isOp())
            event.setCancelled(true);
    }

    @EventHandler
    public void breakBlock(BlockBreakEvent event) {
        if(!event.getPlayer().isOp())
            event.setCancelled(true);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
