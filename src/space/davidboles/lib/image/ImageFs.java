package space.davidboles.lib.image;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ImageFs {
	/**
	 * Convert specified image to specified type
	 * 
	 * @param image The starting image
	 * @param type The type as defined by BufferedImage.TYPE_...
	 * @return A new BufferedImage with image image and type type
	 */
	public static BufferedImage copyToType(BufferedImage image, int type) {
	    BufferedImage newImage = new BufferedImage(
	        image.getWidth(), image.getHeight(),
	        type);
	    Graphics2D g = newImage.createGraphics();
	    g.drawImage(image, 0, 0, null);
	    g.dispose();
	    return newImage;
	}
}
