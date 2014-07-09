/**
 * 
 */
package com.yuan.ap;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.yuan.ap.R;
import com.yuan.ap.R.id;
import com.yuan.ap.R.layout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


public class InfoQueryActivity extends Activity{
	private Button cancelBtn, searchBtn;
	private EditText plateEditText;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.query);
		cancelBtn = (Button) findViewById(R.id.cancelButton);
		searchBtn = (Button) findViewById(R.id.searchButton);

		plateEditText = (EditText) findViewById(R.id.plateEditText);
		
		
		searchBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String plateNumber = plateEditText.getText().toString();
				search(plateNumber);
			}
		});

	}
	
	private void search(String p) {
		String urlStr = "http://192.168.1.8:8080/Android_Police_Server/servlet/SearchServlet?";
		String queryString = "plate=" + p;
		urlStr += queryString;
		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStream in = (InputStream) conn.getInputStream();
				byte[] b = new byte[in.available()];
				in.read(b);
				String msg = new String(b);
				showDialog(msg);
				in.close();
			}
			conn.disconnect();
		} catch (Exception e) {
			showDialog(e.getMessage());
		}
	}
	
	private void showDialog(String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(msg).setCancelable(false).setPositiveButton(
				"Submit", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

}
