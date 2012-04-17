package com.mime.minefront.graphics;

import com.mime.minefront.Game;
import com.mime.minefront.input.Controller;
import com.mime.minefront.level.Block;
import com.mime.minefront.level.Level;

public class Render3D extends Render {

	public double zBuffer[];
	public double zBufferWall[];
	private double renderDistance = 5000;
	private double forward, sideways, sin, cos, up, walking;

	public Render3D(int width, int height) {
		super(width, height);
		zBuffer = new double[width * height];
		zBufferWall = new double[width];
	}

	public void floor(Game game) {
		double ceilingPosition = 8;
		double floorPosition = 8;
		forward = game.controls.z;
		sideways = game.controls.x;
		up = game.controls.y;
		walking = 0;

		double rotation = game.controls.rotation;
		cos = Math.cos(rotation);
		sin = Math.sin(rotation);

		for (int x = 0; x < width; x++) {
			zBufferWall[x] = 0;
		}

		for (int y = 0; y < height; y++) {

			double ceiling = (y - height / 2.0) / height;

			double z = (floorPosition + up) / ceiling;
			if (Controller.walk) {
				walking = Math.sin(game.time / 5.0) * 0.5;
				if (Controller.crouching) {
					walking = Math.sin(game.time / 7.0) * 0.25;
				}
				if (Controller.sprinting) {
					walking = Math.sin(game.time / 3.0) * 0.8;
				}
				z = (floorPosition + up + walking) / ceiling;
			}

			if (ceiling < 0) {
				z = (ceilingPosition - up) / -ceiling;
				if (Controller.walk) {
					z = (ceilingPosition - up - walking) / -ceiling;
				}
			}

			for (int x = 0; x < width; x++) {
				double depth = (x - width / 2.0) / height;
				depth *= z;

				double xx = depth * cos + z * sin;
				double yy = z * cos - depth * sin;
				int xPix = (int) (xx + sideways);
				int yPix = (int) (yy + forward);
				zBuffer[x + y * width] = z;

				pixels[x + y * width] = Texture.floor.pixels[(xPix & 7) + (yPix & 7) * 16];

				if (z > 500) {
					pixels[x + y * width] = 0;
				}
			}
		}

		Level level = game.level;
		int size = 100;
		for (int xBlock = -size; xBlock <= size; xBlock++) {
			for (int zBlock = -size; zBlock <= size; zBlock++) {
				Block block = level.create(xBlock, zBlock);
				Block east = level.create(xBlock + 1, zBlock);
				Block south = level.create(xBlock, zBlock + 1);

				if (block.solid) {
					if (!east.solid) {
						renderWall(xBlock + 1, xBlock + 1, zBlock, zBlock + 1, 0);
						renderWall(xBlock + 1, xBlock + 1, zBlock, zBlock + 1, 1);
					}
					if (!south.solid) {
						renderWall(xBlock + 1, xBlock, zBlock + 1, zBlock + 1, 0);
						renderWall(xBlock + 1, xBlock, zBlock + 1, zBlock + 1, 1);
					}
				} else {
					if (east.solid) {
						renderWall(xBlock + 1, xBlock + 1, zBlock + 1, zBlock, 0);
						renderWall(xBlock + 1, xBlock + 1, zBlock + 1, zBlock, 1);
					}
					if (south.solid) {
						renderWall(xBlock, xBlock + 1, zBlock + 1, zBlock + 1, 0);
						renderWall(xBlock, xBlock + 1, zBlock + 1, zBlock + 1, 1);
					}
				}

			}
		}

	}

