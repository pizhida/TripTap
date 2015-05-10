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
import com.example.triptap.Const;
import com.example.triptap.DynamicListView;
import com.example.triptap.DynamicListView2;
import com.example.triptap.R;
import com.example.triptap.TripInfo;

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
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
  public class PlanAdjustment extends Fragment implements OnItemClickListener
  {
	// share pref
	public static final String MyPREFERENCES = "MyPrefs" ;
	public static final String Name = "nameKey"; 
	   public static final String Phone = "phoneKey"; 
	   public static final String Email = "emailKey"; 
	   public static final String Street = "streetKey"; 
	   public static final String Place = "placeKey"; 
	   SharedPreferences sharedpreferences;
	
	private Button btnSubmit;
	private ArrayList <AdData> arrMain;
	private ArrayList <AdData> arrPlan;
	private String TAG =  PlanAdjustment.class.getSimpleName();
	public FragmentTransaction ft;
	public Fragment fr;
	public FragmentManager fm;
	private String jsonResponse;
	
	private String jsonPlan;
	private int pos;
	private JSONArray jar;
	private String [] venid;
	
	private boolean [] check;
	private ListView lsvMain;
	
	private JSONObject [] jadtrip;
	private JSONArray jartrip;
	
	private int jarlength;
	
	
	
	private ProgressDialog pDialog;
	public PlanAdjustment()
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
        View rootView = inflater .inflate(R.layout.plan_adjust, container, false);
        btnSubmit = (Button)rootView.findViewById(R.id.finish_btn);
        
        // sharedpres
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    
        jarlength = 0;
        Editor editor = sharedpreferences.edit();
        
        editor.putString("fff", "FFF");
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        // Recieve Extra from PlanSelected
        jsonPlan = getArguments().getString("jsonSt");
        pos = getArguments().getInt("position");
        
        pDialog = new ProgressDialog(getActivity());
		pDialog.setMessage("Loading...");
		pDialog.setCancelable(false);
        
        arrMain = new ArrayList <AdData>();
        arrPlan = new ArrayList<AdData>();
        AdData data;
        for(int i=0; i<arrTitle.length; i++)
        {
        	data = new AdData();
        	data.setUrl("https://irs2.4sqi.net/img/general/720x537/Q3X0FFSHIJYQ0DSCNJGMN5H5ZHEF31P3K0CN3TESUUUJIEWX.jpg");
        	data.setTitle(arrTitle[i]);
        	data.setRate(arrSub[i]);
        	data.setId("111");
        	data.setCheck(false);
        	arrMain.add(data);
        }
        
        
        // ListView setUP
        lsvMain = (ListView)rootView.findViewById(R.id.adjust_list);
        AdjustmentAdapter adt = new AdjustmentAdapter(getActivity(), arrMain);
        lsvMain.setAdapter(adt);
        lsvMain.setOnItemClickListener(this);
        
        
        // JSON Parsing
        try 
        {
            jar = new JSONArray(jsonPlan);
            jarlength = jar.length();
            jadtrip = new JSONObject[jar.length()];
            jartrip = new JSONArray();
            
        } catch (JSONException e) 
        {
            e.printStackTrace();
        }
        jsonParser(jar);
        
                
        // Button setuo
        btnSubmit.setOnClickListener(new OnClickListener() 
        {
 
        	 @Override
             public void onClick(View v) 
             {
        		 String msg = "";
        		 ArrayList <AdData> arrNew = new ArrayList<AdData>();
        		 AdData data;
        		 for(int i=0;i<arrPlan.size();i++)
        		 {
        			 data = new AdData();
        			 if(arrPlan.get(i).isCheck() == true)
        			 {
        				 //msg += arrPlan.get(i).getId() + " ";
        				data.setId(arrPlan.get(i).getId());
        				data.setTitle(arrPlan.get(i).getTitle());
        				data.setRate(arrPlan.get(i).getRating());
        				data.setUrl(arrPlan.get(i).getUrl());
        				arrNew.add(data);	
        			 }
        			 
        		 }
        		 for(int i=0;i<arrNew.size();i++)
        		 {
        			 msg += arrNew.get(i).getId() + " ";
        		 }
        		 
        		// Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
  
             	fr = new PlaceInformation();
             	 Bundle args = new Bundle();
      		 	//String v = venid[position];
             	 args.putParcelableArrayList("data", arrNew);
              	//args.putString("venId", purl);
              	//args.putInt("Previous", pp);
              	fr.setArguments(args);
           	   	fm = getFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.frame_container, fr);
                ft.commit();
             }
 
        });
