package com.example.earth_core;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class viewmaps extends FragmentActivity{
	String s;
	private GoogleMap mMap;
	LatLng userLocation;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		super.onCreate(savedInstanceState);
		
		 requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.vieworemap);
		
		 
		  
	      //  setContentView(R.layout.main);
	 
	        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.window_title);
	        ImageButton icon  = (ImageButton) findViewById(R.id.button);
	        icon.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					SharedPreferences settings = getSharedPreferences("MYPREFS", 0);
	     	        String mainMenuView = settings.getString("MAIN_MENU_VIEW", "");
	     	        Intent mainIntent = new Intent(viewmaps.this,MainActivity.class);
	               	if(mainMenuView.equalsIgnoreCase("grid view"))
	               		mainIntent = new Intent(viewmaps.this,MainActivity.class);
	            	else if(mainMenuView.equalsIgnoreCase("list view"))
	            		mainIntent = new Intent(viewmaps.this,MainActivityList.class);
	            	
	                startActivity(mainIntent);
				}
	        	
	        });
		if(!haveNetworkConnection(this))
		{
			Toast.makeText(this,"Check Internet Settings",Toast.LENGTH_LONG).show();
		}
		
		
		//for getting users location
		LocationManager service = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		String provider = service.getBestProvider(criteria, false);
		Location location = service.getLastKnownLocation(provider);
		userLocation = new LatLng(location.getLatitude(),location.getLongitude());
		setUpMapIfNeeded();
		
		
		s= getIntent().getExtras().getString("mineral");
		TextView tv= (TextView) findViewById(R.id.textView1);
		tv.setText(s.toUpperCase());
		if(s.equalsIgnoreCase("Lead-Zinc"))
			s="Lead_Zinc";
		
		mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		mMap.setMyLocationEnabled(true);
		mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		UiSettings set=mMap.getUiSettings();
		set.setAllGesturesEnabled(true);
		set.setZoomControlsEnabled(true);
		mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(23.91877,81.79894), 4));
			
		
		TestAdapter mDbHelper1 = new TestAdapter(this);        
		mDbHelper1.open();
		Cursor testdata1 = mDbHelper1.getData("SELECT * FROM " + s);
	
		for(testdata1.moveToFirst();!testdata1.isAfterLast();testdata1.moveToNext())
		 {
			 
			 mMap.addMarker(new MarkerOptions()
			 	.position(new LatLng(testdata1.getDouble(4),testdata1.getDouble(5)))
			 	.title(testdata1.getString(1)));
			
		 }
		mDbHelper1.close();
	
		mMap.setInfoWindowAdapter(new InfoWindowAdapter()
		 {

			@Override
			public View getInfoContents(Marker marker) {
				View myContentView=getLayoutInflater().inflate(R.layout.custommarker,null);
				TextView tv1 = ((TextView)myContentView.findViewById(R.id.textView1));
				tv1.setGravity(Gravity.CENTER_HORIZONTAL);
				if(marker.getTitle().equalsIgnoreCase("you"))
				{
					
					return null;
				}
				
				
				
				
				TextView tv2 = ((TextView)myContentView.findViewById(R.id.textView2));
				TextView tv3 = ((TextView)myContentView.findViewById(R.id.textView3));
				TextView tv4 = ((TextView)myContentView.findViewById(R.id.textView4));
				TextView tv5 = ((TextView)myContentView.findViewById(R.id.textView5));
								
				String title=marker.getTitle();
				
				TestAdapter mDbHelper = new TestAdapter(viewmaps.this);        
				mDbHelper.open();
				
				try{
				Cursor testdata = mDbHelper.getData("SELECT * FROM " +s+" where Locality like '%"+title+"%'");							
				tv1.setText(testdata.getString(2)+" ("+testdata.getString(1)+")");
				tv2.setText(testdata.getColumnName(0)+":"+testdata.getString(0));
				tv3.setText(testdata.getColumnName(7)+":"+testdata.getString(7));
				tv4.setText(testdata.getColumnName(8)+":"+testdata.getString(8));
				tv5.setText(testdata.getColumnName(9)+":"+testdata.getString(9));
				}
				catch(Exception e){
					Log.d("error in values",""+e);
					
				}
				mDbHelper.close();				
				return myContentView;
			}

			@Override
			public View getInfoWindow(Marker marker) {
				
				return null;
			}});

	
		 }

	private void setUpMapIfNeeded() {

        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }
	private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(userLocation).title("You")
        		.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
    }
	
	
	private boolean haveNetworkConnection(Context context)
	{
	    boolean haveConnectedWifi = false;
	    boolean haveConnectedMobile = false;

	    ConnectivityManager cm = (ConnectivityManager) viewmaps.this.getSystemService(Context.CONNECTIVITY_SERVICE);
	    // or if function is out side of your Activity then you need context of your Activity
	    // and code will be as following
	    // (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

	    NetworkInfo[] netInfo = cm.getAllNetworkInfo();
	    for (NetworkInfo ni : netInfo)
	    {
	        if (ni.getTypeName().equalsIgnoreCase("WIFI"))
	        {
	            if (ni.isConnected())
	            {
	                haveConnectedWifi = true;
	                System.out.println("WIFI CONNECTION AVAILABLE");
	            } else
	            {
	                System.out.println("WIFI CONNECTION NOT AVAILABLE");
	            }
	        }
	        if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
	        {
	            if (ni.isConnected())
	            {
	                haveConnectedMobile = true;
	                System.out.println("MOBILE INTERNET CONNECTION AVAILABLE");
	            } else
	            {
	                System.out.println("MOBILE INTERNET CONNECTION NOT AVAILABLE");
	            }
	        }
	    }
	    return haveConnectedWifi || haveConnectedMobile;
	}
}
