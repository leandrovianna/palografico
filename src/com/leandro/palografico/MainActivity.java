package com.leandro.palografico;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {
	
	private ImageView imageView;
	private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        BitmapFactory.Options options = new BitmapFactory.Options();
		options.inMutable = true;
		
		bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.palos2, options);
		
		imageView = (ImageView) findViewById(R.id.image);
		imageView.setImageBitmap(bitmap);
    }
    
    public void mexerImagem(View v) {
    	bitmap = ImageTools.toGreyScale(bitmap);
    	//bitmap = ImageTools.binaryImage(bitmap, 180);
    	bitmap = ImageTools.binaryImageAndInvert(bitmap, 180);
    	
    	imageView.setImageBitmap(bitmap);    	
    }
    
    public void testarBinarizacao(View v) {
    	int nPretos = 0, 
    			nBrancos = 0, 
    			nOutras = 0; //conta os pixels com as cores
    	
    	for (int i = 0; i < bitmap.getWidth(); ++i)
    		for (int j = 0; j < bitmap.getHeight(); ++j) {
    			int color = bitmap.getPixel(i,j);
    			
    			switch (Color.red(color)) {
    			case 0:
    				nPretos++;
    				break;
    			case 255:
    				nBrancos++;
    				break;
    			default:
    				nOutras++;
    			}
    				
//    			Log.d("MainActivity", 
//    					"Cor (x="+i+", y ="+j+") = "+Color.red(bitmap.getPixel(i, j)));
    		}
    	
    	Log.i("MainActivity", "testarBinarizacao terminado.");
    	Log.i("MainActivity", "Pixels Pretos: "+nPretos);
    	Log.i("MainActivity", "Pixels Brancos: "+nBrancos);
    	Log.i("MainActivity", "Pixels Outras Cores: "+nOutras);
    	
    	//Mostrando diálogo com resultados
    	GUI.showDialog("Resultado", 
    			"Pixels Pretos: "+nPretos
    			+ "\nPixels Brancos: "+nBrancos
    			+ "\nPixels Outras Cores: "+nOutras, 
    			MainActivity.this);
    }
}
