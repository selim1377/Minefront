package com.mime.minefront.input;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.mime.minefront.Display;

public class InputHandler implements KeyListener, FocusListener, MouseListener, MouseMotionListener {

	public boolean key[] = new boolean[68836];
	public static boolean Mouse[] = new boolean[4];
	public static int MouseX;
	public static int MouseY;
	public static int MouseDX;
	public static int MouseDY;
	public static int MousePX; //mouse pressed
	public static int MousePY;
	public static boolean dragged = false;
	
	public void mouseDragged(MouseEvent e) {
		MouseX = e.getX();
		MouseY = e.getY();
		
		dragged = true;
		MouseDX = e.getX();
		MouseDY = e.getY();
	}

	public void mouseMoved(MouseEvent e) {
		MouseX = e.getX();
		MouseY = e.getY();
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) {
		if(e.getButton() == 3) {
			Display.aiming = true;
			System.out.println("Aiming");
		}
		Mouse[e.getButton()] = true;
		
		MousePX = e.getX();
		MousePY = e.getY();
	}

	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == 3) {
			Display.aiming = false;
			System.out.println("Not aiming");
		}
		Mouse[e.getButton()] = false;
		
		dragged = false;
	}

	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void focusLost(FocusEvent e) {
		for(int i = 0; i < key.length; i++) {
			key[i] = false;
		}
	}

	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode > 0 && keyCode < key.length) {
			key[keyCode] = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode > 0 && keyCode < key.length) {
			key[keyCode] = false;
		}
	}

	public void keyTyped(KeyEvent e) {}

}
