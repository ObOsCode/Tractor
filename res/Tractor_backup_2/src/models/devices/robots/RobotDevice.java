package models.devices.robots;

import models.devices.DeviceBase;
import ru.roboticsUMK.commandProtocol.deviceConnection.ProtocolDeviceBase;


public class RobotDevice extends DeviceBase
{
	
	
	public RobotDevice(ProtocolDeviceBase connection, RobotType type)
	{
		super(connection, type);
	}
	
}
