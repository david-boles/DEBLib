package com.deb.lib.image;

import java.awt.image.BufferedImage;

public abstract class BGRAYMatrixTransform extends ImageTransform {

	@Override
	public int getType() {
		return BufferedImage.TYPE_BYTE_GRAY;
	}

	@Override
	void applyTransformationChecked(BufferedImage i) {
		//NOTE: Incomplete

	}

}
