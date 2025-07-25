package players.country;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import players.Player;
import players.side.SideTeam;

public class Team {

	private String name = ""; 
	private Color color = null;
	private SideTeam sideTeam = null;
	private String flag = null;
	
	public Team(String name, String flag, Color color, SideTeam sideTeam) {
		this.name = name;
		this.color = color;
		this.flag = flag;
		this.sideTeam = sideTeam;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public SideTeam getSideTeam() {
		return this.sideTeam;
	}
	
	public void resetPlayers() {
		sideTeam.resetPlayers(this, sideTeam);
	}
	
	public Player getNearestPlayer(Vector2 position) {
		return sideTeam.getNearestPlayer(position);
	}

	public Texture getFlag() {
		return new Texture(flag);
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
}
