package space.davidboles.lib.neuralnetwork;

public class NeuralNetwork {
	/**
	 * The number of neurons per layer of the network. This includes input and output layers.
	 */
	int[] layerNumNeurons;
	
	/**
	 * The coefficients of the connections between layers. []--: From 0, the layer of connections (e.g. the number of the preceding layer). -[]-: The neuron of the following layer (e.g. the one getting input). --[]: The coefficient for the neuron of the preceding layer (e.g. the one giving output).
	 */
	float[][][] connectionCoefficients;
}
