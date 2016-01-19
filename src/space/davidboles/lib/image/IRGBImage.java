package space.davidboles.lib.image;

import java.awt.image.BufferedImage;
import java.io.File;

public class IRGBImage extends Image {
	
	public IRGBImage(int width, int height) {
		super(width, height);
	}
	
	public IRGBImage(File f) {
		super(f);
	}
	
	public IRGBImage(Image i) {
		super(i);
	}
	
	public IRGBImage(BufferedImage i) {
		super(i);
	}

	@Override
	public int getType() {
		return BufferedImage.TYPE_INT_RGB;
	}

}
