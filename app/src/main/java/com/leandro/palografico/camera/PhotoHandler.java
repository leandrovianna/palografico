package com.leandro.palografico.camera;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhotoHandler implements PictureCallback {

    private final Context context;
    private String albumName;
    private Bitmap bitmap;

    public PhotoHandler(Context context, String albumName) {
        this.context = context;
        this.albumName = albumName;
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        try {
            addImage(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addImage(byte[] data) throws IOException {
        File file = getImageFile();

        FileOutputStream fOut = new FileOutputStream(file);
        fOut.write(data);
        fOut.close();

        Intent broadcastIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        broadcastIntent.setData(Uri.fromFile(file));
        context.sendBroadcast(broadcastIntent);

        bitmap = BitmapFactory.decodeFile(file.getPath());
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

    public Bitmap getBitmap() {
        return bitmap;
    }
}
