package com.example.triptap.tripme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.triptap.nearbyme.Hotel;
import com.example.triptap.nearbyme.Restaurant;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Request;
import com.android.volley.Response;
//import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.triptap.Const;
import com.example.triptap.R;
import com.example.triptap.TapMaps;
import com.example.triptap.TripInfo;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;


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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
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
  public class PlaceInformation extends Fragment implements OnItemClickListener
  {
	private String TAG = PlaceInformation.class.getSimpleName();
	private Spinner province;
	private Button btnSubmit;
	
	public FragmentTransaction ft;
	public Fragment fr;
	public FragmentManager fm;
	
	private String location;
	private String name;
	private String rating;
	private RequestQueue mRequestQueue;
	
	private ProgressDialog pDialog;
	private String tag_json_arry = "jarray_req";
	private String jsonResponse;
	
	private GoogleMap googleMap;
	private MapFragment mf;
	//private Toast t;
	private String latitude ;
	private String longitude;
	private MarkerOptions marker;
	private String imgUrl;
	private String mapUrl;
	private String mapUrl1;
	private String address;
	
	private String [] comment;
	
	private ImageLoader iml;
	
	private DisplayImageOptions dmp;
	
	private String purl;
	private int pp;
	
	private TextView title;
	private TextView rate;
	private ImageView mapIm;
	private ImageView picm;
	private TextView fotex;
	private ListView lsvMain;
	private String jsonPlan;
	private int pos;
	
	public PlaceInformation()
	{}
		
	private String[] arrTitle = 
		{
			"สะพานอันมีเรื่องราวเกี่ยวกับความรักตัองห้าม", 
			"สวยนะคับตอนเย็นๆมาดูพระอาทิตย์ตกดินกันนะคับ แวะทานข้าวได้ที่ร้านท่านุ่นซีฟู้ดนะคับ", 
			"สะพานสารสินเก่า เดี๋ยวนี้ห้ามรถวิ่งแล้วสร้างเป็นจุดชมวิว มีคนมาแวะถ่ายภาพถ่ายพรีเวดดิ้งกัน คลาสสิคมาก ^^",
			"ติดกับสะพานสารสินจะมองเห็น ร้านท่านุ่นซีฟู๊ด ซันเซ็ทบีช ด้วย xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" +
			"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" +
			"xxxxxxxxxxxxx"
	};
	
	@Override 
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)     
	{ 
        View rootView = inflater .inflate(R.layout.place_information, container, false);
        
        //purl = getArguments().getString("venId");
        
        iml = ImageLoader.getInstance();
		dmp = new DisplayImageOptions.Builder().cacheInMemory()
				.cacheOnDisc().resetViewBeforeLoading()
				.showImageForEmptyUri(R.drawable.phuket1).build();	
        //province = (Spinner)rootView.findViewById(R.id.province);
        //btnSubmit = (Button)rootView.findViewById(R.id.pi_back);
        
        purl = getArguments().getString("venId");
        pp = getArguments().getInt("Previous");
        
        jsonPlan = getArguments().getString("jsonSt");
        pos = getArguments().getInt("position");
        
        pDialog = new ProgressDialog(getActivity());
 		pDialog.setMessage("Loading...");
 		pDialog.setCancelable(false);        
        //btnSubmit.setText("Back");
        title = (TextView)rootView.findViewById(R.id.pi_place);
        rate = (TextView)rootView.findViewById(R.id.pi_rating);
        mapIm = (ImageView)rootView.findViewById(R.id.map_image);
        picm = (ImageView)rootView.findViewById(R.id.pi_image);
        fotex = (TextView)rootView.findViewById(R.id.for_lo);
        lsvMain = (ListView)rootView.findViewById(R.id.commentlist);
        
        CommentAdapter comdt = new CommentAdapter(getActivity(),arrTitle);
        //ArrayAdapter<String> arrAdt = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_expandable_list_item_1,arrTitle);
        lsvMain.setAdapter(comdt);
        lsvMain.setOnItemClickListener(this);
        
        makeJsonObjReq();
        
        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();

        rootView.setOnKeyListener(new OnKeyListener() 
        {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (event.getAction() == KeyEvent.ACTION_DOWN) {
                            if (keyCode == KeyEvent.KEYCODE_BACK) 
                            {
                            	if(pp == 1)
                            	{
                            		 fr = new PlanAdjustment();
                        			 //Bundle args = new Bundle();
                         		 	//String v = venid[position];
                                 	//args.putString("venId", purl);
                                 	//fr.setArguments(args);
                            		 Bundle args = new Bundle();
            
                                 	//args.putString("venId", v);
                                 	//args.putInt("Previous", 1);
                                 	args.putString("jsonSt", jsonPlan);
                                 	args.putInt("position", pos);
                                 	
               
                                 	fr.setArguments(args);
                                	 
                                	  fm = getFragmentManager();
                                       ft = fm.beginTransaction();
                                       ft.replace(R.id.frame_container, fr);
                                       ft.commit();
                            	}
                            	if(pp == 2)
                            	{
                            		 fr = new Hotel();
                        			 //Bundle args = new Bundle();
                         		 	//String v = venid[position];
                                 	//args.putString("venId", purl);
                                 	//fr.setArguments(args);
                                	 
                                	  fm = getFragmentManager();
                                       ft = fm.beginTransaction();
                                       ft.replace(R.id.frame_container, fr);
                                       ft.commit();
                            	}
                            	else if(pp == 3)
                            	{
                            		fr = new Restaurant();
                       			 //Bundle args = new Bundle();
                        		 	//String v = venid[position];
                                	//args.putString("venId", purl);
                                	//fr.setArguments(args);
                               	 
                               	  fm = getFragmentManager();
                                      ft = fm.beginTransaction();
                                      ft.replace(R.id.frame_container, fr);
                                      ft.commit();
                            	}
                               // Toast.makeText(getActivity(), "Back Pressed", Toast.LENGTH_SHORT).show();
                                return true;
                            }
                        }
                        return false;
                    }

			
                });
        
        picm.setOnClickListener(new View.OnClickListener() 
        {
        	   //@Override
        	   public void onClick(View v) 
        	   {
        			 fr = new PlaceGallery();
        			 Bundle args = new Bundle();
         		 	//String v = venid[position];
                 	args.putString("venId", purl);
                 	args.putInt("Previous", pp);
                 	fr.setArguments(args);
                	 
                	  fm = getFragmentManager();
                       ft = fm.beginTransaction();
                       ft.replace(R.id.frame_container, fr);
                       ft.commit();
        	      Log.v(TAG, " click");         
        	   }        
        });
        
        mapIm.setOnClickListener(new View.OnClickListener() 
        {
        	   //@Override
        	   public void onClick(View v) 
        	   {
        			 fr = new TapMaps();
        			 Bundle args = new Bundle();
         		 	//String v = venid[position];
        			 
        			 args.putString("name", name);
                 	args.putString("latitude", latitude);
                 	args.putString("longitude", longitude);
                 	args.putInt("Previous", pp);
                 	args.putString("venId", purl);
                 	fr.setArguments(args);
                	 
                	  fm = getFragmentManager();
                       ft = fm.beginTransaction();
                       ft.replace(R.id.frame_container, fr);
                       ft.commit();
        	      Log.v(TAG, " click");         
        	   }        
        });
        
        
        //title = (TextView)rootView.findViewById(R.id.pi_place);
        //title.setText(name);
        
        
        // Image Picture
