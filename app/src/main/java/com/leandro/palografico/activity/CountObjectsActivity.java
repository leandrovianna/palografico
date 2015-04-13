package com.leandro.palografico.activity;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.leandro.palografico.Constantes;
import com.leandro.palografico.GUI;
import com.leandro.palografico.R;
import com.leandro.palografico.imageapi.ImageObjectCounter;

import java.io.File;


public class CountObjectsActivity extends ActionBarActivity implements ImageObjectCounter.Listener {

    private Button buttonInitCount;
    private ImageView imageView;
    private Bitmap bitmap;
    private ImageObjectCounter imageObjectCounter;

    private AlertDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_objects);

        File file = (File) getIntent().getSerializableExtra(Constantes.FILE_EXTRA);

        if (file != null) {

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inMutable = true;
            bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);

            buttonInitCount = (Button) findViewById(R.id.buttonInitCount);
            imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setImageBitmap(bitmap);

            buttonInitCount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initCount();
                }
            });

        }
    }

    private void initCount() {
        imageObjectCounter = new ImageObjectCounter();
        imageObjectCounter.setListener(this);
        imageObjectCounter.execute(bitmap);
        loadingDialog = GUI.createLoadingDialog(this);
        loadingDialog.show();
    }

    @Override
    public void update(Integer result, Bitmap bitmap) {
        loadingDialog.hide();
        loadingDialog.dismiss();
        imageView.setImageBitmap(bitmap);
        GUI.createMessageDialog("Resultado", "São " + result + " palos na imagem", this);
    }

    @Override
    protected void onDestroy() {
        if (imageObjectCounter != null)
            imageObjectCounter.cancel(true);

        if (loadingDialog != null)
            loadingDialog.dismiss();

        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_count_objects, menu);
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
