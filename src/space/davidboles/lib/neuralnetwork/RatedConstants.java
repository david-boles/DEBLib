package space.davidboles.lib.neuralnetwork;

import space.davidboles.lib.math.ArrayFs;
import space.davidboles.lib.math.UsefulMaths;

public class RatedConstants {
	public float[][][] connectionScalers;
	public float[][][] connectionOffsets;
	public float accuracy;
	public int tests = 0;
	
	public RatedConstants(float[][][] connectionScalers, float[][][] connectionOffsets) {
		this.connectionOffsets = connectionOffsets;
		this.connectionScalers = connectionScalers;
	}
	
	public RatedConstants(float[][][] connectionScalers, float[][][] connectionOffsets, float[] output, float[] target) {
		this(connectionScalers, connectionOffsets);
		
		accuracy = this.getAccuracy(output, target);
		tests = 1;
	}
	
	public void addTest(float[] output, float[] target) {
		this.accuracy=((accuracy*tests)+(this.getAccuracy(output, target)))/(tests+1);
		tests++;
	}
	
	public static RatedConstants getRandomNew(NeuralNetwork nn) {
		float[][][] scalers = nn.getScalers();
		float[][][] offsets = nn.getOffsets();
		
		for(int i = 0; i < scalers.length; i++) {
			for(int ii = 0; ii < scalers[i].length; ii++) {
				for(int iii = 0; iii < scalers[i][ii].length; iii++) {
					scalers[i][ii][iii] = UsefulMaths.unitRandom();
					offsets[i][ii][iii] = UsefulMaths.unitRandom();
				}
			}
		}
		
		return new RatedConstants(scalers, offsets);
	}
	
	/**
	 * Copies the Constants of this into a new RatedConstants object and 
	 * @param randomization
	 * @return
	 */
	public RatedConstants getRandomizedCopy(float randomization) {
		float unRand = 1-randomization;
		
		RatedConstants copy = new RatedConstants(ArrayFs.copyTFA(this.connectionScalers), ArrayFs.copyTFA(this.connectionOffsets));
		
		for(int i = 0; i < copy.connectionScalers.length; i++) {
			for(int ii = 0; ii < copy.connectionScalers[i].length; ii++) {
				for(int iii = 0; iii < copy.connectionScalers[i][ii].length; iii++) {
					copy.connectionScalers[i][ii][iii] = ((UsefulMaths.unitRandom()*randomization)+(this.connectionScalers[i][ii][iii]*unRand));
					copy.connectionOffsets[i][ii][iii] = ((UsefulMaths.unitRandom()*randomization)+(this.connectionOffsets[i][ii][iii]*unRand));
				}
			}
		}
		
		return copy;
	}
	
	float getAccuracy(float[] output, float[] target) {
		float totalAbsDiff = 0;
		for(int i = 0; i < output.length; i++) {
			totalAbsDiff += 1-(Math.abs(output[i]-target[i]));
		}
		//System.out.println("calc acc " + (totalAbsDiff/output.length));
		return totalAbsDiff/output.length;
	}
}
