package models.devices.robots;

import models.devices.DeviceBase;
import models.devices.DeviceType;
import ru.roboticsnt.commandProtocol.connections.ProtocolConnectionBase;

public class DeltaRobot extends DeviceBase
{

	public DeltaRobot(ProtocolConnectionBase connection)
	{
		super(connection, DeviceType.DELTA);
	}

}
