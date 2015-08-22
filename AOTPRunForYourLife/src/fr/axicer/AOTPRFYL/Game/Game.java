package fr.axicer.AOTPRFYL.Game;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
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
	private boolean started;
	private Integer Startingtask;
	private Objective obj;
	private GameTeam teamRed;
	private GameTeam teamBlue;
	private GameTeam teamGreen;
	private GameTeam teamYellow;
	private ArrayList<GameTeam> teams = new ArrayList<GameTeam>();
	
	public Game(String name, String displayName, MapThemes theme, int maxPlayers, AOTPRFYLMain pl){
		this.pl = pl;
		this.setName(name);
		this.setDisplayName(displayName);
		this.setTheme(theme);
		this.setMaxPlayers(maxPlayers);
		this.scoreboard = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
		setObj(scoreboard.registerNewObjective("RFYL", "dummy"));
		setTeamRed(new GameTeam("red", ChatColor.RED+"Rouge", ChatColor.RED, getScoreboard()));
		setTeamBlue(new GameTeam("blue", ChatColor.RED+"Bleu", ChatColor.BLUE, getScoreboard()));
		setTeamGreen(new GameTeam("green", ChatColor.RED+"Vert", ChatColor.GREEN, getScoreboard()));
		setTeamYellow(new GameTeam("yellow", ChatColor.RED+"Jaune", ChatColor.YELLOW, getScoreboard()));
		this.teams.add(teamRed);
		this.teams.add(teamBlue);
		this.teams.add(teamGreen);
		this.teams.add(teamYellow);
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
	public GameTeam getTeamRed() {
		return teamRed;
	}
	public void setTeamRed(GameTeam teamRed) {
		this.teamRed = teamRed;
	}
	public GameTeam getTeamBlue() {
		return teamBlue;
	}
	public void setTeamBlue(GameTeam teamBlue) {
		this.teamBlue = teamBlue;
	}
	public GameTeam getTeamGreen() {
		return teamGreen;
	}
	public void setTeamGreen(GameTeam teamgreen) {
		this.teamGreen = teamgreen;
	}
	public GameTeam getTeamYellow() {
		return teamYellow;
	}
	public void setTeamYellow(GameTeam teamyellow) {
		this.teamYellow = teamyellow;
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
			e.printStackTrace();
		}
	}
	public void reloadMap() throws Exception{
		unloadMap();
		loadMap();
	}
	@SuppressWarnings("deprecation")
	public void startTimer(){
		timeTaskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(pl, new BukkitRunnable() {
			@Override
			public void run() {
				getObj().unregister();
				
				setObj(scoreboard.registerNewObjective("RFYL", "dummy"));
				
				obj.setDisplaySlot(DisplaySlot.SIDEBAR);
				obj.setDisplayName(ChatColor.GREEN+""+ChatColor.BOLD+"RFYL");
				obj.getScore("Timer:    "+formatter.format(getMinutes())+":"+formatter.format(getSeconds())).setScore(1);
				
				if(seconds == 0){
					setSeconds(59);
					minutes--;
				}
				if(minutes == 0){
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
	public static void delete(File file) throws IOException{
	    	if(file.isDirectory()){
	    		if(file.list().length==0){
	    		   file.delete();
	    		}else{
	        	   String files[] = file.list();
	        	   for (String temp : files) {
	        	      File fileDelete = new File(file, temp);
	        	     delete(fileDelete);
	        	   }
	        	   if(file.list().length==0){
	           	     file.delete();
	        	   }
	    		}
	    	}else{
	    		file.delete();
	    	}
	    }
	
	@SuppressWarnings("deprecation")
	public void checkInGamePlayers(){
		if(getInMapPlayers().size() < getMaxPlayers()){
			for(Player pl: getInMapPlayers()){
				pl.sendMessage(getInMapPlayers().size()+"/"+getMaxPlayers()+" joueurs.");
			}
		}else if(getInMapPlayers().size() >= getMaxPlayers()){
			this.Startingtask = Bukkit.getScheduler().scheduleSyncRepeatingTask(getPl(), new BukkitRunnable() {
				int seconds = 10;
				@Override
				public void run() {
					seconds--;
					if(seconds == 0){
						stopStartingTask();
						start();
					}
					for(Player pl: getInMapPlayers()){
						pl.sendMessage("La partie commence dans"+seconds+"secondes");
					}
				}
			}, 20, 20);
		}
	}
	public void stopStartingTask(){
		Bukkit.getScheduler().cancelTask(Startingtask);
	}
	public GameTeam getTeamForPlayer(Player p) {
		for(GameTeam t : teams) {
			if (t.getPlayers().contains(p)) return t;
		}
		return null;
	}
	
	// START AND STOP FUNCTIONS
	
	public void start(){
		this.started = true;
		for(Player player: getInMapPlayers()){
			if(getTeamForPlayer(player) == null){
				player.sendMessage("Tu n'as pas choisi de team, tu as donc une equipe aleatoire !");
				teams.get(new Random().nextInt(teams.size())).addPlayer(player);
			}
			for(GameTeam team: teams){
				for(Player pl: team.getPlayers()){
					pl.getInventory().clear();
					pl.setGameMode(GameMode.SURVIVAL);
					pl.setHealth(20);
					pl.setFoodLevel(20);
					pl.setExhaustion(5F);
				}
				team.teleport(new Location(getMap(),
						pl.getConfig().getDouble("teams."+team.getName()+".x"),
						pl.getConfig().getDouble("teams."+team.getName()+".y"),
						pl.getConfig().getDouble("teams."+team.getName()+".z")));
			}
		}
		startTimer();
	}
	public void stop(){
		this.started = false;
		stopTimer();
	}
}
