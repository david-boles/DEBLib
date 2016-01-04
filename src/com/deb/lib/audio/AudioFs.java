package com.deb.lib.audio;

import java.nio.ByteBuffer;

import com.deb.lib.math.ArrayFs;

public class AudioFs {
	public static byte[] doubleTo8b_Signed_BEndian(double[] in) {
		byte[] out = new byte[in.length];
		
		for(int i = 0; i < in.length; i++) {
			out[i] = (byte) (in[i] * 127d);
		}
		
		return out;
	}
	
	public static double[] byte_Signed_BEndianToDouble(byte[] in) {
		double[] out = new double[in.length];
		
		for(int i = 0; i < in.length; i++) {
			out[i] = (double) (in[i] / 128d);
		}
		
		return out;
	}
	
	public static byte[] doubleTo16b_Signed_BEndian_Tone(double[] in) {
		ByteBuffer b = ByteBuffer.allocate(in.length * 2);
		
		for(int i = 0; i < in.length; i++) {
			b.putShort((short) (in[i] * 32767d));
		}
		
		return b.array();
	}
	
	@Deprecated
	public static double frequencyProbability(double[] in, int samplesPS, double frequency) {
		int samplesPC = (int) (samplesPS/frequency);
		double[] ref = new SinGenerator().generateDToneSamples(samplesPS, frequency, in.length);
		double[] tempValues = new double[in.length - samplesPC];
		double[] positionValues = new double[samplesPC];
		double out = 0;
		
		for(int offset = 0; offset < samplesPC; offset++) {
			for(int pos = 0; pos < in.length - samplesPC; pos++) {
				tempValues[pos] = (in[pos] * ref[pos+offset]);
			}
			positionValues[offset] = ArrayFs.doubleAverage(tempValues);
		}
		
		
		
		for (int i = 0; i< samplesPC; i++) positionValues[i] = Math.abs(positionValues[i]);
		System.out.println(samplesPC);
		for (int i = 0; i< samplesPC; i++) System.out.println(i + ": " + positionValues[i]);
		
		out = ArrayFs.doubleAverage(positionValues) * Math.PI;
		return out;
	}
}
