package com.example.triptap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.triptap.tripme.ImageAdapter;
import com.example.triptap.tripme.PlaceInformation;
import com.facebook.HttpMethod;

import com.facebook.Request;
import com.facebook.Request.Callback;
import com.facebook.Request.GraphUserCallback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class LoginHowto extends Fragment 
{
	
	private static final String TAG = "MainFragment";
	GraphUserCallback j;
	Response ff;
	private GraphUser fp;
	private UiLifecycleHelper uiHelper;
	private Button updateStatusBtn;
	private Button postImageBtn;
	OnClickListener jcx;
	private String token = "";
	BufferedReader ll;
	InputStreamReader kk;
	private RequestQueue mRequestQueue;
	private ProgressDialog pDialog;
	DefaultHttpClient httpClient; 
	InputStream is;
	String json;
	JSONObject jObj;
	JSONObject jo;
	String faceid = "1008622309166540";
	String uuid;
	
	public FragmentTransaction ft;
	public Fragment fr;
	public FragmentManager fm;
	//String url = "https://graph.facebook.com/" + id + "/albums?access_token=" + session.getAccessToken() + "&fields=id,name,description,updated_time";
	
	private Request yy;
	
	//private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");
	
    private Session.StatusCallback callback = new Session.StatusCallback() 
    {
        @Override
        public void call(final Session session, final SessionState state, final Exception exception) {
            onSessionStateChange(session, state, exception);
        }

    };
    
	@Override
	public View onCreateView(LayoutInflater inflater, 
			ViewGroup container, 
			Bundle savedInstanceState) 
	{
		View view = inflater.inflate(R.layout.face_login, container, false);
	    pDialog = new ProgressDialog(getActivity());
	 		pDialog.setMessage("Loading...");
	 		pDialog.setCancelable(false); 
		
		LoginButton authButton = (LoginButton) view.findViewById(R.id.btn_fblogin);
		authButton.setReadPermissions("user_friends");
		authButton.setReadPermissions("user_tagged_places");
		
		authButton.setFragment(this);
		//authButton.setReadPermissions(Arrays.asList("user_likes", "user_status"));
		
	    updateStatusBtn = (Button)view.findViewById(R.id.btn_fb_post_to_wall);
	    
        updateStatusBtn.setOnClickListener(new OnClickListener() 
        {

            @Override
            public void onClick(View v) 
            {
            	//postStatusMessage();
            }
        });
        
        postImageBtn = (Button) view.findViewById(R.id.btn_get_profile);
        postImageBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                postImage();
            }
        });
        
		return view;
	}
    
	@Override
    public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        uiHelper = new UiLifecycleHelper(getActivity(), callback);
        uiHelper.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() 
    {
        super.onResume();
        
        // For scenarios where the main activity is launched and user
		// session is not null, the session state change notification
		// may not be triggered. Trigger it if it's open/closed.
		Session session = Session.getActiveSession();
		if (session != null &&
				(session.isOpened() || session.isClosed()) ) 
		{
			onSessionStateChange(session, session.getState(), null);
		}
		
        uiHelper.onResume();
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
        
        Session.getActiveSession().onActivityResult(getActivity(), requestCode, resultCode, data);

        if (Session.getActiveSession().isOpened())
        {
            // Request user data and show the results
            Request.newMeRequest(Session.getActiveSession(), new GraphUserCallback()
            {
                @Override
                public void onCompleted(GraphUser user, Response response)
                {
                    if (null != user)
                    {
                    	
                    	uuid = user.getId();
                        // Display the parsed user info
                        Log.v(TAG, "Response : " + response);
                        Log.v(TAG, "UserID : " + user.getId());
                        Log.v(TAG, "User FirstName : " + user.getFirstName());
                        Log.v(TAG, "Token : " + Session.getActiveSession().getAccessToken());
                        
                        
                        String s = response.toString() + " ++++  " + user.getId() + " +++  " + user.getFirstName() ;
                        
                    	fr = new TagPlace();
            		 	Bundle args = new Bundle();
            		 	//String v = venid[position];
                    	args.putString("user_id", user.getId());
                    	args.putString("access_token", Session.getActiveSession().getAccessToken());
                    	fr.setArguments(args);
                    	 
                    	   //fm = getFragmentManager();
                    	  fm = getFragmentManager();
                    	  ft = fm.beginTransaction();
                    	 //fr = new PlaceInformation();
                    	  ft.replace(R.id.frame_container, fr);
                    	  ft.commit();                 	  
                        
//                        Toast.makeText(getActivity(), s , Toast.LENGTH_LONG).show();
                        tagPlace();
                        //String hhh = "";
                        
                        String rurl = "https://graph.facebook.com/" + user.getId() + "?fields=tagged_places.limit(100)" + "&" + "access_token=" + Session.getActiveSession().getAccessToken() ;
                        
                        
//                        Thread thread = new Thread(new Runnable(){
//                            @Override
//                            public void run() {
//                                try 
//                                
//                                {
//                                    //Your code goes here
//                                	 String rurl = "https://graph.facebook.com/" + uuid + "?fields=tagged_places.limit(100)" + "&" + "access_token=" + Session.getActiveSession().getAccessToken() ;
//                                 jo = getRequestFromUrl(rurl);
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        });
//
//                        thread.start(); 
                        
                        
                        //jo = getRequestFromUrl(rurl);
                        
                       // Toast.makeText(getActivity(), jo.toString(), Toast.LENGTH_LONG).show();
                        

                    }
                }
            }).executeAsync();
            
            
            