// 
//
//        // Button click Listener 
//        //addListenerOnButton();

      
        return rootView; 
	}

	// On item click to place information
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		 	fr = new PlaceInformation();
		 	Bundle args = new Bundle();
		 	String v = venid[position];
        	args.putString("venId", v);
        	args.putInt("Previous", 1);
        	args.putString("jsonSt", jsonPlan);
        	args.putInt("position", pos);
        	
        	//jsonPlan = getArguments().getString("jsonSt");
        //pos = getArguments().getInt("position");
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
	
	
	public void jsonParser(JSONArray jr)
	{
		try 
		{
            jsonResponse = "";
            jsonResponse += "Number of rules = " + jr.length();
            JSONObject [] jadtrip = new JSONObject[jr.length()];
            
            JSONObject pro = (JSONObject)jr.get(pos);
        	
        	JSONObject id = pro.getJSONObject("_id");
        	
        	
            	double ra = 0;
            	int count = 0;
            	
            	
            	arrPlan = new ArrayList<AdData>();
            	AdData dataa;
                 //dataa = new AdData();
            	//JSONObject pro = (JSONObject)jr.get(i);
                JSONArray premises = pro.getJSONArray("premises");
                JSONArray conclu = pro.getJSONArray("conclusion");
                
                // Venue Array Id
                venid = new String [premises.length() + conclu.length()];
                
                for(int j=0; j<premises.length(); j++)
                {
                	dataa = new AdData();
                	JSONObject preob = premises.getJSONObject(j);
                	String cat = preob.getString("venueName");
                	//jadtrip[j].put("name", "lll");
                	String rat = preob.getString("rate");
                	//jadtrip[count].put("rating", rat);
                	String ven = preob.getString("venueId");
                	//jadtrip[count].put("id", ven);
                	String urlp = preob.getString("image").replace("\n", "");	                	
                	String [] urlpsplit = urlp.split("oooo");                	
                    String [] urlp0 = urlpsplit[0].split("/-/");
                    String [] urlp00 = urlp0[1].split(".jpg-");
                    String urlpp0 = urlp0[0] + "/" + urlp00[1] + "/" + urlp00[0] + ".jpg";
                    //jadtrip[count].put("url", urlpp0);
                    count ++ ;
                	venid[j] = ven;
                	ra+= Double.parseDouble(rat);
                	jsonResponse += cat + "  " ;
                	dataa.setTitle(cat);
                	dataa.setRate(rat);
                	dataa.setUrl(urlpp0);
                	dataa.setId(ven);
                	dataa.setCheck(false);
                	arrPlan.add(dataa);
                }
                jsonResponse += "connnn ";
            	
                for(int j=0; j<conclu.length(); j++)
                {
                	dataa = new AdData();
                	JSONObject precon = conclu.getJSONObject(j);
                	String cat = precon.getString("vunueName");
                	//jadtrip[count].put("name", cat);
                	String rat = precon.getString("rate");
                	//jadtrip[count].put("rating", rat);
                	String ven = precon.getString("venueId");
                	//jadtrip[count].put("id", ven);
                	
                	String urlp = precon.getString("image").replace("\n", "");	                	
                	String [] urlpsplit = urlp.split("oooo");                	
                    String [] urlp0 = urlpsplit[0].split("/-/");
                    String [] urlp00 = urlp0[1].split(".jpg-");
                    String urlpp0 = urlp0[0] + "/" + urlp00[1] + "/" + urlp00[0] + ".jpg";
                    //jadtrip[count].put("url", urlpp0);
                	venid[j+premises.length()] = ven;
                	
                	ra += Double.parseDouble(rat);
                	jsonResponse += cat + ",   ";
                	
                	dataa.setTitle(cat);
                	dataa.setRate(rat);
                	dataa.setUrl(urlpp0);
                	dataa.setId(ven);
                	dataa.setCheck(false);
                	arrPlan.add(dataa);
                	
                }
                DecimalFormat df = new DecimalFormat("#.#");
                jsonResponse += "end of rule and rate :";
                jsonResponse += "" + df.format(ra/(conclu.length()+premises.length())) + " ";
              
           // Toast.makeText(getActivity(), jsonResponse, Toast.LENGTH_LONG).show();
            
            AdjustmentAdapter adt = new AdjustmentAdapter(getActivity(), arrPlan);
            lsvMain.setAdapter(adt);
            adt.notifyDataSetChanged();
            //Toast.makeText(getActivity(), jsonResponse, Toast.LENGTH_LONG).show();
            

        } catch (JSONException e) 
        {
            e.printStackTrace();
            //Toast.makeText(getActivity(),
                    //"Error: " + e.getMessage(),
                    //Toast.LENGTH_LONG).show();
        }	
			//hideProgressDialog();
		}
		
 
  }
