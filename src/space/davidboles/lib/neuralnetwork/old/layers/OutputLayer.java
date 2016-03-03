package space.davidboles.lib.neuralnetwork.old.layers;

import space.davidboles.lib.neuralnetwork.old.processors.NetworkProcessor;

public class OutputLayer extends NetworkLayer {

	Outputter outputter;
	
	public OutputLayer(int numInNeurons, int numNeurons, int coefInitType, NetworkProcessor processor, Outputter outputter) {
		super(numInNeurons, numNeurons, coefInitType, processor);
		this.outputter = outputter;
	}

	@Override
	public void output(float[] data) {
		outputter.output(data);
	}
	
	
}


