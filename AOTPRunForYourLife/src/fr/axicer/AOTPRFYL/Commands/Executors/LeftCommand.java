package fr.axicer.AOTPRFYL.Commands.Executors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import fr.axicer.AOTPRFYL.AOTPRFYLMain;
import fr.axicer.AOTPRFYL.Game.Game;
import fr.axicer.AOTPRFYL.Game.GameStatus;
import fr.axicer.AOTPRFYL.Utils.InventoryGames;

public class LeftCommand implements CommandExecutor {
	AOTPRFYLMain pl;
	public LeftCommand(AOTPRFYLMain pl) {
		this.pl = pl;
	}
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player){
			Player player = (Player) sender;
			for(final Game game: InventoryGames.getGames()){
				if(game.getMap() == player.getWorld()){
					if(game.getGamestatus().equals(GameStatus.READY)){
						Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new BukkitRunnable() {
							@Override
							public void run() {
								game.checkInMapPlayers();
							}
						}, 20);
					}
					if(game.getTeamForPlayer(player) != null){
						game.getTeamForPlayer(player).removePlayer(player);
					}
					player.teleport(new Location(Bukkit.getWorlds().get(0),
							pl.getConfig().getDouble("worldSpawn.x"),
							pl.getConfig().getDouble("worldSpawn.y"),
							pl.getConfig().getDouble("worldSpawn.z")));
					player.sendMessage(ChatColor.GRAY+"Teleporté au spawn.");
					player.getInventory().clear();
					player.getInventory().setHelmet(new ItemStack(Material.AIR));
					player.getInventory().setChestplate(new ItemStack(Material.AIR));
					player.getInventory().setLeggings(new ItemStack(Material.AIR));
					player.getInventory().setBoots(new ItemStack(Material.AIR));
					player.setGameMode(GameMode.SURVIVAL);
					player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
				}
			}
		}else{
			sender.sendMessage("Il faut etre un joueur pour effectuer cette commande !");
		}
		return true;
	}

}
