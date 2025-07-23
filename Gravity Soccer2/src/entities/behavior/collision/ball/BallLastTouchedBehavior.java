package entities.behavior.collision.ball;

import engine.Control;
import entities.Entity;
import entities.Registry;
import entities.behavior.Behavior;
import entities.behavior.collision.bodys.CollisionReactive;
import entities.behavior.collision.goalzone.GoalZoneBehavior;
import entities.behavior.collision.goalzone.GoalZoneScoreBehavior;
import entities.behavior.collision.goalzone.GoalZoneTouchBehavior;
import goal.Goal;
import goal.GoalZone;
import players.Ball;
import players.Player;
import players.side.SideTeam;
import players.side.leftTeam.LeftTeam;
import players.side.rightTeam.RightTeam;

public class BallLastTouchedBehavior implements Behavior, CollisionReactive {

    @Override
    public void render(Control control, Entity entity) {
        // rien à afficher
    }

    @Override
    public void onCollision(Entity self, Entity other) {
        if (!(other instanceof GoalZone)) Control.soundManager.playNextNote();

        // ✅ on ne s'intéresse qu'aux collisions avec un joueur
        if (!(other instanceof Player)) {
            return;
        }

        Ball ball = (Ball) self;
        SideTeam side = ((Player) other).getSideTeam();
        ball.setLastTeamTouched(side);

        // ✅ récupérer les deux zones
        GoalZone leftZone  = ((Goal) Registry.get("LeftGoal")).getZone();
        GoalZone rightZone = ((Goal) Registry.get("RightGoal")).getZone();

        // ✅ basculer les behaviors et fixtures en fonction de l'équipe
        if (side instanceof LeftTeam) {
            // La gauche touche → gauche = touch, droite = score
            leftZone.addBehavior(GoalZoneBehavior.class, new GoalZoneTouchBehavior());
            rightZone.addBehavior(GoalZoneBehavior.class, new GoalZoneScoreBehavior());
        } 
        else if (side instanceof RightTeam) {
            // La droite touche → droite = touch, gauche = score
            leftZone.addBehavior(GoalZoneBehavior.class, new GoalZoneScoreBehavior());
            rightZone.addBehavior(GoalZoneBehavior.class, new GoalZoneTouchBehavior());
        }
    }

    @Override
    public void batch(Control control, Entity entity) {
        // rien à batcher ici
    }
}
