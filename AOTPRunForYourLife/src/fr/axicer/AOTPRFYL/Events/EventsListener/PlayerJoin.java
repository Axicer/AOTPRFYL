package fr.axicer.AOTPRFYL.Events.EventsListener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import fr.axicer.AOTPRFYL.AOTPRFYLMain;
import fr.axicer.AOTPRFYL.Game.Game;
import fr.axicer.AOTPRFYL.Utils.InventoryGames;

public class PlayerJoin implements Listener {
	AOTPRFYLMain pl;
	public PlayerJoin(AOTPRFYLMain pl) {
		this.pl = pl;
	}
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent ev){
		for(Game game: InventoryGames.getGames()){
			if(game.getMap() == ev.getPlayer().getWorld()){
				ev.getPlayer().getInventory().clear();
				ev.getPlayer().getInventory().setHelmet(new ItemStack(Material.AIR));
				ev.getPlayer().getInventory().setChestplate(new ItemStack(Material.AIR));
				ev.getPlayer().getInventory().setLeggings(new ItemStack(Material.AIR));
				ev.getPlayer().getInventory().setBoots(new ItemStack(Material.AIR));
				ev.getPlayer().teleport(new Location(Bukkit.getWorlds().get(0),
						pl.getConfig().getDouble("worldSpawn.x"),
						pl.getConfig().getDouble("worldSpawn.y"),
						pl.getConfig().getDouble("worldSpawn.z")));
			}
		}
	}
}
