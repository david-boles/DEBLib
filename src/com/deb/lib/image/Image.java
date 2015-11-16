package com.deb.lib.image;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image {
	
	private BufferedImage vImage = null;
	
	public Image(int x, int y) {
		vImage = ImageFs.convertToARGB(new BufferedImage(x, y, BufferedImage.TYPE_INT_ARGB));
	}
	
	public Image(File f) {
		this.readImage(f);
	}
	
	public boolean readImage(File f) {
		
		try {
		    this.vImage = ImageFs.convertToARGB(ImageIO.read(f));
		    return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public boolean writeImage(String type, File f) {
		try {
		    ImageIO.write(vImage, type, f);
		    return true;
		} catch (IOException e) {
			return false;
		}
	}

	public void setPixel(int x, int y, Color c) {
		vImage.setRGB(x, y, c.getRGB());
	}

	public Color getPixel(int x, int y) {
		int pixel = vImage.getRGB(x, y);
		return new Color(pixel, true);
	}

	public int getWidth() {
		return vImage.getWidth();
	}
	
	public int getHeight() {
		return vImage.getHeight();
	}
	
}
