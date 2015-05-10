package com.example.triptap.tripme;

import android.graphics.drawable.Drawable;

public class PlanData 
{
//	private Drawable pic1;
//	private Drawable pic2;
//	private Drawable pic3;
	private String urlp1;
	private String urlp2;
	private String urlp3;
	private String title;
	private String rating;
	
//	public Drawable getDrawable1()
//	{
//		return pic1;
//	}
//	
//	public Drawable getDrawable2()
//	{
//		return pic2;
//	}
//	
//	public Drawable getDrawable3()
//	{
//		return pic3;
//	}
//	
//	public void setDrawable1(Drawable pic1)
//	{
//		this.pic1 = pic1;
//	}
//	
//	public void setDrawable2(Drawable pic2)
//	{
//		this.pic2 = pic2;
//	}
//	
//	public void setDrawable3(Drawable pic3)
//	{
//		this.pic3 = pic3;
//	}
	
	public String getUrlp1()
	{
		return urlp1;
	}
	
	public String getUrlp2()
	{
		return urlp2;
	}
	
	public String getUrlp3()
	{
		return urlp3;
	}
	
	public void setUrlp1(String urlp1)
	{
		this.urlp1 = urlp1;
	}

	public void setUrlp2(String urlp2)
	{
		this.urlp2 = urlp2;
	}
	
	public void setUrlp3(String urlp3)
	{
		this.urlp3 = urlp3;
		
		
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
}