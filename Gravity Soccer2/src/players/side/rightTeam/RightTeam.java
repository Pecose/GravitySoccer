package players.side.rightTeam;

import java.awt.Point;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import engine.Control;
import entities.Registry;
import players.side.SideTeam;

public class RightTeam implements SideTeam{

    private static final List<Point> BASE_POSITIONS = Arrays.asList(
        new Point( 840,   0),
        new Point( 560, -270),
        new Point( 560,   0),
        new Point( 560,  270),
        new Point( 200, -360),
        new Point( 200,   0),
        new Point( 200,  360),
        new Point( 180, -180),
        new Point( 180,  180),
        new Point( 700,   0)
    );

    private static final List<String> NAMES = Arrays.asList(
        "Right1", "Right2", "Right3", "Right4",
        "Right5", "Right6", "Right7", "Right8",
        "Right9", "Right10"
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
        int x = baseX + random.nextInt(21) - 10;  // de -10 à +10
        int y = baseY + random.nextInt(21) - 10;
        Registry.add(new RightPlayer(x, y, 15, this), name);
    }
}
