package com.example.triptap.nearbyme;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.triptap.Const;
import com.example.triptap.DynamicListView;
import com.example.triptap.DynamicListView2;
import com.example.triptap.GPSTracker;
import com.example.triptap.R;
import com.example.triptap.TripInfo;
import com.example.triptap.tripme.AdData;
import com.example.triptap.tripme.AdjustmentAdapter;
import com.example.triptap.tripme.CommentAdapter;
import com.example.triptap.tripme.PlaceInformation;

import android.annotation.SuppressLint;  
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup; 
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;



@SuppressLint("NewApi")  
  public class Restaurant extends Fragment implements OnItemClickListener
  {
	private Button btnSubmit;
	private ArrayList <ResData> arrMain;
	private ArrayList <ResData> arrPlan;
	private String TAG =  Restaurant.class.getSimpleName();
	public FragmentTransaction ft;
	public Fragment fr;
	public FragmentManager fm;
	private String jsonResponse;
	private String tag_json_arry = "jarray_req";
	
	private String jsonPlan;
	private int pos;
	private JSONArray jar;
	
	
	private String [] venid;
	private String [] names;
	
	private double [] lats;
	private double [] lngs;
	
	
	
	private boolean [] check;
	private ListView lsvMain;
	
	private RequestQueue mRequestQueue;
	private GPSTracker gpsTracker;
	private double lng;
	private double lat;
	
	private ProgressDialog pDialog;
	
	public Restaurant()
	{}
	
//	private int [] arrImage1 = 
//		{
//			R.drawable.phuket1,
//			R.drawable.phuket2,
//			R.drawable.phuket3
//		};
	
	private String[] arrTitle = 
		{
			"Place A", "Place B", "Place C"
	};
	
	
	
	private String [] arrSub = 
		{
			"rating 9", "rating 8", "rating 7"
	};
	
	

	@Override 
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)     
	{ 
        View rootView = inflater .inflate(R.layout.restaurant, container, false);
        //btnSubmit = (Button)rootView.findViewById(R.id.btn_res);
        
        // Recieve Extra from PlanSelected
//        jsonPlan = getArguments().getString("jsonSt");
//        pos = getArguments().getInt("position");
        
        // init gps tracker
        gpsTracker = new GPSTracker(getActivity());
        //lng = gpsTracker.getLongitude();
        
        
         //gps function
        if(gpsTracker.canGetLocation())
        {
        	
        	lat = gpsTracker.getLatitude();
        	lng = gpsTracker.getLongitude();
        
        	
        	//Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        	//Toast.makeText(getActivity(), "Your Location is - \n " + gpsTracker.getCompleteAddressString(lat, lng) , Toast.LENGTH_LONG).show();
        	 
        	}else
        	{
        	// can't get location
        	// GPS or Network is not enabled
        	// Ask user to enable GPS/network in settings
        	gpsTracker.showSettingsAlert();
        	
        	lat = 7.966284931517934;
        	lng = 98.39051758782342;
        }
        
        
        
        
        
        
        pDialog = new ProgressDialog(getActivity());
		pDialog.setMessage("Loading...");
		pDialog.setCancelable(false);
        
        arrMain = new ArrayList <ResData>();
        ResData data;
        for(int i=0; i<arrTitle.length; i++)
        {
        	data = new ResData();
        	data.setPurl("https://ss3.4sqi.net/img/categories_v2/food/asian_bg_64.png");
        	data.setTitle(arrTitle[i]);
        	data.setDistance("4.56 KM");
        	arrMain.add(data);
        }
        
        
        // ListView setUP
        lsvMain = (ListView)rootView.findViewById(R.id.res_list);
        ResAdapter rdt = new ResAdapter(getActivity(), arrMain);
        lsvMain.setAdapter(rdt);
        lsvMain.setOnItemClickListener(this);
        makeJsonObjReq();
        
        
        // JSON Parsing
//        try 
//        {
//            jar = new JSONArray(jsonPlan);
//            
//        } catch (JSONException e) 
//        {
//            e.printStackTrace();
//        }
        
       // jsonParser(jar);
        
                
        // Button setuo
//        btnSubmit.setOnClickListener(new OnClickListener() 
//        {
// 
//        	 @Override
//             public void onClick(View v) 
//             {
//        		 makeJsonObjReq();
//        		 
////        		  if(gpsTracker.canGetLocation())
////        	        {
////        	        	
////        	        	double latitude = gpsTracker.getLatitude();
////        	        	double longitude = gpsTracker.getLongitude();
////        	        
////        	        	
////        	        	//Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
////        	        	Toast.makeText(getActivity(), "Your Location is - \n " + gpsTracker.getCompleteAddressString(latitude, longitude) + "   "
////        	        			+ latitude + "  " + longitude , Toast.LENGTH_LONG).show();
////        	        	 
////        	        	}else{
////        	        	// can't get location
////        	        	// GPS or Network is not enabled
////        	        	// Ask user to enable GPS/network in settings
////        	        	gpsTracker.showSettingsAlert();
////        	        }
////             	 fr = new PlaceInformation();
////             	 
////           	   fm = getFragmentManager();
////                  ft = fm.beginTransaction();
////                  ft.replace(R.id.frame_container, fr);
////                  ft.commit();
//             }
// 
//        });
 

        // Button click Listener 
        //addListenerOnButton();

      
        return rootView; 
	}

	// On item click to place information
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
		
		//makeJsonObjReq();
		
		 	fr = new PlaceInformation();
		 	Bundle args = new Bundle();
		 	String v = venid[position];
		 	int pr = 3;
        	args.putString("venId", v);
        	args.putInt("Previous", pr);
        	fr.setArguments(args);
        	
        	//Toast.makeText(getActivity(), v, Toast.LENGTH_LONG).show();
     	    fm = getFragmentManager();
            ft = fm.beginTransaction();
            ft.replace(R.id.frame_container, fr);
            ft.commit();
		
	}
	
	private void showProgressDialog() 
	{
		if (!pDialog.isShowing())
			pDialog.show();
	}

	private void hideProgressDialog() 
	{
		if (pDialog.isShowing())
			pDialog.hide();
	}
	
	public double distanceFunction(double lat1, double lon1, double lat2, double lon2)
	{
		double R = 6371; // Radius of the earth in km
		  double dLat = deg2rad(lat2-lat1);  // deg2rad below
		  double dLon = deg2rad(lon2-lon1); 
		  double a = 
		    Math.sin(dLat/2) * Math.sin(dLat/2) +
		    Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * 
		    Math.sin(dLon/2) * Math.sin(dLon/2)
		    ; 
		  double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
		  double d = R * c; // Distance in km
		  return d;
	}
	
	public double deg2rad(double deg) 
	{
		  return deg * (Math.PI/180);
	}
	
	
	private void makeJsonObjReq() 
	{
		String ur = "https://api.foursquare.com/v2/venues/search?near=" +
				lat +
				"," +
				lng +
				"&client_id=" +
				Const.CLIENT_ID +
				"&client_secret=" +
				Const.CLIENT_SECRET +
				"&categoryId=4d4b7105d754a06374d81259&v=20130815";
		showProgressDialog();
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
			      ur, null,
				new Response.Listener<JSONObject>() 
				{

					@Override
					public void onResponse(JSONObject response) 
					{
						Log.d(TAG, response.toString());
						
						
						//msgResponse.setText(response.toString());						
						try 
						{
							jsonResponse = "kk";
							//Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_LONG).show();
							JSONObject rp = response.getJSONObject("response");
	                        // Parsing json object response
	                        // response will be a json object
							JSONArray venues = rp.getJSONArray("venues");
							
							venid = new String [venues.length()];
							names = new String [venues.length()];
							lats = new double [venues.length()];
							lngs = new double [venues.length()];
							
							arrPlan = new ArrayList<ResData>();
							ResData dat;
							
							
							for(int i=0;i<venues.length();i++)
							{
								dat = new ResData();
								JSONObject pl = venues.getJSONObject(i);
								
								JSONArray cate = pl.getJSONArray("categories");
								JSONObject categ = cate.getJSONObject(0);
								JSONObject icon = categ.getJSONObject("icon");
								String pref = icon.getString("prefix");
								pref.replace("\"", "");
								String suff = icon.getString("suffix");
								String pixurl = pref + "bg_64" + suff;
								
								String name = pl.getString("name");
								names[i] = name;
								dat.setTitle(names[i]);
								String id = pl.getString("id");
								venid[i] = id;
								JSONObject location = pl.getJSONObject("location");
								String lt = location.getString("lat");
								lats[i] = Double.parseDouble(lt);
								String lg = location.getString("lng");
								lngs[i] = Double.parseDouble(lg);
								
								double ds = distanceFunction(lats[i],lngs[i],lat,lng);
								DecimalFormat df = new DecimalFormat("#.#");
								dat.setDistance(df.format(ds) + " km"  );
								dat.setPurl(pixurl);
								arrPlan.add(dat);
							}
								
							 ResAdapter rdt = new ResAdapter(getActivity(), arrPlan);
						     lsvMain.setAdapter(rdt);
						     rdt.notifyDataSetChanged();
						     
						     
						     //Toast.makeText(getActivity(), jsonResponse, Toast.LENGTH_LONG).show();
						        
	 
	                    } catch (JSONException e) 
	                    {
	                        e.printStackTrace();
//	                        Toast.makeText(getActivity(),
//	                                "Error: " + e.getMessage(),
//	                                Toast.LENGTH_LONG).show();
	                    }
						
						hideProgressDialog();
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d(TAG, "Error: " + error.getMessage());
						hideProgressDialog();
					}
				}) {

			/**
			 * Passing some request headers
			 * */
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError 
			{
				HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Content-Type", "application/json");
				return headers;
			}

			@Override
			protected Map<String, String> getParams() 
			{
				Map<String, String> params = new HashMap<String, String>();
				params.put("name", "Androidhive");
				params.put("email", "abc@androidhive.info");
				params.put("pass", "password123");

				return params;
			}

		};

		// Adding request to request queue
		addToRequestQueue(jsonObjReq,tag_json_arry);	
	}

	
	public RequestQueue getRequestQueue() 
	{
		if (mRequestQueue == null) 
		{
			mRequestQueue = Volley.newRequestQueue(getActivity());
		}

		return mRequestQueue;
	}

	public <T> void addToRequestQueue(Request<T> req, String tag) {
		// set the default tag if tag is empty
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		getRequestQueue().add(req);
	}

	public <T> void addToRequestQueue(Request<T> req) {
		req.setTag(TAG);
		getRequestQueue().add(req);
	}

	public void cancelPendingRequests(Object tag) 
	{
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}
		
 
  }