//        imgUrl = "https://irs2.4sqi.net/img/general/960x541/1829134_t1lUUsIXg4FdMxg8kNk7WFuqxCMC1j4jXiYOfjWjB1g.jpg";
//        ImageView viewIm = (ImageView)rootView.findViewById(R.id.pi_image);
//        iml.displayImage(imgUrl, viewIm, dmp);
        
        
        // Map Picture
        
//        mapUrl = "http://maps.google.com/maps/api/staticmap?center=8.201657156030517,98.29755306243896&zoom=15&size=200x200&markers=color:red|8.201657156030517,98.29755306243896&sensor=false";
//        mapUrl1 = "http://maps.google.com/maps/api/staticmap?center=" +
//        		latitude +
//        		"," +
//        		longitude +
//        		"&zoom=15&size=200x200&markers=color:red|" +
//        		latitude +
//        		"," +
//        		longitude +
//        		"&sensor=false";
        //ImageView mapIm = (ImageView)rootView.findViewById(R.id.map_image);
        //iml.clearMemoryCache();
        //iml.cancelDisplayTask(mapIm);
        //iml.displayImage(mapUrl1, mapIm, dmp);
        
//        
//        TextView fotex = (TextView)rootView.findViewById(R.id.for_lo);
//        fotex.setText(address);
        
        
        lsvMain.setOnItemClickListener(this);
                
