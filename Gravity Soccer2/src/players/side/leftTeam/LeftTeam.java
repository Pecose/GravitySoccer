package players.side.leftTeam;

import java.awt.Point;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;

import entities.Registry;
import players.Player;
import players.side.SideTeam;

public class LeftTeam implements SideTeam{

    private static final List<Point> BASE_POSITIONS = Arrays.asList(
        new Point(-840,   0),
        new Point(-560, -270),
        new Point(-560,   0),
        new Point(-560,  270),
        new Point(-200, -360),
        new Point(-200,   0),
        new Point(-200,  360),
        new Point(-180, -180),
        new Point(-180,  180),
        new Point(-700,   0)
    );

    private static final List<String> NAMES = Arrays.asList(
        "Left1", "Left2", "Left3", "Left4", 
        "Left5", "Left6", "Left7", "Left8",
        "Left9", "Left10"
    );

    private final Random random = new Random();

    @Override
    public void resetPlayers() {
        for (int i = 0; i < BASE_POSITIONS.size(); i++) {
            Point base = BASE_POSITIONS.get(i);
            String name = NAMES.get(i);
            addRandomizedPlayer(base.x, base.y, name);
        }
    }

    private void addRandomizedPlayer(int baseX, int baseY, String name) {
    	Registry.remove(name);
        int x = baseX + random.nextInt(21) - 10;  // [–10 … +10]
        int y = baseY + random.nextInt(21) - 10;
        Registry.add(new LeftPlayer(x, y, 15, this), name);
    }
    
    @Override
    public Player getNearestPlayer(Vector2 position) {
        Player closest = null;
        float bestDist2 = Float.MAX_VALUE;

        // Parcours tous les joueurs de l'équipe via leur noms
        for (String name : NAMES) {
            Player p = (Player) Registry.get(name);
            if (p == null || p.getBody() == null) continue;

            Vector2 pPos = p.getBody().getPosition();
            float dist2 = position.dst2(pPos);
            if (dist2 < bestDist2) {
                bestDist2 = dist2;
                closest = p;
            }
        }
        return closest;
    }
}
