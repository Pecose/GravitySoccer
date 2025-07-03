package players.side;

import com.badlogic.gdx.math.Vector2;

import players.Player;

public interface SideTeam {

	void resetPlayers();
	Player getNearestPlayer(Vector2 position);
}
