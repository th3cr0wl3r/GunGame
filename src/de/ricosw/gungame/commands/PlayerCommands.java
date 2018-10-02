/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ricosw.gungame.commands;

import de.ricosw.gungame.utils.ConfigManger;
import static org.bukkit.ChatColor.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Rico
 */
public class PlayerCommands implements CommandExecutor {

  enum GunGameCommand {
    Gungame,
    SetLobby,
    Credits,
    SetPos;

    static GunGameCommand find(final Command cmd) {
      for (GunGameCommand ggcmd : values()) {
        if (ggcmd.name().equalsIgnoreCase(cmd.getName())) {
          return ggcmd;
        }
      }
      return null;
    }
  }

  private final ConfigManger cfg;

  public PlayerCommands(ConfigManger cfg) {
    this.cfg = cfg;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
    final Player p = (Player) sender;
    final GunGameCommand ggcmd = GunGameCommand.find(cmd);

    switch (ggcmd) {
      case Credits:
        p.sendMessage(RED + "Plugin Programmiert von ricosw");
        p.sendMessage(AQUA + "Dies ist ein Tutorial Plugin!");
        p.sendMessage(GRAY + "http://youtube.com/RicoswLive");
        break;

      case Gungame:
        if (cfg.isLobbySet() && cfg.isPosSet()) {
          p.sendMessage(GREEN + "Alles Funktioniert und wurde richtig gesetzt!");
        } else if (!cfg.isLobbySet()) {
          p.sendMessage("§4Die Lobby wurde nicht gesetzt!");
        } else if (!cfg.isPosSet()) {
          p.sendMessage("§4Die Positionen wurde nicht gesetzt!");
        }
        break;

      case SetLobby:
        if (p.hasPermission("gungame.*")) {
          cfg.setLobby(p.getLocation());
          p.sendMessage("§4Die Lobby wurde gesetzt!");
        }
        break;

      case SetPos:
        if (p.hasPermission("gungame.*")) {
          cfg.setPos(p.getLocation(), Integer.parseInt(args[0]));
          p.sendMessage("§4Die Position " + args[0] + " wurde gesetzt!");
        }
        break;
    }

    return true;
  }

}
