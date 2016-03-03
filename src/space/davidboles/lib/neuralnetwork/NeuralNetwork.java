package space.davidboles.lib.neuralnetwork;

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
	float[][][] absConnectionCoefficients;
	float[][][] powishConnectionCoefficients;
	
	/**
	 * The constants of the connections between layers. []--: From 0, the layer of connections (e.g. the number of the preceding layer). -[]-: The neuron of the following layer (e.g. the one getting input). --[]: The neuron of the preceding layer (e.g. the one giving output).
	 */
	float[][][] connectionConstants;
	float[][] doubleConstantTotal;//TODO need just double
	
	public NeuralNetwork() throws IllegalArgumentException {
		//TODO
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
			absConnectionCoefficients = new float[coefficients.length][][];
			for(int layer = 0; fine && layer < coefficients.length; layer++) {
				absConnectionCoefficients[layer] = new float[coefficients[layer].length][];
				for(int folNeuron = 0; fine && folNeuron < coefficients[layer].length; folNeuron++) {
					absConnectionCoefficients[layer][folNeuron] = new float[coefficients[layer][folNeuron].length];
					for(int preNeuron = 0; fine && preNeuron < coefficients[layer][folNeuron].length; preNeuron++) {
						absConnectionCoefficients[layer][folNeuron][preNeuron] = Math.abs(coefficients[layer][folNeuron][preNeuron]);
					}
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
			doubleConstantTotal = new float[constants.length][];
			for(int layer = 0; fine && layer < constants.length; layer++) {
				doubleConstantTotal[layer] = new float[constants[layer].length];
				for(int folNeuron = 0; fine && folNeuron < constants[layer].length; folNeuron++) {
					float absTotal = 0;
					for(int preNeuron = 0; fine && preNeuron < constants[layer][folNeuron].length; preNeuron++) {
						absTotal += Math.abs(constants[layer][folNeuron][preNeuron]*3f);
					}
					doubleConstantTotal[layer][folNeuron] = absTotal;
				}
			}
		}
	}
}
