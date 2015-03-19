package com.leandro.palografico.imageproc;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.AsyncTask;

import com.leandro.palografico.imageproc.hk.HoshenKopelman;

/**
 * Task que faz o processamento de uma imagem para contar os objetos contidos nela
 */
public class ImageObjectCounter extends AsyncTask<Bitmap, Void, Integer> {

    public interface Listener {
        public void update(Integer result, Bitmap bitmap);
    }

    private HoshenKopelman hoshenKopelman;
    private Bitmap bitmap;
    private Listener listener;

    public ImageObjectCounter() {
        hoshenKopelman = new HoshenKopelman();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
        listener.update(result, bitmap);
    }

    @Override
    protected Integer doInBackground(Bitmap... params) {
        this.bitmap = params[0];

        Rect rect = new Rect((int) (bitmap.getWidth() * 0.2),
                (int) (bitmap.getHeight() * 0.2),
                (int) (bitmap.getWidth() - bitmap.getWidth() * 0.2),
                (int) (bitmap.getHeight() - bitmap.getHeight() * 0.2));

        bitmap = ImageTool.cropBitmap(bitmap, rect);
        bitmap = ImageTool.convertToGreyScale(bitmap);
        bitmap = ImageTool.binaryImage(bitmap, ImageTool.determineThreshold(bitmap));

        return hoshenKopelman.countObjects(bitmap);
    }
}
