package space.davidboles.lib.neuralnetwork;

import space.davidboles.lib.program.Logger;

public abstract class Learner {
	
	protected Logger logger = Logger.uLogger;
	
	final protected NeuralNetwork nn;
	final protected float[][] inputDataSet;
	final protected float[][] targetDataSet;
	final protected DataSet dataSet;
	
	public Learner(NeuralNetwork neuralNetwork, DataSet dataSet) throws IllegalArgumentException {
		if(dataSet.inputData.length > 0 && dataSet.inputNumNeurons == neuralNetwork.layerNumNeurons[0] && dataSet.outputNumNeurons == neuralNetwork.layerNumNeurons[neuralNetwork.layerNumNeurons.length-1]) {
			this.nn = neuralNetwork;
			float[][][] data = dataSet.getDataCopy();
			this.inputDataSet = data[0];
			this.targetDataSet = data[1];
			this.dataSet = dataSet;
		}else throw new IllegalArgumentException("Num of neurons for input or output to not match between network and dataset or there is no data in the DataSet.");
	}
	
	public void learn(int iterations) {
		logger.vLog("Starting learning iterations", iterations);
		for(int i = 0; i < iterations; i++) {
			learnSingle();
			logger.vLog("Completed interation", i);
		}
		RatedConstants best = this.getBest();
		this.nn.setOffsets(best.connectionOffsets);
		this.nn.setScalers(best.connectionScalers);
	}
	
	protected abstract void learnSingle();
	
	public Learner setLogger(Logger logger) {
		this.logger = logger;
		return this;
	}
	
	protected abstract RatedConstants getBest();
}
