package com.example.triptap;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;  
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
//import android.widget.ArrayAdapter;  
import android.widget.ListView;

import com.example.triptap.nearbyme.Hotel;
import com.example.triptap.nearbyme.Restaurant;
import com.example.triptap.tripforyou.TripForYou;
import com.example.triptap.tripme.PlanSelected;
import com.example.triptap.tripme.TripMe;

public class MainActivity extends FragmentActivity 
{ 
	String[] menutitles;  TypedArray menuIcons;
	// nav drawer title
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;   
	private ActionBarDrawerToggle mDrawerToggle;
	private List<RowItem> rowItems;
	private CustomAdapter adapter;
	private PlanSelected ps;
	private ColorDrawable cd;

	@SuppressLint("NewApi")
	@Override  
	protected void onCreate(Bundle savedInstanceState) 
	{
			super.onCreate(savedInstanceState);
	       setContentView(R.layout.activity_main);
	       ActionBar ab = getActionBar();
	       ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#CC00BFA5")));
	       mTitle = mDrawerTitle = getTitle();
	       menutitles = getResources().getStringArray(R.array.titles);
	       menuIcons = getResources().obtainTypedArray(R.array.icons);    
	       mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
	       mDrawerList = (ListView) findViewById(R.id.slider_list); 
	       
	       //Fragment nn = new LoginHowto();
	       //Fragment n = new TapMaps();

         rowItems = new ArrayList<RowItem>(); 

         for (int i = 0; i < menutitles.length; i++) 
         { 
           RowItem items = new RowItem(menutitles[i], menuIcons.getResourceId(i, -1)); 
           rowItems.add(items);
         }    

         menuIcons.recycle();    
         adapter = new CustomAdapter(getApplicationContext(), rowItems);   
         mDrawerList.setAdapter(adapter); 
         mDrawerList.setOnItemClickListener(new SlideitemListener()); 

         // enabling action bar app icon and behaving it as toggle button                                                
	    getActionBar().setDisplayHomeAsUpEnabled(true);     
	    getActionBar().setHomeButtonEnabled(true);

	    mDrawerToggle = new ActionBarDrawerToggle((Activity)this, mDrawerLayout,R.drawable.ic_drawer, R.string.app_name,R.string.app_name)
	    {       
        public void onDrawerClosed(View view) 
        { 
                 getActionBar().setTitle(mTitle); 
                 // calling onPrepareOptionsMenu() to show action bar icons 
                  invalidateOptionsMenu(); 
        } 
        public void onDrawerOpened(View drawerView) 
        {           
             getActionBar().setTitle(mDrawerTitle); 
       // calling onPrepareOptionsMenu() to hide action bar icons 
             invalidateOptionsMenu();          } 
        }; 
        mDrawerLayout.setDrawerListener(mDrawerToggle); 
        if (savedInstanceState == null) 
        {         
        	// on first time display view for first nav item 
           updateDisplay(0); 
      }
    }  
	class SlideitemListener implements ListView.OnItemClickListener 
    { 
     
        @Override 
          public void onItemClick(AdapterView<?> parent, View view, int position, long id)   
             {                  
        		updateDisplay(position); 
        		//mDrawerLayout.closeDrawer(mDrawerList);
                 
             } 

       }  
    	private void updateDisplay(int position) 
       { 
    		Fragment fragment = null; 
          switch (position) 
          { 
                  case 0:      fragment = new TripMe();               
                                         break; 
                  case 1:       fragment = new TripForYou(); 
                                     break; 
                  case 2:         fragment = new Hotel();
                                         break; 
                  case 3:      fragment = new Restaurant();               
                  						break; 
                  case 4:       fragment = new HowToReach(); 
                  						break; 
                case 5:        fragment = new LoginHowto();
//                   						break;                             
                  default:           
                                         break; 
         }   
         if (fragment != null) 
         { 
                     	FragmentManager fragmentManager = getSupportFragmentManager();           
                     	fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();                    
                     	// update selected item and title, then close the drawer 
                     	setTitle(menutitles[position]);         
                     	mDrawerLayout.closeDrawer(mDrawerList); 
         }
        else 
        { 
                     	// error in creating fragment 
                     	Log.e("MainActivity", "Error in creating fragment"); 
        } 
             } 
    	
//    	private void updateDisplay1(int position) 
//        { 
//                 Fragment fragment = null; 
//                  switch (position) 
//                  { 
//                          case 0:      fragment = new TripMe();               
//                                                 break; 
//                          case 1:       fragment = new TripForYou(); 
//                                             break; 
//                          case 2:         fragment = new Hotel();
//                                                 break; 
//                          case 3:      fragment = new Restaurant();               
//                          						break; 
//                          case 4:       fragment = new TapMaps(); 
//                          						break; 
//                          case 5:         fragment = new TripInfo();
//                           						break;                             
//                          default:           
//                                                 break; 
//                 }   
//                 if (fragment != null) 
//                 { 
//                             	FragmentManager fragmentManager = getFragmentManager();           
//                             	fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();                    
//                             	// update selected item and title, then close the drawer 
//                             	setTitle(menutitles[position]);         
//                             	mDrawerLayout.closeDrawer(mDrawerList); 
//                 }
//                else 
//                { 
//                             	// error in creating fragment 
//                             	Log.e("MainActivity", "Error in creating fragment"); 
//                } 
//              } 
//    	
    	
    	@Override     
    	public void setTitle(CharSequence title) 
    	{       
                   mTitle = title;         
                   getActionBar().setTitle(mTitle); 
    	} 
    	@Override   
    	public boolean onCreateOptionsMenu(Menu menu) 
    	{        
         getMenuInflater().inflate(R.menu.main, menu); 
         return true; 
    	} 
    	@Override 
    	public boolean onOptionsItemSelected(MenuItem item) 
    	{   
    		// toggle nav drawer on selecting action bar app icon/title       
    		if (mDrawerToggle.onOptionsItemSelected(item)) {                   
    			return true;           
    	} 
    		// Handle action bar actions click   
    		int itemId = item.getItemId();
    		if (itemId == R.id.action_settings) 
    		{
    			return true;
    		} else 	
    		{
    			return super.onOptionsItemSelected(item);
    		}
    	}

 /***   * Called when invalidateOptionsMenu() is triggered   */ 
    	@Override      
    	public boolean onPrepareOptionsMenu(Menu menu) 
    	{ 
    		// if nav drawer is opened, hide the action items             
    		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList); 
    		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);           
    		return super.onPrepareOptionsMenu(menu); 
    	}

  /**   * When using the ActionBarDrawerToggle, you must call it during   * onPostCreate() and onConfigurationChanged()...   */ 
    	@Override    
    	protected void onPostCreate(Bundle savedInstanceState) 
    	{ 
        super.onPostCreate(savedInstanceState); 
        // Sync the toggle state after onRestoreInstanceState has occurred.           
        mDrawerToggle.syncState();    
        } 
    	@Override      
    	public void onConfigurationChanged(Configuration newConfig) 
    	{ 
    		super.onConfigurationChanged(newConfig); 
    		// Pass any configuration change to the drawer toggles 
    		mDrawerToggle.onConfigurationChanged(newConfig); 
    	}
    	
//    	@Override
//        public void onBackPressed() 
//    	{
//            super.onBackPressed();
//            ps.backButtonWasPressed();
//        }
}