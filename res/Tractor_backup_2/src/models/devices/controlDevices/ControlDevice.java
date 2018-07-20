package models.devices.controlDevices;

import models.devices.DeviceBase;
import ru.roboticsUMK.commandProtocol.deviceConnection.ProtocolDeviceBase;


public class ControlDevice extends DeviceBase
{

	
	public ControlDevice(ProtocolDeviceBase connection, ControlDeviceType type)
	{
		super(connection, type);
	}

}
