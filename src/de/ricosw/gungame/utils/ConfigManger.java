/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ricosw.gungame.utils;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author Rico
 */
public class ConfigManger {

  private final File file = new File("plugins/GunGame/config.yml");
  private final FileConfiguration cfg = YamlConfiguration.loadConfiguration(this.file);

  public void setLobby(Location loc) {
    this.cfg.set("Lobby.world", loc.getWorld().getName());
    this.cfg.set("Lobby.x", loc.getX());
    this.cfg.set("Lobby.y", loc.getY());
    this.cfg.set("Lobby.z", loc.getZ());
    this.cfg.set("Lobby.yaw", loc.getYaw());
    this.cfg.set("Lobby.pitch", loc.getPitch());
    
    this.cfg.set("LobbySet", true);
    save();
  }

  public Location getLobby() {
    String world = this.cfg.getString("Lobby.world");
    double x = this.cfg.getDouble("Lobby.x");
    double y = this.cfg.getDouble("Lobby.y");
    double z = this.cfg.getDouble("Lobby.z");
    float yaw = this.cfg.getInt("Lobby.yaw");
    float pitch = this.cfg.getInt("Lobby.pitch");

    return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
  }

  public void setPos(Location loc, int i) {
    this.cfg.set("Game.pos.world." + i, loc.getWorld().getName());
    this.cfg.set("Game.pos.x." + i, loc.getX());
    this.cfg.set("Game.pos.y." + i, loc.getY());
    this.cfg.set("Game.pos.z." + i, loc.getZ());
    this.cfg.set("Game.pos.yaw." + i, loc.getYaw());
    this.cfg.set("Game.pos.pitch." + i, loc.getPitch());
    
    this.cfg.set("PosSet", true);
    save();
  }

  public Location getPos(int i) {
    String world = this.cfg.getString("Game.pos.world." + i);
    double x = this.cfg.getDouble("Game.pos.x." + i);
    double y = this.cfg.getDouble("Game.pos.y." + i);
    double z = this.cfg.getDouble("Game.pos.z." + i);
    float yaw = this.cfg.getInt("Game.pos.yaw." + i);
    float pitch = this.cfg.getInt("Game.pos.pitch." + i);

    return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
  }

  public boolean isPosSet() {
    return this.cfg.getBoolean("PosSet");
  }

  public boolean isLobbySet() {
    return this.cfg.getBoolean("LobbySet");
  }

  private void save() {
    try {
      this.cfg.save(file);
    } catch (IOException ex) {
      Logger.getLogger(ConfigManger.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

}
