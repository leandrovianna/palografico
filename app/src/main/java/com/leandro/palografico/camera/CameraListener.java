package com.leandro.palografico.camera;

import android.graphics.Bitmap;

import java.io.File;

public interface CameraListener {

    public void onPictureIsTaken(Bitmap bitmap, File imageFile);

    public void onPictureIsNotTaken();
}
