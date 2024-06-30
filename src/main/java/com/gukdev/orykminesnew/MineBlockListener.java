package com.gukdev.orykminesnew;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

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
        block.setType(Material.COBBLESTONE); // Start the regeneration cycle
        Bukkit.getScheduler().runTaskLater(plugin, new BlockRegenerationTask(block, block.getType(), plugin), 15 * 20L);
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if (mineRegionManager.isInMineRegion(player)) {
            MineRegion mineRegion = mineRegionManager.getMineRegion(block.getLocation().toString());
            if (mineRegion != null) {
                BlockConfig blockConfig = mineRegion.getBlocks().get(block.getType());
                if (blockConfig != null && random.nextDouble() <= blockConfig.getChance()) {
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
                }
            }
        }
    }
}