//        btnSubmit.setOnClickListener(new OnClickListener() 
//        {
// 
//            @Override
//            public void onClick(View v) 
//            {
//            	
////            	 fr = new PlaceGallery();
////            	 
////            	   fm = getFragmentManager();
////                   ft = fm.beginTransaction();
////                   ft.replace(R.id.frame_container, fr);
////                   ft.commit();
//            	
//            	//makeJsonObjReq();
//            	Toast.makeText(getActivity(), name, Toast.LENGTH_LONG).show();
//            	 
////                Toast.makeText(getActivity(),
////                        "On Button Click : " + 
////                        "\n" + String.valueOf(province.getSelectedItem()) ,
////                        Toast.LENGTH_LONG).show();
//            }
// 
//        });
 

        // Button click Listener 
        //addListenerOnButton();

      
        return rootView; 
	}
	 //category list view

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
 
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) 
	{
		// TODO Auto-generated method stub
		
	}
	
	private void makeJsonObjReq() 
	{
		String uuu = "https://api.foursquare.com/v2/venues/";
		String ven = 		"4da5570f0cb6d75e29f5fb13" ;
		String uu3 = 		"?&client_id=VQFA1NFZFVHNCSQL1GTBVAOWBDQOHSQEHOW5YZKU1IS1JRFO&client_secret=KMIYI5FXHQFHCQYKRE35EKX125UEH4AQERSJRXMAZXDRFLDF&v=20130815";
		
		String fs = uuu + purl + uu3;
		showProgressDialog();
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
				fs, null,
				new Response.Listener<JSONObject>() 
				{

					@Override
					public void onResponse(JSONObject response) 
					{
						Log.d(TAG, response.toString());
						//msgResponse.setText(response.toString());						
						try {
	                        // Parsing json object response
	                        // response will be a json object
							
							JSONObject res = response.getJSONObject("response");
							JSONObject ven = res.getJSONObject("venue");
							name = ven.getString("name");
							title.setText(name);
							rating = ven.getString("rating");
							rate.setText(rating);
							JSONObject loc = ven.getJSONObject("location");
							
							
							
							latitude = loc.getString("lat");
							longitude = loc.getString("lng");
							
							 mapUrl1 = "http://maps.google.com/maps/api/staticmap?center=" +
						        		latitude +
						        		"," +
						        		longitude +
						        		"&zoom=15&size=200x200&markers=color:red|" +
						        		latitude +
						        		"," +
						        		longitude +
						        		"&sensor=false";
							iml.displayImage(mapUrl1, mapIm, dmp);
							 							
							JSONArray form = loc.getJSONArray("formattedAddress");
							//String add = "";
							address = "";
							for(int i=0;i<form.length();i++)
							{
								address += form.getString(i) + ", ";
							}
							fotex.setText(address);
							JSONObject tips = ven.getJSONObject("tips");
							
							JSONObject bp = ven.getJSONObject("bestPhoto");
							String prefix = bp.getString("prefix");
							String suffix = bp.getString("suffix");
							String width = bp.getString("width");
							String height = bp.getString("height");
						
							prefix = prefix.replace("\"","" );
							suffix = suffix.replace("\"", "");
							
							String picurl = prefix + width + "x" + height +suffix;
							
							iml.displayImage(picurl, picm, dmp);
							
							
							JSONArray group = tips.getJSONArray("groups");
							JSONObject co = (JSONObject) group.get(0);
							JSONArray items = co.getJSONArray("items");
							comment = new String[items.length()];
							for(int i=0;i<items.length();i++)
							{
								JSONObject com = (JSONObject)items.get(i);
								comment[i] = com.getString("text");
							}	
							
							CommentAdapter comdt = new CommentAdapter(getActivity(),comment);
							lsvMain.setAdapter(comdt);
							comdt.notifyDataSetChanged();
							
							jsonResponse = "";
							jsonResponse += " ************************* " + picurl + "   ";
							jsonResponse += name + " ++ ";
							jsonResponse += rating + " ++ ";
							jsonResponse += latitude + " ++ ";
							jsonResponse += longitude + " ++ ";
							jsonResponse += address + " ++ ";							
							for(int i=0;i<items.length();i++)
							{
								jsonResponse += comment[i] + " ++ ";
							}	 
	                        //Toast.makeText(getActivity(), jsonResponse, Toast.LENGTH_LONG).show();
	 
	                    } catch (JSONException e) 
	                    {
	                        e.printStackTrace();
	                        //Toast.makeText(getActivity(),
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

	public void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}

     
  
 
  }
