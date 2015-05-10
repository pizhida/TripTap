package com.example.triptap.tripme;

import java.util.List;

import com.example.triptap.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CommentAdapter extends BaseAdapter 
{

    Context context;
    String [] rowItem;

    CommentAdapter(Context context, String [] rowItem) 
    {
        this.context = context;
        this.rowItem = rowItem;

    }

    @Override
    public int getCount() 
    {

        return rowItem.length;
    }

    @Override
    public Object getItem(int position) 
    {

        return rowItem[position];
    }

    @Override
    public long getItemId(int position) 
    {

        return rowItem[position].indexOf(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) 
    {

        if (convertView == null) 
        {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.comment_item, null);
        }

        //ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.comment_it);

        String row_pos = rowItem[position];
        // setting the image resource and title
        //imgIcon.setImageResource(row_pos.getIcon());
        
        txtTitle.setText(row_pos);

        return convertView;

    }
    
    @Override
    public void notifyDataSetChanged() // Create this function in your adapter class
    {
        super.notifyDataSetChanged();
    }

}