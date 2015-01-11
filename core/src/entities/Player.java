package entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;

public class Player {
	private Body playerBody;
	RevoluteJoint rearWheelRevoluteJoint;
	RevoluteJoint frontWheelRevoluteJoint;
	
	public Player(Body body){
		this.playerBody = body;
	}
	
	public Body getPlayerBody(){
		return playerBody;
	}
	public void setPlayerBody(Body body){
		playerBody = body;
		return;
	}
	public RevoluteJoint getRearWheelJoint(){
		return rearWheelRevoluteJoint;
	}
	
	public RevoluteJoint getFrontWheelJoint(){
		return rearWheelRevoluteJoint;
	}
	
	
	public void setFrontWheelJoint(RevoluteJoint rj){
		frontWheelRevoluteJoint = rj;
		return;
	}
	
	public void setRearWheelJoint(RevoluteJoint rj){
		frontWheelRevoluteJoint = rj;
		return;
	}
	
	public float getXPosition(){
		return playerBody.getPosition().x;
	}

	public float getYPosition() {
		return playerBody.getPosition().y;
	}
	
}
