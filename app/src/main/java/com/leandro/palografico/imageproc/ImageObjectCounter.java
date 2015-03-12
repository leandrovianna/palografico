package com.leandro.palografico.imageproc;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.leandro.palografico.imageproc.hk.HoshenKopelman;

import java.util.Observable;
import java.util.Observer;

/**
 * Task que faz o processamento de uma imagem para contar os objetos contidos nela
 */
public class ImageObjectCounter extends AsyncTask<Bitmap, Void, Integer> {

    private HoshenKopelman hoshenKopelman;
    private ImageObjectCounterListener listener;

    public ImageObjectCounter() {
        hoshenKopelman = new HoshenKopelman();
    }

    public ImageObjectCounterListener getListener() {
        return listener;
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

        bitmap = ImageTool.convertToGreyScale(bitmap);
        bitmap = ImageTool.binaryImage(bitmap, 80);

        int nObjects = hoshenKopelman.countObjects(bitmap);

        return nObjects;
    }
}
