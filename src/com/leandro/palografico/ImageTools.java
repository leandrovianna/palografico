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

}
