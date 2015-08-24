package fr.axicer.AOTPRFYL.Events.EventsListener;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import fr.axicer.AOTPRFYL.AOTPRFYLMain;
import fr.axicer.AOTPRFYL.Game.Game;
import fr.axicer.AOTPRFYL.Game.GameStatus;
import fr.axicer.AOTPRFYL.Utils.InventoryGames;

public class EntityDamage implements Listener {
	AOTPRFYLMain pl;
	public EntityDamage(AOTPRFYLMain pl) {
		this.pl = pl;
	}
	@EventHandler
	public void onEntityDamage(EntityDamageEvent ev){
		if(ev.getEntity() instanceof Player){
			Player player = (Player) ev.getEntity();
			for(Game game: InventoryGames.getGames()){
				if(game.getMap() == player.getWorld()){
					if(game.getGamestatus().equals(GameStatus.READY) || game.getGamestatus().equals(GameStatus.RELOADING)){
						ev.setCancelled(true);
					}else{
						if(player.getHealth()-ev.getFinalDamage() < 1){
							ev.setCancelled(true);
							player.setHealth(20);
							player.setGameMode(GameMode.SPECTATOR);
							player.getInventory().clear();
							player.sendMessage(ChatColor.RED+"Tu as perdu ! Tu es mort !");
							player.sendMessage(ChatColor.GRAY+"Fait /left pour quitter la partie.");
							player.teleport(new Location(game.getMap(),
									pl.getConfig().getDouble("waitingSpawn.x"),
									pl.getConfig().getDouble("waitingSpawn.y"),
									pl.getConfig().getDouble("waitingSpawn.z")));
							game.checkInGamePlayers();
						}
					}
				}
			}
		}
	}
}
