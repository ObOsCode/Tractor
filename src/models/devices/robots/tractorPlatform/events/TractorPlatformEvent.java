package models.devices.robots.tractorPlatform.events;

import ru.roboticsnt.events.Event;

public class TractorPlatformEvent extends Event
{
	
	public static final String ENGINE_STATE_CHANGE = "engineStateChange";
	public static final String SPEED_CHANGE = "speedChange";
	public static final String TURN_CHANGE = "turnChange";
	public static final String MOTION_STATE_CHANGE = "motionStateChange";
	
	public static final String GPS_POSITION_CHANGE = "gpsPositionChange";
	
	private String _eventType;
	

	public TractorPlatformEvent(String eventType)
	{
		super(eventType);
		
		_eventType = eventType;
	}
	
	
	public String getType()
	{
		return _eventType;
	}

}
