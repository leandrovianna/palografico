package com.leandro.palografico;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

public class ImageTools {

	public static Bitmap toGreyScale(Bitmap b) {
		Log.d("ImageTools", "Metodo toGreyScale");

		for (int i = 0; i < b.getWidth(); ++i) {
			for (int j = 0; j < b.getHeight(); ++j) {
				int color = b.getPixel(i, j);

				int luminacia = (Color.red(color) + Color.green(color) + Color.blue(color)) / 3;

				b.setPixel(i, j, Color.rgb(luminacia, luminacia, luminacia));
				//setando pixel com tom de cinza, gerado a partir da luminosidade
			}
		}

		return b;
	}

	public static Bitmap binaryImage(Bitmap b, int t) {

		Log.d("ImageTools", "Metodo binaryImage");

		for (int i = 0; i < b.getWidth(); ++i) {
			for (int j = 0; j < b.getHeight(); ++j) {
				int color = b.getPixel(i, j);

				b.setPixel(i, j, Color.red(color) < t ? Color.BLACK : Color.WHITE);
			}
		}

		return b;
	}

	public static Bitmap binaryImageAndInvert(Bitmap b, int t) {
		Log.d("ImageTools", "Metodo binaryImageAndInvert");

		for (int i = 0; i < b.getWidth(); ++i) {
			for (int j = 0; j < b.getHeight(); ++j) {
				int color = b.getPixel(i, j);

				b.setPixel(i, j, Color.red(color) < t ? Color.WHITE : Color.BLACK);
			}
		}

		return b;
	}

	public static int countObjects(Bitmap b) {
		//Fonte: http://www.inf.ufsc.br/~patrec/imagens.html

		int M = b.getWidth(); //tamanho de colunas (j)
		int N = b.getHeight(); //tamanho de linhas (i)

		//Criando um vetor A[M,N] para representar a imagem
		int A[][] = new int[N][M];
		//Criando um vetor Q de mesmo tamanho para armazenar os rotulos das componentes conexas
		int Q[][] = new int[N][M];

		int L = 0; //indice de rotulacao
		int corFundo = 0; //valor do fundo

		//preenchendo A com os pixels da imagem
		for (int i = 0; i < N; i++)
			for (int j = 0; j < M; j++) {
				A[i][j] = Color.red(b.getPixel(j, i));
			}

		if (A[0][0] != corFundo) {
			L++;
			Q[0][0] = L;
		}

		//Percorrendo o restante da primeira linha
		for (int j = 1; j < M; j++) {
			if (A[0][j] > corFundo) {
				if (A[0][j] == A[0][j-1]) {
					Q[0][j] = Q[0][j-1];
				} else {
					L++;
					Q[0][j] = L;
				}
			}
		}

		for (int i = 1; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (j == 0 && A[i][0] > corFundo) { //primeira coluna, verificar a de cima
					if (A[i][0] == A[i-1][0]) {
						Q[i][0] = Q[i-1][0];
					} else {
						L++;
						Q[i][0] = L;
					}
				}
				else {
					//A[p] == A[i][j] --- corrente
					//A[s] == A[i][j-1] --- esquerda
					//A[t] == A[i-1][j] --- de cima

					//para o resto da linha

					if (A[i][j] == A[i][j-1] && A[i][j] != A[i-1][j])
						Q[i][j] = Q[i][j-1]; //pixel corrente igual ao da esquerda
					
					if (A[i][j] != A[i][j-1] && A[i][j] == A[i-1][j])
						Q[i][j] = Q[i-1][j]; //px corrente igual ao de cima
					
					if (A[i][j] != A[i][j-1] && A[i][j] != A[i-1][j]) {
						L++;
						Q[i][j] = L;
					}
					
					//A[p] == A[t] && A[p] == A[s] && Q[t] == Q[s]
					if (A[i][j] == A[i-1][j] && A[i][j] == A[i][j-1] && Q[i-1][j] == Q[i][j-1])
						Q[i][j] = Q[i-1][j];
					
					if (A[i][j] == A[i-1][j] && A[i][j] == A[i][j-1] && Q[i-1][j] != Q[i][j-1]) {
						Q[i][j] = Math.min(Q[i-1][j], Q[i][j-1]);
					}
				}
			}
		}

		int max = 0;
		for (int i = 0; i < N; i++)
			for (int j = 0; j < M; j++) {
				max = Math.max(Q[i][j], max);
			}
		
		return max;
	}
	
	public static int countObjects2(Bitmap b) {
		//Fonte: OTIMIZAÇÃO E EFICIÊNCIA DE ALGORITMOS DE
		//ROTULAÇÃO DE COMPONENTES CONEXOS EM
		//IMAGENS BINÁRIAS
		//Pág. 15

		
		int label = 1;
		int [][] A = new int[b.getWidth()][b.getHeight()];
		
		for (int y = 1; y < b.getHeight(); ++y) {
			for (int x = 1; x < b.getWidth(); ++x) {
				int pixel = b.getPixel(x, y);
				
				if (pixel == Color.BLACK) {
					if (b.getPixel(x-1, y) != Color.BLACK && b.getPixel(x, y-1) != Color.BLACK)
						A[x][y] = label++;
					else
						A[x][y] = label;
					
				} else {
					A[x][y] = 0;
				}
			}
		}
		
		return label;
	}
}
