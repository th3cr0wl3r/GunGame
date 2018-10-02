/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ricosw.gungame.listener;

import de.ricosw.gungame.utils.ConfigManger;
import de.ricosw.gungame.utils.Utils;
import static org.bukkit.ChatColor.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

/**
 *
 * @author Rico
 */
public class PlayerListener implements Listener {

  private final ConfigManger cfg;

  public PlayerListener(ConfigManger cfg) {
    this.cfg = cfg;
  }
  
  
  @EventHandler
  public void onJoin(PlayerJoinEvent e) {
    final Player p = e.getPlayer();
    p.sendMessage(AQUA + "Viel Spaß beim Spielen!");
    e.setJoinMessage(String.format("%sDer Spieler %s%s%s ist dem Spiel beigetreten!", GRAY, AQUA, p.getName(), GRAY));
  }

  @EventHandler
  public void onDeath(PlayerDeathEvent e) {
    e.getDrops().clear();
    final Player player = e.getEntity().getPlayer();
    final Player killer = e.getEntity().getKiller();
    if (killer instanceof Player) {
      e.setDeathMessage(String.format("%sDer Spieler §6%s§7 wurde von §6%s§7 getötet!", GRAY, player.getName(), killer.getName()));
      player.sendMessage(RED + "Du bist gestorben!");
      killer.sendMessage(GOLD + "Dein Level ist jetzt §2" + Utils.rerank(killer, 1));
    } else {
      e.setDeathMessage(String.format("%sDer Spielder §6%s§7 ist gestorben!", GRAY, player.getName()));
    }

    if (Utils.getRank(player) != 1) {
      killer.sendMessage(GOLD + "Dein Level ist jetzt §2" + Utils.rerank(player, -1));
    }
  }

  @EventHandler
  public void onRespawn(PlayerRespawnEvent e) {
    final int i = Utils.getRandom(1, 10);

    e.setRespawnLocation(cfg.getPos(i));
    e.getPlayer().teleport(cfg.getPos(i));
    
    Utils.setItems(e.getPlayer(), Utils.getRank(e.getPlayer()));
  }

  @EventHandler
  public void onMove(PlayerMoveEvent e) {
    if (!Utils.move) {
      e.setCancelled(true);
    }
  }

  @EventHandler
  public void onPvP(EntityDamageByEntityEvent e) {
    if (!Utils.pvp) {
      e.setCancelled(true);
    }
  }

  @EventHandler
  public void onBuild(BlockBreakEvent e) {
    e.setCancelled(true);
  }

  @EventHandler
  public void onDestroy(BlockPlaceEvent e) {
    e.setCancelled(true);
  }

}
