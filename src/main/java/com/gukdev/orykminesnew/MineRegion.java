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

    public String getName() {
        return name;
    }
}
