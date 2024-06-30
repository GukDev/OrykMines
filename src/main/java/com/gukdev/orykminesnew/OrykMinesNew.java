package com.gukdev.orykminesnew;

import org.bukkit.plugin.java.JavaPlugin;

public class OrykMinesNew extends JavaPlugin {

    private MineRegionManager mineRegionManager;
    private OrykMinesMenu orykMinesMenu;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("OrykMinesNew has been enabled!");
        saveDefaultConfig();

        mineRegionManager = new MineRegionManager(this);
        orykMinesMenu = new OrykMinesMenu(mineRegionManager);

        getCommand("orykmines").setExecutor(new OrykMinesCommand(orykMinesMenu));
        new MineBlockListener(this, mineRegionManager);
        new PlayerListener(this, mineRegionManager);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("OrykMinesNew has been disabled!");
    }
}
