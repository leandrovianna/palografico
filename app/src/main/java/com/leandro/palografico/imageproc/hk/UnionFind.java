package com.leandro.palografico.imageproc.hk;

/*
 * Implementado a partir do código de: https://gist.github.com/tobin/909424
 */

public class UnionFind {
	
	private static int [] labels;
	private static int nLabels;

	public static int ufFind(int x) {
		int y = x;

		while (labels[y] != y)
			y = labels[y];

		while (labels[x] != x) {
			int z = labels[x];
			labels[x] = y;
			x = z;
		}

		return y;
	}
	
	public static int ufMakeSet() {
		labels[0] ++;
		assert(labels[0] < nLabels);
		labels[labels[0]] = labels[0];
		return labels[0];
	}

	public static int ufUnion(int x, int y) {
		return labels[ufFind(x)] = ufFind(y);
	}

	public static void ufInitialize(int max_labels) {
		nLabels = max_labels;
		labels = new int[nLabels];
		labels[0] = 0;
	}

	public static void ufDone() {
		nLabels = 0;
		labels = null;
	}
}
