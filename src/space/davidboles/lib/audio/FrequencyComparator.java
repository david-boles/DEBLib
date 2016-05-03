package space.davidboles.lib.audio;

public class FrequencyComparator {
	final double testFreq;
	final int testSPS;
	final int testSPC;
	final int testSPQC;
	public final double[] testSample;//TODO remove public
	
	public FrequencyComparator(double testFrequency, int testSamplesPerSecond) {
		this.testFreq = testFrequency;
		this.testSPS = testSamplesPerSecond;
		this.testSPC = (int) (this.testSPS/this.testFreq);
		this.testSPQC = this.testSPC/4;
		SinGenerator sampleGen = new SinGenerator();
		this.testSample = sampleGen.generateDToneSamples(this.testSPS, this.testFreq, this.testSPC);
	
		AudioFs.printSamples(this.testSample);
	}
	
	public double testAudio(double[] audio) throws IllegalArgumentException {
		if (audio.length < this.testSPC+this.testSPQC) throw new IllegalArgumentException("Audio data too small!");
		
		int maxComparesPerOffset = (int) Math.floor((audio.length-this.testSPQC)/this.testSPC);
		
		double[] comparisonResults = new double[maxComparesPerOffset*2];
		
		for(int cSet = 0; cSet < maxComparesPerOffset; cSet++) {
			comparisonResults[cSet*2] = compareAudioSection(audio, (cSet*this.testSPC));
			comparisonResults[(cSet*2)+1] = compareAudioSection(audio, (cSet*this.testSPC)+this.testSPQC);
		}
		
		double[] comparisonResultsSquared = new double[comparisonResults.length];
		for(int rPos = 0; rPos < comparisonResultsSquared.length; rPos++) {
			comparisonResultsSquared[rPos] = comparisonResults[rPos] * comparisonResults[rPos];
		}
		
		double[] squaresSetTotals = new double[maxComparesPerOffset];
		for(int rPos = 0; rPos < comparisonResultsSquared.length; rPos+=2) {
			squaresSetTotals[rPos/2] = comparisonResultsSquared[rPos] + comparisonResultsSquared[rPos+1];
		}
		
		double[] sqrtTotals = new double[squaresSetTotals.length];
		for(int sPos = 0; sPos < squaresSetTotals.length; sPos++) {
			sqrtTotals[sPos] = Math.sqrt(squaresSetTotals[sPos]);//TODO fix for 90deg not being 0...
		}
		
		double totalSqrts = sqrtTotals[0];
		for(int sPos = 1; sPos < squaresSetTotals.length; sPos++) {
			totalSqrts += sqrtTotals[sPos];
		}
		
		double average = totalSqrts/sqrtTotals.length;
		
		return average;
	}
	
	public double compareAudioSection(double[] audio, int offset) {//TODO remove public
		if(audio.length-offset < this.testSPC) throw new IllegalArgumentException("Offset to large!");
		
		double[] prodAll = new double[this.testSPC];
		for(int prodPos = 0; prodPos < prodAll.length; prodPos++) {
			prodAll[prodPos] = this.testSample[prodPos]*audio[offset+prodPos];
		}
		
		double total = prodAll[0];
		for(int prodPos = 1; prodPos < prodAll.length; prodPos++) {
			total += prodAll[prodPos];
		}
		
		double average = total/prodAll.length;
		
		return average*2;//TODO Multiple times scale for RMS?
	}
}
