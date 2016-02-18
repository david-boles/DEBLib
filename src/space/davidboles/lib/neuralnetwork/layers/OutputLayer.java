package space.davidboles.lib.neuralnetwork.layers;

import space.davidboles.lib.neuralnetwork.processors.NetworkProcessor;

public abstract class OutputLayer extends NetworkLayer {

	public OutputLayer(int numInNeurons, int numNeurons, int coefInitType, NetworkProcessor processor) {
		super(numInNeurons, numNeurons, coefInitType, processor);
	}

}
