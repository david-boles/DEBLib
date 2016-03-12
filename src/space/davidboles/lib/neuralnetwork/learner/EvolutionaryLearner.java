package space.davidboles.lib.neuralnetwork.learner;

import java.util.ArrayList;

import space.davidboles.lib.neuralnetwork.DataSet;
import space.davidboles.lib.neuralnetwork.Learner;
import space.davidboles.lib.neuralnetwork.NeuralNetwork;
import space.davidboles.lib.neuralnetwork.RatedConstants;

public class EvolutionaryLearner extends Learner {

	ArrayList<RatedConstants> currentConstants = new ArrayList<>();
	int maintainedConstantSets;
	float randomization;
	
	public EvolutionaryLearner(NeuralNetwork neuralNetwork, DataSet dataSet, int maintainedConstantSets, float randomization) throws IllegalArgumentException {
		super(neuralNetwork, dataSet);
		this.maintainedConstantSets = maintainedConstantSets;
		this.randomization = randomization;
	}

	@Override
	protected void learnSingle() {
		if(currentConstants.size() == 0) {
			initialize();
		}
		
		randCopyAll();
		
		testAll();
		
		while(maintainedConstantSets < this.currentConstants.size()) {
			removeLowest();
		}
	}
	
	void initialize() {
		RatedConstants seed = RatedConstants.getRandomNew(this.nn);
		currentConstants.add(seed);
		
		for(int i = 1; i < maintainedConstantSets; i++) {
			currentConstants.add(seed.getRandomizedCopy(this.randomization));
		}
		testAll();
	}
	
	void testAll() {
		for(int cC = 0; cC < currentConstants.size(); cC++) {
			RatedConstants current = this.currentConstants.get(cC);
			
			if(current.tests == 0) {
				this.logger.vLog("Testing constants for maintained constants", cC);
				this.nn.setScalers(current.connectionScalers);
				this.nn.setOffsets(current.connectionOffsets);
				
				for(int data = 0; data < this.inputDataSet.length; data++) {
					float[] output = this.nn.process(this.inputDataSet[data]);
					current.addTest(output, this.targetDataSet[data]);
				}
			}
		}
	}
	
	void randCopyAll() {
		for(int i = 0; i < this.maintainedConstantSets; i++) {
			this.currentConstants.add(this.currentConstants.get(i).getRandomizedCopy(this.randomization));
		}
	}
	
	void removeLowest() {
		int lowestPos = 0;
		for(int i = 0; i < this.currentConstants.size(); i++) {
			RatedConstants current = this.currentConstants.get(i);
			if(current.accuracy < this.currentConstants.get(lowestPos).accuracy) lowestPos = i;
		}
		//System.out.println("removing acc " + this.currentConstants.get(lowestPos).accuracy);
		this.currentConstants.remove(lowestPos);
	}
	
	@Override
	protected RatedConstants getBest() {
		int highestPos = 0;
		for(int i = 0; i < this.currentConstants.size(); i++) {
			RatedConstants current = this.currentConstants.get(i);
			if(current.accuracy > this.currentConstants.get(highestPos).accuracy) highestPos = i;
		}
		return this.currentConstants.get(highestPos);
	}

}
