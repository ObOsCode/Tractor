package models;

import java.util.ArrayList;

import models.devices.DeviceBase;
import models.devices.DeviceType;
import models.devices.robots.tractorPlatform.PlatformTrack;
import models.devices.robots.tractorPlatform.TractorPlatform;
import models.events.MainModelEvent;
import ru.roboticsnt.commandProtocol.connections.ProtocolConnnectionBase;
import ru.roboticsnt.events.EventDispatcher;


public class MainModel extends EventDispatcher
{
	
	private ArrayList<DeviceBase> _devicesList = new ArrayList<>();
	private ArrayList<ProtocolConnnectionBase> _connectionsList = new ArrayList<>();
	
	private DeviceBase _selectedDevice = null;
	private DeviceBase _controlDevice = null;
	
	private TractorPlatform _platform = null;
	
	private ArrayList<PlatformTrack> _trackList = new ArrayList<>();


	public TractorPlatform getPlatform()
	{
		return _platform;
	}
	
	
	public void setControlDevice(DeviceBase device)
	{
		if(_controlDevice != null &&_controlDevice.equals(device))
		{
			return;
		}
		
		_controlDevice = device;
		
		dispatchEvent(new MainModelEvent(MainModelEvent.SELECTED_CONTROL_DEVICE_CHANGE));
	}
	
	
	public DeviceBase getControlDevice()
	{
		return _controlDevice;
	}
	
	
	public void setSelectedDevice(DeviceBase device)
	{
		_selectedDevice = device;
		
		dispatchEvent(new MainModelEvent(MainModelEvent.SELECTED_DEVICE_CHANGE));
	}
	
	
	public DeviceBase getSelectedDevice()
	{
		return _selectedDevice;
	}
	
	
	public ArrayList<ProtocolConnnectionBase> getConnectionsList()
	{
		return _connectionsList;
	}
	
	
	public ArrayList<DeviceBase> getDevicesList()
	{
		return _devicesList;
	}
	
	
	public void addDevice(DeviceBase device)
	{
		
		if(device.getType().equals(DeviceType.PLATFORM))
		{
			_platform = (TractorPlatform) device;
			dispatchEvent(new MainModelEvent(MainModelEvent.PLATFORM_CONNECT));
		}
		
		_devicesList.add(device);
		
		dispatchEvent(new MainModelEvent(MainModelEvent.DEVICE_LIST_CHANGE));
	}
	
	
	public void removeDevice(DeviceBase device)
	{
		
		if(device.getType().equals(DeviceType.PLATFORM))
		{
			_platform = null;
			dispatchEvent(new MainModelEvent(MainModelEvent.PLATFORM_DISCONNECT));
		}
		
		_devicesList.remove(device);
		
		dispatchEvent(new MainModelEvent(MainModelEvent.DEVICE_LIST_CHANGE));
	}
	
	
	public void addConnection(ProtocolConnnectionBase connection)
	{
		_connectionsList.add(connection);
		dispatchEvent(new MainModelEvent(MainModelEvent.CONNECTIONS_LIST_CHANGE));
	}
	
	
	public void removeConnection(ProtocolConnnectionBase connection)
	{
	
		if(_connectionsList.contains(connection))
		{
			_connectionsList.remove(connection);
			dispatchEvent(new MainModelEvent(MainModelEvent.CONNECTIONS_LIST_CHANGE));
			return;
		}
		
		for (DeviceBase device : _devicesList)
		{
			if(device.getConnection().equals(connection))
			{
				removeDevice(device);
				return;
			}
		}
		
	}
	
	
	/**
	 * @param connectionAdress Serial port name or IP adress
	 */
	public boolean isConnectionWithAdressExist(String connectionAdress)
	{
		
		for (ProtocolConnnectionBase connection : _connectionsList)
		{
			if(connection.getAdress().equals(connectionAdress))
			{
				return true;
			}
		}
		
		
		for (DeviceBase device : _devicesList)
		{
			ProtocolConnnectionBase connection = device.getConnection();
			
			if(connection.getAdress().equals(connectionAdress))
			{
				return true;
			}
		}
		
		return false;
	}
	
	
	//Tracks
	public ArrayList<PlatformTrack> getTrackList()
	{
		return _trackList;
	}
	
	
	public void addTrack(PlatformTrack track)
	{
		_trackList.add(track);
		dispatchEvent(new MainModelEvent(MainModelEvent.TRACK_LIST_CHANGE));
	}
	
	
	public void removeTrack(PlatformTrack track)
	{
		_trackList.remove(track);
		dispatchEvent(new MainModelEvent(MainModelEvent.TRACK_LIST_CHANGE));
	}


}
