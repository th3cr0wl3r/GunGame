/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ricosw.gungame.utils;

import java.util.HashMap;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author Rico
 */
public class Utils {

    private static final HashMap<Player, Integer> ranked = new HashMap<>();
    private static final HashMap<Integer, Boolean> pvpMode = new HashMap<>();
    
    public void setPvP(boolean b) {
        pvpMode.put(1, b);
    }
    
    public boolean getPvP() {
        return pvpMode.get(1);
    }
    
    public void setMove(boolean b) {
        pvpMode.put(2, b);
    }
    
    public boolean getMove() {
        return pvpMode.get(2);
    }
    
    

    public void setRanked(Player p, int i) {
        ranked.put(p, i);
        p.sendMessage(ChatColor.GOLD + "Dein Level ist jetzt §2" + i);
        setItems(p, i);
    }

    public int getRanked(Player p) {
        return ranked.get(p);
    }

    public void setItems(Player p, int i) {
        p.getInventory().clear();
        if (i == 1) {
            p.getInventory().setItem(0, new ItemStack(Material.WOOD_AXE));
            p.getInventory().setItem(1, new ItemStack(Material.FISHING_ROD));
        } else if (i == 2) {
            p.getInventory().setItem(0, new ItemStack(Material.STONE_AXE));
            p.getInventory().setItem(1, new ItemStack(Material.BOW));
            p.getInventory().setItem(8, new ItemStack(Material.ARROW, 16));
        } else if (i == 3) {
            p.getInventory().setItem(0, new ItemStack(Material.BOW));
            p.getInventory().setItem(1, new ItemStack(Material.ARROW, 64));
        } else if (i == 4) {
            p.getInventory().setItem(0, new ItemStack(Material.DIAMOND_AXE));
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 120, 120, false));
        } else if (i == 5) {
            p.getInventory().setItem(0, new ItemStack(Material.STICK));
        } else if (i == 6) {
            p.getInventory().setItem(0, new ItemStack(Material.DIAMOND_SWORD));
        } else if (i == 7) {
            p.getInventory().setItem(0, new ItemStack(Material.POTATO_ITEM));
        } else if (i == 8) {
            p.getInventory().setItem(0, new ItemStack(Material.BAKED_POTATO));
        } else if (i == 9) {
            p.getInventory().setItem(0, new ItemStack(Material.ANVIL));
        } else if (i == 10) {
            p.getInventory().setItem(0, new ItemStack(Material.ROTTEN_FLESH));
        }

    }
}
