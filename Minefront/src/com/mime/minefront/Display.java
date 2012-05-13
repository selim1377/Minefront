package com.mime.minefront;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.mime.minefront.graphics.Screen;
import com.mime.minefront.graphics.Texture;
import com.mime.minefront.gui.Launcher;
import com.mime.minefront.input.Controller;
import com.mime.minefront.input.InputHandler;

public class Display extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	public static int width = 800;
	public static int height = 600;

	public static final String TITLE = "Minefront Pre-Alpha 0.03";
	public static int mouseSpeed;
	public static int resSelection; //change value to change default resolution
	public static int fullScreenSelection;
	public static boolean aiming = false;
	private static Launcher launcher;

	private Thread thread;
	private boolean running = false;
	private Game game;
	private Screen screen;
	private InputHandler input;
	private BufferedImage img, ironsight1, ironsight2;
	private int frames;
	private int[] pixels;
	private int newX = InputHandler.MouseX;
	//private int newY = InputHandler.MouseY;
	private int oldX;

	// constructor
	public Display() {
		BufferedImage cursor = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0, 0), "blankCursor");
		setCursor(blankCursor);
		Dimension size = new Dimension(width, height);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);

		screen = new Screen(width, height);
		game = new Game();
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
		
		try {
			ironsight1 = ImageIO.read(Texture.class.getResource("/textures/ironsight1.png"));
			ironsight2 = ImageIO.read(Texture.class.getResource("/textures/ironsight2.png"));
		} catch (IOException e) {
		}

		input = new InputHandler();
		addKeyListener(input);
		addFocusListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);
	}

	// start thread
	public synchronized void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this, "game");
		thread.start();
	}

	// stop thread
	public synchronized void stop() {
		if (!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	// main game loop
	public void run() {
		int frameBuffer = 0;
		frames = 0;
		double unprocessedSeconds = 0;
		long previousTime = System.nanoTime();
		double secondsPerTick = 1 / 60.0;
		int tickCount = 0;
		boolean ticked = false;
		requestFocus();

		while (running) {
			long currentTime = System.nanoTime();
			long passedTime = currentTime - previousTime;
			previousTime = currentTime;
			unprocessedSeconds += passedTime / 1000000000.0;
			
			while (unprocessedSeconds > secondsPerTick) {
				tick();
				unprocessedSeconds -= secondsPerTick;
				ticked = true;
				tickCount++;
				if (tickCount % 60 == 0) {
					frames = frameBuffer;
					//System.out.println("FPS: " + frames);
					previousTime += 1000;
					frameBuffer = 0;
				}
				if (ticked) {
					render();
					frameBuffer++;
				}
			}
		}
	}

	private void tick() {
		game.tick(input.key);
		newX = InputHandler.MouseX;
		//newY = InputHandler.MouseY;
		if (newX > oldX) {
			Controller.turnRight = true;
		}
		if (newX < oldX) {
			Controller.turnLeft = true;
		}
		if (newX == oldX) {
			Controller.turnLeft = false;
			Controller.turnRight = false;
		}
		mouseSpeed = Math.abs(newX - oldX);
		oldX = newX;
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		screen.render(game);

		for (int i = 0; i < width * height; i++) {
			pixels[i] = screen.pixels[i];
		}

		Graphics g = bs.getDrawGraphics();
		g.drawImage(img, 0, 0, width + 10, height + 10, null);
		if (aiming) {
			g.drawImage(ironsight2, (width / 2) - (ironsight2.getWidth() / 2), (height / 2) - (ironsight2.getHeight() / 2), ironsight2.getWidth(), ironsight2.getHeight(), null);
		} else {
			g.drawImage(ironsight1, (width / 2) - (ironsight1.getWidth() / 2), (height / 2) - (ironsight1.getHeight() / 2), ironsight1.getWidth(), ironsight1.getHeight(), null);
		}
		g.setFont(new Font("Verdana", 1, 14));
		g.setColor(Color.RED);
		g.drawString("FPS: " + frames, 5, 15);
		g.dispose();
		bs.show();
	}
	
	
	public static Launcher getLauncherInstance() {
		if(launcher == null) {
			launcher = new Launcher();
		}
		return launcher;
	}

	public static void setGameWidthAndHeight() {
		resSelection = Config.loadConfig("res/settings/config.xml", "resSelection");
		if (resSelection == 0) {
			width = 640;
			height = 480;
		}
		if (resSelection == 1 || resSelection == -1) {
			width = 800;
			height = 600;
		}
		if (resSelection == 2) {
			width = 1024;
			height = 768;
		}
		if (resSelection == 3) {
			width = 854;
			height = 480;
		}
		if (resSelection == 4) {
			width = 960;
			height = 540;
		}
		if (resSelection == 5) {
			width = 1024;
			height = 576;
		}
		if (resSelection == 6) {
			width = 1366;
			height = 768;

		}
		if (resSelection == 7) {
			width = 1920;
			height = 1080;
		}
	}


	// main method
	public static void main(String[] args) {
		getLauncherInstance();
	}
}
