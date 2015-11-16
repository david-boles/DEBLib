package com.deb.lib.math;

import java.lang.reflect.Array;

public class ArrayFs {
	
	public static int intAverage(int[] in) {
		int total = 0;
		
		for(int i = 0; i < in.length; i++) total += in[i];
		
		return (total/in.length);
	}
	
	public static int intTotal(int[] in) {
		int total = 0;
		
		for(int i = 0; i < in.length; i++) total += in[i];
		
		return total;
	}
	
	public static double doubleAverage(double[] in) {
		double total = 0;
		
		for(int i = 0; i < in.length; i++) total += in[i];
		
		return (total/in.length);
	}
	
	public static double doubleTotal(double[] in) {
		double total = 0;
		
		for(int i = 0; i < in.length; i++) total += in[i];
		
		return total;
	}
	
	public static double[] doubleABS(double[] in) {
		double[] total = new double[in.length];
		
		for(int i = 0; i < in.length; i++) total[i] = Math.abs(in[i]);
		
		return total;
	}
	
	public static <T> T[] concatenate (T[] a, T[] b) {
	    int aLen = a.length;
	    int bLen = b.length;

	    @SuppressWarnings("unchecked")
	    T[] c = (T[]) Array.newInstance(a.getClass().getComponentType(), aLen+bLen);
	    System.arraycopy(a, 0, c, 0, aLen);
	    System.arraycopy(b, 0, c, aLen, bLen);

	    return c;
	}
	
}
