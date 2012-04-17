package com.mime.minefront.graphics;

import java.util.Random;

import com.mime.minefront.Game;

public class Screen extends Render {

	
	private Render test;
	private Render3D render;
	private Random random = new Random();
	private int w, h;

	public Screen(int width, int height) {
		super(width, height);
		test = new Render(w = 256, h = 256);
		render = new Render3D(width, height);
		for (int i = 0; i < (w * h); i++) {
			test.pixels[i] = random.nextInt() * random.nextInt(5) / 4;
		}
	}

	public void render(Game game) {
		for (int i = 0; i < width * height; i++) {
			pixels[i] = 0;
		}

		render.floor(game);
		//render.renderWall(0, 0.5, 2.0, 2.0, 0);
		//render.renderWall(0, 0, 1.5, 2.0, 0);
		//render.renderWall(0.5, 0.5, 1.5, 2.0, 0);
		//render.renderWall(0, 0.5, 1.5, 1.5, 0);
		render.renderDistanceLimiter();
		draw(render, 0, 0);
	}
}
