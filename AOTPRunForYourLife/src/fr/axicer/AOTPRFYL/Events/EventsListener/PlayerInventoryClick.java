package fr.axicer.AOTPRFYL.Events.EventsListener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import fr.axicer.AOTPRFYL.AOTPRFYLMain;
import fr.axicer.AOTPRFYL.Game.Game;
import fr.axicer.AOTPRFYL.Utils.InventoryGames;

public class PlayerInventoryClick implements Listener {
	
	AOTPRFYLMain pl;
	
	public PlayerInventoryClick(AOTPRFYLMain pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent ev){
		if(ev.getInventory().getName().equals("Run for Your Life - Games")){
			if(ev.getCurrentItem().getType().equals(Material.BEACON)){
				for(Game game: InventoryGames.getGames()){
					if(game.getName().equals(ev.getCurrentItem().getItemMeta().getLore().get(0))){
						ev.setCancelled(true);
						if(game.isStarted()){
							ev.getWhoClicked().sendMessage("La partie a déja commencé !");
						}else{
							ev.getWhoClicked().teleport(new Location(game.getMap(),
									pl.getConfig().getDouble("waitingSpawn.x"),
									pl.getConfig().getDouble("waitingSpawn.y"),
									pl.getConfig().getDouble("waitingSpawn.z")));
						}	
					}
				}
			}
		}
	}
}