	public void renderWall(double xLeft, double xRight, double zDistanceLeft, double zDistanceRight, double yHeight) {
		double wallLenghtX = ((xRight - xLeft));
		double wallLenghtZ = ((zDistanceRight - zDistanceLeft));
		double wallLenght = Math.sqrt(wallLenghtX * wallLenghtX + wallLenghtZ * wallLenghtZ);
		yHeight /= 2;

		double upCorrection = 0.0625;
		double sidewaysCorrection = 0.0625;
		double forwardCorrection = 0.0625;
		double walkCorrection = 0.0625;

		double xcLeft = ((xLeft / 2) - sideways * sidewaysCorrection) * 2;
		double zcLeft = ((zDistanceLeft / 2) - forward * forwardCorrection) * 2;

		double rotLeftX = xcLeft * cos - zcLeft * sin;
		double yCornerTL = ((-yHeight) + (up * upCorrection + walking * walkCorrection)) * 2;
		double yCornerBL = ((+0.5 - yHeight) + (up * upCorrection + walking * walkCorrection)) * 2;
		double rotLeftZ = zcLeft * cos + xcLeft * sin;

		double xcRight = ((xRight / 2) - sideways * sidewaysCorrection) * 2;
		double zcRight = ((zDistanceRight / 2) - forward * forwardCorrection) * 2;

		double rotRightX = xcRight * cos - zcRight * sin;
		double yCornerTR = ((-yHeight) + (up * upCorrection + walking * walkCorrection)) * 2;
		double yCornerBR = ((+0.5 - yHeight) + (up * upCorrection + walking * walkCorrection)) * 2;
		double rotRightZ = zcRight * cos + xcRight * sin;

		double tex30 = 0;
		double tex40 = 8;
		double clip = 0.5;

		if (rotLeftZ < clip && rotRightZ < clip) {
			return;
		}

		if (rotLeftZ < clip) {
			double clip0 = (clip - rotLeftZ) / (rotRightZ - rotLeftZ);
			rotLeftZ = rotLeftZ + (rotRightZ - rotLeftZ) * clip0;
			rotLeftX = rotLeftX + (rotRightX - rotLeftX) * clip0;
			tex30 = tex30 + (tex40 - tex30) * clip0;
		}

		if (rotRightZ < clip) {
			double clip0 = (clip - rotLeftZ) / (rotRightZ - rotLeftZ);
			rotRightZ = rotLeftZ + (rotRightZ - rotLeftZ) * clip0;
			rotRightX = rotLeftX + (rotRightX - rotLeftX) * clip0;
			tex40 = tex30 + (tex40 - tex30) * clip0;
		}

		double xPixLeft = (rotLeftX / rotLeftZ * height + width / 2);
		double xPixRight = (rotRightX / rotRightZ * height + width / 2);

		if (xPixLeft >= xPixRight) {
			return;
		}

		int xPixLeftInt = (int) (xPixLeft);
		int xPixRightInt = (int) (xPixRight);

		if (xPixLeftInt < 0) {
			xPixLeftInt = 0;
		}
		if (xPixRightInt > width) {
			xPixRightInt = width;
		}

		double yPixLeftTop = (yCornerTL / rotLeftZ * height + height / 2.0);
		double yPixLeftBottom = (yCornerBL / rotLeftZ * height + height / 2.0);
		double yPixRightTop = (yCornerTR / rotRightZ * height + height / 2.0);
		double yPixRightBottom = (yCornerBR / rotRightZ * height + height / 2.0);

		double tex1 = 1 / rotLeftZ;
		double tex2 = 1 / rotRightZ;
		double tex3 = tex30 / rotLeftZ;
		double tex4 = tex40 / rotRightZ - tex3;

		for (int x = xPixLeftInt; x < xPixRightInt; x++) {
			double pixelRotation = (x - xPixLeft) / (xPixRight - xPixLeft);
			double zWall = (tex1 + (tex2 - tex1) * pixelRotation) * wallLenght;

			if (zBufferWall[x] > zWall) {
				continue;
			}
			zBufferWall[x] = zWall;

			int xTexture = (int) (((tex3 + tex4 * pixelRotation) / zWall));

			double yPixTop = yPixLeftTop + (yPixRightTop - yPixLeftTop) * pixelRotation;
			double yPixBottom = yPixLeftBottom + (yPixRightBottom - yPixLeftBottom) * pixelRotation;

			int yPixTopInt = (int) (yPixTop);
			int yPixBottomInt = (int) (yPixBottom);

			if (yPixTopInt < 0) {
				yPixTopInt = 0;
			}

			if (yPixBottomInt > height) {
				yPixBottomInt = height;
			}

			for (int y = yPixTopInt; y < yPixBottomInt; y++) {
				double pixelRotationY = (y - yPixTop) / (yPixBottom - yPixTop);
				int yTexture = (int) ((pixelRotationY * 8));
				pixels[x + y * width] = Texture.floor.pixels[((xTexture & 7) + 8) + (yTexture & 7) * 16];
				//	pixels[x + y * width] = xTexture * 100 + yTexture * 100 * 256;
				zBuffer[x + y * width] = 1 / (tex1 + (tex2 - tex1) * pixelRotation) * 8;
			}
		}
	}

	public void renderDistanceLimiter() {
		for (int i = 0; i < width * height; i++) {
			int colour = pixels[i];
			int brightness = (int) (renderDistance / (zBuffer[i]));

			if (brightness < 0) {
				brightness = 0;
			}
			if (brightness > 255) {
				brightness = 255;
			}

			int r = (colour >> 16) & 0xff;
			int g = (colour >> 8) & 0xFF;
			int b = (colour) & 0xFF;

			r = r * brightness / 255;
			g = g * brightness / 255;
			b = b * brightness / 255;

			pixels[i] = r << 16 | g << 8 | b;
		}
	}

}
