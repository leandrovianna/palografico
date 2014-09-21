package com.leandro.palografico.twopass;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

public class TwoPass {

	private int[][] labeledMatrix;
	private int nObjects;
	
	public int[][] getLabeledMatrix() {
		return labeledMatrix;
	}
	
	public int getNObjects() {
		return this.nObjects;
	}
	
	public TwoPass(Bitmap b) {
		int [][] matrix = new int[b.getWidth()][b.getHeight()];
		
		for (int x = 0; x < b.getWidth(); x++)
			for (int y = 0; y < b.getHeight(); y++)
				if (b.getPixel(x, y) == Color.WHITE)
					matrix[x][y] = 0;
				else
					matrix[x][y] = 1;
		
		List<List<Integer>> linked = new ArrayList<List<Integer>>();
        int[][] labels = new int[matrix.length][matrix[0].length];

        int NextLabel = 0;

        //First pass
//        for(int i=0; i<matrix.length; i++) {
//            for( int j=0; j<matrix.length; j++) {
//                labels[i][j] = 0;
//            }
//        }

        for(int i=0; i<matrix.length; i++) {
            for(int j=0; j<matrix[0].length; j++) {
                if(matrix[i][j] != 0) {

                    //Labels of neighbors
                    ArrayList<Integer> neighbors = new ArrayList<Integer>();
                    for(int ni=-1; ni<=1; ni++) {
                        for(int nj=-1; nj<=1; nj++) {
                            if(i+ni<0 || j+nj<0 || i+ni>labels.length-1 || j+nj>labels[0].length-1) {
                                continue;
                            }
                            else {
                                if(i+ni == 0 && i+nj == 0) continue;
                                if(labels[i+ni][j+nj] != 0) neighbors.add(labels[i+ni][j+nj]);
                            }
                        }
                    }

                    if(neighbors.size() == 0) {
                        ArrayList<Integer> tempArrayList = new ArrayList<Integer>();
                        tempArrayList.add(NextLabel);
                        linked.add(NextLabel, tempArrayList);
                        labels[i][j] = NextLabel;
                        NextLabel++;
                    }
                    else {

                        labels[i][j]=1000*1000;
                        for(int neighbor : neighbors) {
                            if(neighbor < labels[i][j]) labels[i][j] = neighbor;
                        }

                        for(int neighbor : neighbors) {
                            linked.set(neighbor,union(linked.get(neighbor),neighbors));
                        }
                    }
                }

            }
        }

        //Second pass
        for(int i=0; i<matrix.length; i++) {
            for(int j=0; j<matrix[0].length; j++) {
                List<Integer> EquivalentLabels = linked.get(labels[i][j]);
                labels[i][j]=1000*1000;
                for(int label : EquivalentLabels) {
                    if(label < labels[i][j]) labels[i][j]=label;
                }
            }
        }

        labeledMatrix = labels;
        
        this.countObjects();
    }

	//union: http://stackoverflow.com/questions/5283047/intersection-and-union-of-arraylists-in-java
    public <T> List<T> union(List<T> list1, List<T> list2) {
        Set<T> set = new HashSet<T>();

        set.addAll(list1);
        set.addAll(list2);

        return new ArrayList<T>(set);
    }
    
    public void countObjects() {
    	int n = 0;
    	List<Integer> processados = new ArrayList<Integer>();
    	
    	for (int i = 0; i < labeledMatrix.length; i++)
    		for (int j = 0; j < labeledMatrix[i].length; j++) {
    			if (labeledMatrix[i][j] != 0) {
    				if (!processados.contains(labeledMatrix[i][j])) {
    					n++;
    					processados.add(labeledMatrix[i][j]);
    					Log.d("countObjects", ""+labeledMatrix[i][j]);
    				}
    			}
    		}
    			
    	this.nObjects = n;
    }

}
