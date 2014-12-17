/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ricosw.gungame.commands;

import de.ricosw.gungame.utils.ConfigManger;
import de.ricosw.gungame.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Rico
 */
public class PlayerCommands implements CommandExecutor {
    
    Utils util = new Utils();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        Player p = (Player) sender;
        ConfigManger cfg = new ConfigManger();

        if (cmd.getName().equalsIgnoreCase("credits")) {
            p.sendMessage(ChatColor.RED + "Plugin Programmiert von ricosw");
            p.sendMessage(ChatColor.AQUA + "Dies ist ein Tutorial Plugin!");
            p.sendMessage(ChatColor.GRAY + "http://youtube.com/RicoswLive");
        }

        if (cmd.getName().equalsIgnoreCase("gungame")) {
            if (cfg.getLobbySeted() && cfg.getPosSeted()) {
                p.sendMessage(ChatColor.GREEN + "Alles Funktioniert und wurde richtig gesetzt!");
            } else if (cfg.getLobbySeted() == false) {
                p.sendMessage("§4Die Lobby wurde nicht gesetzt!");

            } else if (cfg.getPosSeted() == false) {
                p.sendMessage("§4Die Positionen wurde nicht gesetzt!");
            }
        }

        if (cmd.getName().equalsIgnoreCase("setLobby")) {
            if (p.hasPermission("gungame.*")) {
                cfg.setLobby(p.getLocation());
                p.sendMessage("§4Die Lobby wurde gesetzt!");
                cfg.setLobbySeted();
            }
        }

        if (cmd.getName().equalsIgnoreCase("setPos")) {
            if (p.hasPermission("gungame.*")) {
                cfg.setPos(p.getLocation(), Integer.parseInt(args[0]));
                p.sendMessage("§4Die Position " + args[0] + " wurde gesetzt!");
                cfg.setPosSeted();

            }
        }

        return true;
    }

}
