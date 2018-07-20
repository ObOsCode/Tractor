package models.devices.robots.platform.events;

public class TractorPlatformEvent
{
	
	public static final String ENGINE_STATE_CHANGE = "engineStateChange";
	public static final String SPEED_CHANGE = "speedChange";
	public static final String TURN_CHANGE = "turnChange";
	public static final String MOTION_STATE_CHANGE = "motionStateChange";
	
	private String _eventType;
	

	public TractorPlatformEvent(String eventType)
	{
		_eventType = eventType;
	}
	
	
	public String getType()
	{
		return _eventType;
	}

}
