package com.leandro.palografico;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.leandro.palografico.hk.HoshenKopelman;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends ActionBarActivity {

    public static final int CAPTURE_IMAGE_REQUEST_CODE = 102;
    private Uri uriImage;

    private Bitmap bitmapFromCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openCamera(View view) {

        Intent intent = new Intent(this, CameraActivity.class);
        startActivityForResult(intent, CAPTURE_IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                bitmapFromCamera =
                        data.getExtras().getParcelable(CameraActivity.BITMAP_EXTRA);

                int nPalos = countPalos(processBitmap(bitmapFromCamera));

                GUI.showDialog("Contagem de Palos Concluida",
                        "São " + nPalos,
                        this);
            }
        }
    }

    private Bitmap processBitmap(Bitmap bitmap) {

        Bitmap newBitmap = ImageTool.convertToGreyScale(bitmap);
        newBitmap = ImageTool.binaryImage(newBitmap, 120);

        return newBitmap;
    }

    private int countPalos(Bitmap b) {
        HoshenKopelman hk = new HoshenKopelman();
        return hk.countObjects(b);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
