package com.deb.lib.image;

public abstract class MatrixImageTransform extends ImageTransform {
	public static final int IGNORE_EDGES = 0;
	public static final int USE_EDGES = 1;
	public static final int MAP_EDGES = 2;
	public static final int REMOVE_EDGES = 3;
	
	public MatrixImageTransform(int edgeAction) {
		//NOTE: Incomplete...
	}
}
