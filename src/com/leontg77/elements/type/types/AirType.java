package com.leontg77.elements.type.types;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.leontg77.elements.type.Type;
import com.leontg77.elements.utils.NumberUtils;

/**
 * Air type class.
 * 
 * @author LeonTG77
 */
public class AirType extends Type implements Listener {

	public AirType() {
		super("Air", new PotionEffect(PotionEffectType.JUMP, NumberUtils.TICKS_IN_999_DAYS, 2));
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
		case WOOD_HOE:
		case STONE_HOE:
		case IRON_HOE:
		case GOLD_HOE:
		case DIAMOND_HOE:
			break;
		default:
			return;
		}
		
		ItemMeta meta = item.getItemMeta();
		meta.addEnchant(Enchantment.KNOCKBACK, 3, true);
		item.setItemMeta(meta);
		
		event.setCancelled(true);
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
		
		if (event.getCause() != DamageCause.FALL) {
			return;
		}
		
		event.setCancelled(true);
	}
}