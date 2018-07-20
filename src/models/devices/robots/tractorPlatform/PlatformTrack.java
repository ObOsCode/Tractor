package models.devices.robots.tractorPlatform;

import java.util.ArrayList;
import java.util.Date;

import com.google.gson.annotations.SerializedName;


public class PlatformTrack
{
	@SerializedName("points")
	private ArrayList<GPSPoint> _pointList = new ArrayList<>();
	
	@SerializedName("name")
	private String _name;
	
	@SerializedName("date")
	private Date _date;
	
	
	public PlatformTrack(String name)
	{
		_date = new Date();
		
		_name = name;
	}
	
	
	@Override
	public String toString()
	{
		return _name + " " + _date.toString();
//		return _name + " ";
	}
	
	
	public String getName()
	{
		return _name;
	}
	
	
	public void addPoint(GPSPoint point)
	{
		_pointList.add(point);
	}
	
	
	public ArrayList<GPSPoint> getPointList()
	{
		return _pointList;
	}
	

}
