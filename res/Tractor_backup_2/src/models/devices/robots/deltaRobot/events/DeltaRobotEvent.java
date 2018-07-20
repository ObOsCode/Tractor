package models.devices.robots.deltaRobot.events;

public class DeltaRobotEvent
{
	
	public static final String POSITION_CHANGE = "positionChange";

	private String _eventType;
	
	
	public DeltaRobotEvent(String eventType)
	{
		_eventType = eventType;
	}

	
	public String getType()
	{
		return _eventType;
	}
}
