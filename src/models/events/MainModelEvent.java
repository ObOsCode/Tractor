package models.events;

import ru.roboticsnt.events.Event;

public class MainModelEvent extends Event
{

	public static final String DEVICE_LIST_CHANGE = "deviceListChange";
	public static final String CONNECTIONS_LIST_CHANGE = "connectionsListChange";
	public static final String SELECTED_CONTROL_DEVICE_CHANGE = "selectedControlDeviceChange";
	public static final String SELECTED_DEVICE_CHANGE = "selectedDeviceChange";
	public static final String PLATFORM_CONNECT = "platformConnect";
	public static final String PLATFORM_DISCONNECT = "platformDisconnect";
	
	public static final String TRACK_LIST_CHANGE = "trackListChange";
	

	private String _eventType;
	
	
	public MainModelEvent(String eventType)
	{
		super(eventType);
		
		_eventType = eventType;
	}
	
	
	public String getType()
	{
		return _eventType;
	}

}
