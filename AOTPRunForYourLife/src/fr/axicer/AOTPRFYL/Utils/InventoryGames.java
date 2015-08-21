package fr.axicer.AOTPRFYL.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.configuration.file.YamlConfiguration;

import fr.axicer.AOTPRFYL.AOTPRFYLMain;
import fr.axicer.AOTPRFYL.Game.Game;
import fr.axicer.AOTPRFYL.Game.MapThemes;

public class InventoryGames {
	
	private static ArrayList<Game> games = new ArrayList<Game>();
	private static YamlConfiguration gamesConfig = null;
	private static File gamesConfigFile = null;

	public static ArrayList<Game> getGames() {
		return games;
	}

	public static YamlConfiguration getGamesConfig() {
		return gamesConfig;
	}

	public static void setGamesConfig(YamlConfiguration gamesConfig) {
		InventoryGames.gamesConfig = gamesConfig;
	}

	public static File getGamesConfigFile() {
		return gamesConfigFile;
	}

	public static void setGamesConfigFile(File gamesConfigFile) {
		InventoryGames.gamesConfigFile = gamesConfigFile;
	}
	
	public static void loadGames(AOTPRFYLMain pl) throws Exception{
		setGamesConfigFile(new File(pl.getDataFolder()+"/gamesConfig.yml"));
		if(!getGamesConfigFile().exists()){
			getGamesConfigFile().createNewFile();
			setGamesConfig(YamlConfiguration.loadConfiguration(gamesConfigFile));
			getGamesConfig().set("games.default.displayName", "Default games");
			getGamesConfig().set("games.default.maxPlayers", 20);
			getGamesConfig().set("games.default.theme", MapThemes.MEDIEVAL.getName());
			saveGameConfig();
		}else{
			setGamesConfig(YamlConfiguration.loadConfiguration(gamesConfigFile));
		}
		for(String gamesName : getGamesConfig().getConfigurationSection("games").getKeys(false)){
			String path = "games."+gamesName+".";
			if(getGamesConfig().getString(path+"displayName") != null &&
					getGamesConfig().getInt(path+"maxPlayers") != 0 &&
					getGamesConfig().getString(path+"theme") != null){
				Game game = new Game(gamesName,
						getGamesConfig().getString(path+"displayName"),
						MapThemes.getByName(getGamesConfig().getString(path+"theme")),
						getGamesConfig().getInt(path+"maxPlayers"),
						pl);
				game.loadMap();
				getGames().add(game);
			}else{
				pl.getLogger().info("La game "+gamesName+" n'est pas correctement defini dans la config !");
			}
		}
	}
	public static void saveGameConfig() throws IOException{
		getGamesConfig().save(getGamesConfigFile());
	}
}
