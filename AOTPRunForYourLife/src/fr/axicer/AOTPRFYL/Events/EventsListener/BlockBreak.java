package fr.axicer.AOTPRFYL.Events.EventsListener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import fr.axicer.AOTPRFYL.AOTPRFYLMain;
import fr.axicer.AOTPRFYL.Game.Game;
import fr.axicer.AOTPRFYL.Utils.InventoryGames;

public class BlockBreak implements Listener {
	AOTPRFYLMain pl;
	public BlockBreak(AOTPRFYLMain pl) {
		this.pl = pl;
	}
	@EventHandler
	public void onBlockBreak(BlockBreakEvent ev){
		for(final Game game: InventoryGames.getGames()){
			if(game.getMap() == ev.getPlayer().getWorld()){
				if(ev.getBlock().getType().equals(Material.BEACON) && game.isStarted()){
					for(Player player: game.getInMapPlayers()){
						player.sendMessage(ev.getPlayer().getDisplayName()+" a cassé le beacon !");
						player.sendMessage("L'equipe "+game.getTeamForPlayer(ev.getPlayer()).getDisplayName()+" a gagné !");
					}
					Bukkit.getScheduler().runTaskLater(pl, new Runnable() {
						@Override
						public void run() {
							game.stop();
						}
					}, 300);
				}
			}
		}
	}
}
