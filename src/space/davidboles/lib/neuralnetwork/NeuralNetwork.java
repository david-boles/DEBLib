package space.davidboles.lib.neuralnetwork;

import java.util.Arrays;
import java.util.Random;

import space.davidboles.lib.math.UsefulMaths;

public class NeuralNetwork {
	/**
	 * The number of neurons per layer of the network. This includes input and output layers.
	 */
	int[] layerNumNeurons;
	
	/**
	 * The coefficients of the connections between layers. []--: From 0, the layer of connections (e.g. the number of the preceding layer). -[]-: The neuron of the following layer (e.g. the one getting input). --[]: The coefficient for the neuron of the preceding layer (e.g. the one giving output).
	 */
	float[][][] connectionCoefficients;
	float[][] absConnectionCoefficientsTotal;
	float[][][] powishConnectionCoefficients;
	
	/**
	 * The constants of the connections between layers. []--: From 0, the layer of connections (e.g. the number of the preceding layer). -[]-: The neuron of the following layer (e.g. the one getting input). --[]: The neuron of the preceding layer (e.g. the one giving output).
	 */
	float[][][] connectionConstants;
	float[][][] doubleConnectionConstants;
	
	public NeuralNetwork(int[] neuronsPerLayer, boolean initRandom) {
		this.layerNumNeurons = neuronsPerLayer;
		
		float[][][] empty = new float[neuronsPerLayer.length-1][][];
		for(int layer = 0; layer < empty.length; layer++) {
			empty[layer] = new float[neuronsPerLayer[layer+1]][];
			for(int folNeuron = 0; folNeuron < empty[layer].length; folNeuron++) {
				empty[layer][folNeuron] = new float[neuronsPerLayer[layer]];
			}
		}
		if(initRandom) empty = randomize(empty);
		setCoefficients(empty);
		if(initRandom) empty = randomize(empty);
		setConstants(empty);
	}
	
	/**
	 * Randomizes the float[][][] with values between -1 and 1
	 * @param in Input
	 * @return Randomized input with values between -1 and 1
	 */
	float[][][] randomize(float[][][] in) {
		Random random = new Random();
		for(int i = 0; i < in.length; i++) {
			for(int ii = 0; ii < in[i].length; ii++) {
				for(int iii = 0; iii < in[i][ii].length; iii++) {
					boolean negative = random.nextBoolean();
					float val = random.nextFloat();
					if(negative) val *= -1;
					in[i][ii][iii] = val;
				}
			}
		}
		return in;
	}
	
	/**
	 * Sets all the coefficients of the NeuralNetwork.
	 * @param coefficients The coefficients of the connections between layers. []--: From 0, the layer of connections (e.g. the number of the preceding layer). -[]-: The neuron of the following layer (e.g. the one getting input). --[]: The coefficient for the neuron of the preceding layer (e.g. the one giving output).
	 * @throws IllegalArgumentException Thrown if the length of the arrays do not work or their values are not between -1 and 1 or are 0.
	 */
	public void setCoefficients(float[][][] coefficients) throws IllegalArgumentException {
		//CHECKS
		//Init fine variable
		boolean fine = true;
		//Check if there are the wrong number of layers
		if(fine && coefficients.length != layerNumNeurons.length-1) fine = false;
		//Check if there are the wrong number of following neurons
		for(int layer = 0; fine && layer < coefficients.length; layer++) {
			if(fine && coefficients[layer].length != layerNumNeurons[layer+1]) fine = false;
			//Check if there are the wrong number of preceding neurons
			for(int folNeuron = 0; fine && folNeuron < coefficients[layer].length; folNeuron++) {
				if(fine && coefficients[layer][folNeuron].length != layerNumNeurons[layer]) fine = false; 
			}
		}
		//Throw array length exception
		if(!fine) throw new IllegalArgumentException("Incorrect array length");
		
		//Check values
		for(int layer = 0; fine && layer < coefficients.length; layer++) {
			for(int folNeuron = 0; fine && folNeuron < coefficients[layer].length; folNeuron++) {
				for(int preNeuron = 0; fine && preNeuron < coefficients[layer][folNeuron].length; preNeuron++) {
					float val = coefficients[layer][folNeuron][preNeuron];
					if(fine && (val < -1 || val > 1)) fine = false;
				}
			}
		}
		//Throw abs > 1 exception
		if(!fine) throw new IllegalArgumentException("Abs of value > 1");
		
		//USE
		if(fine) {
			//Set coefficients
			connectionCoefficients = coefficients;
			//Set abs coefficients
			absConnectionCoefficientsTotal = new float[coefficients.length][];
			for(int layer = 0; fine && layer < coefficients.length; layer++) {
				absConnectionCoefficientsTotal[layer] = new float[coefficients[layer].length];
				for(int folNeuron = 0; fine && folNeuron < coefficients[layer].length; folNeuron++) {
					//absConnectionCoefficientsTotal[layer][folNeuron] = new float[coefficients[layer][folNeuron].length];
					float total = 0;
					for(int preNeuron = 0; fine && preNeuron < coefficients[layer][folNeuron].length; preNeuron++) {
						//absConnectionCoefficientsTotal[layer][folNeuron][preNeuron] = Math.abs(coefficients[layer][folNeuron][preNeuron]);
						total += Math.abs(coefficients[layer][folNeuron][preNeuron]);
					}
					absConnectionCoefficientsTotal[layer][folNeuron] = total;
				}
			}
			//Set powish coefficients
			powishConnectionCoefficients = new float[coefficients.length][][];
			for(int layer = 0; fine && layer < coefficients.length; layer++) {
				powishConnectionCoefficients[layer] = new float[coefficients[layer].length][];
				for(int folNeuron = 0; fine && folNeuron < coefficients[layer].length; folNeuron++) {
					powishConnectionCoefficients[layer][folNeuron] = new float[coefficients[layer][folNeuron].length];
					for(int preNeuron = 0; fine && preNeuron < coefficients[layer][folNeuron].length; preNeuron++) {
						powishConnectionCoefficients[layer][folNeuron][preNeuron] = UsefulMaths.powish(coefficients[layer][folNeuron][preNeuron], 2);
					}
				}
			}
		}
	}
	
