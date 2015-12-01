package com.deb.lib.image;

import java.awt.image.BufferedImage;
import java.io.File;

public class IARGBImage extends Image {
	
	public IARGBImage(int width, int height) {
		super(width, height);
	}
	
	public IARGBImage(File f) {
		super(f);
	}
	
	public IARGBImage(Image i) {
		super(i);
	}
	
	public IARGBImage(BufferedImage i) {
		super(i);
	}

	@Override
	public int getType() {
		return BufferedImage.TYPE_INT_ARGB;
	}

}
