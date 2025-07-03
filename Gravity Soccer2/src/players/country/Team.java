package players.country;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import engine.Control;
import players.Player;
import players.side.SideTeam;

public class Team {

	private String name = "";
	private Color color = null;
	private SideTeam sideTeam = null;
	
	public Team(String name, Color color, SideTeam sideTeam) {
		this.name = name;
		this.color = color;
		this.sideTeam = sideTeam;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Color getColor() {
		return color;
	}
	
	public SideTeam getSideTeam() {
		return this.sideTeam;
	}
	
	public void resetPlayers() {
		sideTeam.resetPlayers();
	}
	
	public Player getNearestPlayer(Vector2 position) {
		return sideTeam.getNearestPlayer(position);
	}
}
