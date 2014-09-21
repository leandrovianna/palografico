package com.leandro.palografico;

import java.util.LinkedList;
import java.util.Queue;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

public class ImageTools {

	public static Bitmap toGreyScale(Bitmap b) {
		Log.d("ImageTools", "Metodo toGreyScale");

		for (int x = 0; x < b.getWidth(); ++x) {
			for (int y = 0; y < b.getHeight(); ++y) {
				int color = b.getPixel(x, y);
				
				int red = Color.red(color);
				int green = Color.green(color);
				int blue = Color.blue(color);

				//int luminacia = (Color.red(color) + Color.green(color) + Color.blue(color)) / 3;
				int luminacia = (int) (red * 0.3 + green * 0.59 + blue * 0.11);
				
				b.setPixel(x, y, Color.rgb(luminacia, luminacia, luminacia));
				//setando pixel com tom de cinza, gerado a partir da luminosidade
			}
		}

		return b;
	}

	public static Bitmap binaryImage(Bitmap b, int t) {

		Log.d("ImageTools", "Metodo binaryImage");

		for (int x = 0; x < b.getWidth(); ++x) {
			for (int y = 0; y < b.getHeight(); ++y) {
				int color = b.getPixel(x, y);

				b.setPixel(x, y, Color.red(color) < t ? Color.BLACK : Color.WHITE);
			}
		}

		return b;
	}

	public static Bitmap binaryImageAndInvert(Bitmap b, int t) {
		Log.d("ImageTools", "Metodo binaryImageAndInvert");

		for (int x = 0; x < b.getWidth(); ++x) {
			for (int y = 0; y < b.getHeight(); ++y) {
				int color = b.getPixel(x, y);

				b.setPixel(x, y, Color.red(color) < t ? Color.WHITE : Color.BLACK);
			}
		}

		return b;
	}
	
	public static int countPalos(Bitmap b) {
		return algorithmOneComponentAtTime8(b);
	}
	
	//Algorithm: Connected-component label - One component at a time
	//FUNCIONANDO, MAS APENAS COM IMAGENS COM PALOS NÃO INCLINADOS
	public static int algorithmOneComponentAtTime(Bitmap b) {
		Log.d("ImageTools", "Metodo countPalos");
		
		int image[][] = new int[b.getWidth()][b.getHeight()]; //armazena as labels
		int curlab = 1; //current label
		int foregroundColor = Color.BLACK; //color of objects (palos)
		Queue<Pixel> queue = new LinkedList<Pixel>();
		
		for (int x = 0; x < b.getWidth(); ++x) {
			for (int y = 0; y < b.getHeight(); ++y) {
				Pixel pixel = new Pixel(x, y, b.getPixel(x, y), b);
				
				if (pixel.getColor() == foregroundColor && image[x][y] == 0) {
					image[x][y] = curlab;
					queue.offer(pixel);
					
					while (queue.isEmpty() == false) {
						Pixel p = queue.poll();
						
						Pixel pRight = p.getNeighborRight();
						Pixel pBottom = p.getNeighborBottom();
						
						if (pRight.getColor() == foregroundColor && image[pRight.getX()][pRight.getY()] == 0) {
							image[pRight.getX()][pRight.getY()] = curlab;
							queue.offer(pRight);
						}
						
						if (pBottom.getColor() == foregroundColor && image[pBottom.getX()][pBottom.getY()] == 0) {
							image[pBottom.getX()][pBottom.getY()] = curlab;
							queue.offer(pBottom);
						}
					}
				} else continue;
				
				curlab++;
			}
		}
		
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < image.length; i++) {
			s.delete(0, s.length());
			
			for (int j = 0; j < image[i].length; j++)
				s.append(image[i][j]+" ");
			
			Log.d("Matriz image", s.toString());
		}

		return curlab-1;
	}
	
	public static int algorithmOneComponentAtTime8(Bitmap b) {
		Log.d("ImageTools", "Metodo countPalos");
		
		int image[][] = new int[b.getWidth()][b.getHeight()]; //armazena as labels
		int curlab = 1; //current label
		int foregroundColor = Color.BLACK; //color of objects (palos)
		Queue<Pixel> queue = new LinkedList<Pixel>();
		
		for (int y = 0; y < b.getHeight(); ++y) {
			for (int x = 0; x < b.getWidth(); ++x) {
				Pixel pixel = new Pixel(x, y, b.getPixel(x, y), b);
				
				if (pixel.getColor() == foregroundColor && image[x][y] == 0) {
					image[x][y] = curlab;
					queue.offer(pixel);
					
					while (queue.isEmpty() == false) {
						Pixel p = queue.poll();
						
						Pixel pEast = p.getNeighborEast();
						Pixel pSouth = p.getNeighborSouth();
						Pixel pSouthWest = p.getNeighborSouthWest();
						Pixel pSouthEast = p.getNeighborSouthEast();
						
						if (pEast.getColor() == foregroundColor && image[pEast.getX()][pEast.getY()] == 0) {
							image[pEast.getX()][pEast.getY()] = curlab;
							queue.offer(pEast);
						}
						
						if (pSouth.getColor() == foregroundColor && image[pSouth.getX()][pSouth.getY()] == 0) {
							image[pSouth.getX()][pSouth.getY()] = curlab;
							queue.offer(pSouth);
						}
						
						if (pSouthWest.getColor() == foregroundColor && image[pSouthWest.getX()][pSouthWest.getY()] == 0) {
							image[pSouthWest.getX()][pSouthWest.getY()] = curlab;
							queue.offer(pSouthWest);
						}
						
						if (pSouthEast.getColor() == foregroundColor && image[pSouthEast.getX()][pSouthEast.getY()] == 0) {
							image[pSouthEast.getX()][pSouthEast.getY()] = curlab;
							queue.offer(pSouthEast);
						}
					}
				} else continue;
				
				curlab++;
			}
		}
		
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < image.length; i++) {
			s.delete(0, s.length());
			
			for (int j = 0; j < image[i].length; j++) {
				s.append(image[i][j]+" ");
			}
			
			Log.d("Matriz image", s.toString());
		}

		return curlab-1;
	}
}
