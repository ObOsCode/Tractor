package models.devices.robots.tractorPlatform.events;

import ru.roboticsnt.events.Event;
import ru.roboticsnt.events.EventListener;

public class TractorPlatformListener extends EventListener
{

	public TractorPlatformListener()
	{

	}
	
	
	public void onEngineStateChange(TractorPlatformEvent event)
	{
		
	}

	
	public void onTurnChange(TractorPlatformEvent event)
	{
		
	}


	public void onSpeedChange(TractorPlatformEvent event)
	{
		
	}


	public void onMotionStaeChange(TractorPlatformEvent event)
	{
		
	}
	
	
	public void onGPSPositionChange(TractorPlatformEvent event)
	{
		
	}
	
	
	@Override
	public void onEvent(Event event)
	{
		switch (event.getType())
		{
		
		case TractorPlatformEvent.ENGINE_STATE_CHANGE:
			onEngineStateChange((TractorPlatformEvent) event);
			break;
			
		case TractorPlatformEvent.SPEED_CHANGE:
			onSpeedChange((TractorPlatformEvent) event);
			break;
			
		case TractorPlatformEvent.TURN_CHANGE:
			onTurnChange((TractorPlatformEvent) event);
			break;
			
		case TractorPlatformEvent.MOTION_STATE_CHANGE:
			onMotionStaeChange((TractorPlatformEvent) event);
			break;
			
		case TractorPlatformEvent.GPS_POSITION_CHANGE:
			onGPSPositionChange((TractorPlatformEvent) event);
			break;

		default:
			break;
		}

	}

}
