package com.example.triptap.tripme;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.triptap.AppController;
import com.example.triptap.Const;
import com.example.triptap.LruBitmapCache;
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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



@SuppressLint("NewApi")  
  public class TripMe extends Fragment implements OnItemClickListener
  {
	private String TAG =  TripMe.class.getSimpleName();
	private Spinner province;
	private Button btnSubmit;
	
	private List <ArrData> arrMain;
	
	public int len;
	
	public FragmentTransaction ft;
	public Fragment fr;
	public FragmentManager fm;
	
	private ProgressDialog pDialog;
	
	private AppController app;
	
	private String jsonResponse;
	
	private String tag_json_arry = "jarray_req";
	
	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;
	
	private String selPro;
	
	private String [] selCat;
	
	//private String chPro;
	
	private ListView lsvMain;
	
	String [] arrCat;
	boolean [] rcheck;
	
	private ArrayList<String> sendCat;
	
	String ex = Const.RULE;
	
	public TripMe()
	{}
	
	
	private String[] arrTitle = 
		{
			"Facebook", "Skype", "Twitter", "YouTube"
	};
	
	
	
	
	

	@Override 
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)     
	{ 
        View rootView = inflater .inflate(R.layout.trip_me, container, false);
        province = (Spinner)rootView.findViewById(R.id.province);
        btnSubmit = (Button)rootView.findViewById(R.id.submit);
        app = new AppController();
        pDialog = new ProgressDialog(getActivity());
		pDialog.setMessage("Loading...");
		pDialog.setCancelable(false);
        btnSubmit.setText("Submit");
        List<String> list = new ArrayList<String>();
        arrCat = new String[2];
        arrCat[0] = "ffff";
        arrCat[1] = "nnnn";
        int size = Province.provinceStrings.length;
        for(int i=0;i<size;i++)
        {
        	list.add(Province.provinceStrings[i]);
        }
        
        // ListView
        arrMain = new ArrayList <ArrData>();
        ArrData data;
        // init boolean data
        for(int i=0; i<arrTitle.length; i++)
        {
        	data = new ArrData();
        	data.setCheck(false);
        	data.setTitle(arrTitle[i]);
        	arrMain.add(data);
        }
        len = arrMain.size();
        
        lsvMain = (ListView)rootView.findViewById(R.id.catlist);
        CustomAdapter cdt = new CustomAdapter(getActivity(), arrMain);
        lsvMain.setAdapter(cdt);
        lsvMain.setOnItemClickListener(this);
        
        
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
        (this.getActivity(), android.R.layout.simple_spinner_item,list);
         
        dataAdapter.setDropDownViewResource
        (android.R.layout.simple_spinner_dropdown_item);
         
        province.setAdapter(dataAdapter);

        // Spinner item selection Listener  
        addListenerOnSpinnerItemSelection();
        
        
        btnSubmit.setOnClickListener(new OnClickListener() 
        {
 
            @Override
            public void onClick(View v) 
            {
            	String msg = "";
            	sendCat = new ArrayList <String>();
            	for(int i=0;i<len;i++)
            	{
            		if(arrMain.get(i).isCheck() == true)
            		{
            			
            			sendCat.add(arrMain.get(i).getTitle().replace(" ", "%20"));
            			//msg += arrMain.get(i).getTitle();
            		}
            	}
            	for(int i=0;i<sendCat.size();i++)
            		msg += sendCat.get(i) + " ";
            	msg += selPro;
            	//Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
            	
            	 fr = new PlanSelected();
            	 Bundle args = new Bundle();
            	 args.putString("Extra",ex);
            	 args.putString("SelectPro", selPro);
            	 args.putStringArrayList("SelectCat", sendCat);
            	 // send extra between fragment
            	 fr.setArguments(args);
            	 fm = getFragmentManager();
                 ft = fm.beginTransaction();
                 ft.replace(R.id.frame_container, fr);
                 ft.commit();
                   
                   
                   //MusicAlbumList fragment = new MusicAlbumList();
                   //Bundle args = new Bundle();
                   //args.putString("StringName","Value here");
                   //fragment.setArguments(args);
            	
            	
            	//makeJsonArryReq();
            	
            	
//            	 String[] planets = new String[] { "Mercury m", "Venus m", "Earth m", "Mars m",  
//                         "Jupiter m", "Saturn m", "Uranus m", "Neptune m"};    
//            	   arrMain = new ArrayList <ArrData>();
//                   ArrData data;
//                   for(int i=0; i<planets.length; i++)
//                   {
//                   	data = new ArrData();
//                   	data.setTitle(planets[i]);
//                   	arrMain.add(data);
//                   }
//                   
//                   //ListView lsvMain = (ListView)v.findViewById(R.id.catlist);
//                   CustomAdapter cdt = new CustomAdapter(getActivity(), arrMain);
//                   lsvMain.setAdapter(cdt);
//                //listView.setAdapter(adapter);
//                 cdt.notifyDataSetChanged();
            	
            	
            	 //Toast.makeText(getActivity(), selPro , Toast.LENGTH_LONG).show();
            	
            	 
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
	
	/**
	 * Making json array request
	 * */
	private void makeJsonArryReq() 
	{
		
		String pro1  = "https://api.mongolab.com/api/1/databases/triptap_location_category/collections/category" +
				"?apiKey=pssG0fVnXU2G1hV3eI9_SuidpTGqSi4N" +
				"&q=%7B++%22state_init%22+%3A+%22";
		
		String pro2 = "%22+++%7D";
		
		//String pro3 = "Chiang%20Mai";
		
		String proo = pro1 + selPro + pro2;
		showProgressDialog();
		JsonArrayRequest req = new JsonArrayRequest(proo, 
				new Response.Listener<JSONArray>() 
				{
					@Override
					public void onResponse(JSONArray response) 
					{
						Log.d(TAG, response.toString());
						//msgResponse.setText(response.toString());
						try {
	                        jsonResponse = "";
	                        for (int i = 0; i < response.length(); i++) 
	                        {
	                            
	                            JSONObject pro = (JSONObject)response.get(i);
	                            JSONArray cc = pro.getJSONArray("cats");
	                            arrCat = new String[cc.length()];
	                            // init checkbox
	                            rcheck = new boolean[cc.length()];
	                            
	                            
	                            for(int j=0; j<cc.length(); j++)
	                            {
	                            	JSONObject c = cc.getJSONObject(j);
	                            	String cat = c.getString("catName");
	                            	arrCat[j] = cat;
	                            	rcheck[i] = false;
	                            	jsonResponse += cat + ", ";	              
	                            }
	 
	                        }                       
	                        Toast.makeText(getActivity(), jsonResponse, Toast.LENGTH_LONG).show();
	                        setListChange();
	 
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
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d(TAG, "Error: " + error.getMessage());
						hideProgressDialog();
					}
				});
		
		
		//appCtx = (AppController) getActivity().getApplication();
		// Adding request to request queue
		
		//AppController.getInstance().addToRequestQueue(req,
		//		tag_json_arry);
		
		addToRequestQueue(req,tag_json_arry);
		
		
		
		

		// Cancelling request
		// ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_arry);
	}

	
	public RequestQueue getRequestQueue() 
	{
		if (mRequestQueue == null) 
		{
			mRequestQueue = Volley.newRequestQueue(getActivity());
		}

		return mRequestQueue;
	}

	public ImageLoader getImageLoader() {
		getRequestQueue();
		if (mImageLoader == null) {
			mImageLoader = new ImageLoader(this.mRequestQueue,
					new LruBitmapCache());
		}
		return this.mImageLoader;
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
	
	

	  // Add spinner data
    
    public void addListenerOnSpinnerItemSelection()
    {
          province.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) 
			{
				// TODO Auto-generated method stub
				String state = parent.getItemAtPosition(position).toString();
				selPro = Province2.provinceStrings[position];
				 Toast.makeText(parent.getContext(), 
			                "On Item Select : \n" + state,
			                Toast.LENGTH_LONG).show();
				 
				 makeJsonArryReq();	
				 
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
        	  
          }
          );
    }
    
    public void setListChange()
    {
	   	 String[] planets = new String[] { "Mercury m", "Venus m", "Earth m", "Mars m",  
	             "Jupiter m", "Saturn m", "Uranus m", "Neptune m"};    
		   arrMain = new ArrayList <ArrData>();
	       ArrData data;
	       for(int i=0; i<arrCat.length; i++)
	       {
	       	data = new ArrData();
	       	data.setTitle(arrCat[i]);
	       	arrMain.add(data);
	       }
	       len = arrMain.size();
	       CustomAdapter cdt = new CustomAdapter(getActivity(), arrMain);
	       lsvMain.setAdapter(cdt);
	     cdt.notifyDataSetChanged();
    }

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}

     
  
 
  }
