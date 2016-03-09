package space.davidboles.lib.neuralnetwork;

import space.davidboles.lib.math.ArrayFs;

public class DataSet {
	final int inputNumNeurons;
	final int outputNumNeurons;
	float[][] inputData;
	float[][] targetData;
	
	public DataSet(int inputNumNeurons, int outputNumNeurons) {
		this.inputNumNeurons = inputNumNeurons;
		this.outputNumNeurons = outputNumNeurons;
	}
	
	public void addDataItem(float[] input, float[] targetOut) throws IllegalArgumentException {
		if(input.length == inputNumNeurons && targetOut.length == outputNumNeurons) {
			float[][] newIn = new float[inputData.length+1][];
			float[][] newTarg = new float[targetData.length+1][];
			newIn[inputData.length] = input;
			newTarg[targetData.length] = targetOut;
			this.inputData = newIn;
			this.targetData = newTarg;
		}else throw new IllegalArgumentException("Data array lengths do not correspond to the number of input or output neurons.");
	}
	
	
	public float[][][] getDataCopy() {
		return new float[][][]{ArrayFs.copyDFA(inputData), ArrayFs.copyDFA(targetData)};
	}
	
}
