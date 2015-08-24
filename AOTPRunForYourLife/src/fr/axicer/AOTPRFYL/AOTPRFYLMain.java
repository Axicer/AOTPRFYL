package fr.axicer.AOTPRFYL;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import fr.axicer.AOTPRFYL.Commands.CommandManager;
import fr.axicer.AOTPRFYL.Events.EventManager;
import fr.axicer.AOTPRFYL.Game.Game;
import fr.axicer.AOTPRFYL.Utils.InventoryGames;

public class AOTPRFYLMain extends JavaPlugin{
	
	@Override
	public void onEnable() {
		Bukkit.setSpawnRadius(0);
		EventManager.registerEvents(this);
		CommandManager.registerCommands(this);
		saveDefaultConfig();
		try {
			InventoryGames.loadGames(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void onDisable() {
		for(Game game : InventoryGames.getGames()){
			game.unloadMap();
		}
	}
}
