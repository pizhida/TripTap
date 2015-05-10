package com.example.triptap.tripme;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.ImageView;


import com.example.triptap.R;
import com.example.triptap.nearbyme.Hotel;
import com.example.triptap.nearbyme.Restaurant;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class SingleView extends Fragment
{
	
	private String [] picUrl;
	private int position;
	ImageLoader iml;
	DisplayImageOptions dmp;
	ImageView imgView;
	private int pp;
	private String venid;
	public FragmentTransaction ft;
	public Fragment fr;
	public FragmentManager fm;
	
	@Override 
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)     
	{ 
        View rootView = inflater .inflate(R.layout.single_view, container, false); 
        
        // Get intent data
        iml = ImageLoader.getInstance();
      		dmp = new DisplayImageOptions.Builder().cacheInMemory()
      				.cacheOnDisc().resetViewBeforeLoading()
      				.showImageForEmptyUri(R.drawable.phuket1).build();	
      	
      	Button prev = (Button)rootView.findViewById(R.id.btn_pre);
      	Button next = (Button)rootView.findViewById(R.id.btn_next);
      	
        picUrl = getArguments().getStringArray("picUrl");
        position = getArguments().getInt("pos");
        pp = getArguments().getInt("Previous");
        venid = getArguments().getString("venId");
        
        		
        imgView = (ImageView)rootView.findViewById(R.id.single_image);
        
        iml.displayImage(picUrl[position], imgView, dmp);
        
        prev.setOnClickListener(new OnClickListener() 
        {
 
            @Override
            public void onClick(View v) 
            {
            	position = position-1;
            	iml.displayImage(picUrl[position], imgView, dmp);
           
            }
 
        });
        
        next.setOnClickListener(new OnClickListener() 
        {
 
            @Override
            public void onClick(View v) 
            {
            	 position = position+1;
            	 iml.displayImage(picUrl[position], imgView, dmp);
            }
 
        });
        
        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();

        rootView.setOnKeyListener(new OnKeyListener() 
        {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (event.getAction() == KeyEvent.ACTION_DOWN) {
                            if (keyCode == KeyEvent.KEYCODE_BACK) 
                            {
                            	
                            		 fr = new PlaceGallery();
                        			 Bundle args = new Bundle();
                         		 	//String v = venid[position];
                                 	args.putString("venId", venid);
                                 	args.putInt("Previous", pp);
                                 	fr.setArguments(args);
                                	 
                                	  fm = getFragmentManager();
                                       ft = fm.beginTransaction();
                                       ft.replace(R.id.frame_container, fr);
                                       ft.commit();
                            
                                //Toast.makeText(getActivity(), "Back Pressed", Toast.LENGTH_SHORT).show();
                                return true;
                            }
                        }
                        return false;
                    }

			
                });
        
        
        return rootView; 
	}

}
