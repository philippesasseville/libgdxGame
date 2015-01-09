package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.mygdx.handlers.BoundedCamera;
import com.mygdx.handlers.GameInput;
import com.mygdx.handlers.GameInputProcessor;
import com.mygdx.handlers.GameStateManager;


public class MyGdxGame extends ApplicationAdapter {

	public static final String TITLE = "MyGdxGame";
	public static final int V_WIDTH = 320;
	public static final int V_HEIGHT = 240;
	public static final int SCALE = 2;
	public static final float STEP = 1 / 60f;
	
    private SpriteBatch sb;
    private BoundedCamera cam;
    private OrthographicCamera hudCam;
    private GameStateManager gsm;
	
    @Override
    public void create() {
    	
    	Gdx.input.setInputProcessor(new GameInputProcessor());
    	
    	
		cam = new BoundedCamera();
		cam.setToOrtho(false, V_WIDTH, V_HEIGHT);
		hudCam = new OrthographicCamera();
		hudCam.setToOrtho(false, V_WIDTH, V_HEIGHT);
		
		sb = new SpriteBatch();
		
		gsm = new GameStateManager(this);
    }

    @Override
    public void dispose() {

    }

    @Override
    public void render() {        

    	Gdx.graphics.setTitle(TITLE + " -- FPS: " + Gdx.graphics.getFramesPerSecond());
		
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render();
		GameInput.update();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
    
	public SpriteBatch getSpriteBatch() { return sb; }
	public Camera getCamera() { return cam; }
	public OrthographicCamera getHUDCamera() { return hudCam; }
    
}
