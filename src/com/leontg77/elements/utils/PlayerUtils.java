package com.leontg77.elements.utils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 * Player utilities class.
 * <p>
 * Contains methods for broadcasting messages, giving items, getting nearby entities and offline players.
 * 
 * @author LeonTG77
 */
@SuppressWarnings("deprecation")
public class PlayerUtils {
	
	/**
	 * Gets an offline player by a name.
	 * <p>
	 * This is just because of the deprecation on <code>Bukkit.getOfflinePlayer(String)</code> 
	 * 
	 * @param name The name.
	 * @return the offline player.
	 */
	public static OfflinePlayer getOfflinePlayer(final String name) {
		return Bukkit.getOfflinePlayer(name);
	}
	
	/**
	 * Broadcasts a message to everyone online.
	 * 
	 * @param message the message.
	 */
	public static void broadcast(String message) {
		broadcast(message, null);
	}
	
	/**
	 * Broadcasts a message to everyone online with a specific permission.
	 * 
	 * @param message the message.
	 * @param permission the permission.
	 */
	public static void broadcast(String message, final String permission) {
		for (Player online : Bukkit.getOnlinePlayers()) {
			if (permission != null && !online.hasPermission(permission)) {
				continue;
			}
			
			online.sendMessage(message);
		}

		message = message.replaceAll("§l", "");
		message = message.replaceAll("§o", "");
		message = message.replaceAll("§r", "§f");
		message = message.replaceAll("§m", "");
		message = message.replaceAll("§n", "");
		
		Bukkit.getLogger().info(message);
	}
}