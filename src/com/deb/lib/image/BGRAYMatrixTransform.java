package com.deb.lib.image;

import java.awt.image.BufferedImage;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;

@Deprecated
public abstract class BGRAYMatrixTransform extends MatrixImageTransform {
	
	public BGRAYMatrixTransform(int edgeAction) {
		super(edgeAction);
	}

	@Override
	public int getType() {
		return BufferedImage.TYPE_BYTE_GRAY;
	}

	@Override
	void applyTransformationChecked(BufferedImage i) {
		i.setData(this.applyMatrix(i));
	}
	
	abstract float[][] getMatrix();

	WritableRaster applyMatrix(BufferedImage i) {
		WritableRaster wr = i.getRaster();
		float[][] matrix = this.getMatrix();
		WritableRaster outwr = wr.createCompatibleWritableRaster(0, 0, wr.getWidth(), wr.getHeight());
		
		for(int xPos = 0; xPos < wr.getWidth(); xPos++) {
			for(int yPos = 0; yPos < wr.getHeight(); yPos++) {
				outwr.setDataElements(xPos, yPos, this.getMatrixResult(xPos, yPos, wr, matrix));
			}
		}
		
		return outwr;
	}
	
	byte[] getMatrixResult(int x, int y, WritableRaster wr, float[][] matrix) {
		float total = 0;
		int offX = x - this.matrixRad;
		int offY = y - this.matrixRad;
		float px = 0;
		
		for(int mXPos = 0; mXPos < matrix.length; mXPos++) {
			for(int mYPos = 0; mYPos < matrix[0].length; mYPos++) {
				try{
					px = ((byte[])wr.getDataElements(this.getPx(offX+mXPos, wr.getWidth()), this.getPx(offY+mYPos, wr.getHeight()), new byte[]{0}))[0];
					px += 128;
					
					float value = (px * matrix[mXPos][mYPos]);
					
					System.out.println(value);
					
					total += value;
				}catch (Exception e) {
					//e.printStackTrace(System.err);
				}
			}
		}
		
		return new byte[]{(byte)(total-128)};
	}
}
