package com.example.triptap.tripme;

import com.example.triptap.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter 
{
   private Context mContext;
   private String[] picr;
   ImageLoader iml;
   DisplayImageOptions dmp;

   // Constructor
   public ImageAdapter(Context c, String [] picr) 
   {
      mContext = c;
      this.picr = picr;
      
      iml = ImageLoader.getInstance();
		dmp = new DisplayImageOptions.Builder().cacheInMemory()
				.cacheOnDisc().resetViewBeforeLoading()
				.showImageForEmptyUri(R.drawable.phuket1).build();		
   }
   
   class ViewHolder
   {
	   ImageView imageView;
   }

   public int getCount() 
   {
      return picr.length;
   }

   public Object getItem(int position) 
   {
      return null;
   }

   public long getItemId(int position) 
   {
      return 0;
   }

   // create a new ImageView for each item referenced by the Adapter
   public View getView(int position, View convertView, ViewGroup parent) 
   {
	  View view = convertView;
      ImageView imageView;
      if (convertView == null) 
      {
    	  LayoutInflater mInflater = (LayoutInflater) mContext
                  .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
          view = mInflater.inflate(R.layout.item_grid, null);
	      imageView = (ImageView)view.findViewById(R.id.gal_item);
//	      imageView.setLayoutParams(new LinearLayout.LayoutParams(150,150));
	      imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	      imageView.setPadding(8, 8, 8, 8);
	      view.setTag(imageView);
      } else 
      {
    	  imageView = (ImageView) view.getTag();
      }
      
      iml.displayImage(picr[position], imageView, dmp);

      //imageView.setImageResource(mThumbIds[position]);
      return view;
   }

    //Keep all Images in array
//   public Integer[] mThumbIds = 
//	{
//      R.drawable.phuket1,  R.drawable.phuket1,
//      R.drawable.phuket1,  R.drawable.phuket2,
//      R.drawable.phuket2,  R.drawable.phuket2,
//      R.drawable.phuket3,  R.drawable.phuket3,
//      R.drawable.phuket3, R.drawable.place_1,  R.drawable.place_1,
//      R.drawable.place_1
//  
//   };
   
   @Override
   public void notifyDataSetChanged() // Create this function in your adapter class
   {
       super.notifyDataSetChanged();
   }
}