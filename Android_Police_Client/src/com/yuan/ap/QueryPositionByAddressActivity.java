package com.yuan.ap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
//import com.google.android.maps.GmmGeocoder; 
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.yuan.ap.R;

public class QueryPositionByAddressActivity extends MapActivity {
	  private MapController mMapController01;
	  private MapView mMapView01;
	  private Button mButton01,mButton02,mButton03;
	  private EditText mEditText01;
	  private int intZoomLevel=15;
	  private String TAG = "HIPPO_GEO_DEBUG";
	  private  String address;
	  @Override
	  protected void onCreate(Bundle icicle)
	  {
	    // TODO Auto-generated method stub
	    super.onCreate(icicle);
	    setContentView(R.layout.query_position_by_address);
	    
	    mEditText01 = (EditText)findViewById(R.id.myEditText1);
	    mEditText01.setText
	    (
	      getResources().getText(R.string.str_default_address).toString()
	    );
	    
	    
	    mMapView01 = (MapView)findViewById(R.id.myMapView1);
	    mMapController01 = mMapView01.getController();
	    
	 
	    mMapView01.setSatellite(true);
	    mMapView01.setStreetView(true);
	    
	    mButton01 = (Button)findViewById(R.id.myButton1);
	    mButton01.setOnClickListener(new Button.OnClickListener()
	    {
	      @Override
	      public void onClick(View v)
	      {  	    	  
	    	  
	    	  address= mEditText01.getText().toString();
	      
	        // TODO Auto-generated method stub
	        if(mEditText01.getText().toString()!="")
	        {
	          refreshMapViewByGeoPoint
	          (
	            getGeoByAddress(mEditText01.getText().toString()),
	            mMapView01,intZoomLevel,true);
	        }
	      }
	    });
	    
	    
	    mButton02 = (Button)findViewById(R.id.myButton2);
	    mButton02.setOnClickListener(new Button.OnClickListener()
	    {
	      @Override
	      public void onClick(View v)
	      {
	        // TODO Auto-generated method stub
	        intZoomLevel++;
	        if(intZoomLevel>mMapView01.getMaxZoomLevel())
	        {
	          intZoomLevel = mMapView01.getMaxZoomLevel();
	        }
	        mMapController01.setZoom(intZoomLevel);
	      }
	    });
	    
	    
	    mButton03 = (Button)findViewById(R.id.myButton3);
	    mButton03.setOnClickListener(new Button.OnClickListener()
	    {
	      @Override
	      public void onClick(View v)
	      {
	        // TODO Auto-generated method stub
	        intZoomLevel--;
	        if(intZoomLevel<1)
	        {
	          intZoomLevel = 1;
	        }
	        mMapController01.setZoom(intZoomLevel);
	      }
	    });
	    
	    
	    refreshMapViewByGeoPoint
	    (
	      getGeoByAddress
	      (
	    		  mEditText01.getText().toString()
	      ),mMapView01,intZoomLevel,true
	    );
	  }
	  
	  private GeoPoint getGeoByAddress(String strSearchAddress)
	  {
	    GeoPoint gp = null;
	    try
	    {
	      if(strSearchAddress!="")
	      {
	        Geocoder mGeocoder01 = new Geocoder(QueryPositionByAddressActivity.this, Locale.getDefault());
	        //showDialog(strSearchAddress);
	        List<Address> lstAddress = mGeocoder01.getFromLocationName(strSearchAddress, 1);
	        showDialog(strSearchAddress);
	        if (!lstAddress.isEmpty())
	        { 
	          
	          Address adsLocation = lstAddress.get(0);
	          double geoLatitude = adsLocation.getLatitude()*1E6;
	          double geoLongitude = adsLocation.getLongitude()*1E6;
	          gp = new GeoPoint((int) geoLatitude, (int) geoLongitude);
	        }
	        else
	        {showDialog("Y");
	          Log.i(TAG, "Address GeoPoint NOT Found.");
	        }
	      }
	    }
	    catch (Exception e)
	    { 
	      //showDialog(e.toString()); 
	    }
	    return gp;
	  }
	  
	  public static void refreshMapViewByGeoPoint(GeoPoint gp, MapView mv, int zoomLevel, boolean bIfSatellite)
	  {
	    try
	    {
	      mv.displayZoomControls(true);
	      mv.displayZoomControls(true);

	      MapController mc = mv.getController();
	      
	      mc.animateTo(gp);
	      
	    
	      mc.setZoom(zoomLevel);
	      
	      
	      if(bIfSatellite)
	      {
	        mv.setSatellite(true);
	        mv.setStreetView(true);
	      }
	      else
	      {
	        mv.setSatellite(false);
	      }
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	  }
	  
	  
	  
	  @Override
	  protected boolean isRouteDisplayed()
	  {
	    // TODO Auto-generated method stub
	    return false;
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
