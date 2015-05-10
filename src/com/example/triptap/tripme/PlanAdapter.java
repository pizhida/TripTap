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
import android.widget.ImageView;
import android.widget.TextView;

public class PlanAdapter extends BaseAdapter 
{

    Context context;
    List<PlanData> rowItem;
	ImageLoader iml;
	DisplayImageOptions dmp;

    PlanAdapter(Context context, List<PlanData> rowItem) 
    {
        this.context = context;
        this.rowItem = rowItem;
        
    	iml = ImageLoader.getInstance();
		dmp = new DisplayImageOptions.Builder().cacheInMemory()
				.cacheOnDisc().resetViewBeforeLoading()
				.showImageForEmptyUri(R.drawable.phuket1).build();		

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

        if (convertView == null) 
        {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.plan_item, null);
        }

        //ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.planname);
        TextView txtRate = (TextView) convertView.findViewById(R.id.rating);
        ImageView img1 = (ImageView) convertView.findViewById(R.id.preview1);
        ImageView img2 = (ImageView) convertView.findViewById(R.id.preview2);
        ImageView img3 = (ImageView) convertView.findViewById(R.id.preview3);
        

        PlanData row_pos = rowItem.get(position);
        // setting the image resource and title
        //imgIcon.setImageResource(row_pos.getIcon());
        txtTitle.setText(row_pos.getTitle());
        txtRate.setText(row_pos.getRating());
        
        iml.displayImage(row_pos.getUrlp1(), img1, dmp);
        iml.displayImage(row_pos.getUrlp2(), img2, dmp);
        iml.displayImage(row_pos.getUrlp3(), img3, dmp);
        
        
//        img1.setImageDrawable(row_pos.getDrawable1());
//        img2.setImageDrawable(row_pos.getDrawable2());
//        img3.setImageDrawable(row_pos.getDrawable3());
        

        return convertView;

    }
    
    @Override
    public void notifyDataSetChanged() // Create this function in your adapter class
    {
        super.notifyDataSetChanged();
    }


}