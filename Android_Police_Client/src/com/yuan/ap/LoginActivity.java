package com.yuan.ap;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {
	/** Called when the activity is first created. */
	private Button cancelBtn, loginBtn;
	private EditText userEditText, pwdEditText;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		cancelBtn = (Button) findViewById(R.id.cancelButton);
		loginBtn = (Button) findViewById(R.id.loginButton);

		userEditText = (EditText) findViewById(R.id.userEditText);
		pwdEditText = (EditText) findViewById(R.id.pwdEditText);

		loginBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String username = userEditText.getText().toString();
				String pwd = pwdEditText.getText().toString();
				login(username, pwd);
			}
		});
		
		cancelBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
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

	private void login(String username, String password) {
		String urlStr = "http://192.168.1.8:8080/Android_Police_Server/servlet/LoginServlet?";
		String queryString = "username=" + username + "&password=" + password;
		urlStr += queryString;
		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStream in = (InputStream) conn.getInputStream();
				byte[] b = new byte[in.available()];
				in.read(b);
				String msg = new String(b);
				if (msg.equals("success")) {
					Intent intent = new Intent(LoginActivity.this,MainActivity.class);
					startActivity(intent);
				}
				if (msg.equals("fail")) {
					showDialog(msg);
					in.close();
				}
			}
			conn.disconnect();
		} catch (Exception e) {
			showDialog(e.getMessage());
		}

	}
}