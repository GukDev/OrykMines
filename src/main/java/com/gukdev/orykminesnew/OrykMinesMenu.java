package com.gukdev.orykminesnew;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class OrykMinesMenu {

    private Inventory mainMenu;
    private MineRegionManager mineRegionManager;

    public OrykMinesMenu(MineRegionManager mineRegionManager) {
        this.mineRegionManager = mineRegionManager;
        mainMenu = Bukkit.createInventory(null, 27, "OrykMines Menu");

        // Add menu items
        ItemStack createRegion = new ItemStack(Material.EMERALD);
        ItemMeta createRegionMeta = createRegion.getItemMeta();
        createRegionMeta.setDisplayName("Create New Region");
        createRegion.setItemMeta(createRegionMeta);
        mainMenu.setItem(11, createRegion);

        ItemStack editRegions = new ItemStack(Material.BOOK);
        ItemMeta editRegionsMeta = editRegions.getItemMeta();
        editRegionsMeta.setDisplayName("Edit Regions");
        editRegions.setItemMeta(editRegionsMeta);
        mainMenu.setItem(15, editRegions);
    }

    public void openMainMenu(Player player) {
        player.openInventory(mainMenu);
    }
}
