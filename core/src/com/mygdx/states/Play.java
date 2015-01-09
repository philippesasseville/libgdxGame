package com.mygdx.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MyGdxGame;
import com.mygdx.handlers.GameContactListener;
import com.mygdx.handlers.GameInput;
import com.mygdx.handlers.GameStateManager;
import com.mygdx.game.B2DVars;

import entities.Player;

public class Play extends GameState{

	private World world;
	private Box2DDebugRenderer b2dr;
	
	private GameContactListener cl;
	
	private OrthographicCamera b2dCam;
	
	private Player player;
	
	public Play(GameStateManager gsm) {
		
		super(gsm);
		
		world = new World(new Vector2(0, -9.81f), false);
		
		cl = new GameContactListener();
		world.setContactListener(cl);
		
		b2dr = new Box2DDebugRenderer();
		
		createPlatform();
		
		createPlayer();
		
		setupB2d();
		
	}
	
	public void handleInput() {
		if(GameInput.isPressed(GameInput.BUTTON4)) {
			gsm.setState(GameStateManager.MENU);
		}
		if(GameInput.isPressed(GameInput.BUTTON1) && cl.isPlayerOnGround()){
			player.getPlayerBody().applyForceToCenter(0, 175, false);
		}
		if(GameInput.isDown(GameInput.BUTTON2)){
			player.setSprintFactor(2);
		}
		else{
			player.setSprintFactor(1);
		}
		if(GameInput.isDown(GameInput.BUTTON5)){
			//player.getPlayerBody().applyLinearImpulse( -10 * player.getSprintFactor() / B2DVars.PPM, 0, 0, 0, false);
			player.getPlayerBody().setAngularVelocity(1000 * player.getSprintFactor());
		}
		if(GameInput.isDown(GameInput.BUTTON6)){
			//player.getPlayerBody().applyLinearImpulse( 10 * player.getSprintFactor() / B2DVars.PPM, 0, 0, 0, false);
			player.getPlayerBody().setAngularVelocity(-1000 * player.getSprintFactor());
		}

	}
	
	public void update(float dt) {
		handleInput();
		world.step(dt, 6, 2);
	}
	
	public void render() {
		//set cam to follow player
		b2dCam.position.set(player.getXPosition(), MyGdxGame.V_HEIGHT / B2DVars.PPM / 2, 0);
		b2dCam.update();
		
		// clear screen
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// draw box2d world
		b2dr.render(world, b2dCam.combined);
		
	}
	
	public void dispose() {}
	
	private void createPlatform(){
		// create platform
		BodyDef bdef = new BodyDef();
		bdef.position.set(160 / B2DVars.PPM , 120 / B2DVars.PPM);
		bdef.type = BodyType.StaticBody;
		Body body = world.createBody(bdef);
				
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(150 / B2DVars.PPM, 5 / B2DVars.PPM);
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		//fdef.friction = 0.95f;
		fdef.filter.categoryBits = B2DVars.BIT_GROUND;
		fdef.filter.maskBits = B2DVars.BIT_PLAYER;
		body.createFixture(fdef);
		
		bdef.position.set(640 / B2DVars.PPM , 120 / B2DVars.PPM);
		bdef.type = BodyType.StaticBody;
		body = world.createBody(bdef);
				
		shape.setAsBox(150 / B2DVars.PPM, 5 / B2DVars.PPM);
		fdef.shape = shape;
		//fdef.friction = 0.95f;
		fdef.filter.categoryBits = B2DVars.BIT_GROUND;
		fdef.filter.maskBits = B2DVars.BIT_PLAYER;
		body.createFixture(fdef);
		
	}
	
	private void createPlayer(){
		BodyDef bdef = new BodyDef();
		// create player
		bdef.position.set(160 / B2DVars.PPM, 200 / B2DVars.PPM);
		bdef.type = BodyType.DynamicBody;
		player = new Player(world.createBody(bdef));
				
		//PolygonShape shape = new PolygonShape();
		//shape.setAsBox(5 / B2DVars.PPM, 5 / B2DVars.PPM);
		CircleShape cshape = new CircleShape();
		cshape.setRadius(5 / B2DVars.PPM);
		FixtureDef fdef = new FixtureDef();
		fdef.shape = cshape;
		fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
		fdef.filter.maskBits = B2DVars.BIT_GROUND;
		player.getPlayerBody().createFixture(fdef).setUserData("player");
		player.getPlayerBody().setAngularDamping(2);
				
		//PolygonShape shape = new PolygonShape();
		//create foot sensor
		//shape.setAsBox(5 / B2DVars.PPM, 2 / B2DVars.PPM, new Vector2(0,-5 / B2DVars.PPM),0);
		cshape.setRadius(6 / B2DVars.PPM);
		fdef.shape = cshape;
		fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
		fdef.filter.maskBits = B2DVars.BIT_GROUND;
		fdef.isSensor = true;
		player.getPlayerBody().createFixture(fdef).setUserData("foot");
	}
	
	private void setupB2d(){
		// set up box2d cam
		b2dCam = new OrthographicCamera();
		b2dCam.setToOrtho(false, MyGdxGame.V_WIDTH / B2DVars.PPM, MyGdxGame.V_HEIGHT / B2DVars.PPM);
	}
}
