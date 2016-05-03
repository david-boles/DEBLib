package space.davidboles.lib.image;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image {
	BufferedImage image;
	
	public Image(int width, int height, int type) {
		this.image = new BufferedImage(width, height, this.getType());
	}
	
	public Image(File f) throws IOException {
		this.image = ImageIO.read(f);
	}
	
	public Image(Image i) {
		this.image = ImageFs.copyToType(i.image, i.getType());
	}
	
	public Image(BufferedImage i) {
		this.image = ImageFs.copyToType(i, i.getType());
	}
	
	/** 
	 * Type of the image
	 * 
	 * @return The type as defined by BufferedImage.TYPE_...
	 */
	public int getType() {
		return this.image.getType();
	}
	
	/**
	 * Returns a new image with the results from the transformation.
	 * @param tranformation
	 * @return New Image.
	 */
	public void applyImageTransform(ImageTransform tranform) {
		this.image = tranform.makeTransformedImage(this).image;
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
		return ImageFs.copyToType(this.image, this.image.getType());
	}
	
	public void setBufImageCopy(BufferedImage i) {
		this.image = ImageFs.copyToType(i, i.getType());
	}
	
	public Image getVersionWithType(int type) {
		if(this.getType() == type) return this;
		return new Image(ImageFs.copyToType(this.image, type));
	}
}
