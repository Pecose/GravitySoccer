package engine.command;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public enum Command {
    MoveLeft(Input.Keys.LEFT),
    MoveRight(Input.Keys.RIGHT),
    MoveUp(Input.Keys.UP),
    MoveDown(Input.Keys.DOWN),
    Menu(Input.Keys.DEL),
    Active(Input.Keys.ENTER);
    
    private int key;
    
    private Command(int keyCode) {
        this.key = keyCode;
    }
    
    public int getKey() {
        return key;
    }
    
    public void setKey(int newKeyCode) {
        this.key = newKeyCode;
    }
    
    public boolean isPressed() {
    	if (Gdx.input.isKeyPressed(key)) return true;
		return false;
    }
    
    public boolean isJustPressed() {
    	if (Gdx.input.isKeyJustPressed(key)) return true;
		return false;
    }
}
