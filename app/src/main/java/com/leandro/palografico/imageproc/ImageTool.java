package com.leandro.palografico.imageproc;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;

public class ImageTool {

	/**
	 * Imprime os pixels de uma {@link android.graphics.Bitmap} no console
	 * @param image imagem que os pixels serão impressos
	 */
	public static void printPixels(Bitmap image) {
		//y relativo as linhas (menor que a Altura)
		//x relativo as colunas (menor que a Largura)
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				System.out.printf("%3d", Color.red(image.getPixel(x, y)));
				System.out.print(" ");
			}
			System.out.print("\n");
		}
	}
	
	public static Bitmap convertToGreyScale(Bitmap image) {
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++)  {
				int color = image.getPixel(x, y);
				
				int red = Color.red(color);
				int green = Color.green(color);
				int blue = Color.blue(color);
				
				int luminancia = (int) (red + green + blue) / 3;
				
				image.setPixel(x, y, Color.rgb(luminancia, luminancia, luminancia));
			}
		}
		
		return image;
	}
	
	public static Bitmap binaryImage(Bitmap image, int threshold) {
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++)  {
				int r = Color.red(image.getPixel(x, y));
				
				image.setPixel(x, y, (r < threshold) ? Color.BLACK : Color.WHITE);
			}
		}
		
		return image;
	}
	
	/**
	 * Retorna uma matriz com valores 1 (para pixel de objeto) e 0 (para pixel de background)
	 * @param image imagem binarizada
	 * @return matrix de inteiros com os valores
	 */
	public static int[][] createBinaryImageMatrix(Bitmap image) {

		int [][] matrix = new int[image.getHeight()][image.getWidth()];

		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				if (image.getPixel(x, y) == Color.BLACK)
					matrix[y][x] = 1;
				else
					matrix[y][x] = 0;				
			}
		}
		
		return matrix;
	}
	
	public static int[][] riseImageObjects(int[][] matrix) {
		int m = matrix.length;
		int n = matrix[0].length;
		
		//Aumentando a área dos objetos
		//Para cada pixel (item da matriz), os vizinhos serão igualados a 1, aumentando o objeto na matrix)
		
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (matrix[i][j] != 0) { 

					matrix[i == 0 ? 0 : i-1][j] = 1;
					matrix[i][j == 0 ? 0 : j-1] = 1;
					matrix[i == 0 ? 0 : i-1][j == 0 ? 0 : j-1] = 1;
					matrix[i == 0 ? 0 : i-1][j == n-1 ? n-1 : j+1] = 1;
				}
			}
		}
		
		return matrix;
		
	}

	public static int[][] riseImageObjects(int[][] matrix, int riseSize) {
		
		for (int i = 0; i < riseSize; i++) {
			matrix = riseImageObjects(matrix);
		}
		
		return matrix;
	}

    public static Bitmap cropBitmap(Bitmap src, Rect dst) {
        Bitmap crop;
        crop = Bitmap.createBitmap(dst.width(), dst.height(), Bitmap.Config.ARGB_8888);

        for (int y = 0; y < src.getHeight(); ++y)
            for (int x = 0; x < src.getWidth(); ++x)
                if (y >= dst.top && y < dst.bottom
                        && x >= dst.left && x < dst.right)
                    crop.setPixel(x - dst.left, y - dst.top, src.getPixel(x, y));

        return crop;
    }

    public static int[] calculateHistogram(Bitmap bitmap) {

        int[] histogram = new int[256];

        for (int y = 0; y < bitmap.getHeight(); y++) {
            for (int x = 0; x < bitmap.getWidth(); x++) {
//                int h = Color.red(bitmap.getPixel(x,y));
                int h = 0xFF & bitmap.getPixel(x,y);
                ++histogram[h];

                Log.i("Histogram", h+": "+histogram[h]);
            }
        }

        return histogram;
    }
}
