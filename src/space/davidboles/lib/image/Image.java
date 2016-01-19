package space.davidboles.lib.image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Image {
	BufferedImage image;
	
	public Image(int width, int height) {
		this.image = new BufferedImage(width, height, this.getType());
	}
	
	public Image(File f) {
		this.readImage(f);
	}
	
	public Image(Image i) {
		this.image = this.convertToThisType(i.image);
	}
	
	public Image(BufferedImage i) {
		this.image = this.convertToThisType(i);
	}
	
	/** 
	 * Type of the image
	 * 
	 * @return The type as defined by BufferedImage.TYPE_...
	 */
	public abstract int getType();
	
	public boolean applyImageTransform(ImageTransform i) {
		if(i.getType() == this.getType() || i.getType() == -1) {
			return i.applyTransformation(this.image);
		}
		return false;
	}
	
	/**
	 * Convert specified image to specified type
	 * 
	 * @param image The starting image
	 * @param type The type as defined by BufferedImage.TYPE_...
	 * @return A new BufferedImage with image image and type type
	 */
	public static BufferedImage convertToType(BufferedImage image, int type) {
	    BufferedImage newImage = new BufferedImage(
	        image.getWidth(), image.getHeight(),
	        type);
	    Graphics2D g = newImage.createGraphics();
	    g.drawImage(image, 0, 0, null);
	    g.dispose();
	    return newImage;
	}
	
	/**
	 * Convert specified image to type this.getType()
	 * 
	 * @param image The starting image
	 * @return A new BufferedImage with image image and type this.getType()
	 */
	public BufferedImage convertToThisType(BufferedImage image) {
		return convertToType(image, this.getType());
	}

	public boolean readImage(File f) {
		try {
		    this.image = this.convertToThisType(ImageIO.read(f));
		    return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public boolean writeImage(String type, File f) {
		try {
		    ImageIO.write(this.convertToThisType(image), type, f);
		    return true;
		} catch (IOException e) {
			return false;
		}
	}

	public void setPixel(int x, int y, Color c) {
		image.setRGB(x, y, c.getRGB());
	}

	public Color getPixel(int x, int y) {
		int pixel = image.getRGB(x, y);
		return new Color(pixel, true);
	}

	public int getWidth() {
		return image.getWidth();
	}
	
	public int getHeight() {
		return image.getHeight();
	}
	
	public BufferedImage getBufImageCopy() {
		return this.convertToThisType(this.image);
	}
	
	public void setBufImageCopy(BufferedImage i) {
		this.image = this.convertToThisType(this.image);
	}
}
