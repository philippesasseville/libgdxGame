package com.mygdx.handlers;

import java.util.Stack;

import com.mygdx.game.MyGdxGame;
import com.mygdx.states.Credits;
import com.mygdx.states.GameState;
import com.mygdx.states.Menu;
import com.mygdx.states.Play;

public class GameStateManager {

	private MyGdxGame game;
	
	private Stack<GameState> gameStates;
	
	public static final int MENU = 83774392;
	public static final int PLAY = 388031654;
	public static final int CREDITS = 1234556;
	
	public void update(float dt) {
		gameStates.peek().update(dt);
	}
	
	public void render() {
		gameStates.peek().render();
	}
	
	public GameStateManager(MyGdxGame game) {
		this.game = game;
		gameStates = new Stack<GameState>();
		pushState(MENU);
	}
	
	public void setState(int state){
		popState();
		pushState(state);
	}
	
	public void pushState(int state){
		gameStates.push(getState(state));
	}
	
	public void popState(){
		GameState g = gameStates.pop();
		g.dispose();
	}
	
	private GameState getState(int state) {
		if(state == MENU) return new Menu(this);
		if(state == PLAY) return new Play(this);
		if(state == CREDITS) return new Credits(this);
		return null;
	}

	public MyGdxGame game() {
		return game;
	}

}
