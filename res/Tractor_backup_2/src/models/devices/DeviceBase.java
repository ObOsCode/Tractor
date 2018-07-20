package models.devices;

import ru.roboticsUMK.commandProtocol.deviceConnection.ProtocolDeviceBase;


public abstract class DeviceBase
{
	
	private DeviceTypeBase _deviceType;
	
	private ProtocolDeviceBase _connection;
	
	
	public DeviceBase(ProtocolDeviceBase connection, DeviceTypeBase type)
	{
		_connection = connection;
		_deviceType = type;
	}
	
	
	public DeviceTypeBase getType()
	{
		return _deviceType;
	}
	
	
	public String getName()
	{
		return ((DeviceTypeBase) _deviceType).getName();
	}
	
	
	public ProtocolDeviceBase getConnection()
	{
		return _connection;
	}

}
