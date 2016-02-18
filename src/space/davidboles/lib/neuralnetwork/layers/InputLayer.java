package space.davidboles.lib.neuralnetwork.layers;

import space.davidboles.lib.neuralnetwork.processors.NetworkProcessor;

public abstract class InputLayer extends NetworkLayer {

	NetworkLayer output;
	Runnable outFail;
	
	public InputLayer(int numInNeurons, int numNeurons, int coefInitType, NetworkProcessor processor, NetworkLayer output, Runnable outputFailureHandler) {
		super(numInNeurons, numNeurons, coefInitType, processor);
		this.output = output;
		this.outFail = outputFailureHandler;
	}
	
	@Override
	public void output(float[] output) {
		if(!this.output.addInput(output)) this.outFail.run();;
	}

}
