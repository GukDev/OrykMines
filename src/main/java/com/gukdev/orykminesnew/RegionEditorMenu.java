package com.gukdev.orykminesnew;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RegionEditorMenu {

    private MineRegion mineRegion;
    private Inventory editorMenu;

    public RegionEditorMenu(MineRegion mineRegion) {
        this.mineRegion = mineRegion;
        this.editorMenu = Bukkit.createInventory(null, 54, "Edit Region: " + mineRegion.getName());

        // Add menu items for blocks
        int slot = 0;
        for (Material material : mineRegion.getBlocks().keySet()) {
            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("Edit " + material.toString());
            item.setItemMeta(meta);
            editorMenu.setItem(slot++, item);
        }

        // Add additional menu items as needed
    }

    public void openEditorMenu(Player player) {
        player.openInventory(editorMenu);
    }
}
