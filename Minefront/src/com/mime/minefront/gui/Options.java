package com.mime.minefront.gui;

import java.awt.Choice;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.mime.minefront.Config;
import com.mime.minefront.Display;

public class Options extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final int WIDTH = 540;
	private final int HEIGHT = 440;

	private JPanel window = new JPanel();
	private JButton OK;
	private JLabel resLabel, fullScreenLabel;
	private Choice res, fullScreen;

	public Options() {
		setTitle("Options - Minefront Launcher");
		setSize(new Dimension(WIDTH, HEIGHT));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
		
		getContentPane().add(window);
		window.setLayout(null);

		drawButtons();
		repaint();
	}

	private void drawButtons() {
		OK = new JButton("OK!");
		OK.setBounds((WIDTH - 100), (HEIGHT - 70), 80, 30);
		OK.addActionListener(this);
		window.add(OK);
		
		res = new Choice();
		resLabel = new JLabel("Resolution: ");
		res.setBounds(150, 80, 130, 25);
		resLabel.setBounds(50, 77, 130, 25);
		res.add("640, 480");
		res.add("800, 600");
		res.add("1024, 768");
		res.add("854, 480 (Widescreen)");
		res.add("960, 540 (Widescreen)");
		res.add("1024, 576 (Widescreen)");
		res.add("1366, 768 (Widescreen)");
		res.select(Config.loadConfig("res/settings/config.xml", "resSelection"));
		window.add(res);
		window.add(resLabel);
		
		fullScreen = new Choice();
		fullScreenLabel = new JLabel("Full Screen: ");
		fullScreen.setBounds(150, 105, 130, 25);
		fullScreenLabel.setBounds(50, 105, 130, 25);
		fullScreen.add("On");
		fullScreen.add("Off");
		fullScreen.add("Borderless");
		fullScreen.select(Config.loadConfig("res/settings/config.xml", "fullScreenSelection"));
		window.add(fullScreen);
		window.add(fullScreenLabel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == OK) {
			Display.resSelection = res.getSelectedIndex();
			Display.fullScreenSelection = fullScreen.getSelectedIndex();
			Config.saveConfig("res/settings/config.xml", "resSelection", Display.resSelection);
			Config.saveConfig("res/settings/config.xml", "fullScreenSelection", Display.fullScreenSelection);
			Display.setGameWidthAndHeight();
			dispose();
		}
	}

}
