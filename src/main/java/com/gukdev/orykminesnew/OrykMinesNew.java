package com.gukdev.orykminesnew;

import org.bukkit.plugin.java.JavaPlugin;

public class OrykMinesNew extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("OrykMinesNew has been enabled!");
        // Load configuration
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("OrykMinesNew has been disabled!");
    }
}
