package com.deb.lib.image;

import java.awt.image.BufferedImage;

public abstract class ImageTransform {
	
	/**
	 * Type of the transformation
	 * 
	 * @return The type as defined by BufferedImage.TYPE_..., -1 if unimportant.
	 */
	public abstract int getType();
	
	/**
	 * Public transformation application, checks type
	 * 
	 * @param i The image to which the transformation is applied
	 * @return True if applied, false if incorrect type
	 */
	public boolean applyTransformation(BufferedImage i) {
		if(i.getType() == this.getType() || i.getType() == -1) {
			this.applyTransformation(i);
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * Private transformation application, called if applyTransformation finds the image has the right type
	 * 
	 * @param i The image to which the transformation is applied
	 */
	abstract void applyTransformationChecked(BufferedImage i);
	
}
