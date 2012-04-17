package com.mime.minefront.graphics;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Texture {
	public static Render floor = loadBitmap("/textures/floor.png");

	public static Render loadBitmap(String path) {
		try{
			BufferedImage image = ImageIO.read(Texture.class.getResource(path));
			int w = image.getWidth();
			int h = image.getHeight();
			Render result = new Render(w, h);
			image.getRGB(0, 0, w, h, result.pixels, 0, w);
			return result;
		} catch(Exception e) {
			System.out.println("Crash at Texture.loadBitMap");
			throw new RuntimeException(e);
		}
	}
}
