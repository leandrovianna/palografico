package com.leandro.palografico.camera;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PhoneCamera {

    private final Context context;
    private Camera mCamera;
    private Camera.Parameters mCameraParams;
    private final String albumName;
    private final CameraListener listener;

    private Camera.PictureCallback jpegPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            saveImage(data);
        }
    };

    public PhoneCamera(Context context, String albumName, CameraListener listener) {
        this.context = context;
        this.albumName = albumName;
        this.listener = listener;
    }

    public void start() {
        this.mCamera = getAndroidCameraInstance();
        configureCamera();
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

    private void configureCamera() {
        mCamera.setDisplayOrientation(90);

        mCameraParams = mCamera.getParameters();
        mCameraParams.setRotation(90);

        List<Camera.Size> sizes = mCameraParams.getSupportedPictureSizes();
        mCameraParams.setPictureSize(
                sizes.get(sizes.size()-1).width,
                sizes.get(sizes.size()-1).height
            );

        mCamera.setParameters(mCameraParams);
    }

    public void startPreview(SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("PhoneCamera", "Couln't preview camera: "+e.getMessage());
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

    /** Tira uma foto da vista atual da camera */
    public void takePicture() {
        mCamera.takePicture(null, null, jpegPictureCallback);
    }

    private void saveImage(byte[] data) {
        File file = getImageFile(); //recebendo endereço da nova foto

        if (file == null)
            listener.onPictureIsNotTaken();

        //criando o arquivo a partir do vetor de bytes
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data);
            fos.close();
        } catch (IOException e) {
            listener.onPictureIsNotTaken();
        }

        //disparando Intent para avisar da nova foto na Galeria
        Intent broadcastIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        broadcastIntent.setData(Uri.fromFile(file));
        context.sendBroadcast(broadcastIntent);

        //avisando para o Observer que a ação acabou
        listener.onPictureIsTaken(
                BitmapFactory.decodeFile(file.getPath()),
                file
        );
    }

    private File getImageFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);

        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("PhotoHandler", "failed to create directory of album");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        File mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                "palos_teste_"+ timeStamp + ".jpg");

        return mediaFile;
    }

    public void destroy() {
        mCamera.release();
    }
}
