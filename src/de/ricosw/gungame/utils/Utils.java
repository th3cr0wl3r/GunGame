/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ricosw.gungame.utils;

import java.util.HashMap;
import java.util.Random;
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
  
  private final static Random random = new Random();
  private static final HashMap<Player, Integer> ranked = new HashMap<>();
  public static boolean pvp = false;
  public static boolean move = false;

  private Utils() {}

  public static void reset() {
    ranked.clear();
  }
  
  public static int rerank(final Player p, final int dif) {
    ranked.put(p, getRank(p) + dif);
    return ranked.get(p);
  }
  
  public static int getRank(final Player p) {
    int currentRank = 0;
    if (!ranked.containsKey(p)) {
      currentRank = ranked.get(p);
    }
    return currentRank;
  }
  
  public void setRanked(Player p, int i) {
    ranked.put(p, i);
    p.sendMessage(ChatColor.GOLD + "Dein Level ist jetzt §2" + i);
    setItems(p, i);
  }
  
  public static int getRandom(int lower, int upper) {
    return random.nextInt((upper - lower) + 1) + lower;
  }

  public static void setItems(Player p, int i) {
    p.getInventory().clear();
    switch (i) {
      case 1:
        p.getInventory().setItem(0, new ItemStack(Material.WOOD_AXE));
        p.getInventory().setItem(1, new ItemStack(Material.FISHING_ROD));
        break;
      case 2:
        p.getInventory().setItem(0, new ItemStack(Material.STONE_AXE));
        p.getInventory().setItem(1, new ItemStack(Material.BOW));
        p.getInventory().setItem(8, new ItemStack(Material.ARROW, 16));
        break;
      case 3:
        p.getInventory().setItem(0, new ItemStack(Material.BOW));
        p.getInventory().setItem(1, new ItemStack(Material.ARROW, 64));
        break;
      case 4:
        p.getInventory().setItem(0, new ItemStack(Material.DIAMOND_AXE));
        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 120, 120, false));
        break;
      case 5:
        p.getInventory().setItem(0, new ItemStack(Material.STICK));
        break;
      case 6:
        p.getInventory().setItem(0, new ItemStack(Material.DIAMOND_SWORD));
        break;
      case 7:
        p.getInventory().setItem(0, new ItemStack(Material.POTATO_ITEM));
        break;
      case 8:
        p.getInventory().setItem(0, new ItemStack(Material.BAKED_POTATO));
        break;
      case 9:
        p.getInventory().setItem(0, new ItemStack(Material.ANVIL));
        break;
      case 10:
        p.getInventory().setItem(0, new ItemStack(Material.ROTTEN_FLESH));
        break;
      default:
        break;
    }

  }
}
