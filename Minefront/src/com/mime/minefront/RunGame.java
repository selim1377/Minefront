package com.mime.minefront;

import java.awt.DisplayMode;
import java.awt.Window;

import javax.swing.JFrame;

public class RunGame {
	
	public static ScreenManager s;
	static JFrame frame;
	
	private static DisplayMode modes[] = {
		new DisplayMode(Display.width, Display.height, 32, 0),
		new DisplayMode(Display.width, Display.height, 24, 0),
		new DisplayMode(Display.width, Display.height, 16, 0),
	};
	private static DisplayMode modes2[] = 	{
			new DisplayMode(800, 600, 32, 0),
			new DisplayMode(800, 600, 24, 0),
			new DisplayMode(800, 600, 16, 0),
			new DisplayMode(640, 480, 32, 0),
			new DisplayMode(640, 480, 24, 0),
			new DisplayMode(640, 480, 16, 0),
		};
	
	public RunGame() {
		Display.setGameWidthAndHeight();
		Display display = new Display();
		Display.fullScreenSelection = Config.loadConfig("res/settings/config.xml", "fullScreenSelection");
		
		if(Display.fullScreenSelection == 0) {
			s = new ScreenManager();
			DisplayMode dm = s.findFirstCompatibleMode(modes);
			if(dm == null) {
				System.out.println("Error 101");
				dm = s.findFirstCompatibleMode(modes2);
			}
			s.setFullScreen(dm);
			Window w = s.getFullScreenWindow();
			w.add(display);
		} else {
			frame = new JFrame();
			if(Display.fullScreenSelection == 2) {
				frame.setUndecorated(true);
			}
			frame.add(display);
			frame.setSize(Display.width, Display.height);
			frame.setLocationRelativeTo(null);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setResizable(false);
			frame.setTitle(Display.TITLE);
			frame.setVisible(true);
		}
		display.start();
		stopMenuThread();
	}
	
	public static void stopGame() {
		frame.dispose();
	}
	
	private void stopMenuThread() {
		Display.getLauncherInstance().stopMenu();
	}
	
	public static void main(String[] args) {
		new RunGame();
	}
}
