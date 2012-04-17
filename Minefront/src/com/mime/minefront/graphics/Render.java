package com.mime.minefront.graphics;

import com.mime.minefront.Display;

public class Render {
	protected int width;
	protected int height;
	public int[] pixels;

	public Render(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}

	public void draw(Render render, int xOffSet, int yOffSet) {
		for (int y = 0; y < render.height; y++) {
			int yPix = y + yOffSet;
			if(yPix < 0 || yPix >= Display.height) {
				continue;
			}
			for (int x = 0; x < render.width; x++) {
				int xPix = x + xOffSet;
				if(xPix < 0 || xPix >= Display.width) {
					continue;
				}
				
				int alpha = render.pixels[x + y * render.width];
				if(alpha > 0) {
					pixels[xPix + yPix * width] = alpha;
				}
			}
		}
	}
}
