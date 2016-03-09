package space.davidboles.lib.neuralnetwork;

import space.davidboles.lib.program.Logger;

public abstract class Learner {
	
	Logger logger = Logger.uLogger;
	
	NeuralNetwork nn;
	float[][] inputDataSet;
	float[][] targetDataSet;
	int dataSetPosition = 0;
	
	public Learner(NeuralNetwork neuralNetwork, DataSet dataSet) throws IllegalArgumentException {
		if(dataSet.inputData.length > 0 && dataSet.inputNumNeurons == neuralNetwork.layerNumNeurons[0] && dataSet.outputNumNeurons == neuralNetwork.layerNumNeurons[neuralNetwork.layerNumNeurons.length-1]) {
			this.nn = neuralNetwork;
			float[][][] data = dataSet.getDataCopy();
			this.inputDataSet = data[0];
			this.targetDataSet = data[1];
		}else throw new IllegalArgumentException("Num of neurons for input or output to not match between network and dataset or there is no data in the DataSet.");
	}
	
	public void learn(int iterations) {
		logger.vLog("Starting learning iterations", iterations);
		for(int i = 0; i < iterations; i++) {
			learnSingle(inputDataSet[dataSetPosition], targetDataSet[dataSetPosition]);
		}
	}
	
	abstract void learnSingle(float[] input, float[] target);
	
	public Learner setLogger(Logger logger) {
		this.logger = logger;
		return this;
	}
}
