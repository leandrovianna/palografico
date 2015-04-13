package com.leandro.palografico;
import android.app.AlertDialog;
import android.content.Context;
import android.widget.ProgressBar;

public class GUI {

	public static AlertDialog createMessageDialog(String title, String text, Context context) {
		AlertDialog.Builder dialogo = new AlertDialog.Builder(context);
		dialogo.setTitle(title)
			.setMessage(text)
			.setPositiveButton("OK", null);

        return dialogo.show();
	}

    public static AlertDialog createLoadingDialog(Context context) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(context);

        dialog.setTitle("Carregando...");
        dialog.setView(new ProgressBar(context));

        return dialog.create();
    }
}
