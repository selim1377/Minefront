package com.mime.minefront.gui;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.mime.minefront.RunGame;

public class LauncherBackup extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	protected JPanel window = new JPanel();
	private JButton play, options, help, quit;
	private Rectangle rplay, roptions, rhelp, rquit;

	private final int WIDTH = 240;
	private final int HEIGHT = 320;
	protected final int BUTTON_WIDTH = 80;
	protected final int BUTTON_HEIGHT = 40;

	public LauncherBackup(int id) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		setTitle("Minefront Launcher");
		setSize(new Dimension(WIDTH, HEIGHT));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().add(window);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
		window.setLayout(null);

		if (id == 0) {
			drawButtons();
		}
		repaint();
	}

	private void drawButtons() {
		play = new JButton("Play!");
		rplay = new Rectangle((WIDTH / 2) - (BUTTON_WIDTH / 2), 90, BUTTON_WIDTH, BUTTON_HEIGHT);
		play.setBounds(rplay);
		play.addActionListener(this);
		window.add(play);

		options = new JButton("Options");
		roptions = new Rectangle((WIDTH / 2) - (BUTTON_WIDTH / 2), 140, BUTTON_WIDTH, BUTTON_HEIGHT);
		options.setBounds(roptions);
		options.addActionListener(this);
		window.add(options);

		help = new JButton("Help!");
		rhelp = new Rectangle((WIDTH / 2) - (BUTTON_WIDTH / 2), 190, BUTTON_WIDTH, BUTTON_HEIGHT);
		help.setBounds(rhelp);
		help.addActionListener(this);
		window.add(help);

		quit = new JButton("Quit...");
		rquit = new Rectangle((WIDTH / 2) - (BUTTON_WIDTH / 2), 240, BUTTON_WIDTH, BUTTON_HEIGHT);
		quit.setBounds(rquit);
		quit.addActionListener(this);
		window.add(quit);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == play) {
			dispose();
			new RunGame();
		}
		if (e.getSource() == options) {
			dispose();
			new Options();
		}
		if (e.getSource() == help) {

		}
		if (e.getSource() == quit) {
			WindowEvent windowClosing = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
			dispatchEvent(windowClosing);
			System.exit(0);
		}
	}
}
