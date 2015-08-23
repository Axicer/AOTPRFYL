package fr.axicer.AOTPRFYL.Events.EventsListener;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.axicer.AOTPRFYL.AOTPRFYLMain;
import fr.axicer.AOTPRFYL.Game.Game;
import fr.axicer.AOTPRFYL.Game.GameTeam;
import fr.axicer.AOTPRFYL.Utils.InventoryGames;

public class PlayerMove implements Listener {
	AOTPRFYLMain pl;
	public PlayerMove(AOTPRFYLMain pl) {
		this.pl = pl;
	}
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent ev){
		for(Game game: InventoryGames.getGames()){
			if(game.getMap() == ev.getPlayer().getWorld() && game.isStarted() && ev.getTo().getY() <= 50){
				GameTeam team = game.getTeamForPlayer(ev.getPlayer());
				Location teamSpawnLoc = new Location(game.getMap(),
						pl.getConfig().getDouble("teamSpawn."+team.getName()+".x"),
						pl.getConfig().getDouble("teamSpawn."+team.getName()+".y"),
						pl.getConfig().getDouble("teamSpawn."+team.getName()+".z"));
				ev.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 5, 50));
				ev.getPlayer().teleport(teamSpawnLoc);
			}
		}
	}
}
