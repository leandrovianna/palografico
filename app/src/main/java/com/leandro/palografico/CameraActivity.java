package com.leandro.palografico;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import com.leandro.palografico.camera.CameraView;
import com.leandro.palografico.camera.MyCamera;
import com.leandro.palografico.util.FullScreenActivity;


public class CameraActivity extends FullScreenActivity {

    private MyCamera camera;
    private CameraView cameraView;
    private Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        camera = new MyCamera(this);
        cameraView = (CameraView) findViewById(R.id.cameraView);
        cameraView.setCamera(camera);
    }

    /** Acionado com o toque no botão de tirar foto */
    public void takePicture(View v) {
        camera.takePicture();

        /*if (image != null)
            Toast.makeText(this, "A foto foi tirada com sucesso!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "ERRO", Toast.LENGTH_SHORT).show();*/
    }

    @Override
    protected void onDestroy() {
        camera.destroy();
        super.onDestroy();
    }
}
