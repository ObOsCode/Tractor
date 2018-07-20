package models.events;

import ru.roboticsnt.events.Event;
import ru.roboticsnt.events.EventListener;

public class MainModelListener extends EventListener
{


	public void onDevicesListChange()
	{
		
	}
	

	public void onControlDeviceChange()
	{
		
	}


	public void onConnectionsListChange()
	{

	}

	
	public void onSelectedDeviceChange()
	{

	}
	
	
	public void onPlatformConnect()
	{
		
	}
	
	
	public void onPlatformDisconnect()
	{
		
	}
	
	
	public void onTrackListChange()
	{
		
	}

	
	@Override
	public void onEvent(Event event)
	{
		switch (event.getType())
		{
			
		case MainModelEvent.SELECTED_CONTROL_DEVICE_CHANGE:
			onControlDeviceChange();
			break;
			
		case MainModelEvent.DEVICE_LIST_CHANGE:
			onDevicesListChange();
			break;
			
		case MainModelEvent.CONNECTIONS_LIST_CHANGE:
			onConnectionsListChange();
			break;
			
		case MainModelEvent.SELECTED_DEVICE_CHANGE:
			onSelectedDeviceChange();
			break;
			
		case MainModelEvent.PLATFORM_CONNECT:
			onPlatformConnect();
			break;
			
		case MainModelEvent.PLATFORM_DISCONNECT:
			onPlatformDisconnect();
			break;
			
		case MainModelEvent.TRACK_LIST_CHANGE:
			onTrackListChange();
			break;
			
		default:
			break;
		}
		
	}

}
