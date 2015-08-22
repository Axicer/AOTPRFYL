package fr.axicer.AOTPRFYL.Commands.Executors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.axicer.AOTPRFYL.Game.Game;
import fr.axicer.AOTPRFYL.Utils.InventoryGames;

public class LeftCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player){
			Player player = (Player) sender;
			for(Game game: InventoryGames.getGames()){
				if(game.getMap() == player.getWorld()){
					if(game.isStarted()){
						game.getTeamForPlayer(player).removePlayer(player);
						game.checkInGamePlayers();
					}
					player.teleport(Bukkit.getServer().getWorlds().get(0).getSpawnLocation());
					player.sendMessage(ChatColor.GRAY+"Teleporté au spawn.");
					player.getInventory().clear();
					player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
				}
			}
		}else{
			sender.sendMessage("Il faut etre un joueur pour effectuer cette commande !");
		}
		return true;
	}

}
