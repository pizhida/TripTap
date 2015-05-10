package com.example.triptap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;




import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.triptap.tripme.ArrData;
import com.example.triptap.tripme.ImageAdapter;
import com.example.triptap.tripme.PlaceGallery;
import com.example.triptap.tripme.PlaceInformation;

import android.annotation.SuppressLint;
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
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("NewApi")  
public class TagPlace extends Fragment implements OnItemClickListener
{
	private String TAG = TagPlace.class.getSimpleName();
	private Button btnSubmit;
	private Button btnJson;
	public FragmentTransaction ft;
	public Fragment fr;
	public FragmentManager fm;
	private RequestQueue mRequestQueue;
	String jsonResponse;
	private String tag_json_arry = "jarray_req";
	private ProgressDialog pDialog;
	private String acto;
	private String useid;
	private String urltag;
	private String urllo;
	private String [] plid;
	
	private String hh;
	private String [] category;
	private JSONArray [] catlist;
	private JSONObject [] location;
	
	private String userid;
	private String userinfo;
	
	private int count;
	
	private JSONArray info;
	
	private JSONArray useinfo;
	
	private JSONObject [] jo;
	
	private JSONObject fino;
	
	//private JSONArray info;
	String jc = "ggg";
	
	private int posi;
	//private String jsonResponse;
	private String jres;
	private TextView tv;
	
	private String posturl;
	
	public TagPlace()
	{}
	
