package fr.axicer.AOTPRFYL.Game;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class GameTeam {
	
	private String name;
	private String displayName;
	private ArrayList<Player> players = new ArrayList<Player>();
	private ChatColor color;
	private Scoreboard scoreboard;
	
	public GameTeam(String name, String displayName, ChatColor color, Scoreboard scoreboard) {
		setName(name);
		setDisplayName(displayName);
		setColor(color);
		setScoreboard(scoreboard);
		
		getScoreboard().registerNewTeam(getName());
		
		Team t = getScoreboard().getTeam(getName());
		t.setAllowFriendlyFire(true);
		t.setCanSeeFriendlyInvisibles(true);
		t.setDisplayName(getDisplayName());
		t.setPrefix(getColor()+"");
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public ArrayList<Player> getPlayers() {
		return players;
	}
	@SuppressWarnings("deprecation")
	public void addPlayer(Player player){
		getPlayers().add(player);
		getScoreboard().getTeam(getName()).addPlayer(player);
	}
	@SuppressWarnings("deprecation")
	public void removePlayer(Player player){
		if(getPlayers().contains(player)){
			getScoreboard().getTeam(getName()).removePlayer(player);
			players.remove(player);
		}
	}
	public ChatColor getColor() {
		return color;
	}
	public void setColor(ChatColor color) {
		this.color = color;
	}
	public Scoreboard getScoreboard() {
		return scoreboard;
	}
	public void setScoreboard(Scoreboard scoreboard) {
		this.scoreboard = scoreboard;
	}
	public void teleport(Location loc){
		for(Player player: getPlayers()){
			player.teleport(loc);
		}
	}
}
