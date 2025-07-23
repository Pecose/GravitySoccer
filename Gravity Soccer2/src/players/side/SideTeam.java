package players.side;

import com.badlogic.gdx.math.Vector2;

import players.Player;
import players.country.Team;

public interface SideTeam {

	void resetPlayers(Team team, SideTeam side);
	Player getNearestPlayer(Vector2 position);
}
