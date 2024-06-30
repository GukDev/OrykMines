package com.gukdev.orykminesnew;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldedit.bukkit.BukkitAdapter;

import java.util.Map;
import java.util.Random;

public class MineBlockListener implements Listener {

    private MineRegionManager mineRegionManager;
    private JavaPlugin plugin;
    private Random random;

    public MineBlockListener(JavaPlugin plugin, MineRegionManager mineRegionManager) {
        this.plugin = plugin;
        this.mineRegionManager = mineRegionManager;
        this.random = new Random();
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        plugin.getLogger().info("Block break event triggered");

        if (mineRegionManager.isInMineRegion(player)) {
            plugin.getLogger().info("Player is in mine region");

            // Get the WorldGuard region from the player's location
            Location loc = player.getLocation();
            String regionId = getRegionId(loc);

            MineRegion mineRegion = mineRegionManager.getMineRegion(regionId);

            if (mineRegion != null) {
                plugin.getLogger().info("Mine region found: " + regionId);
                BlockConfig blockConfig = mineRegion.getBlocks().get(block.getType());

                if (blockConfig != null && random.nextDouble() <= blockConfig.getChance()) {
                    plugin.getLogger().info("Block configuration found with valid chance");

                    event.setDropItems(false);

                    for (Map.Entry<Material, Double> entry : blockConfig.getDrops().entrySet()) {
                        if (random.nextDouble() <= entry.getValue()) {
                            block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(entry.getKey()));
                        }
                    }

                    if (blockConfig.isSoundEffect()) {
                        player.playSound(block.getLocation(), "minecraft:block.anvil.use", 1.0f, 1.0f);
                    }

                    block.setType(Material.COBBLESTONE); // Start the regeneration cycle
                    Bukkit.getScheduler().runTaskLater(plugin, new BlockRegenerationTask(block, block.getType(), plugin), 15 * 20L);
                } else {
                    plugin.getLogger().info("Block configuration not found or chance not met");
                }
            } else {
                plugin.getLogger().info("Mine region not found: " + regionId);
            }
        } else {
            plugin.getLogger().info("Player is not in mine region");
        }
    }

    private String getRegionId(Location loc) {
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regionManager = container.get(BukkitAdapter.adapt(loc.getWorld()));
        if (regionManager == null) {
            return null;
        }
        ApplicableRegionSet set = regionManager.getApplicableRegions(BukkitAdapter.asBlockVector(loc));
        for (ProtectedRegion region : set) {
            if (region.getId().startsWith("mine_")) {
                return region.getId();
            }
        }
        return null;
    }
}
