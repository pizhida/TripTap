package com.example.triptap;

import com.example.triptap.R;
import com.example.triptap.nearbyme.Hotel;
import com.example.triptap.nearbyme.Restaurant;
import com.example.triptap.tripme.PlaceInformation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
//import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.annotation.SuppressLint;  

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup; 
import android.view.View.OnKeyListener;
import android.widget.Toast;
@SuppressLint("NewApi")  
  public class TapMaps extends Fragment
  { 
//	private String latitude;
//	private String longitude;
	private String name;
	private GoogleMap googleMap;
	private int pp;
	//private MapFragment mf;
	private Toast t;
	private double latitude ;
	private double longitude;
	private String id;
	MapView mapView;
	
	private MarkerOptions marker;
	private SupportMapFragment fk;
	public FragmentTransaction ft;
	public Fragment fr;
	public FragmentManager fm;

	@Override 
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)     
	{ 
        View rootView = inflater.inflate(R.layout.fragment2, container, false);  
       
        latitude = Double.parseDouble(getArguments().getString("latitude"));
        longitude = Double.parseDouble(getArguments().getString("longitude"));
        name = getArguments().getString("name");
        id = getArguments().getString("venId");
        pp = getArguments().getInt("Previous");
        
        MapsInitializer.initialize(getActivity());
        
        
        
        
        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();

        rootView.setOnKeyListener(new OnKeyListener() 
        {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (event.getAction() == KeyEvent.ACTION_DOWN) {
                            if (keyCode == KeyEvent.KEYCODE_BACK) 
                            {
                            	if(pp == 2)
                            	{
                            		 fr = new PlaceInformation();
                        			 Bundle args = new Bundle();
                         		 	//String v = venid[position];
                                 	args.putString("venId", id);
                                 	args.putInt("Previous", pp);
                                 	fr.setArguments(args);
                                	 
                                	  fm = getFragmentManager();
                                       ft = fm.beginTransaction();
                                       ft.replace(R.id.frame_container, fr);
                                       ft.commit();
                            	}
                            	else if(pp == 3)
                            	{
                            		 fr = new PlaceInformation();
                        			 Bundle args = new Bundle();
                         		 	//String v = venid[position];
                                 	args.putString("venId", id);
                                 	args.putInt("Previous", pp);
                                 	fr.setArguments(args);
                                	 
                                	  fm = getFragmentManager();
                                       ft = fm.beginTransaction();
                                       ft.replace(R.id.frame_container, fr);
                                       ft.commit();
                            	}
                                Toast.makeText(getActivity(), "Back Pressed", Toast.LENGTH_SHORT).show();
                                return true;
                            }
                        }
                        return false;
                    }

			
                });
        
        switch (GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity()) )
        {
            case ConnectionResult.SUCCESS:
                Toast.makeText(getActivity(), "SUCCESS", Toast.LENGTH_SHORT).show();
                mapView = (MapView) rootView.findViewById(R.id.map);
                mapView.onCreate(savedInstanceState);
                // Gets to GoogleMap from the MapView and does initialization stuff
                if(mapView!=null)
                {
                    googleMap = mapView.getMap();
           
                    googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    googleMap.getUiSettings().setMyLocationButtonEnabled(false);
                    googleMap.setMyLocationEnabled(true);
                    // Enable / Disable zooming controls
                 	googleMap.getUiSettings().setZoomControlsEnabled(false);
                 	
                 // Enable / Disable my location button
                 	googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                 			
                 // Enable / Disable Compass icon
                 			googleMap.getUiSettings().setCompassEnabled(true);

                 			// Enable / Disable Rotate gesture
                 			googleMap.getUiSettings().setRotateGesturesEnabled(true);

                 			// Enable / Disable zooming functionality
                 			googleMap.getUiSettings().setZoomGesturesEnabled(true);
                

                				// changing marker color
                				
                				MarkerOptions marker = new MarkerOptions().position(
                						new LatLng(latitude, longitude))
                						.title(name);

                				googleMap.addMarker(marker);

                			
                					CameraPosition cameraPosition = new CameraPosition.Builder()
                							.target(new LatLng(latitude,
                									longitude)).zoom(15).build();

                					googleMap.animateCamera(CameraUpdateFactory
                							.newCameraPosition(cameraPosition));
                }
                break;
            case ConnectionResult.SERVICE_MISSING: 
                Toast.makeText(getActivity(), "SERVICE MISSING", Toast.LENGTH_SHORT).show();
                break;
            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED: 
                Toast.makeText(getActivity(), "UPDATE REQUIRED", Toast.LENGTH_SHORT).show();
                break;
            default: Toast.makeText(getActivity(), GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity()), Toast.LENGTH_SHORT).show();
        }
        
        
        
        
        
