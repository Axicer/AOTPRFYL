package fr.axicer.AOTPRFYL.Events.EventsListener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;

import fr.axicer.AOTPRFYL.AOTPRFYLMain;
import fr.axicer.AOTPRFYL.Game.Game;
import fr.axicer.AOTPRFYL.Game.GameStatus;
import fr.axicer.AOTPRFYL.Utils.InventoryGames;

public class BlockBreak implements Listener {
	AOTPRFYLMain pl;
	public BlockBreak(AOTPRFYLMain pl) {
		this.pl = pl;
	}
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBlockBreak(BlockBreakEvent ev){
		for(final Game game: InventoryGames.getGames()){
			if(game.getMap() == ev.getPlayer().getWorld()){
				if(game.getGamestatus().equals(GameStatus.READY) || game.getGamestatus().equals(GameStatus.RELOADING)){
					ev.setCancelled(true);
				}else{
					if(ev.getBlock().getType().equals(Material.BEACON)){
						ev.setCancelled(true);
						ev.getBlock().setType(Material.AIR);
						for(Player player: game.getInMapPlayers()){
							player.sendMessage(ChatColor.BOLD+ev.getPlayer().getDisplayName()+" a cassé le beacon !");
							player.sendMessage(ChatColor.BOLD+"L'equipe "+game.getTeamForPlayer(ev.getPlayer()).getColor()+game.getTeamForPlayer(ev.getPlayer()).getDisplayName()+ChatColor.RESET+ChatColor.BOLD+" a gagné !");
						}
						game.setGamestatus(GameStatus.RELOADING);
						Bukkit.getScheduler().runTaskLater(pl, new BukkitRunnable() {
							@Override
							public void run() {
								game.stop();
							}
						}, 20*10);
					}
				}
			}
		}
	}
}