//            new Request(Session.getActiveSession(), "/1008622309166540/tagged_places", null, 
//            		HttpMethod.GET,
//            		new Request.Callback() 
//            {
//						
//						@Override
//						public void onCompleted(Response response) 
//						{
//							// TODO Auto-generated method stub
//							Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_LONG).show();
//							
//						}
//			}).executeAsync();
         
//            Request req = Request.newGraphPathRequest(Session.getActiveSession(), "/"+"1008622309166540"+"?fields=tagged_places.limit(100)",
//                    new Callback() {
//                        @Override
//                        public void onCompleted(Response response) 
//                        {
//                                    Log.w("FriendsListFragment.Facebook.onComplete", response.toString());
//                        }
//                    });
//            Request.executeBatchAsync(req);
            
            
//            new Request(
//            	    Session.getActiveSession(),
//            	    "/1008622309166540",
//            	    null,
//            	    HttpMethod.GET,
//            	    new Request.Callback() 
//            	    {
//            	        public void onCompleted(Response response) 
//            	        {
//            	            /* handle the result */
//            	        	Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_LONG).show();
//            	        }
//            	    }
//            	).executeAsync();
            
        }
        
    }
    
    public void tagPlace()
    {
    	//Session session = Session.getActiveSession();

    	Bundle params = new Bundle();
    	params.putString("fields", "tagged_places.limit(100)");
    	
    	
    	  Request rq =  new Request(Session.getActiveSession(), "me/" + "access_token=" + Session.getActiveSession().getAccessToken(), params, HttpMethod.GET,
                   new Callback() {
                       @Override
                       public void onCompleted(Response response) 
                    
                       {
                    	   //"1008622309166540"
                                   Log.w("FriendsListFragment.Facebook.onComplete", response.toString());
                       }
                   });
           rq.executeAsync();
           
            Request req = Request.newGraphPathRequest(Session.getActiveSession(), "/"+"1008622309166540"+"?fields=tagged_places.limit(100)" + "",
                 new Callback() {
                     @Override
                     public void onCompleted(Response response) 
                     {
                                 Log.w("FriendsListFragment.Facebook.onComplete", response.toString());
                     }
                 });
         Request.executeBatchAsync(req);
    }
    
    @Override
    public void onPause() 
    {
        super.onPause();
        uiHelper.onPause();
    }
    
    @Override
    public void onDestroy() 
    {
        super.onDestroy();
        uiHelper.onDestroy();
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) 
    {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }
    
    private void onSessionStateChange(Session session, SessionState state, Exception exception) 
    {
    	if (state.isOpened()) {
    		Log.i(TAG, "Logged in...");
        } else if (state.isClosed()) {
        	Log.i(TAG, "Logged out...");
        }
    }
    
//    public boolean checkPermissions() 
//    {
//        Session s = Session.getActiveSession();
//        if (s != null) {
//            return s.getPermissions().contains("publish_actions");
//        } else
//            return false;
//    }
    
