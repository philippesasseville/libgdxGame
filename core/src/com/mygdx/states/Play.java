package com.mygdx.states;

import java.util.Vector;

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
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
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
		
		drawHill(5, 10, 75, 1200);
		
		createPlayerCart();
		
		setupB2d();
		
	}
	
	public void handleInput() {
		if(GameInput.isPressed(GameInput.BUTTON4)) {
			gsm.setState(GameStateManager.MENU);
		}
		if(GameInput.isPressed(GameInput.BUTTON1) && cl.isPlayerOnGround()){
			player.getPlayerBody().applyForceToCenter(0, 300, false);
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
		b2dCam.position.set(new Vector2(player.getXPosition(), player.getYPosition()), 0);
		b2dCam.zoom = 5;
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
		shape.setAsBox(150 / B2DVars.PPM, 5 / B2DVars.PPM, new Vector2(0,0), -60);
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.friction = 0.2f;
		fdef.filter.categoryBits = B2DVars.BIT_GROUND;
		fdef.filter.maskBits = B2DVars.BIT_PLAYER;
		body.createFixture(fdef);
		
		bdef.position.set(640 / B2DVars.PPM , 120 / B2DVars.PPM);
		bdef.type = BodyType.StaticBody;
		body = world.createBody(bdef);
				
		shape.setAsBox(150 / B2DVars.PPM, 5 / B2DVars.PPM, new Vector2(0,0), 60);
		fdef.shape = shape;
		//fdef.friction = 0.95f;
		fdef.filter.categoryBits = B2DVars.BIT_GROUND;
		fdef.filter.maskBits = B2DVars.BIT_PLAYER;
		body.createFixture(fdef);
		
	}
	
	private void createPlayer(){
		BodyDef bdef = new BodyDef();
		// create player
		bdef.position.set(160 / B2DVars.PPM, 1000 / B2DVars.PPM);
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
		player.getPlayerBody().setAngularDamping(0.8f);
				
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
	
	private void createPlayerCart(){
		// add the cart
		BodyDef carBodyDef = new BodyDef();
		carBodyDef.type= BodyType.DynamicBody;
		carBodyDef.position.set(320/B2DVars.PPM,200/B2DVars.PPM);
		PolygonShape box = new PolygonShape();
		box.setAsBox(30/B2DVars.PPM,10/B2DVars.PPM);
		FixtureDef boxDef = new FixtureDef();
		boxDef.density=0.5f;
		boxDef.friction=3f;
		boxDef.restitution=0.3f;
		boxDef.filter.groupIndex=-1;
		boxDef.shape=box;
		player = new Player(world.createBody(carBodyDef));
		player.getPlayerBody().createFixture(boxDef);
		// wheel shape
		CircleShape wheelShape =new CircleShape();
		wheelShape.setRadius(12/B2DVars.PPM);
		// wheel fixture
		FixtureDef wheelFixture = new FixtureDef();
		wheelFixture.density=1;
		wheelFixture.friction=3;
		wheelFixture.restitution=0.1f;
		wheelFixture.filter.groupIndex=-1;
		wheelFixture.shape=wheelShape;
		// wheel body definition
		BodyDef wheelBodyDef = new BodyDef();
		wheelBodyDef.type= BodyType.DynamicBody;
		// rear wheel
		wheelBodyDef.position.set(player.getPlayerBody().getWorldCenter().x-(16/B2DVars.PPM),player.getPlayerBody().getWorldCenter().y-(15/B2DVars.PPM));
		Body rearWheel =world.createBody(wheelBodyDef);
		rearWheel.createFixture(wheelFixture);
		// front wheel
		wheelBodyDef.position.set(player.getPlayerBody().getWorldCenter().x+(16/B2DVars.PPM),player.getPlayerBody().getWorldCenter().y-(15/B2DVars.PPM));
		Body frontWheel=world.createBody(wheelBodyDef);
		frontWheel.createFixture(wheelFixture);
		// rear joint
		RevoluteJointDef rearWheelRevoluteJointDef=new RevoluteJointDef();
		rearWheelRevoluteJointDef.initialize(rearWheel,player.getPlayerBody(),rearWheel.getWorldCenter());
		rearWheelRevoluteJointDef.enableMotor=true;
		rearWheelRevoluteJointDef.maxMotorTorque=10000;
		Joint rearWheelRevoluteJoint;
		rearWheelRevoluteJoint=world.createJoint(rearWheelRevoluteJointDef);
		// front joint
		RevoluteJointDef frontWheelRevoluteJointDef=new RevoluteJointDef();
		frontWheelRevoluteJointDef.initialize(frontWheel,player.getPlayerBody(),frontWheel.getWorldCenter());
		frontWheelRevoluteJointDef.enableMotor=true;
		frontWheelRevoluteJointDef.maxMotorTorque=10000;
		Joint frontWheelRevoluteJoint;
		frontWheelRevoluteJoint=world.createJoint(frontWheelRevoluteJointDef);
	}
	
	private void setupB2d(){
		// set up box2d cam
		b2dCam = new OrthographicCamera();
		b2dCam.setToOrtho(false, MyGdxGame.V_WIDTH / B2DVars.PPM, MyGdxGame.V_HEIGHT / B2DVars.PPM);
	}
	
	private void drawHill(int numberOfHills, int pixelStep, int variability, int levelLenght){
		
		int hillStartY =(int)(Math.random()*200);
		int hillWidth = levelLenght/numberOfHills;
		int hillSliceWidth = hillWidth/pixelStep;
		Vector<Vector2> hillVector = new Vector<Vector2>();
		Vector2[] hillVectorArray = {};
		
		for (int i = 0; i < numberOfHills; i++) {
			int randomHeight = (int)(Math.random()*variability);
			System.out.println("randomHeight :" + randomHeight);
			if(i != 0){
					hillStartY-=randomHeight;
			}
			for (int j = 0; j < hillSliceWidth; j++){
				hillVector = new Vector<Vector2>();
				hillVector.add(new Vector2((j*pixelStep+hillWidth*i)/B2DVars.PPM,-480/B2DVars.PPM));
				hillVector.add(new Vector2((j*pixelStep+hillWidth*i)/B2DVars.PPM,-(float) (hillStartY+randomHeight*Math.cos(2*Math.PI/hillSliceWidth*j))/B2DVars.PPM));
				hillVector.add(new Vector2(((j+1)*pixelStep+hillWidth*i)/B2DVars.PPM,-(float) ((hillStartY+randomHeight*Math.cos(2*Math.PI/hillSliceWidth*(j+1)))/B2DVars.PPM)));
				hillVector.add(new Vector2(((j+1)*pixelStep+hillWidth*i)/B2DVars.PPM,-480/B2DVars.PPM));
				
				BodyDef sliceBody = new BodyDef();
				Vector2 centre = findCentroid(hillVector,hillVector.size());
				sliceBody.position.set(centre.x,centre.y);
				for(int z = 0; z<hillVector.size(); z++){
					hillVector.get(z).sub(centre);
				}
				PolygonShape slicePoly = new PolygonShape();
				hillVectorArray = hillVector.toArray(hillVectorArray);
				slicePoly.set(hillVectorArray);
				FixtureDef sliceFixture = new FixtureDef();
				sliceFixture.shape = slicePoly;
				Body worldSlice = world.createBody(sliceBody);
				worldSlice.createFixture(sliceFixture);
			}
			hillStartY = hillStartY+randomHeight;
			
		}
	}

	private Vector2 findCentroid(Vector<Vector2> vs, int count) {
		Vector2 c = new Vector2();
		float area = 0.0f;
		float p1X = 0.0f;
		float p1Y = 0.0f;
		float inv3 = 1.0f/3.0f;
		for(int i = 0; i < count; ++i){
			Vector2 p2 = vs.get(i);
			Vector2 p3 = i+1<count?vs.get(i+1):vs.get(0);
			float e1X = p2.x-p1X;
			float e1Y = p2.y-p1Y;
			float e2X = p3.x-p1X;
			float e2Y = p3.y-p1Y;
			float D = (e1X * e2Y - e1Y * e2X);
			float triangleArea = 0.5f*D;
			area +=triangleArea;
			c.x += triangleArea * inv3 * (p1X + p2.x + p3.x);
			c.y += triangleArea * inv3 * (p1Y + p2.y + p3.y);
		}
		return c;
	}
}
