package fr.axicer.AOTPRFYL.Events.EventsListener;

import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import fr.axicer.AOTPRFYL.AOTPRFYLMain;
import fr.axicer.AOTPRFYL.Game.Game;

public class PlayerInteractEntity implements Listener {
	
	AOTPRFYLMain pl;
	
	public PlayerInteractEntity(AOTPRFYLMain pl) {
		this.pl = pl;
	}
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent ev){
		if(ev.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(ev.getClickedBlock().getState() instanceof Sign){
				Sign sign = (Sign) ev.getClickedBlock().getState();
				if(sign.getLine(0).contains("RFYL")){
					if(pl.loadedGame.containsKey(sign.getLine(2))){
						Game game = pl.loadedGame.get(sign.getLine(2));
						if(game.isStarted()){
							ev.getPlayer().sendMessage("La partie a deja commencé !");
						}else{
							ev.getPlayer().teleport(new Location(game.getMap(), 0.5, 100, 0.5));
						}
					}
				}
			}
		}
	}
}
