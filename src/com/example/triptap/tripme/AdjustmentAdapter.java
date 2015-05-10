package com.example.triptap.tripme;

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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AdjustmentAdapter extends BaseAdapter 
{

    Context context;
    List<AdData> rowItem;
    ImageLoader iml;
	DisplayImageOptions dmp;

    public AdjustmentAdapter(Context context, List<AdData> rowItem) 
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
    	public ImageView img;
		public TextView tvTitle;
		public TextView tvRating;
		public CheckBox box;
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
            convertView = mInflater.inflate(R.layout.planaj_item, null);
            lsvItem = new ListViewCustomItem();
            lsvItem.tvTitle = (TextView)convertView.findViewById(R.id.planaj_name);
            lsvItem.tvRating = (TextView)convertView.findViewById(R.id.planaj_rate);
            lsvItem.img = (ImageView)convertView.findViewById(R.id.planaj_image);
            lsvItem.box = (CheckBox)convertView.findViewById(R.id.planaj_box);
            
                      
            lsvItem.box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() 
            {
    			
    			@Override
    			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) 
    			{
    				// TODO Auto-generated method stub
    				int getPosition = (Integer)buttonView.getTag();
    				
    				if(rowItem.get(getPosition).isCheck() == false)
    				{
    					rowItem.get(getPosition).setCheck(true);
    					Toast.makeText(context, rowItem.get(getPosition).getTitle() + " is selected", Toast.LENGTH_SHORT).show();
    				}
    				else 
    				{
    					rowItem.get(getPosition).setCheck(false);
    					Toast.makeText(context, rowItem.get(getPosition).getTitle() + " is unselected", Toast.LENGTH_SHORT).show();
    				}
    			}
    		});
            
            convertView.setTag(lsvItem);
			convertView.setTag(R.id.planaj_name, lsvItem.tvTitle);
			convertView.setTag(R.id.planaj_box, lsvItem.box);
			convertView.setTag(R.id.planaj_rate, lsvItem.tvRating);
			convertView.setTag(R.id.planaj_image, lsvItem.img);
            
        }
		else
		{
			lsvItem = (ListViewCustomItem)convertView.getTag();
		}
        
        lsvItem.box.setTag(position);
        
    	if(rowItem.get(position).getTitle() != null)
		{
			lsvItem.tvTitle.setText(rowItem.get(position).getTitle());
		}
    	
    	if(rowItem.get(position).getUrl() != null)
		{
    		iml.displayImage(rowItem.get(position).getUrl(), lsvItem.img, dmp);
		}
    		
		if(rowItem.get(position).getRating() != null)
		{
			lsvItem.tvRating.setText(rowItem.get(position).getRating());
		}

        return convertView;

    }
    
    @Override
    public void notifyDataSetChanged() // Create this function in your adapter class
    {
        super.notifyDataSetChanged();
    }

}