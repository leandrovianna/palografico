package com.leandro.palografico;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.leandro.palografico.camera.CameraListener;
import com.leandro.palografico.camera.CameraView;
import com.leandro.palografico.camera.PhoneCamera;
import com.leandro.palografico.hk.HoshenKopelman;
import com.leandro.palografico.util.FullScreenActivity;

import java.io.File;


public class CameraActivity extends FullScreenActivity implements CameraListener {

    public static final String BITMAP_EXTRA = "com.leandro.palografico.BITMAP_EXTRA";

    private PhoneCamera camera;
    private CameraView cameraView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        camera = new PhoneCamera(this, "PALOGRAFICO", this);
        cameraView = (CameraView) findViewById(R.id.cameraView);
        cameraView.setCamera(camera);
    }

    /** Acionado com o toque no botão de tirar foto */
    public void takePicture(View v) {
        camera.takePicture();
    }

    @Override
    protected void onResume() {
        super.onResume();
        camera.start();
    }

    @Override
    protected void onPause() {
        camera.destroy();
        super.onPause();
    }

    @Override
    protected void onStop() {
        camera.start();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        camera.destroy();
        super.onDestroy();
    }

    @Override
    public void onPictureIsTaken(Bitmap bitmap, File imageFile) {
        Toast.makeText(this, "A foto foi tirada com sucesso!", Toast.LENGTH_SHORT).show();

        Intent resultIntent = new Intent();
        resultIntent.putExtra(BITMAP_EXTRA, bitmap);
        setResult(Activity.RESULT_OK, resultIntent);
//        finishActivity(MainActivity.CAPTURE_IMAGE_REQUEST_CODE);
        finish();
    }

    @Override
    public void onPictureIsNotTaken() {
        Toast.makeText(this, "ERRO", Toast.LENGTH_SHORT).show();
    }
}
