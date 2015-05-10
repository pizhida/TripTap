package com.example.triptap.tripme;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;


public class AdData implements Parcelable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String imgurl;
	private String title;
	private String rating;
	private String vid;
	private boolean chk;
	
	
	public String getUrl()
	{
		return imgurl;
	}
	
	public void setUrl(String imgurl)
	{
		this.imgurl = imgurl;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public String getRating()
	{
		return rating;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}

	public void setRate(String rating)
	{
		this.rating = rating;
	}
	
	public boolean isCheck()
	{
		return chk;
	}
	
	public void setCheck(boolean chk)
	{
		this.chk = chk;
	}
	
	public String getId()
	{
		return vid;
	}
	
	public void setId(String vid)
	{
		this.vid = vid;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}



}