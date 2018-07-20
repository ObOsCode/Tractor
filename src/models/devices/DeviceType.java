package models.devices;

import java.util.ArrayList;

public class DeviceType
{
	private static ArrayList<DeviceType> _list = new ArrayList<DeviceType>();
	
	private static int _nextIndex = 0;
	
	//Robots
	public static final DeviceType PLATFORM = new DeviceType("Tractor platform");
	public static final DeviceType DELTA = new DeviceType("Delta robot");
	//Control devices
	public static final DeviceType CONTROL_ANDROID_SMARTPHONE = new DeviceType("Android smartphone");
	public static final DeviceType CONTROL_ARDUINO = new DeviceType("Arduino controller");
	

	private String _name;
	private int _index;
	
	
	public static ArrayList<DeviceType> getList()
	{
		return _list;
	}
	
	
	public static DeviceType getByIndex(int index)
	{
		return _list.get(index);
	}
	
	
	protected DeviceType(String name)
	{
		this._name = name;
		this._index = _nextIndex;
		
		_nextIndex++;
		
		_list.add(this);
	}
	
	
	public String getName()
	{
		return this._name;
	}
	
	
	public int getIndex()
	{
		return this._index;
	}
	
	
	@Override
	public String toString()
	{
		return getName();
	}

}
