package fr.axicer.AOTPRFYL.Events;

import fr.axicer.AOTPRFYL.AOTPRFYLMain;
import fr.axicer.AOTPRFYL.Events.EventsListener.PlayerInteractEntity;

public class EventManager {
	public static void registerEvents(AOTPRFYLMain pl){
		pl.getServer().getPluginManager().registerEvents(new PlayerInteractEntity(pl), pl);
	}
}