//    public void requestPermissions() 
//    {
//        Session s = Session.getActiveSession();
//        if (s != null)
//            s.requestNewPublishPermissions(new Session.NewPermissionsRequest(
//                    this, PERMISSIONS));
//    }
    
    
    public void postStatusMessage() 
    {
      
            Request request = Request.newStatusUpdateRequest(
                    Session.getActiveSession(), "Test TripsTap post message from facebook SDK 3.0",
                    new Request.Callback() {
                        @Override
                        public void onCompleted(Response response) 
                        {
                            if (response.getError() == null)
                                Toast.makeText(getActivity(),
                                        "Status updated successfully",
                                        Toast.LENGTH_LONG).show();
                        }
                    });
            request.executeAsync();
     
    }
    
    public void postImage() 
    {
            Bitmap img = BitmapFactory.decodeResource(getResources(),
                    R.drawable.phuket1);
            Request uploadRequest = Request.newUploadPhotoRequest(
                    Session.getActiveSession(), img, new Request.Callback() {
                        @Override
                        public void onCompleted(Response response) {
                            Toast.makeText(getActivity(),
                                    "Photo uploaded successfully",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
            uploadRequest.executeAsync();
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
	
	 public JSONObject getRequestFromUrl (String url) 
	 {
	     try {
	         httpClient = new DefaultHttpClient();
	         HttpGet get = new HttpGet(url);

	         HttpResponse httpResponse = httpClient.execute(get);
	         HttpEntity responseEntity = httpResponse.getEntity();
	         is = responseEntity.getContent();
	         //DebugLog.log("Estoy aqui");
	    } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	    } catch (ClientProtocolException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    try {
	        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
	        StringBuilder sb = new StringBuilder();
	        String line = null;

	        while ((line = reader.readLine()) != null) {
	            sb.append(line + "\n");             
	        }

	        is.close();
	        json = sb.toString();
	        Log.e("JSON", json);
	    } catch (Exception e) {
	        Log.e("Buffer Error", "Error converting result " + e.toString());
	    }

	    try {
	        jObj = new JSONObject(json);
	    } catch (JSONException e) {
	        Log.e("JSON Parser", "Error parsing data " + e.toString());
	    }

	    return jObj;
	 }
    
//	private void makeJsonObjReq() 
//	{
//		String venreal = "https://api.foursquare.com/v2/venues/4da5570f0cb6d75e29f5fb13" +
//				"/photos?client_id=VQFA1NFZFVHNCSQL1GTBVAOWBDQOHSQEHOW5YZKU1IS1JRFO" +
//				"&client_secret=KMIYI5FXHQFHCQYKRE35EKX125UEH4AQERSJRXMAZXDRFLDF&&v" +
//				"=20130815&limit=150";
//		
//		
//		String venurl = "https://api.foursquare.com/v2/venues/" +
//				venid +
//				"/photos?client_id=" +
//				Const.CLIENT_ID +
//				"&client_secret=" +
//				Const.CLIENT_SECRET +
//				"&&v=20130815&limit=150";
//		
//		//String fs = uuu + purl + uu3;
//		showProgressDialog();
//		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
//				venurl, null,
//				new Listener<JSONObject>() 
//				{
//
//					@Override
//					public void onResponse(JSONObject response) 
//					{
//						//Log.d(TAG, response.toString());
//						//msgResponse.setText(response.toString());						
//						try {
//	                        // Parsing json object response
//	                        // response will be a json object
//							
//							JSONObject res = response.getJSONObject("response");
//							JSONObject phot = res.getJSONObject("photos");
//							
//							JSONArray items = phot.getJSONArray("items");
//							
//							picar = new String[items.length()];
//							
//							for(int i=0;i<items.length();i++)
//							{
//								JSONObject bp = items.getJSONObject(i);
//								String prefix = bp.getString("prefix");
//								String suffix = bp.getString("suffix");
//								String width = bp.getString("width");
//								String height = bp.getString("height");
//							
//								prefix = prefix.replace("\"","" );
//								suffix = suffix.replace("\"", "");
//								
//								String picurl = prefix + width + "x" + height +suffix;
//								picar[i] = picurl;
//							}
//							
//							 ImageAdapter idt = new ImageAdapter(getActivity(),picar);
//						     gridview.setAdapter(idt);
//						     idt.notifyDataSetChanged();
//	 
//	                    } catch (JSONException e) 
//	                    {
//	                        e.printStackTrace();
//	                        Toast.makeText(getActivity(),
//	                                "Error: " + e.getMessage(),
//	                                Toast.LENGTH_LONG).show();
//	                    }
//						
//						hideProgressDialog();
//					}
//				}, new ErrorListener() {
//
//					@Override
//					public void onErrorResponse(VolleyError error) 
//					{
//						VolleyLog.d(TAG, "Error: " + error.getMessage());
//						hideProgressDialog();
//					}
//				}) {
//
//			/**
//			 * Passing some request headers
//			 * */
//			@Override
//			public Map<String, String> getHeaders() throws AuthFailureError 
//			{
//				HashMap<String, String> headers = new HashMap<String, String>();
//				headers.put("Content-Type", "application/json");
//				return headers;
//			}
//
//			@Override
//			protected Map<String, String> getParams() 
//			{
//				Map<String, String> params = new HashMap<String, String>();
//				params.put("name", "Androidhive");
//				params.put("email", "abc@androidhive.info");
//				params.put("pass", "password123");
//
//				return params;
//			}
//
//		};
//
//		// Adding request to request queue
//		addToRequestQueue(jsonObjReq,tag_json_arry);	
//	}
//	
//
//	
//	public RequestQueue getRequestQueue() 
//	{
//		if (mRequestQueue == null) 
//		{
//			mRequestQueue = Volley.newRequestQueue(getActivity());
//		}
//
//		return mRequestQueue;
//	}
//
////	public <T> void addToRequestQueue(Request<T> req, String tag) {
////		// set the default tag if tag is empty
////		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
////		getRequestQueue().add(req);
////	}
////
////	public <T> void addToRequestQueue(Request<T> req) {
////		req.setTag(TAG);
////		getRequestQueue().add(req);
////	}
//
//	public void cancelPendingRequests(Object tag) {
//		if (mRequestQueue != null) 
//		{
//			mRequestQueue.cancelAll(tag);
//		}
//	}
    
}