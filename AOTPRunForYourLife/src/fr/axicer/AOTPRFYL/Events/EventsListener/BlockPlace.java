package fr.axicer.AOTPRFYL.Events.EventsListener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import fr.axicer.AOTPRFYL.AOTPRFYLMain;
import fr.axicer.AOTPRFYL.Game.Game;
import fr.axicer.AOTPRFYL.Game.GameStatus;
import fr.axicer.AOTPRFYL.Utils.InventoryGames;

public class BlockPlace implements Listener {
	AOTPRFYLMain pl;
	public BlockPlace(AOTPRFYLMain pl) {
		this.pl = pl;
	}
	@EventHandler
	public void onBreakBlock(BlockBreakEvent ev){
		for(Game game : InventoryGames.getGames()){
			if(game.getMap() == ev.getPlayer().getWorld() && game.getGamestatus().equals(GameStatus.READY)){
				ev.setCancelled(true);
			}
		}
	}
}
