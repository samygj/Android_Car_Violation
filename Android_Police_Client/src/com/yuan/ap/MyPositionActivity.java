package com.yuan.ap;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.yuan.ap.R;

public class MyPositionActivity extends MapActivity {

	private LinearLayout linearLayout;
	private MapView mapView;
	private ZoomControls mZoom;
	private LocationManager locationManager;
	
	private List<Overlay> mapOverlays;
	private Drawable drawable;
	private MyPositionItemizedOverlay itemizedOverlay;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_position);

		linearLayout = (LinearLayout) findViewById(R.id.zoomview);
		mapView = (MapView) findViewById(R.id.mapview);
		mZoom = (ZoomControls) mapView.getZoomControls();
		linearLayout.addView(mZoom);
		
		
		mapOverlays = mapView.getOverlays();
		drawable = this.getResources().getDrawable(R.drawable.androidmarker);
		itemizedOverlay = new MyPositionItemizedOverlay(drawable);
		
		MapController controller = mapView.getController();
		
		GeoPoint point = locate(controller);
		
		OverlayItem overlayitem = new OverlayItem(point, "", "");

		
		itemizedOverlay.addOverlay(overlayitem);
		mapOverlays.add(itemizedOverlay);

	}

	private GeoPoint locate(MapController controller) {
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		Criteria crit = new Criteria();
		crit.setAccuracy(Criteria.ACCURACY_FINE);
		String locationProvider = locationManager.getBestProvider(crit, true);

		
		
		
		//String locationProvider = LocationManager.GPS_PROVIDER;
		
		//locationManager.requestLocationUpdates(this.myLocationManager.GPS_PROVIDER, 0, 0, this); 


		Location location = locationManager.getLastKnownLocation(locationProvider);
		updateToNewLocation(location);

		//showDialog(locationProvider);
//		while(location== null)
//		{
//		locationManager.requestLocationUpdates(locationProvider,60000, 10, locationListener);
// 
//			//location = locationManager.getLastKnownLocation(locationProvider);   
////showDialog();
//		}
		
		double lat=0.0;
		double lng = 0.0;
		if (location != null) {
			 lat = location.getLatitude();
			 lng = location.getLongitude();
		}else{
			lat = 40.521983;
			lng = -74.462832;
		}
		
		GeoPoint point = new GeoPoint((int) (lat * 1E6), (int) (lng * 1E6));
		controller.animateTo(point);
		return point;
	}

	@Override
	protected boolean isRouteDisplayed() {
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
	
	
	private final LocationListener locationListener = new LocationListener()
    {
        public void onLocationChanged(Location location)
        {
            updateWithNewLocation(location);
        }

        public void onProviderDisabled(String provider)
        {
            updateWithNewLocation(null);
        }

        public void onProviderEnabled(String provider)
        {

        }

        public void onStatusChanged(String provider, int status, Bundle extras)
        {

        }
    };

	
    private void updateWithNewLocation(Location location)
    {
        String latLongString;
        TextView myLocationText;
      // myLocationText = (TextView)findViewById(R.id.myLocationText);
showDialog("aa");
        if (location != null)
        {
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            latLongString = "Lat:" + lat + "\nLong:" + lng;
        }
        else
        {
            latLongString = "No location found";
        }

        showDialog("Your Current Position is:\n" +  latLongString);
    }
	
	
	
    private void updateToNewLocation(Location location) {

    	
    	if (location !=null) {
    	double latitude = location.getLatitude();
    	double longitude= location.getLongitude();
    	showDialog("Your Current Position is:\n" +  latitude + longitude);
    	} else {
    		
    	}
    	}
    
    
	
	
	
	}
