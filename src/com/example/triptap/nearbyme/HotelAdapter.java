package com.example.triptap.nearbyme;

import java.util.List;

import com.example.triptap.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HotelAdapter extends BaseAdapter 
{

    Context context;
    List<HotelData> rowItem;
	ImageLoader iml;
	DisplayImageOptions dmp;

    HotelAdapter(Context context, List<HotelData> rowItem) 
    {
        this.context = context;
        this.rowItem = rowItem;
        
    	iml = ImageLoader.getInstance();
		dmp = new DisplayImageOptions.Builder().cacheInMemory()
				.cacheOnDisc().resetViewBeforeLoading()
				.showImageForEmptyUri(R.drawable.phuket1).build();		

    }
    
    private class ListViewCustomItem
 	{
    	public ImageView imgView;
 		public TextView tvTitle;
 		public TextView tvDistance;
 	}

    @Override
    public int getCount() 
    {

        return rowItem.size();
    }

    @Override
    public Object getItem(int position) 
    {

        return rowItem.get(position);
    }

    @Override
    public long getItemId(int position) 
    {

        return rowItem.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) 
    {

    	ListViewCustomItem lsvItem = null;
        if (convertView == null) 
        {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.hotel_item, null);
            lsvItem = new ListViewCustomItem();
            lsvItem.tvTitle = (TextView)convertView.findViewById(R.id.hot_name);
            lsvItem.tvDistance = (TextView)convertView.findViewById(R.id.hot_dis);
            lsvItem.imgView = (ImageView)convertView.findViewById(R.id.hot_cat);
            
                                 
            convertView.setTag(lsvItem);
			convertView.setTag(R.id.hot_name, lsvItem.tvTitle);
			convertView.setTag(R.id.hot_dis, lsvItem.tvDistance);
			convertView.setTag(R.id.hot_cat, lsvItem.imgView);
            
        }
		else
		{
			lsvItem = (ListViewCustomItem)convertView.getTag();
		}
        
        
    	if(rowItem.get(position).getTitle() != null)
		{
			lsvItem.tvTitle.setText(rowItem.get(position).getTitle());
		}
    	
    	if(rowItem.get(position).getDistance() != null)
		{
			lsvItem.tvDistance.setText(rowItem.get(position).getDistance());
		}
    	
    	if(rowItem.get(position).getPurl() != null)
		{
			iml.displayImage(rowItem.get(position).getPurl(), lsvItem.imgView, dmp);
		}        

        return convertView;

    }
    
    @Override
    public void notifyDataSetChanged() // Create this function in your adapter class
    {
        super.notifyDataSetChanged();
    }


}