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
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Rico
 */
public class GunGame extends JavaPlugin implements Runnable {

  enum State {
    Waiting,
    Starting,
    Playing,
    Ending
  }

  private static final int START_COUNTDOWN = 31;
  private static final int END_COUNTDOWN = 10;
  
  private final ConfigManger cfg;
  private final CommandExecutor commands;
  private final Listener listener;
 
  private State current;
  private int countDown;

  public GunGame() {
    cfg = new ConfigManger();
    commands = new PlayerCommands(cfg);
    listener = new PlayerListener(cfg);
  }
  
  @Override
  public void onEnable() {
    Utils.reset();
    Utils.move = true;
    Utils.pvp = false;
    current = State.Waiting;

    System.out.println("Plugin by ricosw");
    System.out.println("(C)opyright by ricosw.de");
    System.out.println("MOVE: " + Utils.move);
    System.out.println("PVP: " + Utils.pvp);
    
    getServer().getScheduler().runTaskTimer(this, this, 0L, 20L);

    getCommand("credits").setExecutor(commands);
    getCommand("setLobby").setExecutor(commands);
    getCommand("setPos").setExecutor(commands);
    getCommand("gungame").setExecutor(commands);

    getServer().getPluginManager().registerEvents(listener, this);
  }

  @Override
  public void onDisable() {
  }

  private void winning(Player p) {
    Bukkit.broadcastMessage("§6Der Spieler §3" + p.getName() + " §6hatt das Spiel gewonnen!");

    Utils.pvp = false;
    Utils.move = false;
    Utils.reset();

    current = State.Ending;
    countDown = END_COUNTDOWN;    
  }
  
  @Override
  public void run() {
    switch (current) {
      case Waiting:
        if (Bukkit.getOnlinePlayers().size() >= 2) {
          current = State.Starting;
          countDown = START_COUNTDOWN;
          Utils.move = false;
          Utils.pvp = false;
        }
        break;

      case Starting:
        switch (countDown--) {
          case 5:
            for (Player p : Bukkit.getOnlinePlayers()) {
              p.teleport(cfg.getPos(Utils.getRandom(1, 10)));
              Utils.setItems(p, Utils.rerank(p, 1));
            }
          case 30: case 10: case 4: case 3: case 2: case 1:
            Bukkit.broadcastMessage("§6Spiel startet in §3" + countDown + "§6 Sekunden");
            break;
            
          case 0:
            Utils.move = true;
            Utils.pvp = true;

            Bukkit.broadcastMessage("§4Lass die Spiele beginnen!");
            current = State.Playing;
            break;
        }
        break;

      case Playing:
        if (Bukkit.getOnlinePlayers().size() < 2) {
          Player max = null;
          for (final Player p : Bukkit.getOnlinePlayers()) {
            if (max == null) {
              max = p;
            }
            if (Utils.getRank(p) > Utils.getRank(max)) {
              max = p;
            }
          }
          if (max == null) {
            // No Players on Server
            Bukkit.shutdown();
            return;
          }
          winning(max);
        }
        for (final Player p : Bukkit.getOnlinePlayers()) {
          if (Utils.getRank(p) >= 11) {
            winning(p);
          }
        }
        break;

      case Ending:
        switch (countDown--) {
          case 10:
            Bukkit.broadcastMessage("§4Der Server startet neu!");
            break;
          case 5:
            Bukkit.broadcastMessage("§4Der Server startet jetzt neu!");
            break;
          case 0:
            for (final Player p : Bukkit.getOnlinePlayers()) {
              p.kickPlayer("Server startet neu!");
            }
            Bukkit.shutdown();
        }
        break;
    }
  }
}
