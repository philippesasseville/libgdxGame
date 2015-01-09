package com.mygdx.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;
import com.mygdx.handlers.GameInput;
import com.mygdx.handlers.GameStateManager;

public class Credits extends GameState {
	
    private SpriteBatch batch;
    private BitmapFont font;
	
	public Credits(GameStateManager gsm) {
		super(gsm);
        batch = new SpriteBatch();    
        font = new BitmapFont();
        font.setColor(Color.WHITE);
	}

	@Override
	public void handleInput() {
		if(GameInput.isPressed(GameInput.BUTTON4)) {
			gsm.setState(GameStateManager.MENU);
		}
		
	}

	@Override
	public void update(float dt) {
		handleInput();
	}

	@Override
	public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.begin();
        font.draw(batch, "CREDITS", MyGdxGame.V_WIDTH * MyGdxGame.SCALE / 2 - 20, MyGdxGame.V_HEIGHT * MyGdxGame.SCALE -50);
        font.draw(batch, "Game made by Philippe Sasseville", MyGdxGame.V_WIDTH * MyGdxGame.SCALE / 2 - 100, MyGdxGame.V_HEIGHT * MyGdxGame.SCALE -150);
        batch.end();
	}

	@Override
	public void dispose() {
        batch.dispose();
        font.dispose();
	}

}
