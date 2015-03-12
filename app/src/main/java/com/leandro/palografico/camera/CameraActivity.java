package com.leandro.palografico.camera;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import com.leandro.palografico.Constantes;
import com.leandro.palografico.R;
import com.leandro.palografico.util.FullScreenActivity;

import java.io.File;


public class CameraActivity extends FullScreenActivity implements CameraListener {

    private PhoneCamera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        camera = new PhoneCamera(this, "PALOGRAFICO", this);
        CameraView cameraView = (CameraView) findViewById(R.id.cameraView);
        cameraView.setCamera(camera);
        camera.start();

        findViewById(R.id.takePictureButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera.takePicture();
            }
        });
    }

    @Override
    protected void onDestroy() {
        camera.destroy();
        super.onDestroy();
    }

    @Override
    public void onPictureIsTaken(Bitmap bitmap, File imageFile) {

        //Note: Passing a Bitmap for Extra cause a FAILED BINDER TRANSACTION (Bitmap is too large)
//        getIntent().putExtra(Constantes.BITMAP_EXTRA, bitmap);
        getIntent().putExtra(Constantes.FILE_EXTRA, imageFile);

        setResult(RESULT_OK, getIntent());

        finish();
    }

    @Override
    public void onPictureIsNotTaken() {
        setResult(RESULT_CANCELED, getIntent());
        finish();
    }
}
