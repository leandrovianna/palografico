package com.leandro.palografico;

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

import com.leandro.palografico.imageproc.ImageObjectCounter;
import com.leandro.palografico.imageproc.ImageObjectCounterListener;

import java.io.File;


public class CountObjectsActivity extends ActionBarActivity implements ImageObjectCounterListener {

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

            imageView.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageBitmap(bitmap);
                }
            });

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
    public void update(Integer result) {
        loadingDialog.hide();
        GUI.createMessageDialog("Resultado", "São " + result + " palos na imagem", this);
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
