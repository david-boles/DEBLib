package com.deb.lib.image;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ImageFs {
	
	public static BufferedImage convertToARGB(BufferedImage image) {
	    BufferedImage newImage = new BufferedImage(
	        image.getWidth(), image.getHeight(),
	        BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g = newImage.createGraphics();
	    g.drawImage(image, 0, 0, null);
	    g.dispose();
	    return newImage;
	}
	
}
