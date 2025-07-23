package goal;

import engine.Control;
import entities.Entity;
import entities.behavior.Behavior;
import entities.behavior.collision.goalzone.GoalZoneBehavior;
import entities.behavior.collision.goalzone.GoalZoneTouchBehavior;

public class GoalZone extends Entity {

	private Goal goal; 
    public Goal getGoal() { return goal; }
    
    public GoalZone(float x, float y, float width, float height, Goal goal) {
        super(x, y, width, height); 
        this.goal = goal;        
        this.addBehavior(GoalZoneBehavior.class, new GoalZoneTouchBehavior());

    }

    @Override
    public void render(Control control) {
    	for(Behavior behavior : this.getBehaviors()) {
			behavior.render(control, this);
		}
    }

	@Override
	public void batch(Control control) {
		for(Behavior behavior : this.getBehaviors()) {
			behavior.batch(control, this);
		}
	}

	@Override
	public void dispose() {
		
	}

}

