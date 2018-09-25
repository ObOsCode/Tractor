package models.devices.robots.tractorPlatform.events;

import java.util.ArrayList;

import models.devices.DeviceBase;
import models.devices.DeviceType;
import ru.roboticsnt.commandProtocol.connections.ProtocolConnectionBase;
import ru.roboticsnt.events.Event;
import ru.roboticsnt.events.EventListener;
import ru.roboticsnt.events.IEventDispatcher;


public class TractorPlatformEventDispatcher extends DeviceBase implements IEventDispatcher
{

	private ArrayList<EventListener> _listenersList = new ArrayList<>();
	
	
	public TractorPlatformEventDispatcher(ProtocolConnectionBase connection, DeviceType type)
	{
		super(connection, type);
	}
	
	
	@Override
	public void addEventListener(EventListener listener)
	{
		_listenersList.add(listener);
	}
	
	
	@Override
	public void removeEventListener(EventListener listener)
	{
		_listenersList.remove(listener);
	}
	
	
	@Override
	public void dispatchEvent(Event event)
	{
		for (EventListener listener : _listenersList)
		{
			listener.onEvent(event);
		}
	}

}
