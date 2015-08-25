package fr.axicer.AOTPRFYL.Events.EventsListener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.axicer.AOTPRFYL.AOTPRFYLMain;
import fr.axicer.AOTPRFYL.Game.Game;
import fr.axicer.AOTPRFYL.Utils.InventoryGames;

public class PlayerChat implements Listener {
	AOTPRFYLMain pl;
	public PlayerChat(AOTPRFYLMain pl) {
		this.pl = pl;
	}
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent ev){
		for(Game game : InventoryGames.getGames()){
			if(game.getMap() == ev.getPlayer().getWorld()){
				ev.setCancelled(true);
				if(ev.getMessage().startsWith("!")){
					for(Player pl : game.getInMapPlayers()){
						pl.sendMessage("["+ChatColor.GREEN+""+ChatColor.BOLD+"RFYL"+ChatColor.RESET+"] "+ev.getPlayer().getDisplayName()+ChatColor.RESET+" -> "+ev.getMessage());
					}
				}else{
					for(Player pl : game.getTeamForPlayer(ev.getPlayer()).getPlayers()){
						pl.sendMessage("["+ChatColor.GREEN+""+ChatColor.BOLD+"RFYL"+ChatColor.RESET+"] "+ev.getPlayer().getDisplayName()+ChatColor.RESET+" -> "+ev.getMessage());
					}
				}
			}
		}
	}
}