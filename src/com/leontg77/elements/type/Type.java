package com.leontg77.elements.type;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

/**
 * Type super class.
 * 
 * @author LeonTG77
 */
public abstract class Type {
	private final PotionEffect[] effects;
	private final String name;
	
	/**
	 * Type class constructor.
	 * 
	 * @param name The name of the type.
	 * @param effects The effects of the type.
	 */
	public Type(String name, PotionEffect... effects) {
		this.effects = effects;
		this.name = name;
	}
	
	private final List<UUID> players = new ArrayList<UUID>();
	
	/**
	 * Get the name of the type.
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Get the effects for this type.
	 * 
	 * @return Array of effects.
	 */
	public PotionEffect[] getEffects() {
		return effects;
	}
	
	/**
	 * Add the given player to have this type.
	 * 
	 * @param player The player to add.
	 */
	public void addPlayer(Player player) {
		players.add(player.getUniqueId());
	}

	/**
	 * Remove the given player to have this type.
	 * 
	 * @param player The player to remove.
	 */
	public void removePlayer(Player player) {
		players.remove(player.getUniqueId());
	}
	
	/**
	 * Check if the given player has this type.
	 * 
	 * @param player The player checking.
	 * @return True if he has, false otherwise.
	 */
	public boolean hasType(Player player) {
		return players.contains(player.getUniqueId());
	}
}