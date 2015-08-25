package fr.axicer.AOTPRFYL.Events;

import fr.axicer.AOTPRFYL.AOTPRFYLMain;
import fr.axicer.AOTPRFYL.Events.EventsListener.BlockBreak;
import fr.axicer.AOTPRFYL.Events.EventsListener.BlockPlace;
import fr.axicer.AOTPRFYL.Events.EventsListener.EntityDamage;
import fr.axicer.AOTPRFYL.Events.EventsListener.PlayerChat;
import fr.axicer.AOTPRFYL.Events.EventsListener.PlayerInventoryClick;
import fr.axicer.AOTPRFYL.Events.EventsListener.PlayerInteract;
import fr.axicer.AOTPRFYL.Events.EventsListener.PlayerJoin;
import fr.axicer.AOTPRFYL.Events.EventsListener.PlayerMove;
import fr.axicer.AOTPRFYL.Events.EventsListener.PlayerQuit;
import fr.axicer.AOTPRFYL.Events.EventsListener.PlayerTeleport;

public class EventManager {
	public static void registerEvents(AOTPRFYLMain pl){
		pl.getServer().getPluginManager().registerEvents(new PlayerInteract(pl), pl);
		pl.getServer().getPluginManager().registerEvents(new PlayerTeleport(pl), pl);
		pl.getServer().getPluginManager().registerEvents(new PlayerInventoryClick(pl), pl);
		pl.getServer().getPluginManager().registerEvents(new EntityDamage(pl), pl);
		pl.getServer().getPluginManager().registerEvents(new PlayerQuit(pl), pl);
		pl.getServer().getPluginManager().registerEvents(new PlayerJoin(pl), pl);
		pl.getServer().getPluginManager().registerEvents(new BlockBreak(pl), pl);
		pl.getServer().getPluginManager().registerEvents(new BlockPlace(pl), pl);
		pl.getServer().getPluginManager().registerEvents(new PlayerMove(pl), pl);
		pl.getServer().getPluginManager().registerEvents(new PlayerChat(pl), pl);
	}
}
