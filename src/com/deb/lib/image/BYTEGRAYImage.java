package com.deb.lib.image;

import java.awt.image.BufferedImage;

public class BYTEGRAYImage extends Image {

	@Override
	public int getType() {
		return BufferedImage.TYPE_BYTE_GRAY;
	}

}
