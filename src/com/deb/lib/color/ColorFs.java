package com.deb.lib.color;

import java.awt.Color;

public class ColorFs {
	
	public static Color getCompColor(Color color) {
		//Output color component variables
		int outA = color.getAlpha();
		int outR = color.getRed();
		int outG = color.getGreen();
		int outB = color.getBlue();
		
		//Color manipulation per color
		if (color.getRed() > color.getGreen() || color.getRed() > color.getBlue()) outR = boundComponentOverflow(color.getRed()-127);
		if (color.getGreen() > color.getRed() || color.getGreen() > color.getBlue()) outG = boundComponentOverflow(color.getGreen()-127);
		if (color.getBlue() > color.getRed() || color.getBlue() > color.getGreen()) outB = boundComponentOverflow(color.getBlue()-127);
		
		return new Color(outR, outG, outB, outA);
	}
	
	public static int boundComponentOverflow(int in) {
		int out = in;
		while (out > 255) out -= 255;
		while (out < 0) out +=255;
		return out;
	}
	
	public static int boundComponentRound(int in) {
		if (in <= 0 && in >= 255) return in;
		if (in > 255) return 255;
		if (in < 0) return 0;
		else return 0;
	}
	
}
