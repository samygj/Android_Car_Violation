package com.yuan.ap;

//import com.yuan.ap.fileupload.FileUploadActivity;
//import com.yuan.ap.infocollect.InfoCollectActivity;
//import com.yuan.ap.infoquery.InfoQueryActivity;
//import com.yuan.ap.location.GpsLocationActivity;




import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String[] menus = { "Car Info Query", "Car Info Collection", "GPS location" };
		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, menus));
		getListView().setTextFilterEnabled(true);
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

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent=null;
		switch (position) {
		case 0:
			intent = new Intent(MainActivity.this,InfoQueryActivity.class);
			startActivity(intent);
			break;
		case 1:
			intent = new Intent(MainActivity.this,InfoCollectActivity.class);
			startActivity(intent);
			break;
		case 2:
			intent = new Intent(MainActivity.this,GpsLocationActivity.class);
			startActivity(intent);
			break;
		}
	}
}