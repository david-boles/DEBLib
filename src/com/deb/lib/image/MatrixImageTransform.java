package com.deb.lib.image;

public abstract class MatrixImageTransform extends ImageTransform {
	public static final int IGNORE_EDGES = 0;
	public static final int USE_EDGES = 1;
	public static final int MAP_EDGES = 2;
	
	int edgeAction;
	int matrixDim;
	int matrixRad;
	
	public MatrixImageTransform(int edgeAction) {
		this.edgeAction = edgeAction;
	}
	
	public void updateMatrixDimension(int dim) {
		this.matrixDim = dim;
		this.matrixRad = (int)(Math.floor(matrixDim/2));
	}
	
	int getPx(int pos, int dim) {
		switch(edgeAction) {
		case 0:
			if(pos < this.matrixRad) {
				return -1;
			}else if(pos > dim-this.matrixRad) {
				return -1;
			}else {
				return pos;
			}
		
		case 1:
			return pos;
			
		case 2:
			return pos%dim;
			
		default:
			return -1;
		}
	}
	
}
