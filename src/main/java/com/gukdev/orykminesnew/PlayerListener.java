package com.gukdev.orykminesnew;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerListener implements Listener {

    private MineRegionManager mineRegionManager;
    private Map<UUID, String> playerRegions;
    private JavaPlugin plugin;

    public PlayerListener(JavaPlugin plugin, MineRegionManager mineRegionManager) {
        this.plugin = plugin;
        this.mineRegionManager = mineRegionManager;
        this.playerRegions = new HashMap<>();
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location loc = player.getLocation();
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regionManager = container.get(BukkitAdapter.adapt(loc.getWorld()));
        if (regionManager == null) {
            return;
        }
        ApplicableRegionSet set = regionManager.getApplicableRegions(BukkitAdapter.asBlockVector(loc));

        String currentRegion = null;
        for (ProtectedRegion region : set) {
            if (region.getId().startsWith("mine_")) {
                currentRegion = region.getId();
                break;
            }
        }

        UUID playerId = player.getUniqueId();
        String previousRegion = playerRegions.get(playerId);

        if (currentRegion != null && !currentRegion.equals(previousRegion)) {
            // Player entered a new mine region
            sendEnterMessage(player, currentRegion);
            playerRegions.put(playerId, currentRegion);
        } else if (currentRegion == null && previousRegion != null) {
            // Player left a mine region
            sendLeaveMessage(player, previousRegion);
            playerRegions.remove(playerId);
        }
    }

    private void sendEnterMessage(Player player, String regionId) {
        FileConfiguration config = plugin.getConfig();
        String message = config.getString("mines." + regionId + ".enter_message", "You have entered a mine region!");
        player.sendMessage(message);
    }

    private void sendLeaveMessage(Player player, String regionId) {
        FileConfiguration config = plugin.getConfig();
        String message = config.getString("mines." + regionId + ".leave_message", "You have left the mine region.");
        player.sendMessage(message);
    }
}
