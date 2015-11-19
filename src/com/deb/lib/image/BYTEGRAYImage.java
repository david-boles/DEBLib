package com.deb.lib.image;

import java.awt.image.BufferedImage;
import java.io.File;

public class BYTEGRAYImage extends Image {

	public BYTEGRAYImage(int width, int height) {
		super(width, height);
	}
	
	public BYTEGRAYImage(File f) {
		super(f);
	}
	
	public BYTEGRAYImage(Image i) {
		super(i);
	}

	@Override
	public int getType() {
		return BufferedImage.TYPE_BYTE_GRAY;
	}

}
