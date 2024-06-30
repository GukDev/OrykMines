package com.gukdev.orykminesnew;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class OrykMinesMenu implements Listener {

    private Inventory mainMenu;
    private MineRegionManager mineRegionManager;

    public OrykMinesMenu(MineRegionManager mineRegionManager) {
        this.mineRegionManager = mineRegionManager;
        this.mainMenu = Bukkit.createInventory(null, 27, "OrykMines Menu");

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

        Bukkit.getPluginManager().registerEvents(this, mineRegionManager.getPlugin());
    }

    public void openMainMenu(Player player) {
        player.openInventory(mainMenu);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getClickedInventory() == null) return;

        if (event.getView().getTitle().equals("OrykMines Menu")) {
            event.setCancelled(true);
            if (event.getCurrentItem() == null || !event.getCurrentItem().hasItemMeta()) return;

            String itemName = event.getCurrentItem().getItemMeta().getDisplayName();
            if (itemName.equals("Create New Region")) {
                mineRegionManager.createRegion("NewRegion");
                player.sendMessage("New region created!");
                player.closeInventory();
            } else if (itemName.equals("Edit Regions")) {
                player.sendMessage("Edit region clicked!");
                player.closeInventory();
            }
        }
    }
}
