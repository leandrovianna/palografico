package com.leandro.palografico;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.leandro.palografico.camera.CameraActivity;

import java.io.File;


public class MainActivity extends ActionBarActivity {

    private Button buttonCamera;
    private Button buttonGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonCamera = (Button) findViewById(R.id.buttonCamera);
        buttonGallery = (Button) findViewById(R.id.buttonGallery);

        buttonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });

        buttonGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }

    public void openCamera() {
        Intent intent = new Intent(this, CameraActivity.class);
        startActivityForResult(intent, Constantes.CAPTURE_IMAGE_REQUEST_CODE);
    }

    private void openGallery() {
        //TODO: Fazer activity com as fotos de testes tiradas anteriomente
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constantes.CAPTURE_IMAGE_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {
                Log.i("", "A foto foi tirada com sucesso e CameraActivity deu uma resposta");

                File file = (File) data.getSerializableExtra(Constantes.FILE_EXTRA);

                //TODO: Abrir a activity de contar os palos
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
