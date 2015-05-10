package com.example.triptap;

import com.example.triptap.R;
import android.annotation.SuppressLint;  
import android.app.Fragment;
 import android.os.Bundle;
 import android.view.LayoutInflater;
 import android.view.View;
 import android.view.ViewGroup; 
@SuppressLint("NewApi")  
  public class TripInfo extends Fragment 
  { 

	@Override 
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)     
	{ 
        View rootView = inflater .inflate(R.layout.trip_information, container, false);         
        return rootView; 
	}
  }
