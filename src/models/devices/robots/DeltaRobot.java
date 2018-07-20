package models.devices.robots;

import models.devices.DeviceBase;
import models.devices.DeviceType;
import ru.roboticsnt.commandProtocol.connections.ProtocolConnnectionBase;

public class DeltaRobot extends DeviceBase
{

	public DeltaRobot(ProtocolConnnectionBase connection)
	{
		super(connection, DeviceType.DELTA);
	}

}
