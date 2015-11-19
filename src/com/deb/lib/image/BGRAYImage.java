package com.deb.lib.image;

import java.awt.image.BufferedImage;
import java.io.File;

public class BGRAYImage extends Image {

	public BGRAYImage(int width, int height) {
		super(width, height);
	}
	
	public BGRAYImage(File f) {
		super(f);
	}
	
	public BGRAYImage(Image i) {
		super(i);
	}

	@Override
	public int getType() {
		return BufferedImage.TYPE_BYTE_GRAY;
	}

}
