package com.leontg77.elements.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import com.leontg77.elements.Main;
import com.leontg77.elements.type.Type;
import com.leontg77.elements.type.TypeManager;
import com.leontg77.elements.utils.PlayerUtils;

/**
 * Elements command class.
 * 
 * @author LeonTG77
 */
public class ElementsCommand implements CommandExecutor, TabCompleter {
	private static final String PERMISSION = "elements.manage";
	
	private final TypeManager manager;
	private final Main plugin;
	
	public ElementsCommand(Main plugin, TypeManager manager) {
		this.manager = manager;
		this.plugin = plugin;
	}
	
	private boolean enabled = false;
	
	/**
	 * Check if the scenario is enabled.
	 * 
	 * @return True if it is, false otherwise.
	 */
	public boolean isEnabled() {
		return enabled;
	}
	
	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		if (args.length == 0) {
			sender.sendMessage(Main.PREFIX + "Usage: /elements <info|enable|disable|set|clear>");
			return true;
		}
		
		if (args[0].equalsIgnoreCase("info")) {
			sender.sendMessage(Main.PREFIX + "Plugin creator: §aLeonTG77");
			sender.sendMessage(Main.PREFIX + "Description:");
			sender.sendMessage("§8» §f" + plugin.getDescription().getDescription());
			return true;
		}
		
		if (args[0].equalsIgnoreCase("enable")) {
			if (!sender.hasPermission(PERMISSION)) {
				sender.sendMessage(ChatColor.RED + "You don't have permission.");
				return true;
			}
			
			if (enabled) {
				sender.sendMessage(ChatColor.RED + "Elements is already disabled.");
				return true;
			}
			
			PlayerUtils.broadcast(Main.PREFIX + "Elements has been enabled.");
			enabled = true;
			
			manager.registerListeners();
			return true;
		}

		if (args[0].equalsIgnoreCase("disable")) {
			if (!sender.hasPermission(PERMISSION)) {
				sender.sendMessage(ChatColor.RED + "You don't have permission.");
				return true;
			}
			
			if (!enabled) {
				sender.sendMessage(ChatColor.RED + "Elements is not enabled.");
				return true;
			}

			PlayerUtils.broadcast(Main.PREFIX + "Elements has been disabled.");
			enabled = false;
			
			manager.unregisterListeners();
			
			for (Player online : Bukkit.getOnlinePlayers()) {
				online.setPlayerListName(null);
			}
			return true;
		}

		if (args[0].equalsIgnoreCase("set")) {
			if (!sender.hasPermission(PERMISSION)) {
				sender.sendMessage(ChatColor.RED + "You don't have permission.");
				return true;
			}
			
			if (!enabled) {
				sender.sendMessage(ChatColor.RED + "Elements is not enabled.");
				return true;
			}
			
			for (Player online : Bukkit.getOnlinePlayers()) {
				Type type = getRandomType(online);
				
				if (type == null) {
					online.sendMessage(Main.PREFIX + "Could not give you a type.");
					continue;
				}
				
				online.sendMessage(Main.PREFIX + "You are the §6" + type.getName() + "§7 type.");
			}

			PlayerUtils.broadcast(Main.PREFIX + "Set types.");
			return true;
		}

		if (args[0].equalsIgnoreCase("clear")) {
			if (!sender.hasPermission(PERMISSION)) {
				sender.sendMessage(ChatColor.RED + "You don't have permission.");
				return true;
			}
			
			if (!enabled) {
				sender.sendMessage(ChatColor.RED + "Elements is not enabled.");
				return true;
			}
			
			for (Player online : Bukkit.getOnlinePlayers()) {
				manager.removeType(online);
			}

			PlayerUtils.broadcast(Main.PREFIX + "Cleared all types.");
			return true;
		}
		
		sender.sendMessage(Main.PREFIX + "Usage: /elements <info|enable|disable|set|clear>");
		return true;
	}

	@Override
	public List<String> onTabComplete(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		final List<String> toReturn = new ArrayList<String>();
		final List<String> list = new ArrayList<String>();
		
		if (args.length != 1) {
			return toReturn;
		}
		
		list.add("info");
		
		if (sender.hasPermission(PERMISSION)) {
			list.add("enable");
			list.add("disable");
			list.add("set");
			list.add("clear");
		}

		// make sure to only tab complete what starts with what they 
		// typed or everything if they didn't type anything.
		for (String str : list) {
			if (args[0].isEmpty() || str.startsWith(args[0].toLowerCase())) {
				toReturn.add(str);
			}
		}
		
		return toReturn;
	}
	
	private static final Random RANDOM = new Random();
	
	/**
	 * Get a random Type for the given player.
	 * 
	 * @param player The player getting for.
	 * @return A random type.
	 */
    private Type getRandomType(Player player) {
        List<Type> available = new ArrayList<Type>(manager.getTypes());
        Team team = player.getScoreboard().getEntryTeam(player.getName());

        if (team != null) {
            // Teammates shouldn't have the same effect...
            for (String entry : team.getEntries()) {
            	Type teammateType = manager.getType(Bukkit.getPlayer(entry));
            	
            	if (teammateType != null) {
                    available.remove(teammateType);
            	}
            }
        }

        if (available.size() == 0) {
        	return null;
        }

        Type type = available.get(RANDOM.nextInt(available.size()));
        manager.setType(player, type);

        return type;
    }
}