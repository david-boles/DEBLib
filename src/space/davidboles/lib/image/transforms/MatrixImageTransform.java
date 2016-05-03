package space.davidboles.lib.image.transforms;

import space.davidboles.lib.image.ImageTransform;

public abstract class MatrixImageTransform implements ImageTransform {
	public static final int IGNORE_EDGES = 0;
	public static final int USE_EDGES = 1;
	public static final int MAP_EDGES = 2;
	
	int edgeAction;
	protected int matrixDim;
	protected int matrixRad;
	
	public MatrixImageTransform(int edgeAction, int matrixSize) {
		this.edgeAction = edgeAction;
		this.matrixDim = matrixSize;
		this.matrixRad = (int)(Math.floor(this.matrixDim/2.0));
	}
	
	protected int getTestPx(int pos, int dim) {
		switch(edgeAction) {
		case 0:
			if(pos < this.matrixRad) {
				return -1;
			}else if(pos >= dim-this.matrixRad) {
				return -1;
			}else {
				return pos;
			}
		
		case 1:
			if(pos < 0 || pos >= dim) return -1;
			return pos;
			
		case 2:
			return pos%dim;
			
		default:
			return -1;
		}
	}
	
}
