package com.leandro.palografico.camera;

import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyCamera {

    private final Context context;
    private Camera mCamera;
    private Bitmap image;
    private PhotoHandler photoHandler;

    public MyCamera(Context context) {
        this.context = context;
        mCamera = getAndroidCameraInstance();
        photoHandler = new PhotoHandler(context, "PALOGRAFICO");
    }

    private Camera getAndroidCameraInstance() {
        Camera camera = null;
        try {
            camera = Camera.open();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("CameraAPI", "Couln't open the camera");
        }

        return camera;
    }

    public void startPreview(SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("MyCamera", "Couln't preview camera: "+e.getMessage());
        }
    }

    public void changePreview(SurfaceHolder holder) {

        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("CameraAPI", "Couln't preview camera in CameraView: "+e.getMessage());
        }
    }

    public void takePicture() {
        mCamera.takePicture(null, null, photoHandler);

        //TODO: Fazer MyCamera retornar um Bitmap da imagem salva
    }

    public void destroy() {
        mCamera.release();
    }
}
