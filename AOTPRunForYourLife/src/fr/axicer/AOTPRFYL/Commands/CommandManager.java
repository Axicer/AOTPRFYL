package fr.axicer.AOTPRFYL.Commands;

import fr.axicer.AOTPRFYL.AOTPRFYLMain;
import fr.axicer.AOTPRFYL.Commands.Executors.LeftCommand;
import fr.axicer.AOTPRFYL.Commands.Executors.StartCommand;

public class CommandManager {
	public static void registerCommands(AOTPRFYLMain pl){
		pl.getCommand("left").setExecutor(new LeftCommand(pl));		
		pl.getCommand("start").setExecutor(new StartCommand(pl));
	}
}
