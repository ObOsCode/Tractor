package old.tractorDevices.connections;

import ru.roboticsUMK.commandProtocol.ProtocolCommand;
import ru.roboticsUMK.commandProtocol.deviceConnection.ProtocolDeviceBase;
import ru.roboticsUMK.commandProtocol.deviceConnection.tractorPlatformControlDevice.ControlDeviceCommand;

public class ControlDeviceConnection extends ProtocolDeviceBase
{

	public ControlDeviceConnection()
	{
			
	}
	
	public void sendDeviceConnectResult(String resultData)
	{
		super.sendCommand(new ProtocolCommand(ControlDeviceCommand.TYPE_DEVICE_CONNECT_RESULT, resultData));
	}

}
