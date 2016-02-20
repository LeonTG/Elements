package com.leontg77.elements.type.types;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.leontg77.elements.Main;
import com.leontg77.elements.type.Type;
import com.leontg77.elements.utils.BlockUtils;

/**
 * Earth type class.
 * 
 * @author LeonTG77
 */
public class EarthType extends Type implements Listener {
	private final Random rand = new Random();
	
	private final Set<String> gravel = new HashSet<String>();

	public EarthType() {
		super("Earth");
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
		
		if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) {
			return;
		}
		
		// only for HOES (gettit?)
		switch (item.getType()) {
		case WOOD_SPADE:
		case STONE_SPADE:
		case IRON_SPADE:
		case GOLD_SPADE:
		case DIAMOND_SPADE:
			break;
		default:
			return;
		}
		
		if (gravel.contains(player.getName())) {
			player.sendMessage(Main.PREFIX + "You will now only get flint.");
			gravel.remove(player.getName());
		} else {
			player.sendMessage(Main.PREFIX + "You will now only get gravel.");
			gravel.add(player.getName());
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void on(BlockBreakEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlock();
		
		if (!hasType(player)) {
			return;
		}
		
		Location toDrop = block.getLocation().add(0.5, 0.7, 0.5);
		
		switch (block.getType()) {
		case GRAVEL:
			BlockUtils.blockBreak(player, block);
	        BlockUtils.degradeDurabiliy(player);
			
			event.setCancelled(true);
	        block.setType(Material.AIR);
			
			if (gravel.contains(player.getName())) {
	            BlockUtils.dropItem(toDrop, new ItemStack(Material.GRAVEL));
			} else {
	            BlockUtils.dropItem(toDrop, new ItemStack(Material.FLINT));
			}
			break;
		case COAL_ORE:
			if (rand.nextDouble() > 0.25) {
				return;
			}

            BlockUtils.dropItem(toDrop, new ItemStack(Material.COAL, 2));
			BlockUtils.blockBreak(player, block);
	        BlockUtils.degradeDurabiliy(player);
			
			event.setCancelled(true);
	        block.setType(Material.AIR);
	        break;
		case IRON_ORE:
			if (rand.nextDouble() > 0.25) {
				return;
			}

            BlockUtils.dropItem(toDrop, new ItemStack(Material.IRON_ORE, 2));
			BlockUtils.blockBreak(player, block);
	        BlockUtils.degradeDurabiliy(player);
			
			event.setCancelled(true);
	        block.setType(Material.AIR);
	        break;
		case GOLD_ORE:
			if (rand.nextDouble() > 0.25) {
				return;
			}

            BlockUtils.dropItem(toDrop, new ItemStack(Material.GOLD_ORE, 2));
			BlockUtils.blockBreak(player, block);
	        BlockUtils.degradeDurabiliy(player);
			
			event.setCancelled(true);
	        block.setType(Material.AIR);
	        break;
		case DIAMOND_ORE:
			if (rand.nextDouble() > 0.25) {
				return;
			}

            BlockUtils.dropItem(toDrop, new ItemStack(Material.DIAMOND, 2));
			BlockUtils.blockBreak(player, block);
	        BlockUtils.degradeDurabiliy(player);
			
			event.setCancelled(true);
	        block.setType(Material.AIR);
	        break;
		case EMERALD_ORE:
			if (rand.nextDouble() > 0.25) {
				return;
			}

            BlockUtils.dropItem(toDrop, new ItemStack(Material.EMERALD, 2));
			BlockUtils.blockBreak(player, block);
	        BlockUtils.degradeDurabiliy(player);
			
			event.setCancelled(true);
	        block.setType(Material.AIR);
	        break;
		default:
			break;
		}
	}
	
	@EventHandler
	public void on(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		
		if (!hasType(player)) {
			return;
		}
		
		Block block = event.getTo().clone().subtract(0, 1, 0).getBlock();
		
		if (block == null) {
			return;
		}
		
		if (block.getType() != Material.GRASS) {
			return;
		}
		
		player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 1));
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
		
		if (event.getCause() != DamageCause.SUFFOCATION) {
			return;
		}
		
		event.setCancelled(true);
	}
}