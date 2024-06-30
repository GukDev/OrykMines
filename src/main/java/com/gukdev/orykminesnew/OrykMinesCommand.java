package com.gukdev.orykminesnew;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OrykMinesCommand implements CommandExecutor {

    private OrykMinesMenu orykMinesMenu;
    private MineRegionManager mineRegionManager;

    public OrykMinesCommand(OrykMinesMenu orykMinesMenu, MineRegionManager mineRegionManager) {
        this.orykMinesMenu = orykMinesMenu;
        this.mineRegionManager = mineRegionManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("orykmines.admin")) {
                if (args.length > 0 && args[0].equalsIgnoreCase("create")) {
                    if (args.length > 1) {
                        String regionName = args[1];
                        mineRegionManager.createRegion(regionName);
                        player.sendMessage("Region " + regionName + " created!");
                    } else {
                        player.sendMessage("Usage: /orykmines create <regionName>");
                    }
                } else {
                    orykMinesMenu.openMainMenu(player);
                }
                return true;
            } else {
                player.sendMessage("You do not have permission to use this command.");
                return false;
            }
        }
        return false;
    }
}
