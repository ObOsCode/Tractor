package models.devices.controlDevices;

import models.devices.DeviceBase;
import models.devices.DeviceType;
import ru.roboticsnt.commandProtocol.connections.ProtocolConnectionBase;

public class ControlDevice extends DeviceBase
{

	public ControlDevice(ProtocolConnectionBase connection, DeviceType type)
	{
		super(connection, type);
	}

}
