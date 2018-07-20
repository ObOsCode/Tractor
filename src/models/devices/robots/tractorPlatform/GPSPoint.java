package models.devices.robots.tractorPlatform;

public class GPSPoint
{
	
	private double _latitude = 0;
	
	private double _longitude = 0;
	

	public GPSPoint(double latitude, double longitude)
	{
		_latitude = latitude;
		_longitude = longitude;
	}
	
	
	public double getLatitude()
	{
		return _latitude;
	}
	
	
	public double getLongitude()
	{
		return _longitude;
	}
	
	
//	public

}
