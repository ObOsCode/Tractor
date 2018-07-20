package models.events;

public class MainModelEvet
{

	public static final String ROBOTS_LIST_CHANGE = "robotsListChange";
	public static final String SELECTED_ROBOT_CHANGE = "selectedRobotsChange";
	
	public static final String CONTROL_DEVICES_LIST_CHANGE = "controlDevicesListChange";
	public static final String SELECTED_CONTROL_DEVICE_CHANGE = "selectedControlDeviceChange";

	private String _eventType;
	
	
	public MainModelEvet(String eventType)
	{
		_eventType = eventType;
	}

	
	public String getType()
	{
		return _eventType;
	}

}
