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
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

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
	private Integer Startingtask = 0;
	private Objective obj;
	private GameTeam teamRed;
	private GameTeam teamBlue;
	private GameTeam teamGreen;
	private GameTeam teamYellow;
	private ArrayList<GameTeam> teams = new ArrayList<GameTeam>();
	private GameStatus gamestatus;
	private ArrayList<Player> inGamePlayers = new ArrayList<Player>();
	public boolean TaskTimerstarted = false;
	
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
		setGamestatus(GameStatus.READY);
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
	public ArrayList<Player> getInGamePlayers() {
		return inGamePlayers;
	}
	public void setInGamePlayers(ArrayList<Player> inGamePlayers) {
		this.inGamePlayers = inGamePlayers;
	}
	public GameStatus getGamestatus() {
		return gamestatus;
	}
	public void setGamestatus(GameStatus gamestatus) {
		this.gamestatus = gamestatus;
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
					for(Player player : getInMapPlayers()){
						player.sendMessage("Le timer est arrivé a la fin !");
					}
					Bukkit.getScheduler().runTaskLater(pl, new BukkitRunnable() {
						@Override
						public void run() {
							stop();
						}
					}, 20*5);
				}
				seconds--;
			}
		}, 20, 20);
	}
	public void stopTimer(){
		Bukkit.getScheduler().cancelTask(this.getTimeTaskID());
		for(Player player : getInMapPlayers()){
			player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
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
	public void checkInMapPlayers(){
		if(getInMapPlayers().size() == getMaxPlayers() || getInMapPlayers().size() >= 4){
			for(Player pl: getInMapPlayers()){
				pl.sendMessage(getInMapPlayers().size()+"/"+getMaxPlayers()+" joueurs.");
			}
			if(!TaskTimerstarted){
				this.TaskTimerstarted = true;
				this.Startingtask = Bukkit.getScheduler().scheduleSyncRepeatingTask(getPl(), new BukkitRunnable() {
					int seconds = 20;
					@Override
					public void run() {
						if(getInMapPlayers().size() < 4){
							stopStartingTask();
						}
						if(seconds == 20){
							for(Player pl: getInMapPlayers()){
								pl.sendMessage(ChatColor.BOLD+"La partie va commencer dans "+seconds+" secondes");
							}
						}
						if(seconds == 10){
							for(Player pl: getInMapPlayers()){
								pl.sendMessage(ChatColor.BOLD+"La partie va commencer dans "+seconds+" secondes");
							}
						}
						if(seconds <= 10){
							for(Player pl: getInMapPlayers()){
								pl.sendMessage("La partie commence dans "+seconds+" secondes");
							}
						}
						if(seconds == 0){
							stopStartingTask();
							start();
						}
						
						seconds--;
					}
				}, 20, 20);
			}
		}else{
			for(Player pl: getInMapPlayers()){
				pl.sendMessage(getInMapPlayers().size()+"/"+getMaxPlayers()+" joueurs.");
			}
		}
	}
	public void checkInGamePlayers(){
		if(getInGamePlayers().size() <= 1){
			for(Player pl: getInGamePlayers()){
				pl.sendMessage("tu es tous seul ! la partie va redemarrer !");
			}
			stop();
		}
	}
	public void stopStartingTask(){
		Bukkit.getScheduler().cancelTask(Startingtask);
		this.TaskTimerstarted = false;
	}
	public GameTeam getTeamForPlayer(Player p) {
		for(GameTeam t : teams) {
			if (t.getPlayers().contains(p)) return t;
		}
		return null;
	}
	
	// START AND STOP FUNCTIONS
	
	public void start(){
		setGamestatus(GameStatus.STARTED);
		for(Player player: getInMapPlayers()){
			if(getTeamForPlayer(player) == null){
				player.sendMessage(ChatColor.BOLD+"Tu n'as pas choisi de team, tu as donc une equipe aleatoire !");
				teams.get(new Random().nextInt(4)).addPlayer(player);
			}
			getInGamePlayers().add(player);
		}
		for(GameTeam team: teams){
			Location teamSpawnLoc = new Location(getMap(),
					pl.getConfig().getDouble("teamSpawn."+team.getName()+".x"),
					pl.getConfig().getDouble("teamSpawn."+team.getName()+".y"),
					pl.getConfig().getDouble("teamSpawn."+team.getName()+".z"));
			for(Player pl: team.getPlayers()){
				pl.setGameMode(GameMode.SURVIVAL);
				pl.setHealth(20);
				pl.setFoodLevel(20);
				pl.setExhaustion(5F);
				pl.getInventory().clear();
			}
			team.teleport(teamSpawnLoc);
		}
		startTimer();
	}
	public void stop(){
		setGamestatus(GameStatus.RELOADING);
		stopTimer();
		getInGamePlayers().clear();
		getObj().unregister();
		setObj(scoreboard.registerNewObjective("RFYL", "dummy"));
		for(Player player : getInMapPlayers()){
			player.setGameMode(GameMode.SURVIVAL);
			player.getInventory().clear();
			player.getInventory().setHelmet(new ItemStack(Material.AIR));
			player.getInventory().setChestplate(new ItemStack(Material.AIR));
			player.getInventory().setLeggings(new ItemStack(Material.AIR));
			player.getInventory().setBoots(new ItemStack(Material.AIR));
			player.setHealth(20);
			player.setFoodLevel(20);
			player.setExhaustion(5F);
			player.teleport(new Location(Bukkit.getWorlds().get(0),
					pl.getConfig().getDouble("worldSpawn.x"),
					pl.getConfig().getDouble("worldSpawn.y"),
					pl.getConfig().getDouble("worldSpawn.z")));
		}
		this.seconds = 0;
		this.minutes = 10;
		for(Team team : getScoreboard().getTeams()){
			team.unregister();
		}
		setTeamRed(new GameTeam("red", ChatColor.RED+"Rouge", ChatColor.RED, getScoreboard()));
		setTeamBlue(new GameTeam("blue", ChatColor.RED+"Bleu", ChatColor.BLUE, getScoreboard()));
		setTeamGreen(new GameTeam("green", ChatColor.RED+"Vert", ChatColor.GREEN, getScoreboard()));
		setTeamYellow(new GameTeam("yellow", ChatColor.RED+"Jaune", ChatColor.YELLOW, getScoreboard()));
		this.teams.clear();
		this.teams.add(teamRed);
		this.teams.add(teamBlue);
		this.teams.add(teamGreen);
		this.teams.add(teamYellow);
		try {
			reloadMap();
		} catch (Exception e) {
			e.printStackTrace();
		}
		setGamestatus(GameStatus.READY);
	}
}
