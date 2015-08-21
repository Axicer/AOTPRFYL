package fr.axicer.AOTPRFYL.Game;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import fr.axicer.AOTPRFYL.AOTPRFYLMain;

public class Game {
	
	private String name;
	private String displayName;
	private Integer maxPlayers = 0;
	private MapThemes theme;
	private AOTPRFYLMain pl;
	private World map = null;
	private NumberFormat formatter = new DecimalFormat("00");
	private Integer seconds = 0;
	private Integer minutes = 10;
	private Integer timeTaskID;
	private Scoreboard scoreboard;
	private Objective obj;
	private boolean started;
	
	public Game(String name, String displayName, MapThemes theme, int maxPlayers, AOTPRFYLMain pl){
		this.pl = pl;
		this.setName(name);
		this.setDisplayName(displayName);
		this.setTheme(theme);
		this.setMaxPlayers(maxPlayers);
		this.scoreboard = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
		this.obj = scoreboard.registerNewObjective(ChatColor.GREEN+""+ChatColor.BOLD+"RFYL", "dummy");
	}
	
	// GETTERS AND SETTERS
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getMaxPlayers() {
		return maxPlayers;
	}
	public void setMaxPlayers(Integer maxPlayers) {
		this.maxPlayers = maxPlayers;
	}
	public MapThemes getTheme() {
		return theme;
	}
	public void setTheme(MapThemes theme) {
		this.theme = theme;
	}
	public AOTPRFYLMain getPl() {
		return pl;
	}
	public void setPl(AOTPRFYLMain pl) {
		this.pl = pl;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public World getMap() {
		return map;
	}
	public void setMap(World map) {
		this.map = map;
	}
	public Integer getSeconds() {
		return seconds;
	}
	public void setSeconds(Integer seconds) {
		this.seconds = seconds;
	}
	public Integer getMinutes() {
		return minutes;
	}
	public void setMinutes(Integer minutes) {
		this.minutes = minutes;
	}
	public Integer getTimeTaskID() {
		return timeTaskID;
	}
	public void setTimeTaskID(Integer timeTaskID) {
		this.timeTaskID = timeTaskID;
	}
	public Scoreboard getScoreboard() {
		return scoreboard;
	}
	public void setScoreboard(Scoreboard scoreboard) {
		this.scoreboard = scoreboard;
	}
	public Objective getObj() {
		return obj;
	}
	public void setObj(Objective obj) {
		this.obj = obj;
	}
	public boolean isStarted() {
		return started;
	}
	public void setStarted(boolean started) {
		this.started = started;
	}
	
	// GAME FUNCTIONS

	

	public void loadMap() throws Exception{
		if(pl.getServer().getWorlds().contains(name)){
			throw new Exception("The map has been already initialized !");
		}else if(!(new File(pl.getDataFolder()+theme.getPath()).exists())){
			throw new Exception("The map in config does not exists !");
		}else{
			File toCopyFolder = new File(pl.getDataFolder()+theme.getPath());
			File copiedFolder = new File(name);
			try{
				FileUtils.copyDirectory(toCopyFolder, copiedFolder);
			}catch(IOException ex){
				throw new Exception("Error when copying map folder !");
			}
			WorldCreator wcreator = new WorldCreator(name);
			World world = wcreator.createWorld();
			world.setAutoSave(false);
			this.setMap(world);
		}
	}
	public void unloadMap(){
		Bukkit.unloadWorld(name, false);
		try {
			delete(new File(name));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void reloadMap() throws Exception{
		unloadMap();
		loadMap();
	}
	@SuppressWarnings("deprecation")
	public void startTimer(){
		timeTaskID = Bukkit.getScheduler().scheduleAsyncRepeatingTask(pl, new BukkitRunnable() {
			@Override
			public void run() {
				Objective objectif = null;
				try{
					objectif = getObj();
					objectif.setDisplaySlot(null);
					objectif.unregister();
				}catch(Exception ex){}
				
				objectif = scoreboard.registerNewObjective("RFYL", "dummy");
				setObj(objectif);
				
				getObj().getScore("Timer:    "+formatter.format(getMinutes())+":"+formatter.format(getSeconds())).setScore(0);
				
				if(seconds == -1){
					setSeconds(59);
					minutes--;
				}
				if(minutes == -1){
					setSeconds(0);
					setMinutes(10);
					stopTimer();
					stop();
				}
				seconds--;
			}
		}, 20, 20);
	}
	public void stopTimer(){
		Bukkit.getScheduler().cancelTask(this.getTimeTaskID());
		for(Player player : getInMapPlayers()){
			player.setScoreboard(null);
		}
	}
	public List<Player> getInMapPlayers(){
		return this.getMap().getPlayers();
	}
	public static void delete(File file)
	    	throws IOException{
	 
	    	if(file.isDirectory()){
	 
	    		//directory is empty, then delete it
	    		if(file.list().length==0){
	    			
	    		   file.delete();
	    		   //System.out.println("Directory is deleted : " 
	                                                 //+ file.getAbsolutePath());
	    			
	    		}else{
	    			
	    		   //list all the directory contents
	        	   String files[] = file.list();
	     
	        	   for (String temp : files) {
	        	      //construct the file structure
	        	      File fileDelete = new File(file, temp);
	        		 
	        	      //recursive delete
	        	     delete(fileDelete);
	        	   }
	        		
	        	   //check the directory again, if empty then delete it
	        	   if(file.list().length==0){
	           	     file.delete();
	        	     //System.out.println("Directory is deleted : " 
	                                                  //+ file.getAbsolutePath());
	        	   }
	    		}
	    		
	    	}else{
	    		//if file, then delete it
	    		file.delete();
	    		//System.out.println("File is deleted : " + file.getAbsolutePath());
	    	}
	    }
	
	// START AND STOP FUNCTIONS
	
	public void start(){
		//TODO
	}
	public void stop(){
		//TODO
	}
}
