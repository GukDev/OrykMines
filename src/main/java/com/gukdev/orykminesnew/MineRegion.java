package com.gukdev.orykminesnew;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class MineRegion {
    private String name;
    private Map<Material, BlockConfig> blocks;

    public MineRegion(String name) {
        this.name = name;
        this.blocks = new HashMap<>();
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

    public void loadFromConfig(FileConfiguration config) {
        String path = "mines." + name + ".blocks";
        for (String blockName : config.getConfigurationSection(path).getKeys(false)) {
            Material material = Material.valueOf(blockName);
            BlockConfig blockConfig = new BlockConfig();
            blockConfig.loadFromConfig(config, path + "." + blockName);
            blocks.put(material, blockConfig);
        }
    }

    public Map<Material, BlockConfig> getBlocks() {
        return blocks;
    }
}
