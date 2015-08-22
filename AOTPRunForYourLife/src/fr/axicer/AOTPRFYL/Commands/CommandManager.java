package fr.axicer.AOTPRFYL.Commands;

import fr.axicer.AOTPRFYL.AOTPRFYLMain;
import fr.axicer.AOTPRFYL.Commands.Executors.ForcestartCommand;
import fr.axicer.AOTPRFYL.Commands.Executors.LeftCommand;

public class CommandManager {
	public static void registerCommands(AOTPRFYLMain pl){
		pl.getCommand("left").setExecutor(new LeftCommand(pl));
		pl.getCommand("forcestart").setExecutor(new ForcestartCommand());
	}
}
