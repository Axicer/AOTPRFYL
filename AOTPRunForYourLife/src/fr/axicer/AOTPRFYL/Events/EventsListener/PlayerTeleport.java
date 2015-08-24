package fr.axicer.AOTPRFYL.Events.EventsListener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

import fr.axicer.AOTPRFYL.AOTPRFYLMain;
import fr.axicer.AOTPRFYL.Game.Game;
import fr.axicer.AOTPRFYL.Game.GameStatus;
import fr.axicer.AOTPRFYL.Utils.InventoryGames;

public class PlayerTeleport implements Listener {
	
	AOTPRFYLMain pl;
	
	public PlayerTeleport(AOTPRFYLMain pl) {
		this.pl = pl;
	}
	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent ev){
		for(final Game game : InventoryGames.getGames()){
			if(ev.getTo().getWorld() == game.getMap() && game.getGamestatus().equals(GameStatus.READY)){
				if(ev.getFrom().getWorld() != game.getMap()){
					Player player = ev.getPlayer();
					player.setScoreboard(game.getScoreboard());
					player.getInventory().addItem(new ItemStack(Material.WOOL,1,(byte) 14));
					player.getInventory().addItem(new ItemStack(Material.WOOL,1,(byte) 11));
					player.getInventory().addItem(new ItemStack(Material.WOOL,1,(byte) 13));
					player.getInventory().addItem(new ItemStack(Material.WOOL,1,(byte) 4));
					Bukkit.getScheduler().runTaskLater(pl, new Runnable() {
						@Override
						public void run() {
							game.checkInMapPlayers();
						}
					}, 20);
				}
			}
		}
	}
}
