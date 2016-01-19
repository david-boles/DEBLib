package space.davidboles.lib.image;

@Deprecated
public class BGRAYBlurTransform extends BGRAYMatrixTransform {

	public BGRAYBlurTransform(int edgeAction) {
		super(edgeAction);
	}

	@Override
	float[][] getMatrix() {
		float[][] m = {{0.1111111f,0.1111111f,0.1111111f},{0.1111111f,0.1111111f,0.1111111f},{0.1111111f,0.1111111f,0.1111111f}};
		this.updateMatrixDimension(3);
		return m;
	}

}
