/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ricosw.gungame.listener;

import de.ricosw.gungame.GunGame;
import de.ricosw.gungame.utils.ConfigManger;
import de.ricosw.gungame.utils.Utils;
import java.util.Random;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

/**
 *
 * @author Rico
 */
public class PlayerListener implements Listener {

    Utils util = new Utils();
    ConfigManger cfg = new ConfigManger();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        p.sendMessage(ChatColor.AQUA + "Viel Spaß beim Spielen!");
        e.setJoinMessage(ChatColor.GRAY + "Der Spieler " + ChatColor.AQUA + p.getName() + ChatColor.GRAY + " ist dem Spiel beigetreten!");
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        e.getDrops().clear();
        Player player = e.getEntity().getPlayer();
        Player killer = e.getEntity().getKiller();
        if (killer instanceof Player) {
            e.setDeathMessage(ChatColor.GRAY + "Der Spieler §6" + player.getName() + " §7wurde von §6" + killer.getName() + " §7getötet!");
            player.sendMessage(ChatColor.RED + "Du bist gestorben!");
            util.setRanked(killer, util.getRanked(killer) + 1);
        } else {
            e.setDeathMessage(ChatColor.GRAY + "Der Spieler §6" + player.getName() + " §7ist gestorben!");
        }

        if (util.getRanked(player) != 1) {
            util.setRanked(player, util.getRanked(player) - 1);
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        int i = getRandom(1, 10);

        e.setRespawnLocation(cfg.getPos(i));
        e.getPlayer().teleport(cfg.getPos(i));
        util.setItems(e.getPlayer(), util.getRanked(e.getPlayer()));
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if(util.getMove()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPvP(EntityDamageByEntityEvent e) {
        if(util.getPvP()) {
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

    public int getRandom(int lower, int upper) {
        Random random = new Random();
        return random.nextInt((upper - lower) + 1) + lower;
    }
}
