package com.gukdev.orykminesnew;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class MineRegionManager {

    private JavaPlugin plugin;
    private Map<String, MineRegion> mineRegions;

    public MineRegionManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.mineRegions = new HashMap<>();
        loadMineRegions();
    }

    public void loadMineRegions() {
        FileConfiguration config = plugin.getConfig();
        for (String regionName : config.getConfigurationSection("mines").getKeys(false)) {
            MineRegion mineRegion = new MineRegion(regionName);
            mineRegion.loadFromConfig(config);
            mineRegions.put(regionName, mineRegion);
            plugin.getLogger().info("Loaded mine region: " + regionName);
        }
    }

    public boolean isInMineRegion(Player player) {
        Location loc = player.getLocation();
        RegionContainer container = com.sk89q.worldguard.WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regionManager = container.get(BukkitAdapter.adapt(loc.getWorld()));
        if (regionManager == null) {
            return false;
        }
        ApplicableRegionSet set = regionManager.getApplicableRegions(BukkitAdapter.asBlockVector(loc));
        for (ProtectedRegion region : set) {
            if (mineRegions.containsKey(region.getId())) {
                return true;
            }
        }
        return false;
    }

    public MineRegion getMineRegion(String regionId) {
        return mineRegions.get(regionId);
    }
}
