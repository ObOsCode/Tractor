package models.devices.robots;

import java.util.ArrayList;

import models.devices.DeviceTypeBase;

public class RobotType extends DeviceTypeBase
{
	private static ArrayList<RobotType> _list = new ArrayList<RobotType>();
	

	public static final RobotType PLATFORM = new RobotType(0, "Tractor platform v1.0");
	public static final RobotType DELTA = new RobotType(1, "Delta robot v1.0");
	
	
	public static ArrayList<RobotType> getList()
	{	
		return _list;
	}
	
	
	public static RobotType getByIndex(int index)
	{
		return _list.get(index);
	}
	
	
	private RobotType(int index, String name)
	{
		super(index, name);
		_list.add(this);
	}
	
	
}
