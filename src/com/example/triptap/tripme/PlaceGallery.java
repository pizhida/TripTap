package com.example.triptap.tripme;

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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.triptap.Const;
import com.example.triptap.R;
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
import android.widget.Toast;

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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



@SuppressLint("NewApi")  
  public class PlaceGallery extends Fragment implements OnItemClickListener
  {
	private String TAG = PlaceGallery.class.getSimpleName();
	private Button btnSubmit;
	private List <ArrData> arrMain;
	public FragmentTransaction ft;
	public Fragment fr;
	public FragmentManager fm;
	private String venid;
	private RequestQueue mRequestQueue;
	String jsonResponse;
	private String tag_json_arry = "jarray_req";
	private ProgressDialog pDialog;
	private String [] picar;
	private GridView gridview;
	private int pp;
	private String jsonPlan;
	private int pos;
	public PlaceGallery()
	{}
	
	@Override 
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)     
	{ 
        View rootView = inflater .inflate(R.layout.place_gallery, container, false);
        btnSubmit = (Button)rootView.findViewById(R.id.img_back);
        btnSubmit.setText("Submit");
        
        pDialog = new ProgressDialog(getActivity());
 		pDialog.setMessage("Loading...");
 		pDialog.setCancelable(false); 
 		
 		 jsonPlan = getArguments().getString("jsonSt");
         pos = getArguments().getInt("position");
 		
 		picar = new String [5];
 		picar[0] = "https://irs2.4sqi.net/img/general/720x537/Q3X0FFSHIJYQ0DSCNJGMN5H5ZHEF31P3K0CN3TESUUUJIEWX.jpg";
    	picar[1] = "https://irs0.4sqi.net/img/general/720x540/4955827_fXSv79aA1MPoR2HtAphCrwfWbfNGv8ai6hHeU48DyQk.jpg";
    	picar[2] = "https://irs0.4sqi.net/img/general/720x960/18758956_rMAS8hYRwvIsc5i-Gh6qGNvCx_G1YrYfHfkHFynpsWU.jpg";
    	picar[3] = "https://irs0.4sqi.net/img/general/720x540/4955827_fXSv79aA1MPoR2HtAphCrwfWbfNGv8ai6hHeU48DyQk.jpg";
    	picar[4] = "https://irs0.4sqi.net/img/general/720x960/18758956_rMAS8hYRwvIsc5i-Gh6qGNvCx_G1YrYfHfkHFynpsWU.jpg";
 		
        venid = getArguments().getString("venId");
        pp = getArguments().getInt("Previous");
        
        gridview = (GridView) rootView.findViewById(R.id.gallery);
        ImageAdapter idt = new ImageAdapter(getActivity(),picar);
        gridview.setAdapter(idt);
        gridview.setOnItemClickListener(this);
        
        makeJsonObjReq();
     
            
        btnSubmit.setOnClickListener(new OnClickListener() 
        {
 
            @Override
            public void onClick(View v) 
            {
            	
            	fr = new PlaceInformation();
    		 	Bundle args = new Bundle();
    		 	//String v = venid[position];
            	args.putString("venId", venid);
            	args.putInt("Previous", pp);
            	
            	fr.setArguments(args);
            	 
            	   //fm = getFragmentManager();
            	  fm = getFragmentManager();
            	  ft = fm.beginTransaction();
            	 //fr = new PlaceInformation();
            	  ft.replace(R.id.frame_container, fr);
            	  ft.commit();
//                   ft.replace(R.id.frame_container, fr);
//                   ft.commit();
//            	 
//                Toast.makeText(getActivity(),
//                        "On Button Click : " + 
//                        "\n" + String.valueOf(province.getSelectedItem()) ,
//                        Toast.LENGTH_LONG).show();
            }
 
        });
 

        // Button click Listener 
        //addListenerOnButton();

      
        return rootView; 
	}
    
  
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
		fr = new SingleView();
	 	Bundle args = new Bundle();
	 	//String v = venid[position];
    	args.putStringArray("picUrl", picar);
    	args.putInt("pos",position);
    	
    	fr.setArguments(args);
    	 
    	   //fm = getFragmentManager();
    	  fm = getFragmentManager();
    	  ft = fm.beginTransaction();
    	 //fr = new PlaceInformation();
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
	
	
	private void makeJsonObjReq() 
	{
		String venreal = "https://api.foursquare.com/v2/venues/4da5570f0cb6d75e29f5fb13" +
				"/photos?client_id=VQFA1NFZFVHNCSQL1GTBVAOWBDQOHSQEHOW5YZKU1IS1JRFO" +
				"&client_secret=KMIYI5FXHQFHCQYKRE35EKX125UEH4AQERSJRXMAZXDRFLDF&&v" +
				"=20130815&limit=150";
		
		
		String venurl = "https://api.foursquare.com/v2/venues/" +
				venid +
				"/photos?client_id=" +
				Const.CLIENT_ID +
				"&client_secret=" +
				Const.CLIENT_SECRET +
				"&&v=20130815&limit=150";
		
		//String fs = uuu + purl + uu3;
		showProgressDialog();
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
				venurl, null,
				new Response.Listener<JSONObject>() 
				{

					@Override
					public void onResponse(JSONObject response) 
					{
						//Log.d(TAG, response.toString());
						//msgResponse.setText(response.toString());						
						try {
	                        // Parsing json object response
	                        // response will be a json object
							
							JSONObject res = response.getJSONObject("response");
							JSONObject phot = res.getJSONObject("photos");
							
							JSONArray items = phot.getJSONArray("items");
							
							picar = new String[items.length()];
							
							for(int i=0;i<items.length();i++)
							{
								JSONObject bp = items.getJSONObject(i);
								String prefix = bp.getString("prefix");
								String suffix = bp.getString("suffix");
								String width = bp.getString("width");
								String height = bp.getString("height");
							
								prefix = prefix.replace("\"","" );
								suffix = suffix.replace("\"", "");
								
								String picurl = prefix + width + "x" + height +suffix;
								picar[i] = picurl;
							}
							
							 ImageAdapter idt = new ImageAdapter(getActivity(),picar);
						     gridview.setAdapter(idt);
						     idt.notifyDataSetChanged();
	 
	                    } catch (JSONException e) 
	                    {
	                        e.printStackTrace();
	                        Toast.makeText(getActivity(),
	                                "Error: " + e.getMessage(),
	                                Toast.LENGTH_LONG).show();
	                    }
						
						hideProgressDialog();
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) 
					{
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
		if (mRequestQueue != null) 
		{
			mRequestQueue.cancelAll(tag);
		}
	}

     
  
 
  }