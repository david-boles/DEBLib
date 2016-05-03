package space.davidboles.lib.image.transforms.matrix;

import java.awt.Color;
import java.awt.image.BufferedImage;

import space.davidboles.lib.color.ColorFs;
import space.davidboles.lib.image.Image;
import space.davidboles.lib.image.transforms.MatrixImageTransform;

public class ARGBComponentMatrixImageTransform extends MatrixImageTransform {

	protected float[][] matrix;
	protected boolean matrixCanInvert;
	
	public ARGBComponentMatrixImageTransform(float[][] matrix, int edgeAction, boolean matrixCanInvert) {
		super(edgeAction, matrix.length);
		
		if(matrix.length%2 != 1) throw new IllegalArgumentException("Matrix length not odd.");
		for(int x = 0; x < matrix.length; x++) if(matrix[x].length != matrix.length) throw new IllegalArgumentException("Matrix not square.");
	
		this.matrix	= matrix;
		this.matrixCanInvert = matrixCanInvert;
	}

	@Override
	
	public Image makeTransformedImage(Image i) {
		//Initialize output image with copy of input image
		Image out = new Image(new BufferedImage(i.getWidth(), i.getHeight(), i.getType()));
		
		//Iterate through each pixel in image
		for(int xCalc = 0; xCalc < out.getWidth(); xCalc++) {
			for(int yCalc = 0; yCalc < out.getHeight(); yCalc++) {
				
				//Initialize component totals and tested pixels counter
				float aTotal = 0;
				float rTotal = 0;
				float gTotal = 0;
				float bTotal = 0;
				int pixelsTested = 0;
				
				//Increment through each position in matrix
				for(int xMat = 0; xMat < this.matrixDim; xMat++) {
					for(int yMat = 0; yMat < this.matrixDim; yMat++) {
						
						//Calculate tested pixel position based off calculating pos, matrix pos, matrix rad, and edge action
						int xTest = getTestPx(xCalc + xMat - this.matrixRad, out.getWidth());
						int yTest = getTestPx(yCalc + yMat - this.matrixRad, out.getHeight());
						
						//Check if pixel should be tested
						if(xTest != -1 && yTest != -1) {
							
							//Get test pixel color
							Color pixTest = i.getPixel(xTest, yTest);
							//Increment component totals and tested counter
							aTotal += pixTest.getAlpha() * this.matrix[xMat][yMat];
							rTotal += pixTest.getRed() * this.matrix[xMat][yMat];
							gTotal += pixTest.getGreen() * this.matrix[xMat][yMat];
							bTotal += pixTest.getBlue() * this.matrix[xMat][yMat];
							pixelsTested++;
							
						}
						
					}
				}
				
				//Set output image pixel to average, bounded components (with offset and scale if matrix can invert
				if (this.matrixCanInvert) out.setPixel(xCalc, yCalc, new Color(ColorFs.boundComponentRound(127+(int)(0.5+(rTotal/(pixelsTested*2)))), ColorFs.boundComponentRound(127+(int)(0.5+(gTotal/(pixelsTested*2)))), ColorFs.boundComponentRound(127+(int)(0.5+(bTotal/(pixelsTested*2)))), ColorFs.boundComponentRound(127+(int)(0.5+(aTotal/(2*pixelsTested))))));
				else out.setPixel(xCalc, yCalc, new Color(ColorFs.boundComponentRound((int)(0.5+(rTotal/pixelsTested))), ColorFs.boundComponentRound((int)(0.5+(gTotal/pixelsTested))), ColorFs.boundComponentRound((int)(0.5+(bTotal/pixelsTested))), ColorFs.boundComponentRound((int)(0.5+(aTotal/pixelsTested)))));
				
			}
		}
		
		//Return output image
		return out;
	}

}
