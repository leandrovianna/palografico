package com.leandro.palografico.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.leandro.palografico.Constantes;
import com.leandro.palografico.R;
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
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, Constantes.IMAGE_FROM_GALLERY_RESQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constantes.CAPTURE_IMAGE_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {
                Log.i(Constantes.TAG, "A foto foi tirada com sucesso e CameraActivity deu uma resposta");

                File file = (File) data.getSerializableExtra(Constantes.FILE_EXTRA);

                Intent intent = new Intent(this, CountObjectsActivity.class);
                intent.putExtra(Constantes.FILE_EXTRA, file);
                startActivity(intent);
            }
        } else if (requestCode == Constantes.IMAGE_FROM_GALLERY_RESQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                File file = new File(getPath(data.getData()));

                Intent intent = new Intent(this, CountObjectsActivity.class);
                intent.putExtra(Constantes.FILE_EXTRA, file);
                startActivity(intent);
            }
        }
    }

    /**
     * Retorna o caminho da imagem a partir de um image uri
     * De: http://stackoverflow.com/questions/2169649/get-pick-an-image-from-androids-built-in-gallery-app-programmatically
     * @param uri image uri
     * @return O caminho da imagem
     */
    public String getPath(Uri uri) {
        if (uri == null) {
            Log.e(Constantes.TAG, "getPath(): uri is null");
            return null;
        }

        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            //HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            //THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            String filePath = cursor.getString(columnIndex);
            cursor.close();
            return filePath;
        }
        else
            return uri.getPath();               // FOR OI/ASTRO/Dropbox etc
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
