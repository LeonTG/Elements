package com.leontg77.elements.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.leontg77.elements.Main;
import com.leontg77.elements.type.Type;
import com.leontg77.elements.type.TypeManager;

/**
 * Elist command class.
 * 
 * @author LeonTG77
 */
public class ElistCommand implements CommandExecutor, TabCompleter {
	private final ElementsCommand mainCmd;
	private final TypeManager manager;
	
	public ElistCommand(TypeManager manager, ElementsCommand mainCmd) {
		this.manager = manager;
		this.mainCmd = mainCmd;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		if (!mainCmd.isEnabled()) {
			sender.sendMessage(Main.PREFIX + "Elements is not enabled.");
			return true;
		}
		
		StringBuilder air = new StringBuilder("");
		StringBuilder earth = new StringBuilder("");
		StringBuilder water = new StringBuilder("");
		StringBuilder fire = new StringBuilder("");
		
		for (Player online : Bukkit.getOnlinePlayers()) {
			Type type = manager.getType(online);
			
			if (type == null) {
				continue;
			}
			
			if (type.getName().equals("Air")) {
				if (air.length() > 0) {
					air.append("§7, §a");
				}
				
				air.append(ChatColor.GREEN + online.getName());
			} 
			else if (type.getName().equals("Air")) {
				if (earth.length() > 0) {
					earth.append("§7, §a");
				}
				
				earth.append(ChatColor.GREEN + online.getName());
			}
			else if (type.getName().equals("Air")) {
				if (water.length() > 0) {
					water.append("§7, §a");
				}
				
				water.append(ChatColor.GREEN + online.getName());
			}
			else if (type.getName().equals("Air")) {
				if (fire.length() > 0) {
					fire.append("§7, §a");
				}
				
				fire.append(ChatColor.GREEN + online.getName());
			}
		}
		
		sender.sendMessage(Main.PREFIX + "List of types:");
		sender.sendMessage("§8» §7Air: " + air.toString().trim());
		sender.sendMessage("§8» §7Earth: " + earth.toString().trim());
		sender.sendMessage("§8» §7Water: " + water.toString().trim());
		sender.sendMessage("§8» §7Fire: " + fire.toString().trim());
		return true;
	}
	
	@Override
	public List<String> onTabComplete(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		// no argurements.
		return null;
	}
}