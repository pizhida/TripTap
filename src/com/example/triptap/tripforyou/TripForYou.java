package com.example.triptap.tripforyou;

import com.example.triptap.R;
import android.annotation.SuppressLint;  
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup; 
@SuppressLint("NewApi")  
  public class TripForYou extends Fragment 
  { 

	@Override 
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)     
	{ 
        View rootView = inflater .inflate(R.layout.trip_for_you, container, false);         
        return rootView; 
	}
  }
