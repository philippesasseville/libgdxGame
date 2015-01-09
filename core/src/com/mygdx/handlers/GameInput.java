package com.mygdx.handlers;

public class GameInput {
	
	public static int x;
	public static int y;
	public static boolean down;
	public static boolean pdown;
	
	public static boolean[] keys;
	public static boolean[] pkeys;
	private static final int NUM_KEYS = 6;
	public static final int BUTTON1 = 0;//jump
	public static final int BUTTON2 = 1;//unused
	public static final int BUTTON3 = 2;//space
	public static final int BUTTON4 = 3;//escape
	
	public static final int BUTTON5 = 4;//left
	public static final int BUTTON6 = 5;//right
	
	
	static {
		keys = new boolean[NUM_KEYS];
		pkeys = new boolean[NUM_KEYS];
	}
	
	public static void update() {
		pdown = down;
		for(int i = 0; i < NUM_KEYS; i++) {
			pkeys[i] = keys[i];
		}
	}
	
	public static boolean isDown() { return down; }
	public static boolean isPressed() { return down && !pdown; }
	public static boolean isReleased() { return !down && pdown; }
	
	public static void setKey(int i, boolean b) { keys[i] = b; }
	public static boolean isDown(int i) { return keys[i]; }
	public static boolean isPressed(int i) { return keys[i] && !pkeys[i]; }
	
}