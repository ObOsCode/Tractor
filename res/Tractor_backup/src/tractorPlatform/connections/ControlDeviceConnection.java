package tractorPlatform.connections;

import robotics.protocol.ProtocolCommand;
import robotics.protocol.tractor.ControlDeviceCommand;

public class ControlDeviceConnection extends CommandsConnectionBase
{

	public ControlDeviceConnection()
	{
			
	}
	
	public void sendDeviceConnectResult(String resultData)
	{
		super.sendCommand(new ProtocolCommand(ControlDeviceCommand.TYPE_DEVICE_CONNECT_RESULT, resultData));
	}

}
