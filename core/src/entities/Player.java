package entities;

import com.badlogic.gdx.physics.box2d.Body;

public class Player {
	private Body playerBody;
	private float sprintFactor;
	
	public Player(Body body){
		this.playerBody = body;
		this.sprintFactor = 1;
	}
	
	public Body getPlayerBody(){
		return playerBody;
	}
	public void setPlayerBody(Body body){
		playerBody = body;
		return;
	}
	public float getSprintFactor(){
		return sprintFactor;
	}
	public void setSprintFactor(float sprintf){
		sprintFactor = sprintf;
		return;
	}
	public float getXPosition(){
		return playerBody.getPosition().x;
	}
}
