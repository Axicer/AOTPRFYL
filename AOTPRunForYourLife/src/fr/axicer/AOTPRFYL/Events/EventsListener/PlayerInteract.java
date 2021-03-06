package fr.axicer.AOTPRFYL.Events.EventsListener;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.axicer.AOTPRFYL.AOTPRFYLMain;
import fr.axicer.AOTPRFYL.Game.Game;
import fr.axicer.AOTPRFYL.Game.GameStatus;
import fr.axicer.AOTPRFYL.Utils.InventoryGames;

public class PlayerInteract implements Listener {
	
	public AOTPRFYLMain pl;
	public ItemStack woolRed;
	public ItemStack woolBlue;
	public ItemStack woolGreen;
	public ItemStack woolYellow;
	
	public PlayerInteract(AOTPRFYLMain pl) {
		this.pl = pl;
		woolRed = new ItemStack(Material.WOOL,1,(byte) 14);
		woolBlue = new ItemStack(Material.WOOL,1,(byte) 11);
		woolGreen = new ItemStack(Material.WOOL,1,(byte) 13);
		woolYellow = new ItemStack(Material.WOOL,1,(byte) 4);
	}
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent ev){
		if(ev.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(ev.getClickedBlock().getState() instanceof Sign){
				Sign sign = (Sign) ev.getClickedBlock().getState();
				if(sign.getLine(0).contains("RunForYourLife") ||
						sign.getLine(1).contains("RunForYourLife") ||
						sign.getLine(2).contains("RunForYourLife") ||
						sign.getLine(3).contains("RunForYourLife")){
					Inventory inv = Bukkit.createInventory(null, 54, "Run for Your Life - Games");
					for(Game game : InventoryGames.getGames()){
						ItemStack item = new ItemStack(Material.BEACON);
						ItemMeta itemMeta = item.getItemMeta();
						itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', game.getDisplayName()));
						ArrayList<String> lores = new ArrayList<String>();
						lores.add(game.getName());
						lores.add(ChatColor.DARK_RED+"Theme: "+ChatColor.GOLD+game.getTheme().getName());
						lores.add(ChatColor.WHITE+""+game.getInMapPlayers().size()+"/"+game.getMaxPlayers());
						if(game.getGamestatus().equals(GameStatus.STARTED)){
							lores.add(ChatColor.RED+"Started !");
						}else if(game.getGamestatus().equals(GameStatus.READY)){
							lores.add(ChatColor.GREEN+"Pr�te !");
						}else{
							lores.add(ChatColor.BLUE+"Redemarrage...");
						}
						itemMeta.setLore(lores);
						item.setItemMeta(itemMeta);
						inv.addItem(item);
					}
					ev.getPlayer().openInventory(inv);
				}
			}
		}
		if(ev.getAction().equals(Action.RIGHT_CLICK_AIR) || ev.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
			for(Game game : InventoryGames.getGames()){
				if(ev.getPlayer().getWorld() == game.getMap()){
					if(ev.getItem() != null){
						if(ev.getItem().equals(woolRed) && game.getTeamRed().getPlayers().size() < 5){
							ev.setCancelled(true);
							game.getTeamBlue().removePlayer(ev.getPlayer());
							game.getTeamGreen().removePlayer(ev.getPlayer());
							game.getTeamYellow().removePlayer(ev.getPlayer());
							game.getTeamRed().addPlayer(ev.getPlayer());
							ev.getPlayer().sendMessage("Tu as rejoint l'equipe rouge !");
						}else if(ev.getItem().equals(woolBlue) && game.getTeamBlue().getPlayers().size() < 5){
							ev.setCancelled(true);
							game.getTeamRed().removePlayer(ev.getPlayer());
							game.getTeamGreen().removePlayer(ev.getPlayer());
							game.getTeamYellow().removePlayer(ev.getPlayer());
							game.getTeamBlue().addPlayer(ev.getPlayer());
								ev.getPlayer().sendMessage("Tu as rejoint l'equipe bleu !");
						}else if(ev.getItem().equals(woolGreen) && game.getTeamGreen().getPlayers().size() < 5){
							ev.setCancelled(true);
							game.getTeamRed().removePlayer(ev.getPlayer());
							game.getTeamYellow().removePlayer(ev.getPlayer());
							game.getTeamBlue().removePlayer(ev.getPlayer());
							game.getTeamGreen().addPlayer(ev.getPlayer());
							ev.getPlayer().sendMessage("Tu as rejoint l'equipe verte !");
						}else if(ev.getItem().equals(woolYellow) && game.getTeamYellow().getPlayers().size() < 5){
							ev.setCancelled(true);
							game.getTeamRed().removePlayer(ev.getPlayer());
							game.getTeamGreen().removePlayer(ev.getPlayer());
							game.getTeamBlue().removePlayer(ev.getPlayer());
							game.getTeamYellow().addPlayer(ev.getPlayer());
							ev.getPlayer().sendMessage("Tu as rejoint l'equipe jaune !");
						}
					}
				}
			}
		}
	}
}
