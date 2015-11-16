package com.deb.lib.audio;

import java.util.ArrayList;

import com.deb.lib.math.ArrayFs;

public class FrequencyComparator {
	ArrayList<Double> refF = new ArrayList<Double>();
	ArrayList<Integer> refSPS = new ArrayList<Integer>();
	ArrayList<double[]> refs = new ArrayList<double[]>();
	
	public void addReference(int samplesPS, double frequency) {
		for (int i = 0; i < refF.size(); i++) {
			if (refF.get(i) == frequency && refSPS.get(i) == samplesPS) return;
		}
		
		refs.add(new SinGenerator().generateDToneSamples(samplesPS, frequency, (int)(samplesPS/frequency)));
		refF.add(frequency);
		refSPS.add(samplesPS);
	}
	
	public void clearReferences() {
		refF.clear();
		refSPS.clear();
		refs.clear();
	}
	
	double[] getReference(int samplesPS, double frequency) {
		for (int i = 0; i < refF.size(); i++) {
			if (refF.get(i) == frequency && refSPS.get(i) == samplesPS) return refs.get(i);
		}
		
		addReference(samplesPS, frequency);
		return (refs.get(refs.size()-1));
	}
	
	public double testAudio(double[] audio, int samplesPS, double frequency) {
		int samplesPC = (int) (samplesPS/frequency);
		double[] ref = this.getReference(samplesPS, frequency);
		double[] tempSamplePoss = new double[audio.length];
		double[] cycle = new double[samplesPC];
		double[] tempValues = new double[samplesPC];
		double[] offsetValues = new double[samplesPC];
		
		for(int cPos = 0; cPos < samplesPC; cPos++) {
			for(int position = cPos; position < audio.length; position += samplesPC) {
				tempSamplePoss[position] = audio[position];
			}
			cycle[cPos] = ArrayFs.doubleTotal(tempSamplePoss)/(audio.length/samplesPC);
			tempSamplePoss = new double[audio.length];
		}
		
		for(int off = 0; off < samplesPC; off++) {
			for(int pos = 0; pos < samplesPC; pos++) {
				tempValues[pos] = ref[pos] * cycle[(pos+off)%samplesPC];
			}
			offsetValues[off] = ArrayFs.doubleAverage(tempValues);
		}
		
		//print cycle
		/*for(int i = 0; i < cycle.length; i++) {
			int v = (int)((cycle[i]+1d)*20d);
			for(int I = 0; I < v; I++) {
				System.out.print("-");
			}
			System.out.println();
		}
		System.out.println("positionValues");
		//print cycle
		for(int i = 0; i < offsetValues.length; i++) {
			int v = (int)((offsetValues[i]+1d)*20d);
			for(int I = 0; I < v; I++) {
				System.out.print("-");
			}
			System.out.println();
		}*/
		
		double out = (ArrayFs.doubleTotal(ArrayFs.doubleABS(offsetValues))/((double)samplesPC)) * Math.PI;
		System.out.println(out);
		if (out > 1d) return 1d;
		if (out < 0d) return 0d;
		return out;
		
	}
}