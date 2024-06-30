package com.gukdev.orykminesnew;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OrykMinesCommand implements CommandExecutor {

    private OrykMinesMenu orykMinesMenu;

    public OrykMinesCommand(OrykMinesMenu orykMinesMenu) {
        this.orykMinesMenu = orykMinesMenu;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("orykmines.admin")) {
                orykMinesMenu.openMainMenu(player);
                return true;
            } else {
                player.sendMessage("You do not have permission to use this command.");
                return false;
            }
        }
        return false;
    }
}
