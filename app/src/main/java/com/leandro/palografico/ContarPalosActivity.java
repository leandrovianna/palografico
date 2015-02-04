package com.leandro.palografico;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.leandro.palografico.hk.HoshenKopelman;


public class ContarPalosActivity extends ActionBarActivity {

    private ImageView imageView;
    private Bitmap bitmap;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contar_palos);

        imageView = (ImageView) findViewById(R.id.imageView);
        textView = (TextView) findViewById(R.id.textView);

        String path = getIntent().getStringExtra(MainActivity.IMAGE_URL_EXTRA);

        if (path != null) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inMutable = true;

            bitmap = BitmapFactory.decodeFile(path, options);

            imageView.setImageBitmap(bitmap);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contar_palos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_process_image) {

            bitmap = ImageTool.convertToGreyScale(bitmap);
            bitmap = ImageTool.binaryImage(bitmap, 127);

            HoshenKopelman hk = new HoshenKopelman();

            int numberPalos = hk.countObjects(bitmap);

            imageView.setImageBitmap(hk.createOutputImage());
            textView.setText("São "+numberPalos+" palos.");
        }

        return super.onOptionsItemSelected(item);
    }
}
