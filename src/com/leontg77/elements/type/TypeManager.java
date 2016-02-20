package com.leontg77.elements.type;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;

import com.google.common.collect.ImmutableSet;
import com.leontg77.elements.Main;
import com.leontg77.elements.type.types.AirType;
import com.leontg77.elements.type.types.EarthType;
import com.leontg77.elements.type.types.FireType;
import com.leontg77.elements.type.types.WaterType;

/**
 * Type manager class.
 * 
 * @author LeonTG77
 */
public class TypeManager {
	private final Main plugin;

	/**
	 * Type manager class constructor.
	 * 
	 * @param plugin The main class.
	 */
	public TypeManager(Main plugin) {
		this.plugin = plugin;
	}
	
	private final Set<Type> types = new HashSet<Type>();
	
	/**
	 * Set the type of the given player to the given type.
	 * 
	 * @param player The player to set.
	 * @param type The type to set.
	 */
	public void setType(Player player, Type type) {
		type.addPlayer(player);
		
		for (PotionEffect effect : type.getEffects()) {
			if (player.hasPotionEffect(effect.getType())) {
				player.removePotionEffect(effect.getType());
			}
			
			player.addPotionEffect(effect);
		}
	}
	
	/**
	 * Remove the player from his type.
	 * 
	 * @param player The player to remove.
	 */
	public void removeType(Player player) {
		Type type = getType(player);
		
		type.removePlayer(player);
		
		for (PotionEffect effect : type.getEffects()) {
			if (player.hasPotionEffect(effect.getType())) {
				player.removePotionEffect(effect.getType());
			}
		}
	}
	
	/**
	 * Get the type of the player.
	 * 
	 * @param player The player to get for.
	 * @return The type, null if none.
	 */
	public Type getType(Player player) {
		for (Type type : types) {
			if (type.hasType(player)) {
				return type;
			}
		}
		
		return null;
	}

	public Set<Type> getTypes() {
		return ImmutableSet.copyOf(types);
	}
	
	/**
	 * Setup the types.
	 */
	public void setup() {
		types.add(new AirType());
		types.add(new EarthType());
		types.add(new FireType());
		types.add(new WaterType());
	}
	
	/**
	 * Register the listeners for the types.
	 */
	public void registerListeners() {
		for (Type type : types) {
			if (!(type instanceof Listener)) {
				continue;
			}
			
			Bukkit.getPluginManager().registerEvents((Listener) type, plugin);
		}
	}

	/**
	 * Unregister the listeners for the types.
	 */
	public void unregisterListeners() {
		for (Type type : types) {
			if (!(type instanceof Listener)) {
				continue;
			}
			
			HandlerList.unregisterAll((Listener) type);
		}
	}
}