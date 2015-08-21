package fr.axicer.AOTPRFYL;

import org.bukkit.plugin.java.JavaPlugin;

import fr.axicer.AOTPRFYL.Commands.CommandManager;
import fr.axicer.AOTPRFYL.Events.EventManager;
import fr.axicer.AOTPRFYL.Utils.InventoryGames;

public class AOTPRFYLMain extends JavaPlugin{
	
	@Override
	public void onEnable() {
		EventManager.registerEvents(this);
		CommandManager.registerCommands(this);
		try {
			InventoryGames.loadGames(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void onDisable() {
		
	}
}
