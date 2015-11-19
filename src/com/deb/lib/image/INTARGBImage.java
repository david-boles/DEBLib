package com.deb.lib.image;

import java.awt.image.BufferedImage;
import java.io.File;

public class INTARGBImage extends Image {
	
	public INTARGBImage(int width, int height) {
		super(width, height);
	}
	
	public INTARGBImage(File f) {
		super(f);
	}
	
	public INTARGBImage(Image i) {
		super(i);
	}

	@Override
	public int getType() {
		return BufferedImage.TYPE_INT_ARGB;
	}

}
