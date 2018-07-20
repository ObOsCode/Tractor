package models.devices.controlDevices;

import java.util.ArrayList;

import models.devices.DeviceTypeBase;

public class ControlDeviceType extends DeviceTypeBase
{
	
	private static ArrayList<ControlDeviceType> _list = new ArrayList<ControlDeviceType>();
	
	//Control devices
	public static final ControlDeviceType CONTROL_ARDUINO_BT = new ControlDeviceType(0, "Arduino bluetooth");
	public static final ControlDeviceType CONTROL_ANDROID_WIFI = new ControlDeviceType(1, "Android WI-FI");
	
	
	public static ArrayList<ControlDeviceType> getList()
	{
		return _list;
	}
	
	
	public static ControlDeviceType getByIndex(int index)
	{
		return _list.get(index);
	}
	
	
	public ControlDeviceType(int index, String name)
	{
		super(index, name);
		_list.add(this);
	}

}
