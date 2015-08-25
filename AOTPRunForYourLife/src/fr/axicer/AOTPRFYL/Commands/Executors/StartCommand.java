package fr.axicer.AOTPRFYL.Commands.Executors;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.axicer.AOTPRFYL.AOTPRFYLMain;
import fr.axicer.AOTPRFYL.Game.Game;
import fr.axicer.AOTPRFYL.Utils.InventoryGames;

public class StartCommand implements CommandExecutor {

	AOTPRFYLMain pl;
	public StartCommand(AOTPRFYLMain pl) {
		this.pl = pl;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.isOp()){
			if(sender instanceof Player){
				Player player = (Player) sender;
				for(Game game : InventoryGames.getGames()){
					if(game.getMap() == player.getWorld()){
						game.forceStart();
					}
				}
			}else{
				sender.sendMessage(ChatColor.RED+"Il faut etre un joueur pour effectuer cette commande !");
			}
		}else{
			sender.sendMessage(ChatColor.RED+"Tu n'as pas la permission d'executer cette commande !");
		}
		return true;
	}

}
