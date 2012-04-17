package com.mime.minefront.input;

import com.mime.minefront.Display;
import com.mime.minefront.RunGame;

public class Controller {

	public double x, y, z, rotation, xa, za, rotationa;
	public static boolean turnLeft = false;
	public static boolean turnRight = false;
	public static boolean walk = false;
	public static boolean crouching = false;
	public static boolean sprinting = false;

	public void tick(boolean forward, boolean back, boolean left, boolean right, boolean jump, boolean crouch,
			boolean sprint, boolean turnLeft2, boolean turnRight2, boolean escape) {
		double jumpHeight = 0.5;
		double crouchHeight = 0.3;
		double walkSpeed = 0.5;
		double turnSpeed = 0.0005 * Display.mouseSpeed;
		double turnSpeed2 = 0.015;
		double xMove = 0;
		double zMove = 0;

		if (forward) {
			zMove++;
			walk = true;
		}
		if (back) {
			zMove--;
			walk = true;
		}
		if (left) {
			xMove--;
			walk = true;
		}
		if (right) {
			xMove++;
			walk = true;
		}
		if (!forward && !back && !left && !right) {
			walk = false;
		}
		if (turnLeft) {
			rotationa -= turnSpeed;
		}
		if(turnLeft2) {
			rotationa -= turnSpeed2;
		}
		if (turnRight) {
			rotationa += turnSpeed;
		}
		if(turnRight2) {
			rotationa += turnSpeed2;
		}

		if (jump) {
			y += jumpHeight;
			sprint = false;
		}

		if (crouch) {
			y -= crouchHeight;
			sprint = false;
			walkSpeed = 0.3;
			crouching = true;
		} else {
			crouching = false;
		}

		if (sprint) {
			walkSpeed = 0.8;
			sprinting = true;
		} else {
			sprinting = false;
		}
		
		if(escape) {
			if(Display.fullScreenSelection == 0) {
				RunGame.s.restoreScreen();
			} else {
				RunGame.stopGame();
			}
			System.exit(0);
		}
			
		xa = (xMove * Math.cos(rotation) + zMove * Math.sin(rotation)) * walkSpeed / 1.75;
		za = (zMove * Math.cos(rotation) - xMove * Math.sin(rotation)) * walkSpeed / 1.75;

		x += xa;
		xa *= 0.1;
		y *= 0.9;
		z += za;
		za *= 0.1;
		rotation += rotationa;
		rotationa *= 0.5;
	}

}
