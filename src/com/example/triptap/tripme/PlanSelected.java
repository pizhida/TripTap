package com.example.triptap.tripme;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.triptap.AppController;
import com.example.triptap.Const;
import com.example.triptap.LruBitmapCache;
import com.example.triptap.R;
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
  public class PlanSelected extends Fragment implements OnItemClickListener
  {
	private Button btnSubmit;
	private List <PlanData> arrMain;
	private List <PlanData> arrRule;
	private String TAG =  PlanSelected.class.getSimpleName();
	private String jsonResponse;
	private String tag_json_arry = "jarray_req";
	private RequestQueue mRequestQueue;
	//private ImageLoader mImageLoader;
	public FragmentTransaction ft;
	public Fragment fr;
	public FragmentManager fm;
	String [] arrCat;
	private ListView lsvMain;
	//private String msgResponse;
	
	private String [] arrUrl1;
	private String [] arrUrl2;
	private String [] arrUrl3;
	
	private String [] arrTt;
	private String [] arrRating;
	
	private ImageLoader iml;
	
	private DisplayImageOptions dmp;
	
	private ProgressDialog pDialog;
	
	private int num;
	private String [] ruleid;
	
	private JSONArray resp;
	String ruler;
	
	String rr;
	
	private String roro;
	private String province;
	private ArrayList<String> allCat;
	
	private int poss;
	
	
	public PlanSelected()
	{}
	
	
	private int [] arrImage1 = 
		{
			R.drawable.phuket1,
			R.drawable.phuket1,
			R.drawable.phuket1
		};
	
	private int [] arrImage2 = 
		{
			R.drawable.phuket2,
			R.drawable.phuket2,
			R.drawable.phuket2
		};
	private int [] arrImage3 = 
		{
			R.drawable.phuket3,
			R.drawable.phuket3,
			R.drawable.phuket3
		};
	
	
	private String[] arrTitle = 
		{
			"Phuket 3", "Phuket 3", "Phuket 3"
	};
	
	
	
	private String [] arrSub = 
		{
			"rating 9", "rating 9", "rating 9"
	};
	
	

	@Override 
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)     
	{ 
        View rootView = inflater .inflate(R.layout.plan_selection, container, false);
       // province = (Spinner)rootView.findViewById(R.id.province);
        btnSubmit = (Button)rootView.findViewById(R.id.planaj_button);
        TextView tv = (TextView)rootView.findViewById(R.id.province2);
        
        // Receive Extra
        rr = getArguments().getString("Extra");
        province = getArguments().getString("SelectPro");
        allCat = new ArrayList <String>();
        allCat = getArguments().getStringArrayList("SelectCat");
        
        tv.setText(province.replace("%20", "") + " Plans");
        
        // Image OS
        iml = ImageLoader.getInstance();
		dmp = new DisplayImageOptions.Builder().cacheInMemory()
				.cacheOnDisc().resetViewBeforeLoading()
				.showImageForEmptyUri(R.drawable.phuket1).build();	
		
		
        pDialog = new ProgressDialog(getActivity());
     		pDialog.setMessage("Loading...");
     		pDialog.setCancelable(false);
        
        arrMain = new ArrayList <PlanData>();
        PlanData data;
        for(int i=0; i<arrTitle.length; i++)
        {
        	data = new PlanData();
        	data.setUrlp1("https://irs2.4sqi.net/img/general/720x537/Q3X0FFSHIJYQ0DSCNJGMN5H5ZHEF31P3K0CN3TESUUUJIEWX.jpg");
        	data.setUrlp2("https://irs0.4sqi.net/img/general/720x540/4955827_fXSv79aA1MPoR2HtAphCrwfWbfNGv8ai6hHeU48DyQk.jpg");
        	data.setUrlp3("https://irs0.4sqi.net/img/general/720x960/18758956_rMAS8hYRwvIsc5i-Gh6qGNvCx_G1YrYfHfkHFynpsWU.jpg");
        	data.setTitle(arrTitle[i]);
        	data.setRate(arrSub[i]);
        	arrMain.add(data);
        }
        
        lsvMain = (ListView)rootView.findViewById(R.id.planlist);
        //adtImageWithMultipleLine adt = new adtImageWithMultipleLine();
        PlanAdapter pdt = new PlanAdapter(getActivity(), arrMain);
        lsvMain.setAdapter(pdt);
        //makeJsonArryReq1();
        lsvMain.setOnItemClickListener(this);
        // make request
        //makeJsonArryReq();
        makeJsonArryReq1();
        
       
        
                
       
        btnSubmit.setOnClickListener(new OnClickListener() 
        {
 
            @Override
            public void onClick(View v) 
            {
 
//            	 fr = new PlanAdjustment();
//            	 
//          	   fm = getFragmentManager();
//                 ft = fm.beginTransaction();
//                 ft.replace(R.id.frame_container, fr);
//                 ft.commit();
            	//Toast.makeText(getActivity(), roro, Toast.LENGTH_LONG).show();
            	
            	
            	//makeJsonArryReq();
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
	  // Add spinner data
    
//	private void makeJsonArryReq() 
//	{
//		
//		showProgressDialog();
//		JsonArrayRequest req = new JsonArrayRequest(Const.RULE, 
//				new Response.Listener<JSONArray>() 
//				{
//					@Override
//					public void onResponse(JSONArray response) 
//					{
//						Log.d(TAG, response.toString());					
//						try {
//	                        jsonResponse = "";
//	                        num = response.length();
//	                        ruleid = new String [num];
//	                        jsonResponse += "Number of rules = " + response.length() + " Sample URl   ";	                        
//	                        JSONObject prw = (JSONObject)response.get(0);
//	                        JSONArray premisesa = prw.getJSONArray("premises");
//	                        JSONObject preobee = premisesa.getJSONObject(0);
//	                        String urll = preobee.getString("image");	                        
//	                        String [] urlth = urll.split("oooo");
//	                        String [] url1 = urlth[1].split("/-/");	                        
//	                        String [] url2 = url1[1].split(".jpg-");	                        	                        	                        	                        	                      	                        
//	                        String urlr = url1[0] + "/" + url2[1] + "/" + url2[0] + ".jpg";	                        	                       	                        
//	                        jsonResponse += urlr;
//	                        
//	                        for (int i = 0; i < response.length(); i++) 
//	                        {
//	                        	double ra = 0;
////	                        	int count = 0;
//	                        	
//	                        	
//	                        	JSONObject pro = (JSONObject)response.get(i);
//	                        	
//	                        	
//	                            JSONArray premises = pro.getJSONArray("premises");
//	                            JSONArray conclu = pro.getJSONArray("conclusion");
//	                            for(int j=0; j<premises.length(); j++)
//	                            {
//	                            	JSONObject preob = premises.getJSONObject(j);
//	                            	String cat = preob.getString("venueName");
//	                            	String rat = preob.getString("rate");
//	                            	
//	                            	String urlp = preob.getString("image");
//	                            	
//	                            	String [] urlpsplit = urlp.split("oooo");
//	                        
//	                    
//	     	                        String [] urlp0 = urlpsplit[0].split("/-/");
//	     	                        String [] urlp00 = urlp0[1].split(".jpg-");
//	     	                        
//	     	                        // image 1 url
//	     	                        String urlpp0 = urlp0[0] + "/" + urlp00[1] + "/" + urlp00[0] + ".jpg";
//	     	                        
//	     	                         String [] urlp1 = urlpsplit[1].split("/-/");
//	     	                        String [] urlp11 = urlp1[1].split(".jpg-");
//	     	                         // image 2 url
//	     	                        String urlpp1 = urlp1[0] + "/" + urlp11[1] + "/" + urlp11[0] + ".jpg";
//	     	                        
//	     	                       String [] urlp2 = urlpsplit[2].split("/-/");
//	     	                      String [] urlp22 = urlp2[1].split(".jpg-");
//	     	                       
//	     	                       // image 3 url
//	     	                        String urlpp2 = urlp2[0] + "/" + urlp22[1] + "/" + urlp22[0] + ".jpg";
//	     	                        
//	                            	
//	                            	ra+= Double.parseDouble(rat);
//	                            	jsonResponse += cat + "  " + urlpp0 + " ++ " + urlpp1 + " ++ " + urlpp2 + " ++ ";
//	                            	
//	                            }
//	                            jsonResponse += "connnn ";
//	                        	
//	                            for(int j=0; j<conclu.length(); j++)
//	                            {
//	                            	JSONObject precon = conclu.getJSONObject(j);
//	                            	String cat = precon.getString("vunueName");
//	                            	String rat = precon.getString("rate");
//	                            	
//	                            	String urlc = precon.getString("image");	                            	
//	                            	String [] urlcsplit = urlc.split("oooo");
//	     	                        String [] urlc0 = urlcsplit[0].split("/-/");
//	     	                        String [] urlc00 = urlc0[1].split(".jpg-");
//	     	                        
//	     	                        // image 1 url
//	     	                        String urlcc0 = urlc0[0] + "/" + urlc00[1] + "/" + urlc00[0] + ".jpg";
//	     	                        
//	     	                         String [] urlc1 = urlcsplit[1].split("/-/");
//	     	                        String [] urlc11 = urlc1[1].split(".jpg-");
//	     	                         // image 2 url
//	     	                        String urlcc1 = urlc1[0] + "/" + urlc11[1] + "/" + urlc11[0] + ".jpg";
//	     	                        
//	     	                       String [] urlc2 = urlcsplit[2].split("/-/");
//	     	                      String [] urlc22 = urlc2[1].split(".jpg-");
//	     	                       
//	     	                       // image 3 url
//	     	                        String urlcc2 = urlc2[0] + "/" + urlc22[1] + "/" + urlc22[0] + ".jpg";
//	     	                        
//	     	                        ra+= Double.parseDouble(rat);
//	     	                        jsonResponse += cat + "  " + urlcc0 + " ++ " + urlcc1 + " ++ " + urlcc2 + " ++ ";	                            	
//	                            }
//	                            DecimalFormat df = new DecimalFormat("#.#");
//	                            jsonResponse += "end of rule and rate :";
//	                            jsonResponse += "" + df.format(ra/(conclu.length()+premises.length())) + " ";
//	 
//	                        }                       
//	                        Toast.makeText(getActivity(), jsonResponse, Toast.LENGTH_LONG).show();
//	 
//	                    } catch (JSONException e) 
//	                    {
//	                        e.printStackTrace();
//	                        Toast.makeText(getActivity(),
//	                                "Error: " + e.getMessage(),
//	                                Toast.LENGTH_LONG).show();
//	                    }
//						
//						
//						
//						hideProgressDialog();
//					}
//				}, new Response.ErrorListener() {
//					@Override
//					public void onErrorResponse(VolleyError error) 
//					{
//						VolleyLog.d(TAG, "Error: " + error.getMessage());
//						hideProgressDialog();
//					}
//				});
//
//		// Adding request to request queue
//		addToRequestQueue(req,tag_json_arry);
//	}
	
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
	

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) 
	{
		// TODO Auto-generated method stub
		 	fr = new PlanAdjustment();
		 	Bundle args = new Bundle();
        	args.putString("ruleId", ruleid[position]);
        	args.putString("jsonUrl", ruler);
        	String ress = resp.toString();
        	args.putString("jsonSt", ress);
        	args.putInt("position", position);
        	 // send extra between fragment
        	fr.setArguments(args);
        	fm = getFragmentManager();
        	ft = fm.beginTransaction();
        	ft.replace(R.id.frame_container, fr);
        	ft.commit();
        	
        	//Toast.makeText(getActivity(), ruleid[position] + "  " + ruler + " " + position , Toast.LENGTH_LONG).show();
		
	}
	
	
	private void makeJsonArryReq1() 
	{
		//String ruler;
		
		String rf1 = "https://api.mongolab.com/api/1/databases/triptap_tripme_rules/collections/rules?q={%22state_init%22:%22";
		String rfpro = "Phuket";
		String rf2 = "%22,%22catAll.catName%22:{$all:[%22";
		String rf3 = "%22,%22";
		String rf4 = "%22]}}&apiKey=pssG0fVnXU2G1hV3eI9_SuidpTGqSi4N";
		
		if(allCat.size() == 1)
		{
			ruler = rf1 + province + rf2 + allCat.get(0) + rf4;
		}
		else
		{
			ruler = rf1 + province + rf2 + allCat.get(0);
			for(int i=1;i<allCat.size();i++)
			{
				ruler += rf3 + allCat.get(i); 
			}
			ruler += rf4;
		}
		
		showProgressDialog();
		JsonArrayRequest req = new JsonArrayRequest(ruler, 
				new Response.Listener<JSONArray>() 
				{
					@Override
					public void onResponse(JSONArray response) 
					{
						Log.d(TAG, response.toString());					
						try {
	                        jsonResponse = "";
	                        roro = "";
	                        jsonResponse += "Number of rules = " + response.length() + " Sample URl   ";
	                        resp = response;
	                        num = response.length();
	                        ruleid = new String [num];
	                        
	                        // Add data from JSON to array of list view
	                        arrUrl1 = new String[response.length()];
	                        arrUrl2 = new String[response.length()];
	                        arrUrl3 = new String[response.length()];
	                        arrTt = new String[response.length()];
	                        arrRating = new String[response.length()];
	                        
	                       String [] sp;
	                       String [] sc;
	                        
	                        // Start reading JSON from server example
	                        JSONObject prw = (JSONObject)response.get(0);
	                        JSONArray premisesa = prw.getJSONArray("premises");
	                        JSONObject preobee = premisesa.getJSONObject(0);
	                        String urll = preobee.getString("image").replace("\n", "");
	                        String [] urlth = urll.split("oooo");
	                        String [] url1 = urlth[1].split("/-/");
	                        String [] url2 = url1[1].split(".jpg-");	                        
	                        String urlr = url1[0] + "/" + url2[1] + "/" + url2[0] + ".jpg";	                        	                        	                       	                        
	                        jsonResponse += urlr;
	                        
	                        // Construct array for collecting data
	                        arrRule = new ArrayList <PlanData>();
	                        PlanData data;
	                        int count = 0;
	                        // Start reading each rule
	                        for (int i = 0; i < response.length(); i++) 	                        	
	                        {
	                        	data = new PlanData();
	                        	
	                        	double ra = 0;	                        		                        	
	                        	JSONObject pro = (JSONObject)response.get(i);
	                        	
	                        	JSONObject id = pro.getJSONObject("_id");
	                        	ruleid[i] = id.getString("$oid");
	                        	
	                        	roro += ruleid[i] + " ";
	                        	
	                        	jsonResponse += ruleid[i] + "   ";
	                        	
	                            JSONArray premises = pro.getJSONArray("premises");
	                            JSONArray conclu = pro.getJSONArray("conclusion");
	                            
	                            sp = new String[premises.length()];
	                            sc = new String[conclu.length()];
	                            
	                            // Reading each premises
	                            for(int j=0; j<premises.length(); j++)
	                            {
	                            	JSONObject preob = premises.getJSONObject(j);
	                            	String cat = preob.getString("venueName");
	                            	String rat = preob.getString("rate");
	                            	String urlp = preob.getString("image").replace("\n", "");	
	                            	
	                            	
	                            	String [] urlpsplit = urlp.split("oooo");
	                            	
	     	                        String [] urlp0 = urlpsplit[0].split("/-/");
	     	                        String [] urlp00 = urlp0[1].split(".jpg-");
	     	                        
	     	                        // image 1 url
	     	                        String urlpp0 = urlp0[0] + "/" + urlp00[1] + "/" + urlp00[0] + ".jpg";
	     	                        
	     	                         String [] urlp1 = urlpsplit[1].split("/-/");
	     	                        String [] urlp11 = urlp1[1].split(".jpg-");
	     	                         // image 2 url
	     	                        String urlpp1 = urlp1[0] + "/" + urlp11[1] + "/" + urlp11[0] + ".jpg";
	     	                        
	     	                       String [] urlp2 = urlpsplit[2].split("/-/");
	     	                      String [] urlp22 = urlp2[1].split(".jpg-");	     	                       
	     	                       // image 3 url
	     	                        String urlpp2 = urlp2[0] + "/" + urlp22[1] + "/" + urlp22[0] + ".jpg";
	     	                        
	     	                       String [] urlpr = urlpsplit[randomNum(0,2)].split("/-/");
		     	                      String [] urlprr = urlpr[1].split(".jpg-");
		     	                       
		     	                       // image random url
		     	                    String urlppr = urlpr[0] + "/" + urlprr[1] + "/" + urlprr[0] + ".jpg";
		     	                    
		     	                    sp[j] = urlppr;
		     	                      	     	                        	                            	
	                            	ra+= Double.parseDouble(rat);
	                            	jsonResponse += cat + "  " + urlpp0 + " ++ " + urlpp1 + " ++ " + urlpp2 + " ++ " + sp[j] + " ^^ ";
	                            	count ++;
	                            }
	                            jsonResponse += "connnn ";
	                        	
	                            // Reading each conclusion
	                            for(int j=0; j<conclu.length(); j++)
	                            {
	                            	JSONObject precon = conclu.getJSONObject(j);
	                            	String cat = precon.getString("vunueName");
	                            	String rat = precon.getString("rate");
	                            	
	                            	String urlc = precon.getString("image").replace("\n", "");
	                            	
	                            	String [] urlcsplit = urlc.split("oooo");
	                            	
	     	                        String [] urlc0 = urlcsplit[0].split("/-/");
	     	                        String [] urlc00 = urlc0[1].split(".jpg-");
	     	                        
	     	                        // image 1 url
	     	                        String urlcc0 = urlc0[0] + "/" + urlc00[1] + "/" + urlc00[0] + ".jpg";
	     	                        
	     	                         String [] urlc1 = urlcsplit[1].split("/-/");
	     	                        String [] urlc11 = urlc1[1].split(".jpg-");
	     	                         // image 2 url
	     	                        String urlcc1 = urlc1[0] + "/" + urlc11[1] + "/" + urlc11[0] + ".jpg";
	     	                        
	     	                       String [] urlc2 = urlcsplit[2].split("/-/");
	     	                      String [] urlc22 = urlc2[1].split(".jpg-");
	     	                       
	     	                       // image 3 url
	     	                        String urlcc2 = urlc2[0] + "/" + urlc22[1] + "/" + urlc22[0] + ".jpg";
	     	                        
	     	                      
	     	                      String [] urlcr = urlcsplit[randomNum(0,2)].split("/-/");
		     	                      String [] urlcrr = urlcr[1].split(".jpg-");
		     	                       
		     	                       // image random url
		     	                    String urlccr = urlcr[0] + "/" + urlcrr[1] + "/" + urlcrr[0] + ".jpg";
	     	                        
	     	                        sc[j] = urlccr;
	     	                        
	     	                        ra+= Double.parseDouble(rat);
	     	                        jsonResponse += cat + "  " + urlcc0 + " ++ " + urlcc1 + " ++ " + urlcc2 + " ++ " + sc[j] + " ^^ ";	
	     	                        count++;
	                            }
	                            DecimalFormat df = new DecimalFormat("#.#");
	                            jsonResponse += "end of rule and rate :";
	                            jsonResponse += "" + df.format(ra/(conclu.length()+premises.length())) + " ";
	                            //Toast.makeText(getActivity(), jsonResponse, Toast.LENGTH_LONG);
	                            
	                            // set data for listview
	                            data.setTitle("Phuket " + " " + count + " places" );
	                            data.setRate("Rating : " + df.format(ra/(conclu.length()+premises.length())));
	                            data.setUrlp1(sp[0]);
	                            data.setUrlp2(sc[0]);
	                            if(premises.length()<2)
	                            {
	                            	if(conclu.length()>1)
	                            		data.setUrlp3(sc[1]);
	                            	else
	                            		data.setUrlp3(sc[0]);
	                            }
	                            else
	                            	data.setUrlp3(sp[1]);	
	                            
	                            // Adding Data
	                            arrRule.add(data);
	                            //Toast.makeText(getActivity(), jsonResponse, Toast.LENGTH_LONG);
	                        }   	                        	                        	                        
	                        PlanAdapter pdt = new PlanAdapter(getActivity(), arrRule);
	                        lsvMain.setAdapter(pdt);
	                        pdt.notifyDataSetChanged();
	                       // Toast.makeText(getActivity(), jsonResponse, Toast.LENGTH_LONG).show();
	                        
	 
	                    } catch (JSONException e) 
	                    {
//	                        e.printStackTrace();
//	                        Toast.makeText(getActivity(),
//	                                "Error: " + e.getMessage(),
//	                                Toast.LENGTH_LONG).show();
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
				});		
		addToRequestQueue(req,tag_json_arry);
	}
		
	public int randomNum(int a, int b)
	{
		int Min = Math.min(a,b);
		int Max = Math.max(a,b);
		return Min+(int)(Math.random()*((Max-Min) + 1));
	}
	
//	@Override
//	public void onPause() 
//	{  
//	    super.onPause();
//	    Log.d("DetailFragment", "fragment is pausing. save data");
//	    saveData();
//	}
	
//	public void backButtonWasPressed() 
//	{
//		fr = new TripMe();
//		fm = getFragmentManager();
//    	ft = fm.beginTransaction();
//    	ft.replace(R.id.frame_container, fr);
//    	ft.commit();
//    }
	
  }