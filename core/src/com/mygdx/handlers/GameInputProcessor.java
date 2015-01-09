package com.mygdx.handlers;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

public class GameInputProcessor extends InputAdapter {
	
	public boolean mouseMoved(int x, int y) {
		GameInput.x = x;
		GameInput.y = y;
		return true;
	}
	
	public boolean touchDragged(int x, int y, int pointer) {
		GameInput.x = x;
		GameInput.y = y;
		GameInput.down = true;
		return true;
	}
	
	public boolean touchDown(int x, int y, int pointer, int button) {
		GameInput.x = x;
		GameInput.y = y;
		GameInput.down = true;
		return true;
	}
	
	public boolean touchUp(int x, int y, int pointer, int button) {
		GameInput.x = x;
		GameInput.y = y;
		GameInput.down = false;
		return true;
	}
	
	public boolean keyDown(int k) {
		if(k == Keys.Z) GameInput.setKey(GameInput.BUTTON1, true);
		if(k == Keys.X) GameInput.setKey(GameInput.BUTTON2, true);
		if(k == Keys.SPACE) GameInput.setKey(GameInput.BUTTON3, true);
		if(k == Keys.ESCAPE) GameInput.setKey(GameInput.BUTTON4, true); 
		if(k == Keys.LEFT) GameInput.setKey(GameInput.BUTTON5, true);
		if(k == Keys.RIGHT) GameInput.setKey(GameInput.BUTTON6, true);
		return true;
	}
	
	public boolean keyUp(int k) {
		if(k == Keys.Z) GameInput.setKey(GameInput.BUTTON1, false);
		if(k == Keys.X) GameInput.setKey(GameInput.BUTTON2, false);
		if(k == Keys.SPACE) GameInput.setKey(GameInput.BUTTON3, false);
		if(k == Keys.ESCAPE) GameInput.setKey(GameInput.BUTTON4, false);
		if(k == Keys.LEFT) GameInput.setKey(GameInput.BUTTON5, false);
		if(k == Keys.RIGHT) GameInput.setKey(GameInput.BUTTON6, false);
		return true;
	}
	
}
