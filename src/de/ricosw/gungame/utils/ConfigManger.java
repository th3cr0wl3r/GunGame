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

    File file = new File("plugins/GunGame/config.yml");
    FileConfiguration cfg = YamlConfiguration.loadConfiguration(this.file);

    public void setLobby(Location loc) {
        double x = loc.getX();
        double y = loc.getY();
        double z = loc.getZ();
        double yaw = loc.getYaw();
        double pitch = loc.getPitch();
        String world = loc.getWorld().getName();

        this.cfg.set("Lobby.world", world);
        this.cfg.set("Lobby.x", x);
        this.cfg.set("Lobby.y", y);
        this.cfg.set("Lobby.z", z);
        this.cfg.set("Lobby.yaw", yaw);
        this.cfg.set("Lobby.pitch", pitch);

        try {
            this.cfg.save(this.file);
        } catch (IOException ex) {
        }
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
        double x = loc.getX();
        double y = loc.getY();
        double z = loc.getZ();
        double yaw = loc.getYaw();
        double pitch = loc.getPitch();
        String world = loc.getWorld().getName();

        this.cfg.set("Game.pos.world." + i, world);
        this.cfg.set("Game.pos.x." + i, x);
        this.cfg.set("Game.pos.y." + i, y);
        this.cfg.set("Game.pos.z." + i, z);
        this.cfg.set("Game.pos.yaw." + i, yaw);
        this.cfg.set("Game.pos.pitch." + i, pitch);

        try {
            this.cfg.save(this.file);
        } catch (IOException ex) {
        }
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
    
    
    public boolean getPosSeted() {
        return this.cfg.getBoolean("PosSet");
    }
    
    public boolean getLobbySeted() {
        return this.cfg.getBoolean("LobbySet");
    }
    
    public void setLobbySeted() {
        this.cfg.set("LobbySet" , true);
        
        try {
            this.cfg.save(file);
        } catch (IOException ex) {
            Logger.getLogger(ConfigManger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setPosSeted() {
        this.cfg.set("PosSet" , true);
        
        try {
            this.cfg.save(file);
        } catch (IOException ex) {
            Logger.getLogger(ConfigManger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
