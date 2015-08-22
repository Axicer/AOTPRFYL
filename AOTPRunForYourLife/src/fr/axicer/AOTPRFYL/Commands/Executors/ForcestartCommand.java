package fr.axicer.AOTPRFYL.Commands.Executors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.axicer.AOTPRFYL.Game.Game;
import fr.axicer.AOTPRFYL.Utils.InventoryGames;

public class ForcestartCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if(sender instanceof Player){
			Player player = (Player) sender;
			for(Game game: InventoryGames.getGames()){
				if(game.getMap() == player.getWorld()){
					game.start();
				}
			}
		}
		return true;
	}

}
