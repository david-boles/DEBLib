package space.davidboles.lib.neuralnetwork.old;

import space.davidboles.lib.neuralnetwork.old.layers.InputLayer;
import space.davidboles.lib.neuralnetwork.old.layers.NetworkLayer;
import space.davidboles.lib.neuralnetwork.old.layers.OutputLayer;
import space.davidboles.lib.neuralnetwork.old.layers.Outputter;
import space.davidboles.lib.neuralnetwork.old.layers.ProcessLayer;
import space.davidboles.lib.neuralnetwork.old.processors.NetworkProcessor;

public class NeuralNetwork {
	
	NetworkLayer[] layers;
	boolean running = false;
	
	public NeuralNetwork(int inputNum, int[] numLayersNeurons, NetworkProcessor inputProcessor, NetworkProcessor bodyProcessor, NetworkProcessor outputProcessor, Runnable outputFailureHandler, int coefInitType, Outputter outputter) {
		layers = new NetworkLayer[numLayersNeurons.length];
		
		
		//Layers
		//Output
		layers[layers.length-1] = new OutputLayer(numLayersNeurons[layers.length-2],numLayersNeurons[layers.length-1], coefInitType, outputProcessor, outputter);
		
		//Body
		for(int i = numLayersNeurons.length-2; i > 0; i--) {
			layers[i] = new ProcessLayer(numLayersNeurons[i-1], numLayersNeurons[i], coefInitType, bodyProcessor, layers[i+i], outputFailureHandler);
		}
		
		//Input
		layers[0] = new InputLayer(inputNum, numLayersNeurons[0], coefInitType, inputProcessor, layers[1], outputFailureHandler);
		
		
		//Start All
		for(int i = 0; i < layers.length; i++) layers[i].start();
		running = true;
	}
	
	public void stop() {
		for(int i = 0; i < layers.length; i++) layers[i].interrupt();
		this.running = false;
	}
	
	public boolean input(float[] input) {
		return layers[0].addInput(input);
	}
	
	public boolean getRunning() {
		return this.running;
	}
	
	public int getNumLayers() {
		return layers.length;
	}
	
	public NetworkLayer getLayer(int layer) {
		return layers[layer];
	}
}
