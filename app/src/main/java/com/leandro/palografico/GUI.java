package com.leandro.palografico;
import android.app.AlertDialog;
import android.content.Context;

public class GUI {

	public static void showDialog(String title, String text, Context context) {
		AlertDialog.Builder dialogo = new AlertDialog.Builder(context);
		dialogo.setTitle(title)
			.setMessage(text)
			.setPositiveButton("OK", null)
			.show();
	}
	
}
