package com.deb.lib.image;

import java.awt.image.BufferedImage;

public class INTARGBImage extends Image {

	@Override
	public int getType() {
		return BufferedImage.TYPE_INT_ARGB;
	}

}
