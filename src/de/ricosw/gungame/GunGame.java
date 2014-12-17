/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ricosw.gungame;

import de.ricosw.gungame.commands.PlayerCommands;
import de.ricosw.gungame.listener.PlayerListener;
import de.ricosw.gungame.utils.ConfigManger;
import de.ricosw.gungame.utils.Utils;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

/**
 *
 * @author Rico
 */
public class GunGame extends JavaPlugin {
    
    Utils util = new Utils();

    @Override
    public void onEnable() {
        util.setMove(false);
        util.setPvP(true);
        System.out.println("Plugin by ricosw");
        System.out.println("(C)opyright by ricosw.de");
        startTimer();

        getCommand("credits").setExecutor(new PlayerCommands());
        getCommand("setLobby").setExecutor(new PlayerCommands());
        getCommand("setPos").setExecutor(new PlayerCommands());
        getCommand("gungame").setExecutor(new PlayerCommands());
        
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);

    }

    @Override
    public void onDisable() {
    }

    int start = 31; //62
    int end = 1200;
    BukkitTask startTimer;
    BukkitTask endTimer;
    BukkitTask checkTimer;

    public void startTimer() {
        System.out.println("MOVE: " + util.getMove());
        System.out.println("PVP: " + util.getPvP());
        startTimer = getServer().getScheduler().runTaskTimer(this, new Runnable() {

            @Override
            public void run() {
                if (Bukkit.getOnlinePlayers().length >= 2) {
                    start--;
                    if (start == 60 || start == 30 || start == 10 || start == 5 || start == 4 || start == 3 || start == 2 || start == 1) {
                        Bukkit.broadcastMessage("§6Spiel startet in §3" + start + " §6Sekunden");
                        if(start == 6) {
                            util.setPvP(true);
                            util.setMove(true);
                        }
                        if(start == 5) {
                            for(Player p : Bukkit.getOnlinePlayers()) {
                                p.teleport(new ConfigManger().getPos(getRandom(1, 10)));
                                util.setItems(p, 1);
                                util.setRanked(p, 1);
                            }
                        }
                        
                    } else if (start == 0) {
                        util.setMove(false);
                        util.setPvP(false);
                        Bukkit.broadcastMessage("§4Lass die Spiele beginnen!");
                        startTimer.cancel();
                        endTimer();
                        checkWinner();
                    }
                }
            }
        }, 0L, 20L);
    }

    public void endTimer() {
        endTimer = getServer().getScheduler().runTaskTimer(this, new Runnable() {

            @Override
            public void run() {
                if (end == 600) {
                    Bukkit.broadcastMessage("§4Noch 10 Minute(n) bis zum Unentschieden!");
                } else if (end == 300) {
                    Bukkit.broadcastMessage("§4Noch 10 Minute(n) bis zum Unentschieden!");
                } else if (end == 60) {
                    Bukkit.broadcastMessage("§4Noch 1 Minute(n) bis zum Unentschieden!");
                } else if (end == 10) {
                    Bukkit.broadcastMessage("§4Noch 10 Sekunde(n) bis zum Unentschieden!");
                } else if (end == 5) {
                    Bukkit.broadcastMessage("§4Es hatt nimmand Gewonnen!\n§4Server startet jetzt neu!");
                } else if (end == 5) {
                    for (Player online : Bukkit.getOnlinePlayers()) {
                        online.kickPlayer("§4Es hatt nimmand Gewonnen!");
                    }
                    Bukkit.shutdown();
                    endTimer.cancel();
                }
            }
        }, 0L, 20L);
    }
    
    int checkTimerEnd = 12;

    public void checkWinner() {
        checkTimer = getServer().getScheduler().runTaskTimer(this, new Runnable() {

            @Override
            public void run() {
                for(Player p : Bukkit.getOnlinePlayers()) {
                    if(util.getRanked(p) == 11) {
                        Bukkit.broadcastMessage("§6Der Spieler §3" + p.getName() + " §6hatt das Spiel gewonnen!");
                        util.setRanked(p, 0);
                        util.setPvP(true);
                        util.setMove(true);
                        checkTimerEnd--;
                    }
                    
                    if(util.getRanked(p) == 0) {
                        checkTimerEnd--;
                    }
                    
                    if(checkTimerEnd == 10) {
                        Bukkit.broadcastMessage("§4Der Server startet neu!");
                    }
                    
                    if(checkTimerEnd == 5) {
                        Bukkit.broadcastMessage("§4Der Server startet jetzt neu!");
                    }
                    
                    if(checkTimerEnd == 0) {
                        p.kickPlayer("Server startet neu!");
                        Bukkit.shutdown();
                    }
                }

            }
        }, 0L, 20L);
    }

    public int getRandom(int lower, int upper) {
        Random random = new Random();
        return random.nextInt((upper - lower) + 1) + lower;
    }

}
