package fr.axicer.AOTPRFYL;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import fr.axicer.AOTPRFYL.Events.EventManager;
import fr.axicer.AOTPRFYL.Game.Game;
import fr.axicer.AOTPRFYL.Game.MapThemes;

public class AOTPRFYLMain extends JavaPlugin{
	
	public boolean loadingSigns = false;
	public HashMap<String,Game> loadedGame = new HashMap<String,Game>();
	
	@Override
	public void onEnable() {
		EventManager.registerEvents(this);
	}
	@Override
	public void onDisable() {
		
	}
	public void loadSigns() {
		loadingSigns = true;
		for(Game game : loadedGame.values()){
			if(game.isStarted()){
				game.stop();
			}
			game.unloadMap();
		}
		loadedGame = new HashMap<String, Game>();
		for(Chunk c : Bukkit.getServer().getWorlds().get(0).getLoadedChunks()){
			for(BlockState state: c.getTileEntities()){
				if(state instanceof Sign){
					Sign sign = (Sign) state;
					if(sign.getLine(0).contains("RFYL")){
						MapThemes theme = MapThemes.getByName(sign.getLine(1));
						String name = sign.getLine(2);
						Game game = new Game(name, "Map "+name, theme, 20, this);
						sign.setLine(3, ChatColor.RED+"Map "+name);
						sign.setLine(0, ChatColor.GREEN+sign.getLine(0));
						sign.update();
						try{
							game.loadMap();
						}catch(Exception e){
							e.printStackTrace();
						}
						loadedGame.put(name,game);
					}
				}
			}
		}
		loadingSigns = false;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if(command.getName().equalsIgnoreCase("sign")){
			loadSigns();
			Bukkit.broadcastMessage(loadedGame.toString());
		}
		return true;
	}
}
