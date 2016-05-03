package space.davidboles.lib.image;

import java.awt.image.BufferedImage;

public interface ImageTransform {
	
	/**
	 * Applies the transformation to an Image, returns the result.
	 * 
	 * @param i The Image to which the transformation is applied.
	 * @return A new Image with the results from the transformation.
	 */
	public abstract Image makeTransformedImage(Image i);
	
}
