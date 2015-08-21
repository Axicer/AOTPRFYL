package fr.axicer.AOTPRFYL.Events;

import fr.axicer.AOTPRFYL.AOTPRFYLMain;
import fr.axicer.AOTPRFYL.Events.EventsListener.PlayerInventoryClick;
import fr.axicer.AOTPRFYL.Events.EventsListener.PlayerInteractEntity;
import fr.axicer.AOTPRFYL.Events.EventsListener.PlayerTeleport;

public class EventManager {
	public static void registerEvents(AOTPRFYLMain pl){
		pl.getServer().getPluginManager().registerEvents(new PlayerInteractEntity(pl), pl);
		pl.getServer().getPluginManager().registerEvents(new PlayerTeleport(), pl);
		pl.getServer().getPluginManager().registerEvents(new PlayerInventoryClick(), pl);
	}
}
