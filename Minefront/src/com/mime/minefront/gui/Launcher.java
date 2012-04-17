package com.mime.minefront.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.UIManager;

import com.mime.minefront.RunGame;
import com.mime.minefront.input.InputHandler;

public class Launcher extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;

	private final int WIDTH = 800;
	private final int HEIGHT = 400;
	protected final int BUTTON_WIDTH = 80;
	protected final int BUTTON_HEIGHT = 40;
	
	private boolean running = false;
	private Thread thread;

	public Launcher() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		setUndecorated(true);
		setSize(new Dimension(WIDTH, HEIGHT));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
		startMenu();

		InputHandler input = new InputHandler();
		addKeyListener(input);
		addFocusListener(input);
		addMouseMotionListener(input);
		addMouseListener(input);

		repaint();
	}

	public void updateFrame() {
		if (InputHandler.dragged) {
			int x = getX();
			int y = getY();
			setLocation(x + InputHandler.MouseDX - InputHandler.MousePX, y + InputHandler.MouseDY - InputHandler.MousePY);
		}
	}

	public void startMenu() {
		running = true;
		thread = new Thread(this, "menu");
		thread.start();
	}

	public void stopMenu() {
		running = false;
		try {
			dispose();
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public void run() {
		requestFocus();
		while (running) {
			try{
			renderMenu();
			} catch(IllegalStateException e) {
			System.out.println("Handled 19");	
			}
			updateFrame();
		}
	}

	private void renderMenu() throws IllegalStateException {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		try {
			g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/launcher.png")), 0, 0, 800, 400, null);
			if (InputHandler.MouseX > 690 && InputHandler.MouseX < 690 + 80 && InputHandler.MouseY > 130 && InputHandler.MouseY < 130 + 30) {
				g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/play_on.png")), 690, 130, 80, 30, null);
				g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/arrow.png")), 770, 132, 20, 20, null);
				if(InputHandler.Mouse[1]) {
					dispose();
					new RunGame();
				}
			} else {
				g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/play_off.png")), 690, 130, 80, 30, null);
			}

			if (InputHandler.MouseX > 650 && InputHandler.MouseX < 650 + 120 && InputHandler.MouseY > 165 && InputHandler.MouseY < 165 + 30) {
				g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/options_on.png")), 650, 165, 120, 30, null);
				g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/arrow.png")), 770, 167, 20, 20, null);
				if(InputHandler.Mouse[1]) {
						new Options();
				}
			} else {
				g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/options_off.png")), 650, 165, 120, 30, null);
			}

			if (InputHandler.MouseX > 698 && InputHandler.MouseX < 698 + 70 && InputHandler.MouseY > 200 && InputHandler.MouseY < 200 + 30) {
				g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/help_on.png")), 698, 200, 70, 30, null);
				g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/arrow.png")), 770, 202, 20, 20, null);
				if(InputHandler.Mouse[1]) {
					//TODO add action code
				}
			} else {
				g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/help_off.png")), 698, 200, 70, 30, null);
			}

			if (InputHandler.MouseX > 700 && InputHandler.MouseX < 700 + 80 && InputHandler.MouseY > 235 && InputHandler.MouseY < 235 + 30) {
				g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/quit_on.png")), 700, 235, 70, 30, null);
				g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/arrow.png")), 770, 237, 20, 20, null);
				if(InputHandler.Mouse[1]) {
					System.exit(0);
				}
			} else {
				g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/quit_off.png")), 702, 235, 70, 30, null);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		bs.show();
	}
}
