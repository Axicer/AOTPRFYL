package fr.axicer.AOTPRFYL.Events.EventsListener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import fr.axicer.AOTPRFYL.Game.Game;
import fr.axicer.AOTPRFYL.Utils.InventoryGames;

public class PlayerTeleport implements Listener {
	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent ev){
		for(Game game : InventoryGames.getGames()){
			if(ev.getTo().getWorld() == game.getMap()){
				Player player = ev.getPlayer();
				player.setScoreboard(game.getScoreboard());
			}
		}
	}
}
