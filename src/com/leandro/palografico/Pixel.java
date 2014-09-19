package com.leandro.palografico;

import android.graphics.Bitmap;


public class Pixel {
	
	private int x;
	private int y;
	private int color; //use class Color
	private Bitmap bitmap;
	
	public Pixel(int x, int y, int color, Bitmap bitmap) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.bitmap = bitmap;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}
	
	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public Pixel getNeighborUp() {
		return new Pixel(x, y-1, bitmap.getPixel(x, y-1), bitmap);
	}
	
	public Pixel getNeighborLeft() {
		return new Pixel(x-1, y, bitmap.getPixel(x-1, y), bitmap);
	}
	
	public Pixel getNeighboBottom() {
		return new Pixel(x, y+1, bitmap.getPixel(x, y+1), bitmap);
	}
	
	public Pixel getNeighborRight() {
		return new Pixel(x+1, y, bitmap.getPixel(x+1, y), bitmap);
	}
}
