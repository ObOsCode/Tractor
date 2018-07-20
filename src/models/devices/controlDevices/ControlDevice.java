package models.devices.controlDevices;

import models.devices.DeviceBase;
import models.devices.DeviceType;
import ru.roboticsnt.commandProtocol.connections.ProtocolConnnectionBase;

public class ControlDevice extends DeviceBase
{

	public ControlDevice(ProtocolConnnectionBase connection, DeviceType type)
	{
		super(connection, type);
	}

}
