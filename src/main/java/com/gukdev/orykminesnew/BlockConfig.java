package com.gukdev.orykminesnew;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

class BlockConfig {
    private double chance;
    private Map<Material, Double> drops;
    private boolean soundEffect;

    public BlockConfig() {
        this.drops = new HashMap<>();
    }

    public void loadFromConfig(FileConfiguration config, String path) {
        this.chance = config.getDouble(path + ".chance");
        this.soundEffect = config.getBoolean(path + ".sound_effect");
        for (String dropName : config.getConfigurationSection(path + ".drops").getKeys(false)) {
            Material material = Material.valueOf(dropName);
            double dropChance = config.getDouble(path + ".drops." + dropName + ".chance");
            drops.put(material, dropChance);
        }
    }

    public double getChance() {
        return chance;
    }

    public Map<Material, Double> getDrops() {
        return drops;
    }

    public boolean isSoundEffect() {
        return soundEffect;
    }
}