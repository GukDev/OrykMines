package com.gukdev.orykminesnew;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;

public class BlockRegenerationTask implements Runnable {

    private Block block;
    private Material originalMaterial;
    private JavaPlugin plugin;

    public BlockRegenerationTask(Block block, Material originalMaterial, JavaPlugin plugin) {
        this.block = block;
        this.originalMaterial = originalMaterial;
        this.plugin = plugin;
    }

    @Override
    public void run() {
        if (block.getType() == Material.COBBLESTONE) {
            block.setType(Material.STONE);
            Bukkit.getScheduler().runTaskLater(plugin, new BlockRegenerationTask(block, originalMaterial, plugin), 30 * 20L);
        } else if (block.getType() == Material.STONE) {
            block.setType(originalMaterial);
        }
    }
}
