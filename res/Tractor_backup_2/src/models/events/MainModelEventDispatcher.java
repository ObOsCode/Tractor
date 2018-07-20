package models.events;

import java.util.ArrayList;


public class MainModelEventDispatcher
{
	
	ArrayList<MainModelListener> _listenersList = new ArrayList<>();
	

	public void addEventListener(MainModelListener listener)
	{
		_listenersList.add(listener);
	}
	
	
	public void removeEventListener(MainModelListener listener)
	{
		if(_listenersList.contains(listener))
		{
			_listenersList.remove(listener);
		}
	}
	
	
	public void dispatchEvent(MainModelEvet event)
	{
		for (MainModelListener listener : _listenersList)
		{
			
			switch (event.getType())
			{
			
			case MainModelEvet.ROBOTS_LIST_CHANGE:
				listener.onRobotsListChange();
				break;
				
			case MainModelEvet.SELECTED_ROBOT_CHANGE:
				listener.onSelectedRobotChange();
				break;
				
			case MainModelEvet.SELECTED_CONTROL_DEVICE_CHANGE:
				listener.onSelectedControlDeviceChange();
				break;
				
			case MainModelEvet.CONTROL_DEVICES_LIST_CHANGE:
				listener.onControlDevicesListChange();
				break;
				
			default:
				break;
			}
		}		
	}

}
