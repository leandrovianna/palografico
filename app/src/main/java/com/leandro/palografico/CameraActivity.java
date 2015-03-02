package com.leandro.palografico;

import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.leandro.palografico.camera.CameraListener;
import com.leandro.palografico.camera.CameraView;
import com.leandro.palografico.camera.PhoneCamera;
import com.leandro.palografico.util.FullScreenActivity;

import java.io.File;


public class CameraActivity extends FullScreenActivity implements CameraListener {

    private PhoneCamera camera;
    private CameraView cameraView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        camera = new PhoneCamera(this, "PALOGRAFICO", this);
        cameraView = (CameraView) findViewById(R.id.cameraView);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_camera, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPictureIsTaken(Bitmap bitmap, File imageFile) {

        getIntent().putExtra("bitmap", bitmap);
        getIntent().putExtra("file", imageFile);

        finishActivity(MainActivity.CAPTURE_IMAGE_REQUEST_CODE);
        finish();
    }

    @Override
    public void onPictureIsNotTaken() {

    }
}
