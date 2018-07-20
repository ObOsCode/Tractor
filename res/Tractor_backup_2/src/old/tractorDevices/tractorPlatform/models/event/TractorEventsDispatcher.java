package old.tractorDevices.tractorPlatform.models.event;

import java.util.ArrayList;

public class TractorEventsDispatcher
{
	
	ArrayList<ITractorListener> _listenersList = new ArrayList<>();
	
	
	public void addEventListener(ITractorListener listener)
	{
		_listenersList.add(listener);
	}
	
	
	public void dispatchEvent(TractorEvent event)
	{
		
		for (ITractorListener listener : _listenersList)
		{
			
			switch (event.getType())
			{
			case TractorEvent.ENGINE_STATE_CHANGE:
				listener.onEngineStateChange(event);
				break;
				
			case TractorEvent.SPEED_CHANGE:
				listener.onSpeedChange(event);
				
			case TractorEvent.TURN_CHANGE:
				listener.onTurnChange(event);
				
			case TractorEvent.MOTION_STATE_CHANGE:
				listener.onMotionStaeChange(event);

			default:
				break;
			}
		}		
	}
	

}
