package models.devices;

import ru.roboticsnt.commandProtocol.connections.ProtocolConnectionBase;

public abstract class DeviceBase
{

	private DeviceType _deviceType;
	
	private ProtocolConnectionBase _connection;
	
	
	public DeviceBase(ProtocolConnectionBase connection, DeviceType type)
	{
		_connection = connection;
		_deviceType = type;
	}
	
	
	public DeviceType getType()
	{
		return _deviceType;
	}
	
	
	public String getName()
	{
		return _deviceType.getName() + " (" + _connection.getAdress() + ")";
	}
	
	
	public ProtocolConnectionBase getConnection()
	{
		return _connection;
	}
	
	
	//Override to show in gui elements(list, comboBox...)
	@Override
	public String toString()
	{
		return this.getName();
	}

}
