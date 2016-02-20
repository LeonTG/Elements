package com.leontg77.elements.type.types;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

import com.leontg77.elements.Main;
import com.leontg77.elements.type.Type;

/**
 * Fire type class.
 * 
 * @author LeonTG77
 */
public class FireType extends Type implements Listener {
	private final Set<String> fireball = new HashSet<String>();

	public FireType() {
		super("Fire");
	}
	
	@EventHandler
	public void on(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		if (!hasType(player)) {
			return;
		}
		
		Action action = event.getAction();
		ItemStack item = event.getItem();
		
		if (item == null) {
			return;
		}
		
		if (action != Action.LEFT_CLICK_AIR && action != Action.LEFT_CLICK_BLOCK) {
			return;
		}
		
		if (item.getType() != Material.BOW) {
			return;
		}
		
		if (fireball.contains(player.getName())) {
			player.sendMessage(Main.PREFIX + "You will now shoot arrows.");
			fireball.remove(player.getName());
		} else {
			player.sendMessage(Main.PREFIX + "You will now shoot fireballs.");
			fireball.add(player.getName());
		}
	}
	
	@EventHandler
	public void on(EntityShootBowEvent event) {
		Entity entity = event.getEntity();
		
		if (!(entity instanceof Player)) {
			return;
		}
		
		Player player = (Player) entity;
		
		if (!hasType(player)) {
			return;
		}
		
		if (!fireball.contains(player.getName())) {
			return;
		}
		
		Entity arrow = event.getProjectile();
		Vector vec = arrow.getVelocity();
		
		arrow.remove();
		
		Location loc = arrow.getLocation().toVector().add(arrow.getLocation().getDirection().multiply(3)).toLocation(player.getWorld());
		
		Fireball ball = arrow.getWorld().spawn(loc, Fireball.class);
		ball.setVelocity(vec);
		
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void on(EntityDamageByEntityEvent event) {
		Entity damager = event.getDamager();
		Entity entity = event.getEntity();
		
		if (!(damager instanceof Player) && !(damager instanceof Projectile)) {
			return;
		}
		
		if (damager instanceof Player) {
			Player killer = (Player) damager;
			
			if (!hasType(killer)) {
				return;
			}
			
			switch (entity.getType()) {
			case PIG_ZOMBIE:
			case GHAST:
			case MAGMA_CUBE:
			case BLAZE:
				event.setDamage(100000);
				break;
			default:
				break;
			}
			return;
		}
		
		if (damager instanceof Projectile) {
			Projectile proj = (Projectile) damager;
			ProjectileSource shooter = proj.getShooter();
			
			if (!(shooter instanceof Player)) {
				return;
			}
			
			Player killer = (Player) shooter;
			
			if (!hasType(killer)) {
				return;
			}
			
			switch (entity.getType()) {
			case PIG_ZOMBIE:
			case GHAST:
			case MAGMA_CUBE:
			case BLAZE:
				event.setDamage(100000);
				break;
			default:
				break;
			}
		}
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
		
		DamageCause cause = event.getCause();
		
		if (cause != DamageCause.FIRE_TICK && cause != DamageCause.LAVA && cause != DamageCause.FIRE) {
			return;
		}
		
		event.setCancelled(true);
	}
}