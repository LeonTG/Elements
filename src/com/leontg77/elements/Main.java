package com.leontg77.elements;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.leontg77.elements.commands.ElementsCommand;
import com.leontg77.elements.commands.ElistCommand;
import com.leontg77.elements.type.TypeManager;
import com.leontg77.elements.utils.BlockUtils;

/**
 * Main class of the plugin.
 * 
 * @author LeonTG77
 */
public class Main extends JavaPlugin {
	public static final String PREFIX = "§aElements §8» §7";

	public Main() {
		BlockUtils.setPlugin(this);
	}
	
	@Override
	public void onDisable() {
		final PluginDescriptionFile file = getDescription();
		getLogger().info(file.getName() + " has been disabled.");
	}
	
	@Override
	public void onEnable() {
		final PluginDescriptionFile file = getDescription();
		getLogger().info(file.getName() + " v" + file.getVersion() + " has been enabled.");
		getLogger().info("The plugin is made by LeonTG77.");
		
		final TypeManager manager = new TypeManager(this);
		manager.setup();
		
		final ElementsCommand mainCommand = new ElementsCommand(this, manager);
		final ElistCommand listCommand = new ElistCommand(manager, mainCommand);
		
		// register command.
		getCommand("elements").setExecutor(mainCommand);
		getCommand("elements").setTabCompleter(mainCommand);
		
		getCommand("elist").setExecutor(listCommand);
		getCommand("elist").setTabCompleter(listCommand);
	}
}