//        try {
//            // Loading map
//            //initilizeMap();
//            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//            googleMap.setMyLocationEnabled(true);
//            
//            // Enable / Disable zooming controls
//         	googleMap.getUiSettings().setZoomControlsEnabled(false);
//         	
//         // Enable / Disable my location button
//         	googleMap.getUiSettings().setMyLocationButtonEnabled(true);
//         			
//         // Enable / Disable Compass icon
//         	googleMap.getUiSettings().setCompassEnabled(true);
//
//         			// Enable / Disable Rotate gesture
//         		googleMap.getUiSettings().setRotateGesturesEnabled(true);
//
//         			// Enable / Disable zooming functionality
//         			googleMap.getUiSettings().setZoomGesturesEnabled(true);
//
//
//        				// Adding a marker
//        				MarkerOptions marker = new MarkerOptions().position(
//        						new LatLng(latitude, longitude))
//        						.title(name);
//
//        				googleMap.addMarker(marker);
//
//        			
//        					CameraPosition cameraPosition = new CameraPosition.Builder()
//        							.target(new LatLng(latitude,
//        									longitude)).zoom(15).build();
//
//        					googleMap.animateCamera(CameraUpdateFactory
//        							.newCameraPosition(cameraPosition));
//        				
//        			
// 
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
 
        
        
        
        
        return rootView; 
	}
	
	   @Override
	    public void onResume() {
	        mapView.onResume();
	        super.onResume();
	    }
	    @Override
	    public void onDestroy() {
	        super.onDestroy();
	        mapView.onDestroy();
	    }
	    @Override
	    public void onLowMemory() {
	        super.onLowMemory();
	        mapView.onLowMemory();
	    }
	
	
	 /**
     * function to load map. If map is not created it will create it for you
     * */
//    private void initilizeMap() 
//    {
//        if (googleMap == null) 
//        {
//            googleMap = ((SupportMapFragment) getFragmentManager().findFragmentById(
//                    R.id.map)).getMap();
//            
// 
//            // check if map is created successfully or not
//            if (googleMap == null) 
//            {
//                Toast.makeText(getActivity().getApplicationContext(),
//                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
//                        .show();
//            }
//        }
//    }
    
	/*
	 * creating random postion around a location for testing purpose only
	 */
	private double[] createRandLocation(double latitude, double longitude) {

		return new double[] { latitude + ((Math.random() - 0.5) / 500),
				longitude + ((Math.random() - 0.5) / 500),
				150 + ((Math.random() - 0.5) * 10) };
	}
	
//	@Override
//	public void onDestroy() 
//	{
//	    super.onDestroy();
//	    
//	    //fr = new PlaceInformation();
//	 	 
//    	   //fm = getFragmentManager();
//    	  fm = getFragmentManager();
//    	  ft = fm.beginTransaction();
//    	 //fr = new PlaceInformation();
//    	  ft.remove(this);
//    	  ft.commit();
//	    
//	    //getSupportFragmentManager().beginTransaction().remove(this).commit();
//	}
 
  }