	@Override 
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)     
	{ 
      View rootView = inflater .inflate(R.layout.tag_place, container, false);
      btnSubmit = (Button)rootView.findViewById(R.id.btn_tag);
      btnJson = (Button)rootView.findViewById(R.id.btn_json);
      tv = (TextView)rootView.findViewById(R.id.text_tag);
      btnSubmit.setText("Submit");
      
      userid = "";
      useinfo = new JSONArray();
      
      pDialog = new ProgressDialog(getActivity());
		pDialog.setMessage("Loading...");
		pDialog.setCancelable(false); 
		count = 0;
		info = new JSONArray();
		fino = new JSONObject();
		posturl = "https://api.mongolab.com/api/1/databases/triptap_user_data/collections/user_data?apiKey=pssG0fVnXU2G1hV3eI9_SuidpTGqSi4N";
		
		acto = getArguments().getString("access_token");
		useid = getArguments().getString("user_id");
      
		urltag = "https://graph.facebook.com/" + useid + "?access_token=" + acto + "&fields=tagged_places.limit(100)";
		urllo =   "https://graph.facebook.com/";
      
      //makeJsonObjReq();
   
          
      btnSubmit.setOnClickListener(new OnClickListener() 
      {

          @Override
          public void onClick(View v) 
          {
          	
//          	fr = new PlaceInformation();
//  		 	Bundle args = new Bundle();
//  		 	//String v = venid[position];
//          	args.putString("venId", venid);
//          	fr.setArguments(args);
//          	 
//          	   //fm = getFragmentManager();
//          	  fm = getFragmentManager();
//          	  ft = fm.beginTransaction();
//          	 //fr = new PlaceInformation();
//          	  ft.replace(R.id.frame_container, fr);
//          	  ft.commit();
        	  
        	   
        	  
        	  Toast.makeText(getActivity(), acto + "     " + useid + "     " + urltag, Toast.LENGTH_LONG).show();
        	  
        	  
        	  
        	  
        	  
        	  
        	  
        	  
        	  
        	  
        	  
//                 ft.replace(R.id.frame_container, fr);
//                 ft.commit();
//          	 
//              Toast.makeText(getActivity(),
//                      "On Button Click : " + 
//                      "\n" + String.valueOf(province.getSelectedItem()) ,
//                      Toast.LENGTH_LONG).show();
          }

      });
      
      btnJson.setOnClickListener(new OnClickListener() 
      {

          @Override
          public void onClick(View v) 
          {    
        	  makeJsonObjReq();
        
				
        	  
        	  
        	  //makeJsonObjReq1("232117480178266",0);
        	  //jres += " kkk " + jc;
        	  //tv.setText(jres);
        	  //Toast.makeText(getActivity(), acto + "     " + useid + "     " + urltag, Toast.LENGTH_LONG).show(); 
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
		
		showProgressDialog();
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
			 urltag, null,
				new Response.Listener<JSONObject>() 
				{

					@Override
					public void onResponse(JSONObject response) 
					{
						//Log.d(TAG, response.toString());
						//msgResponse.setText(response.toString());	
						
						String test = "";
						JSONObject dat;
						
						
						try {
	                        // Parsing json object response
	                        // response will be a json object
							
							jres = "";
							JSONObject tagplaces = response.getJSONObject("tagged_places");
							JSONArray data = tagplaces.getJSONArray("data");
							//JSONObject place = data.getJSONObject("place");
							plid = new String[data.length()];
							category = new String[data.length()];
							catlist = new JSONArray[data.length()];
							location = new JSONObject[data.length()];
							jo = new JSONObject[data.length()];
							
							//
							//dat = data.getJSONObject(0);
							//JSONObject places = dat.getJSONObject("place");
							//plid[0] = places.getString("id");
							
							//test = dat.getString("id");
							makeJsonObjReq1(plid[0],0);
							//makeJsonObjReq1(dat.getString("id"),0);
							
							//makeJsonObjReq1("232117480178266",0);
							
							
							
							JSONObject places;
							count = data.length();
//							
							for(int i=0;i<data.length();i++)
							{
								dat = data.getJSONObject(i);
								places = dat.getJSONObject("place");
								plid[i] = places.getString("id");
								makeJsonObjReq1(plid[i],i);	
								jres += plid[i] + "    ";
								jres += category[i] + "    ";
							}
							
						fino.put("userID", useid);
				        	  fino.put("info", info);
				        	  
				        	 // tv.setText(fino.toString());
							//tv.setText(jres);
				        	  
							
							
							
	 
	                    } catch (JSONException e) 
	                    {
	                        e.printStackTrace();
	                        Toast.makeText(getActivity(),
	                                "Error: " + e.getMessage(),
	                                Toast.LENGTH_LONG).show();
	                    }
						
						
						
						
						
						//makeJsonObjReq1(test,0);
						
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
	
	private void makeJsonObjReq1(String id, int pos) 
	{
		
		String realu = urllo + id;
		posi = pos;
		
		
		showProgressDialog();
		//Toast.makeText(getActivity(), realu, Toast.LENGTH_LONG).show();
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
			 realu, null,
				new Response.Listener<JSONObject>() 
				{

					@Override
					public void onResponse(JSONObject response) 
					{
						//Log.d("req1", response.toString());
						String jcat = "";
						
						JSONObject joh = new JSONObject();
						JSONArray jah = new JSONArray();
						//jres = "";
						//msgResponse.setText(response.toString());						
						try {
	                        // Parsing json object response
	                        // response will be a json object
							
							Toast.makeText(getActivity(), "Hello ", Toast.LENGTH_LONG);
							hh = response.toString();
							//jres = "";
							//jres += "test";
							//jres += hh;
							
							//tv.setText(hh);
							
							
							jcat = response.getString("category");
							//tv.setText(jcat);
							//jc = jcat;
							
							//jres += jcat;
							//tv.setText(jcat);
							category[posi] = jcat;
							JSONArray catl = response.getJSONArray("category_list");
							//tv.setText(catl.toString());
							catlist[posi] = catl;
							JSONObject loc = response.getJSONObject("location");
							//tv.setText(loc.toString());
							location[posi] = loc;
//							
						
							joh.put("category_list", catl);
							joh.put("category", jcat);
							joh.put("location", loc);
							
							//tv.setText(joh.toString());
//							
							info.put(joh);
							//tv.setText(info.toString());
//							fino.put("userID", useid);
//				        	fino.put("info", info);
							if(count == info.length())
							{
								fino.put("userID", useid);
								fino.put("info", info);
								//makeJsonObjReq2(useid, info);
								 makeJsonReq4(useid,info);
								//tv.setText(fino.toString());
							}
							
							
							
							 
				
	                    } catch (JSONException e) 
	                    {
	                        e.printStackTrace();
	                        Toast.makeText(getActivity(),
	                                "Error: " + e.getMessage(),
	                                Toast.LENGTH_LONG).show();
	                    }
						
						//jc = jcat;
						
						hideProgressDialog();
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) 
					{
						VolleyLog.d(TAG, "Error: " + error.getMessage());
						hideProgressDialog();
					}
				}) 
		{

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
	
	
//	private void makeJsonObjReq2(String useid, JSONArray info) 
//	{
//		userid = useid;
//		userinfo = info.toString();
//		
//		tv.setText(userid +  "    "  +userinfo);
//		
//		//String name = editName.getText().toString();
//		//String price = editPrice.getText().toString();
//		//String ingredient = editIngredient.getText().toString();
//		//String method = editMethod.getText().toString();
//		mRequestQueue = Volley.newRequestQueue(getActivity());
//		String url = "https://api.mongolab.com/api/1/databases/triptap_user_data/collections/user_data?apiKey=pssG0fVnXU2G1hV3eI9_SuidpTGqSi4N";
//		
//		StringRequest myReq = new StringRequest(Method.POST,
//				url,
//				new Listener<String>() {
//
//					@Override
//					public void onResponse(String response) 
//					{
//						makeJsonObjReq3();
//					}
//			
//				},
//				new Response.ErrorListener() {
//
//					@Override
//					public void onErrorResponse(VolleyError error) {
//						// TODO Auto-generated method stub
//						
//					}
//					
//				}) {
//
//			protected Map getParams() throws com.android.volley.AuthFailureError 
//			{
//				Map params = new HashMap<String, String>();
//				params.put("userID", userid);
//				params.put("info", userinfo);
////				params.put("name", name);
////				params.put("price", price);
////				params.put("method", method);
////				params.put("ingredient", ingredient);
////				params.put("userID", "uesr id");
////				params.put("image", imageView.getDrawable());
//				
//				return params;
//			};
//		};
//
//		// Adding request to request queue
//		addToRequestQueue(myReq,tag_json_arry);
//		Log.d(TAG, " FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF ");
//	}
	
	
	private void makeJsonObjReq3() 
	{
		
		String urll = "http://128.199.130.63:3000/triptap?userId=" + useid;
		showProgressDialog();
		Log.d("req3", "DOKKKKKK");
		
		//Toast.makeText(getActivity(), realu, Toast.LENGTH_LONG).show();
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
			 urll, null,
				new Response.Listener<JSONObject>() 
				{

					@Override
					public void onResponse(JSONObject response) 
					{
						Log.d("req3", response.toString());
//						try
//						{
//							JSONObject maxcat = response.getJSONObject("maxCat");
//							//tv.setText(response.toString());
//						
//							
//							
//							
//							 
//				
//	                    } catch (JSONException e) 
//	                    {
//	                        e.printStackTrace();
//	                        Toast.makeText(getActivity(),
//	                                "Error: " + e.getMessage(),
//	                                Toast.LENGTH_LONG).show();
//	                    }
//						
						//jc = jcat;
						
						hideProgressDialog();
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) 
					{
						Log.e("req3", "error jkhiugiug  " + error);
						//VolleyLog.d(TAG, "Error: " + error.getMessage());
						hideProgressDialog();
					}
				}) 
		;
		jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(20000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		// Adding request to request queue
		addToRequestQueue(jsonObjReq,tag_json_arry);	
	}
	
	private void makeJsonReq4(String useid, JSONArray info) throws JSONException
	{
		JSONObject job = new JSONObject();
		userid = useid;
		useinfo = info;
		
		job.put("userID", userid);
		job.put("info", useinfo);
		
		
		JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST,posturl,job,
			    new Response.Listener<JSONObject>() {
			        @Override
			        public void onResponse(JSONObject response) 
			        {
			        	Log.d("req4", response.toString());
			             //System.out.println(response);
			        	makeJsonObjReq3();
			             hideProgressDialog();
			        }
			    },
			    new Response.ErrorListener() 
			    {
			        @Override
			        public void onErrorResponse(VolleyError error) 
			        {
			        	Log.d("req4", "EEEERRRRPOORRT EDOK");
			        	
			             hideProgressDialog();
			        }
			    });
		
		jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		addToRequestQueue(jsObjRequest,tag_json_arry);
	}
//	
	
	

   


}