package com.deb.lib.image;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class FloatingImage {
	int imageType;
	float[][][] image;
	
	//Constructors
	public FloatingImage() {
		imageType = -1;
	}
	
	public FloatingImage(Image i) {
		switch(i.getType()) {
		case BufferedImage.TYPE_INT_ARGB:
			this.setFromIARGB((IARGBImage) i);
			break;
		case BufferedImage.TYPE_BYTE_GRAY:
			this.setFromBGRAY((BGRAYImage) i);
			break;
		case BufferedImage.TYPE_INT_RGB:
			this.setFromIRGB((IRGBImage) i);
			break;
		}
	}
	
	public FloatingImage(int width, int height, int data, int type) {
		this.imageType = type;
		this.initializeArray(width, height, data);
	}
	
	//Pixel getter and setter
	public float[] getPixel(int x, int y) { return Arrays.copyOf(this.image[x][y], this.image[x][y].length); }
	
	public boolean setPixel(int x, int y, float[] px) {
		if (this.image[x][y].length == px.length) {
			this.image[x][y] = Arrays.copyOf(px, px.length);
			return true;
		}
		return false;
	}
	
	//Range getter and setter
	public float[][][] getRange(int startX, int startY, int width, int height) {
		float[][][] out = new float[width][height][this.image[0][0].length];
		
		for(int rX = 0; rX < width; rX++) {
			for(int rY = 0; rY < height; rY++) {
				out[rX][rY] = Arrays.copyOf(this.image[startX+rX][startY+rY], this.image[startX+rX][startY+rY].length);
			}
		}
		
		return out;
	}
	
	public boolean setRange(int startX, int startY, float[][][] range) {//ADD Range array length equalness checks
		if(this.image.length <= startX+range.length) {
			for(int rX = 0; rX < range.length; rX++) {
				for(int rY = 0; rY < range[0].length; rY++) {
					this.image[rX+startX][rY+startY] = Arrays.copyOf(range[rX][rY], range[rX][rY].length);
				}
			}
		}
		
		return false;
	}
	
	//Type getter, setter, isType
	public int getType() { return this.imageType; }
	
	public void setType(int type) { this.imageType = type; }
	
	public boolean isType(int type) {
		boolean out = type == this.getType();
		return out;
	}
	
	//IARGBImage <-> Floating image converters
	public void setFromIARGB(IARGBImage i) {
		this.initializeArray(i.getWidth(), i.getHeight(), 4);
		this.imageType = i.getType();
		for(int x = 0; x < i.getWidth(); x++) {
			for(int y = 0; y < i.getHeight(); y++) {
				Color px = i.getPixel(x, y);
				this.image[x][y][0] = px.getAlpha()/255f;
				this.image[x][y][1] = px.getRed()/255f;
				this.image[x][y][2] = px.getGreen()/255f;
				this.image[x][y][3] = px.getBlue()/255f;
			}
		}
	}
	
	public IARGBImage getToIARGB() {
		if(this.imageType == BufferedImage.TYPE_INT_ARGB) {
			IARGBImage out = new IARGBImage(this.image.length, this.image[0].length);
			
			for(int x = 0; x < this.image.length; x++) {
				for(int y = 0; y < this.image[0].length; y++) {
					Color px = new Color(this.image[x][y][1], this.image[x][y][2], this.image[x][y][3], this.image[x][y][0]);
					out.setPixel(x, y, px);
				}
			}
			
			return out;
		}else {
			return null;
		}
	}
	
	//IARGBImage <-> Floating image converters NOTE: Incomplete...
	public void setFromIRGB(IRGBImage i) {
		this.initializeArray(i.getWidth(), i.getHeight(), 4);
		this.imageType = i.getType();
		for(int x = 0; x < i.getWidth(); x++) {
			for(int y = 0; y < i.getHeight(); y++) {
				Color px = i.getPixel(x, y);
				this.image[x][y][0] = px.getRed()/255f;
				this.image[x][y][1] = px.getGreen()/255f;
				this.image[x][y][2] = px.getBlue()/255f;
			}
		}
	}
	
	public IARGBImage getToIRGB() {
		if(this.imageType == BufferedImage.TYPE_INT_RGB) {
			IARGBImage out = new IARGBImage(this.image.length, this.image[0].length);
			
			for(int x = 0; x < this.image.length; x++) {
				for(int y = 0; y < this.image[0].length; y++) {
					Color px = new Color(this.image[x][y][0], this.image[x][y][1], this.image[x][y][2]);//NOTE: Incorrect...
					out.setPixel(x, y, px);
				}
			}
			
			return out;
		}else {
			return null;
		}
	}

	//BGRAYImage <-> Floating image converters
	public void setFromBGRAY(BGRAYImage i) {
		this.initializeArray(i.getWidth(), i.getHeight(), 1);
		this.imageType = i.getType();
		for(int x = 0; x < i.getWidth(); x++) {
			for(int y = 0; y < i.getHeight(); y++) {
				Color px = i.getPixel(x, y);
				this.image[x][y][0] = (px.getRed()+px.getGreen()+px.getBlue())/765f;
			}
		}
	}
	
	public BGRAYImage getToBGRAY() {
		if(this.imageType == BufferedImage.TYPE_BYTE_GRAY) {
			BGRAYImage out = new BGRAYImage(this.image.length, this.image[0].length);
			
			for(int x = 0; x < this.image.length; x++) {
				for(int y = 0; y < this.image[0].length; y++) {
					Color px = new Color(this.image[x][y][0], this.image[x][y][0], this.image[x][y][0], 1);
					out.setPixel(x, y, px);
				}
			}
			
			return out;
		}else {
			return null;
		}
	}
	
	//Dimension getters
	public int getWidth() {
		return this.image.length;
	}
	
	public int getHeight() {
		return this.image[0].length;
	}
	
	//Other
	void initializeArray(int width, int height, int data) {
		this.image = new float[width][height][data];
	}
}
