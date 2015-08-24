package fr.axicer.AOTPRFYL.Events.EventsListener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.axicer.AOTPRFYL.AOTPRFYLMain;
import fr.axicer.AOTPRFYL.Game.Game;
import fr.axicer.AOTPRFYL.Game.GameStatus;
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
			if(game.getMap() == ev.getPlayer().getWorld() && game.getGamestatus().equals(GameStatus.STARTED) && ev.getTo().getY() <= 50){
				GameTeam team = game.getTeamForPlayer(ev.getPlayer());
				Location teamSpawnLoc = new Location(game.getMap(),
						pl.getConfig().getDouble("teamSpawn."+team.getName()+".x"),
						pl.getConfig().getDouble("teamSpawn."+team.getName()+".y"),
						pl.getConfig().getDouble("teamSpawn."+team.getName()+".z"));
				ev.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20, 50));
				ev.getPlayer().getInventory().clear();
				ev.getPlayer().getInventory().setHelmet(new ItemStack(Material.AIR));
				ev.getPlayer().getInventory().setChestplate(new ItemStack(Material.AIR));
				ev.getPlayer().getInventory().setLeggings(new ItemStack(Material.AIR));
				ev.getPlayer().getInventory().setBoots(new ItemStack(Material.AIR));
				ev.getPlayer().teleport(teamSpawnLoc);
			}
		}
	}
}
