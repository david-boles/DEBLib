package space.davidboles.lib.neuralnetwork.learner;

import space.davidboles.lib.math.ArrayFs;
import space.davidboles.lib.neuralnetwork.DataSet;
import space.davidboles.lib.neuralnetwork.Learner;
import space.davidboles.lib.neuralnetwork.NeuralNetwork;
import space.davidboles.lib.neuralnetwork.RatedConstants;

public class TerriUltiLearner extends Learner {

	//ArrayList<RatedConstants> constants = new ArrayList<>();
	final float increment;
	RatedConstants bestTested;
	final int numIncrements;
	
	public TerriUltiLearner(NeuralNetwork neuralNetwork, DataSet dataSet, float increment) throws IllegalArgumentException {
		super(neuralNetwork, dataSet);
		this.increment = increment;
		this.numIncrements = (int) Math.floor(2/increment);
		
		RatedConstants base = new RatedConstants(this.nn.getScalers(), this.nn.getOffsets());
		base = base.getRandomizedCopy(1);
		this.bestTested = base;
	}

	@Override
	protected void learnSingle() {
		RatedConstants base = new RatedConstants(this.nn.getScalers(), this.nn.getOffsets());
		
		for(int i = 0; i < base.connectionOffsets.length; i++) {
			for(int ii = 0; ii < base.connectionOffsets[i].length; ii++) {
				for(int iii = 0; iii < base.connectionOffsets[i][ii].length; iii++) {
					base.connectionOffsets[i][ii][iii] = -1;
					base.connectionScalers[i][ii][iii] = -1;
				}
			}
		}
		
		//constants.add(base);
		
		float[][][][] allCs = new float[][][][] {base.connectionScalers, base.connectionOffsets};
		
		boolean terminated = false;
		int it = 0;
		int totalIt = this.calcTotalPossible(allCs);
		System.out.println(totalIt);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (!terminated) {
			it++;
			System.out.println(((((float)it)/((float)totalIt))));
			RatedConstants current = new RatedConstants(ArrayFs.copyTFA(allCs[0]), ArrayFs.copyTFA(allCs[1]));
			testSingle(current);
			//System.out.println("Curr Acc" + current.accuracy);
			if(this.bestTested.accuracy < current.accuracy) this.bestTested = current;
			
			
//			int i = allCs.length-1;
//			int ii = allCs[i].length-1;
//			int iii = allCs[i][ii].length-1;
//			int iiii = allCs[i][ii][iii].length-1;
			//System.out.println(allCs[i][ii][iii][iiii]);
			
			//System.out.println(it);

			terminated = overfIncrementPos(allCs, 0, 0, 0, 0);
		}
		System.out.println("Best acc" + this.bestTested.accuracy);
		System.out.println(this.bestTested.connectionOffsets[0][0][0]);
		System.out.println(this.bestTested.connectionScalers[0][0][0]);
	}
	
	protected void testSingle(RatedConstants current) {
		this.nn.setScalers(current.connectionScalers);
		this.nn.setOffsets(current.connectionOffsets);
		
		for(int data = 0; data < this.inputDataSet.length; data++) {
			float[] output = this.nn.process(this.inputDataSet[data]);
			current.addTest(output, this.targetDataSet[data]);
		}
	}
	
	protected int calcTotalPossible(float[][][][] allCs) {
		int totalConst = 0;
		for(int a = 0; a < allCs.length; a++) {
			for(int b = 0; b < allCs[a].length; b++) {
				for(int c = 0; c < allCs[a][b].length; c++) {
					totalConst += allCs[a][b][c].length;
				}
			}
		}
		float possibleVals = (2f/this.increment);
		return (int) Math.pow(possibleVals, totalConst);
	}
	
	public boolean overfIncrementPos(float[][][][] allCs, int a, int b, int c, int d) {
		//System.out.println("a"+a+"b"+b+"c"+c+"d"+d);
		//System.out.println(allCs[a][b][c][d]);
		
		allCs[a][b][c][d] += this.increment;
		
		boolean overflow = false;
		if(allCs[a][b][c][d] > 1) {
		
			//System.out.println("OVERFLOW");
			//System.out.println(a);
			//System.out.println(allCs.length-1);
			if(a == allCs.length-1 && b == allCs[a].length-1 && c == allCs[a][b].length-1 && d == allCs[a][b][c].length-1){
				//System.out.println("END");
				return true;
			}
			
			int newA = a;
			int newB = b;
			int newC = c;
			int newD = d+1;
			
			if(newD >= allCs[a][b][c].length) {
				newD = 0;
				newC++;
				//System.out.println("inc C");
			}
			
			if(newC >= allCs[a][b].length) {
				newC = 0;
				newB++;
				//System.out.println("inc B");
			}
			if(newB >= allCs[a].length) {
				newB = 0;
				newA++;
				//System.out.println("inc A");
			}

			allCs[a][b][c][d] = -1;
		
			return overfIncrementPos(allCs, newA, newB, newC, newD);
		}
		return false;
	}

	@Override
	public RatedConstants getBest() {
		return bestTested;
	}

}