	/**
	 * Sets all the constants of the NeuralNetwork.
	 * @param constants The constants of the connections between layers. []--: From 0, the layer of connections (e.g. the number of the preceding layer). -[]-: The neuron of the following layer (e.g. the one getting input). --[]: The neuron of the preceding layer (e.g. the one giving output).
	 * @throws IllegalArgumentException Thrown if the length of the arrays do not work or their values are not between -1 and 1.
	 */
	public void setConstants(float[][][] constants) throws IllegalArgumentException {
		//CHECKS
		//Init fine variable
		boolean fine = true;
		//Check if there are the wrong number of layers
		if(fine && constants.length != layerNumNeurons.length-1) fine = false;
		//Check if there are the wrong number of following neurons
		for(int layer = 0; fine && layer < constants.length; layer++) {
			if(fine && constants[layer].length != layerNumNeurons[layer+1]) fine = false;
			//Check if there are the wrong number of preceding neurons
			for(int folNeuron = 0; fine && folNeuron < constants[layer].length; folNeuron++) {
				if(fine && constants[layer][folNeuron].length != layerNumNeurons[layer]) fine = false; 
			}
		}
		//Throw array length exception
		if(!fine) throw new IllegalArgumentException("Incorrect array length");
		
		//Check values
		for(int layer = 0; fine && layer < constants.length; layer++) {
			for(int folNeuron = 0; fine && folNeuron < constants[layer].length; folNeuron++) {
				for(int preNeuron = 0; fine && preNeuron < constants[layer][folNeuron].length; preNeuron++) {
					float val = constants[layer][folNeuron][preNeuron];
					if(fine && (val < -1 || val > 1)) fine = false;
				}
			}
		}
		//Throw abs > 1 exception
		if(!fine) throw new IllegalArgumentException("Abs of value > 1");
		
		//USE
		if(fine) {
			//Set constants
			connectionConstants = constants;
			//Set per following, neuron abs constant total
			doubleConnectionConstants = new float[constants.length][][];
			for(int layer = 0; fine && layer < constants.length; layer++) {
				doubleConnectionConstants[layer] = new float[constants[layer].length][];
				for(int folNeuron = 0; fine && folNeuron < constants[layer].length; folNeuron++) {
					doubleConnectionConstants[layer][folNeuron] = new float[constants[layer][folNeuron].length];
					for(int preNeuron = 0; fine && preNeuron < constants[layer][folNeuron].length; preNeuron++) {
						doubleConnectionConstants[layer][folNeuron][preNeuron] = Math.abs(constants[layer][folNeuron][preNeuron]);
					}
				}
			}
		}
	}

	/**
	 * Calculates the value of a layer based on the value of a previous layer and the number of the layer you are calculating.
	 * @param layer Layer you are trying to calculate the output of.
	 * @param preLayerOutput The output from the layer previous to this or the input to the network if this is layer 0.
	 * @return The value of the layer you are trying to calculate.
	 */
	public float[] calculateLayerOutput(int layer, float[] preLayerOutput) {
		//INITIALIZE
		//Layers and num neurons
		int outLayer = layer - 1;
		int inLayer = layer;
		int outNumNeurons = layerNumNeurons[outLayer];
		int inNumNeurons = layerNumNeurons[inLayer];

		//Populate outs per in
		float[][] perInCalcOuts = new float[outNumNeurons][inNumNeurons];
		for(int inNeuron = 0; inNeuron < inNumNeurons; inNeuron++) perInCalcOuts[inNeuron] = Arrays.copyOf(preLayerOutput, preLayerOutput.length);
		
		
		//CALC
		//Outs per in calc
		for(int folNeuron = 0; folNeuron < inNumNeurons; folNeuron++) {
			for(int preNeuron = 0; preNeuron < outNumNeurons; preNeuron++) {
				perInCalcOuts[folNeuron][preNeuron] = powishConnectionCoefficients[outLayer][folNeuron][preNeuron] * (perInCalcOuts[folNeuron][preNeuron] + doubleConnectionConstants[outLayer][folNeuron][preNeuron]);
			}
		}
		
		//Ins averaging
		float[] dataPreSigmoid = new float[inLayer];
		for(int folNeuron = 0; folNeuron < inNumNeurons; folNeuron++) {
			float total = 0;
			for(int preNeuron = 0; preNeuron < outNumNeurons; preNeuron++) {
				total += perInCalcOuts[folNeuron][preNeuron];
			}
			dataPreSigmoid[folNeuron] = total/absConnectionCoefficientsTotal[outLayer][folNeuron];
		}
		
		//Sigmoid
		float[] dataPostSigmoid = new float[inLayer];
		for(int folNeuron = 0; folNeuron < inNumNeurons; folNeuron++) {
			dataPostSigmoid[folNeuron] = calculateSigmoid(dataPreSigmoid[folNeuron]);
		}
		
		//Return
		return dataPostSigmoid;
	}
	
	/**
	 * Runs the input through a modified sigmoid function.
	 * @param in Input.
	 * @return Output.
	 */
	float calculateSigmoid(float in) {
		float out = in;
		out = (float)Math.pow(3, out);
		out += 1;
		out = -2/out;
		out += 1;
		return out;
	}
}
