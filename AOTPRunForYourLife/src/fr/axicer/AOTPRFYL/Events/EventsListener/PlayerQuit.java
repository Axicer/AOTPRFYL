package fr.axicer.AOTPRFYL.Events.EventsListener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.axicer.AOTPRFYL.AOTPRFYLMain;
import fr.axicer.AOTPRFYL.Game.Game;
import fr.axicer.AOTPRFYL.Utils.InventoryGames;

public class PlayerQuit implements Listener {
	AOTPRFYLMain pl;
	public PlayerQuit(AOTPRFYLMain pl) {
		this.pl = pl;
	}
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent ev){
		for(Game game : InventoryGames.getGames()){
			if(game.getMap() == ev.getPlayer().getWorld()){
				game.checkInGamePlayers();
			}
		}
	}
}
