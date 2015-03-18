package com.leandro.palografico.imageproc;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Parcelable;

import com.leandro.palografico.imageproc.hk.HoshenKopelman;

/**
 * Task que faz o processamento de uma imagem para contar os objetos contidos nela
 */
public class ImageObjectCounter extends AsyncTask<Bitmap, Void, Integer> {

    private HoshenKopelman hoshenKopelman;
    private ImageObjectCounterListener listener;

    public ImageObjectCounter() {
        hoshenKopelman = new HoshenKopelman();
    }

    public void setListener(ImageObjectCounterListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
        listener.update(result);
    }

    @Override
    protected Integer doInBackground(Bitmap... params) {
        Bitmap bitmap = params[0];

        Rect rect;
        rect = new Rect((int) (bitmap.getWidth() * 0.2),
                (int) (bitmap.getHeight() * 0.2),
                (int) (bitmap.getWidth() - bitmap.getWidth() * 0.2),
                (int) (bitmap.getHeight() - bitmap.getHeight() * 0.2));

        bitmap = ImageTool.cropBitmap(bitmap, rect);
        bitmap = ImageTool.convertToGreyScale(bitmap);
        bitmap = ImageTool.binaryImage(bitmap, 80);

        return hoshenKopelman.countObjects(bitmap);
    }
}
