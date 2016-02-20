package com.leontg77.elements.type.types;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.leontg77.elements.type.Type;

/**
 * Water type class.
 * 
 * @author LeonTG77
 */
public class WaterType extends Type implements Listener {

	public WaterType() {
		super("Water");
	}
	
	@EventHandler
	public void on(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		
		if (!hasType(player)) {
			return;
		}
		
		Block block = event.getTo().getBlock();
		
		if (block == null) {
			return;
		}
		
		if (block.getType() != Material.WATER && block.getType() != Material.STATIONARY_WATER) {
			return;
		}
		
		if (player.hasPotionEffect(PotionEffectType.WATER_BREATHING)) {
			player.removePotionEffect(PotionEffectType.WATER_BREATHING);
		}
		
		if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
			player.removePotionEffect(PotionEffectType.NIGHT_VISION);
		}
		
		if (player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
			player.removePotionEffect(PotionEffectType.INVISIBILITY);
		}

		player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 10, 1));
		player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 10, 1));
		player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 10, 1));
		
		player.setVelocity(player.getLocation().getDirection().multiply(1));
	}
	
	@EventHandler
	public void on(EntityDamageEvent event) {
		Entity entity = event.getEntity();
		
		if (!(entity instanceof Player)) {
			return;
		}
		
		Player player = (Player) entity;
		
		if (!hasType(player)) {
			return;
		}
		
		if (event.getCause() != DamageCause.DROWNING) {
			return;
		}
		
		event.setCancelled(true);
	}
}