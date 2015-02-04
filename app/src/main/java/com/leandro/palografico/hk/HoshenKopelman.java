package com.leandro.palografico.hk;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.leandro.palografico.ImageTool;

public class HoshenKopelman {

	private int [][] matrix; //matriz com os pixels rotulados

	public int[][] getLabelledMatrix() {
		return matrix;
	}

	public int countObjects(Bitmap image) {
		int [][] matrix = ImageTool.createBinaryImageMatrix(image);
		matrix = ImageTool.riseImageObjects(matrix, 2);
		
		return runAlgorithm(matrix);
	}

	/**
	 * Etiqueta (labeling) os pixels em matrix e retorna o número de objetos (clusters)
	 * @param matrix Matriz com pixels binarizados, 1 para objeto e 0 para fundo.
	 * @return Número de Objetos
	 * @author Leandro Vianna
	 */
	private int runAlgorithm(int [][] matrix) {
		int m = matrix.length;
		int n = matrix[0].length;
		
		int nLabels = m * n / 2;

		UnionFind.ufInitialize(nLabels);
		
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (matrix[i][j] != 0) { //se pixel de objeto (ocupado)
					
					int up = (i == 0 ? 0 : matrix[i-1][j]); //pegando o pixel de cima
					int left = (j == 0 ? 0 : matrix[i][j-1]); //pegando o pixel da esquerda
					int upleft = (i == 0 || j == 0 ? 0 : matrix[i-1][j-1]);
					int upright = (i == 0 || j == n-1 ? 0 : matrix[i-1][j+1]);
					
					if ((up == 0 && left == 0) && (upleft == 0 && upright == 0)) { //case 0
						matrix[i][j] = UnionFind.ufMakeSet();
					} 
					else if ((up != 0 && left != 0) && (upleft != 0 && upright != 0)) { //case 2
						matrix[i][j] = UnionFind.ufUnion(up, left);
					}
					else {
						//if (up == 0 && left != 0 || up != 0 && left == 0) { //case 1
						matrix[i][j] = Math.max(up, left);
					}
				}
			}
		}
		
		int [] newLabels = new int[nLabels];
		
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				if (matrix[i][j] != 0) {
					int x = UnionFind.ufFind(matrix[i][j]);
					
					if (newLabels[x] == 0) {
						newLabels[0]++;
						newLabels[x] = newLabels[0];
					}
					
					matrix[i][j] = newLabels[x];
				}


		int totalClusters = newLabels[0];
		
		this.matrix = matrix; //Atribuindo a atributo matrix a matriz com os pixels rotulados
		
		UnionFind.ufDone();
		
		return totalClusters;
	}
	
	public Bitmap createOutputImage() {
		Bitmap image = Bitmap.createBitmap(matrix[0].length, matrix.length, Bitmap.Config.ARGB_8888);
		
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				image.setPixel(j, i, matrix[i][j] == 0 ? Color.WHITE: Color.BLACK);
			}
		}
		
		return image;
	}
}
