package com.example.triptap.tripme;

import java.util.List;

import com.example.triptap.R;

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

public class CustomAdapter extends BaseAdapter 
{

    Context context;
    List<ArrData> rowItem;

    CustomAdapter(Context context, List<ArrData> rowItem) 
    {
        this.context = context;
        this.rowItem = rowItem;

    }
    
    private class ListViewCustomItem
	{
		public TextView tvTitle;
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
            convertView = mInflater.inflate(R.layout.category_item, null);
            lsvItem = new ListViewCustomItem();
            lsvItem.tvTitle = (TextView)convertView.findViewById(R.id.catitem);
            lsvItem.box = (CheckBox)convertView.findViewById(R.id.cat_box);
                      
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
			convertView.setTag(R.id.catitem, lsvItem.tvTitle);
			convertView.setTag(R.id.cat_box, lsvItem.box);
            
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
        

//		lsvItem.box.setChecked(arrMain.get(position).isSelected());

        //ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
//        TextView txtTitle = (TextView) convertView.findViewById(R.id.catitem);
//        CheckBox chkBox = (CheckBox) convertView.findViewById(R.id.cat_box);

        //ArrData row_pos = rowItem.get(position);
        
        // setting the image resource and title
        //imgIcon.setImageResource(row_pos.getIcon());
        
        
//        txtTitle.setText(row_pos.getTitle());
        //chkBox.setChecked(row_pos.isCheck());

        return convertView;

    }
    
    @Override
    public void notifyDataSetChanged() // Create this function in your adapter class
    {
        super.notifyDataSetChanged();
    }

}