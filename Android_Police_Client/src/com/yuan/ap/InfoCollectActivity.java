package com.yuan.ap;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class InfoCollectActivity extends Activity {
	private EditText plateEditText, violationEditText;
	private Button uploadBtn;
	
	private TextView mDateDisplay;
    private Button mPickDate;
    private int mYear;
    private int mMonth;
    private int mDay;

    static final int DATE_DIALOG_ID = 0;
    
    // the callback received when the user "sets" the date in the dialog
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, 
                                      int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateDisplay();
                }
            };
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.upload);
		
		uploadBtn = (Button) findViewById(R.id.uploadButton);

		plateEditText = (EditText) findViewById(R.id.plateEditText);
		violationEditText = (EditText) findViewById(R.id.violationEditText);
		
		// capture our View elements
		mDateDisplay = (TextView) findViewById(R.id.dateEditText);
        mPickDate = (Button) findViewById(R.id.setButton);
		
     // add a click listener to the button
        mPickDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        // get the current date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        // display the current date (this method is below)
        updateDisplay();
        
		uploadBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String plateNumber = plateEditText.getText().toString();
				String violation = violationEditText.getText().toString();
				String date = mDateDisplay.getText().toString();
				upload(plateNumber, violation, date);
			}
		});
	}
	
	private void upload(String p, String v, String d) {
		String urlStr = "http://192.168.1.8:8080/Android_Police_Server/servlet/UploadServlet?";
		String queryString = "plateNumber=" + p + "&violation=" + v + "&date=" + d;
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
	
	// updates the date in the TextView
    private void updateDisplay() {
        mDateDisplay.setText(
            new StringBuilder()
                    // Month is 0 based so add 1
                    .append(mMonth + 1).append("-")
                    .append(mDay).append("-")
                    .append(mYear).append(" "));
    }
    
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case DATE_DIALOG_ID:
            return new DatePickerDialog(this,
                        mDateSetListener,
                        mYear, mMonth, mDay);
        }
        return null;
    }
}
