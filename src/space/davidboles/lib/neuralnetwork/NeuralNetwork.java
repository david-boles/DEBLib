package space.davidboles.lib.neuralnetwork;

import java.util.Arrays;
import java.util.Random;

import space.davidboles.lib.math.ArrayFs;
import space.davidboles.lib.math.UsefulMaths;

public class NeuralNetwork {
	/**
	 * The number of neurons per layer of the network. This includes input and output layers.
	 */
	int[] layerNumNeurons;
	
	/**
	 * The scalers of the connections between layers. []--: From 0, the layer of connections (e.g. the number of the preceding layer). -[]-: The neuron of the following layer (e.g. the one getting input). --[]: The neuron of the preceding layer (e.g. the one giving output).
	 */
	float[][][] connectionScalers;
	float[][] absConnectionScalersTotal;//TODO make from powish?
	float[][][] powishConnectionScalers;
	
	/**
	 * The offsets of the connections between layers. []--: From 0, the layer of connections (e.g. the number of the preceding layer). -[]-: The neuron of the following layer (e.g. the one getting input). --[]: The neuron of the preceding layer (e.g. the one giving output).
	 */
	float[][][] connectionOffsets;
	float[][][] doubleConnectionOffsets;
	
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
		setScalers(empty);
		if(initRandom) empty = randomize(empty);
		setOffsets(empty);
	}
	
	/**
	 * Calculates the output of the NeuralNetwork off of input data. This method is not input safe e.g. it modifies the input array object.
	 * @param data Input data, length must be equal to the number of neurons in the 0th layer.
	 * @return The processed output data.
	 * @throws IllegalArgumentException
	 */
	public float[] process(float[] data) throws IllegalArgumentException {
		if(data.length == layerNumNeurons[0]) {
			for(int l = 1; l < layerNumNeurons.length; l++) {
				data = calculateLayerOutput(l, data);
			}
			return data;
		}else {
			throw new IllegalArgumentException("Invalid input data length, diffent that number of neurons in layer 0");
		}
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
	 * Sets all the scalers of the NeuralNetwork.
	 * @param scalers The scalers of the connections between layers. []--: From 0, the layer of connections (e.g. the number of the preceding layer). -[]-: The neuron of the following layer (e.g. the one getting input). --[]: The coefficient for the neuron of the preceding layer (e.g. the one giving output).
	 * @throws IllegalArgumentException Thrown if the length of the arrays do not work or their values are not between -1 and 1 or are 0.
	 */
	public void setScalers(float[][][] scalers) throws IllegalArgumentException {
		//CHECKS
		//Init fine variable
		boolean fine = true;
		//Check if there are the wrong number of layers
		if(fine && scalers.length != layerNumNeurons.length-1) fine = false;
		//Check if there are the wrong number of following neurons
		for(int layer = 0; fine && layer < scalers.length; layer++) {
			if(fine && scalers[layer].length != layerNumNeurons[layer+1]) fine = false;
			//Check if there are the wrong number of preceding neurons
			for(int folNeuron = 0; fine && folNeuron < scalers[layer].length; folNeuron++) {
				if(fine && scalers[layer][folNeuron].length != layerNumNeurons[layer]) fine = false; 
			}
		}
		//Throw array length exception
		if(!fine) throw new IllegalArgumentException("Incorrect array length");
		
		//Check values
		for(int layer = 0; fine && layer < scalers.length; layer++) {
			for(int folNeuron = 0; fine && folNeuron < scalers[layer].length; folNeuron++) {
				for(int preNeuron = 0; fine && preNeuron < scalers[layer][folNeuron].length; preNeuron++) {
					float val = scalers[layer][folNeuron][preNeuron];
					if(fine && (val < -1 || val > 1)) fine = false;
				}
			}
		}
		//Throw abs > 1 exception
		if(!fine) throw new IllegalArgumentException("Abs of value > 1");
		
		//USE
		if(fine) {
			//Set scalers
			connectionScalers = scalers;
			//Set abs scalers
			absConnectionScalersTotal = new float[scalers.length][];
			for(int layer = 0; fine && layer < scalers.length; layer++) {
				absConnectionScalersTotal[layer] = new float[scalers[layer].length];
				for(int folNeuron = 0; fine && folNeuron < scalers[layer].length; folNeuron++) {
					float total = 0;
					for(int preNeuron = 0; fine && preNeuron < scalers[layer][folNeuron].length; preNeuron++) {
						total += Math.abs(scalers[layer][folNeuron][preNeuron]);
					}
					absConnectionScalersTotal[layer][folNeuron] = total;
				}
			}
			//Set powish scalers
			powishConnectionScalers = new float[scalers.length][][];
			for(int layer = 0; fine && layer < scalers.length; layer++) {
				powishConnectionScalers[layer] = new float[scalers[layer].length][];
				for(int folNeuron = 0; fine && folNeuron < scalers[layer].length; folNeuron++) {
					powishConnectionScalers[layer][folNeuron] = new float[scalers[layer][folNeuron].length];
					for(int preNeuron = 0; fine && preNeuron < scalers[layer][folNeuron].length; preNeuron++) {
						powishConnectionScalers[layer][folNeuron][preNeuron] = UsefulMaths.powish(scalers[layer][folNeuron][preNeuron], 2);
					}
				}
			}
		}
	}
	
	/**
	 * Creates and returns a copy of the current scalers.
	 * @return The scalers of the connections between layers. []--: From 0, the layer of connections (e.g. the number of the preceding layer). -[]-: The neuron of the following layer (e.g. the one getting input). --[]: The coefficient for the neuron of the preceding layer (e.g. the one giving output).
	 */
	public float[][][] getScalers() {
		return ArrayFs.copyTFA(this.connectionScalers);
	}
	
	/**
	 * Sets all the offsets of the NeuralNetwork.
	 * @param offsets The offsets of the connections between layers. []--: From 0, the layer of connections (e.g. the number of the preceding layer). -[]-: The neuron of the following layer (e.g. the one getting input). --[]: The neuron of the preceding layer (e.g. the one giving output).
	 * @throws IllegalArgumentException Thrown if the length of the arrays do not work or their values are not between -1 and 1.
	 */
	public void setOffsets(float[][][] offsets) throws IllegalArgumentException {
		//CHECKS
		//Init fine variable
		boolean fine = true;
		//Check if there are the wrong number of layers
		if(fine && offsets.length != layerNumNeurons.length-1) fine = false;
		//Check if there are the wrong number of following neurons
		for(int layer = 0; fine && layer < offsets.length; layer++) {
			if(fine && offsets[layer].length != layerNumNeurons[layer+1]) fine = false;
			//Check if there are the wrong number of preceding neurons
			for(int folNeuron = 0; fine && folNeuron < offsets[layer].length; folNeuron++) {
				if(fine && offsets[layer][folNeuron].length != layerNumNeurons[layer]) fine = false; 
			}
		}
		//Throw array length exception
		if(!fine) throw new IllegalArgumentException("Incorrect array length");
		
		//Check values
		for(int layer = 0; fine && layer < offsets.length; layer++) {
			for(int folNeuron = 0; fine && folNeuron < offsets[layer].length; folNeuron++) {
				for(int preNeuron = 0; fine && preNeuron < offsets[layer][folNeuron].length; preNeuron++) {
					float val = offsets[layer][folNeuron][preNeuron];
					if(fine && (val < -1 || val > 1)) fine = false;
				}
			}
		}
		//Throw abs > 1 exception
		if(!fine) throw new IllegalArgumentException("Abs of value > 1");
		
		//USE
		if(fine) {
			//Set offsets
			connectionOffsets = offsets;
			//Set per following, neuron abs constant total
			doubleConnectionOffsets = new float[offsets.length][][];
			for(int layer = 0; fine && layer < offsets.length; layer++) {
				doubleConnectionOffsets[layer] = new float[offsets[layer].length][];
				for(int folNeuron = 0; fine && folNeuron < offsets[layer].length; folNeuron++) {
					doubleConnectionOffsets[layer][folNeuron] = new float[offsets[layer][folNeuron].length];
					for(int preNeuron = 0; fine && preNeuron < offsets[layer][folNeuron].length; preNeuron++) {
						float abs = Math.abs(offsets[layer][folNeuron][preNeuron]);
						doubleConnectionOffsets[layer][folNeuron][preNeuron] = abs;//TODO
					}
				}
			}
		}
	}

	/**
	 * Creates and returns a copy of the current offsets.
	 * @return The offsets of the connections between layers. []--: From 0, the layer of connections (e.g. the number of the preceding layer). -[]-: The neuron of the following layer (e.g. the one getting input). --[]: The neuron of the preceding layer (e.g. the one giving output).
	 */
	public float[][][] getOffsets() {
		return ArrayFs.copyTFA(this.connectionOffsets);
	}
	
	/**
	 * Calculates the value of a layer based on the value of a previous layer and the number of the layer you are calculating.
	 * @param layer Layer you are trying to calculate the output of.
	 * @param preLayerOutput The output from the layer previous to this or the input to the network if this is layer 0.
	 * @return The value of the layer you are trying to calculate.
	 */
	float[] calculateLayerOutput(int layer, float[] preLayerOutput) {
		//INITIALIZE
		//Layers and num neurons
		int outLayer = layer - 1;
		int inLayer = layer;
		int outNumNeurons = layerNumNeurons[outLayer];
		int inNumNeurons = layerNumNeurons[inLayer];
		
		//Populate outs per in
		float[][] perInCalcOuts = new float[inNumNeurons][outNumNeurons];
		for(int inNeuron = 0; inNeuron < inNumNeurons; inNeuron++) perInCalcOuts[inNeuron] = Arrays.copyOf(preLayerOutput, preLayerOutput.length);
		
		
		//CALC
		//Outs per in calc
		for(int folNeuron = 0; folNeuron < inNumNeurons; folNeuron++) {
			for(int preNeuron = 0; preNeuron < outNumNeurons; preNeuron++) {
				perInCalcOuts[folNeuron][preNeuron] = powishConnectionScalers[outLayer][folNeuron][preNeuron] * (perInCalcOuts[folNeuron][preNeuron] + doubleConnectionOffsets[outLayer][folNeuron][preNeuron]);
			}
		}
		
		//Ins averaging
		float[] dataPreSigmoid = new float[inNumNeurons];
		for(int folNeuron = 0; folNeuron < inNumNeurons; folNeuron++) {
			float total = 0;
			for(int preNeuron = 0; preNeuron < outNumNeurons; preNeuron++) {
				total += perInCalcOuts[folNeuron][preNeuron];
			}
			dataPreSigmoid[folNeuron] = total/absConnectionScalersTotal[outLayer][folNeuron];
		}
		
		//Sigmoid
		float[] dataPostSigmoid = new float[inNumNeurons];
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
		//System.out.println("IN:" + in);
		float out = in;
		out = (float)Math.pow(3, out);
		out += 1;
		out = -2/out;
		out += 1;
		//System.out.println("OUT:" + out);
		return out;
	}
}
