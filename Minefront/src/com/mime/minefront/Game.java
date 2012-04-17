package com.mime.minefront;

import java.awt.event.KeyEvent;

import com.mime.minefront.input.Controller;
import com.mime.minefront.level.Level;

public class Game {

	public int time;
	public Controller controls;
	public Level level;
	
	public Game() {
		controls = new Controller();
		level = new Level(80, 80);
	}
	
	public void tick(boolean key[]) {
		boolean forward;
		boolean back;
		time++;
		if(key[KeyEvent.VK_W] || key[KeyEvent.VK_UP]) {
			forward = true;
		} else {
			forward = false;
		}
		if(key[KeyEvent.VK_S] || key[KeyEvent.VK_DOWN]) {
			back = true;
		} else {
			back = false;
		}
		//boolean forward = key[KeyEvent.VK_W];
		//boolean back = key[KeyEvent.VK_S];
		
		boolean left = key[KeyEvent.VK_A];
		boolean right = key[KeyEvent.VK_D];
		boolean jump = key[KeyEvent.VK_SPACE];
		boolean crouch = key[KeyEvent.VK_CONTROL];
		boolean sprint = key[KeyEvent.VK_SHIFT];
		boolean turnLeft2 = key[KeyEvent.VK_LEFT];
		boolean turnRight2 = key[KeyEvent.VK_RIGHT];
		boolean escape = key[KeyEvent.VK_ESCAPE];
		
		controls.tick(forward, back, left, right, jump, crouch, sprint, turnLeft2, turnRight2, escape);
	}
}
