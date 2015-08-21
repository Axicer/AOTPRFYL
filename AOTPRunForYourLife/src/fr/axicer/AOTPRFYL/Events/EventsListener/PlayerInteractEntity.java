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
import fr.axicer.AOTPRFYL.Utils.InventoryGames;

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
				if(sign.getLine(0).contains("RunForYourLife")){
					Integer playerNumberTotal = 0;
					for(Game g: InventoryGames.getGames()){
						playerNumberTotal = playerNumberTotal+g.getInMapPlayers().size();
					}
					Inventory inv = Bukkit.createInventory(null, 54, "Run for Your Life - Games");
					for(Game game : InventoryGames.getGames()){
						ItemStack item = new ItemStack(Material.BEACON);
						ItemMeta itemMeta = item.getItemMeta();
						itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', game.getDisplayName()));
						ArrayList<String> lores = new ArrayList<String>();
						lores.add(game.getName());
						lores.add(ChatColor.DARK_RED+"Theme: "+ChatColor.GOLD+game.getTheme().getName());
						lores.add(ChatColor.WHITE+""+game.getInMapPlayers().size()+"/"+game.getMaxPlayers());
						itemMeta.setLore(lores);
						item.setItemMeta(itemMeta);
						inv.addItem(item);
					}
					ev.getPlayer().openInventory(inv);
				}
			}
		}
	}
}
