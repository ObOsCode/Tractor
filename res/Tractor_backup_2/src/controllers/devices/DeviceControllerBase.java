package controllers.devices;

import models.devices.DeviceBase;
import ru.roboticsUMK.commandProtocol.ProtocolCommand;
import ru.roboticsUMK.commandProtocol.deviceConnection.ProtocolDeviceBase;
import ru.roboticsUMK.commandProtocol.deviceConnection.ProtocolDeviceListener;
import ru.roboticsUMK.commandProtocol.deviceConnection.arduino.ArduinoCommand;

public abstract class DeviceControllerBase
{
	
	private DeviceBase _device;

	
	public DeviceControllerBase(DeviceBase device)
	{
		_device = device;
		
		_device.getConnection().addListener(new ProtocolDeviceListener(){
			
			@Override
			public void onCommand(ProtocolCommand command)
			{
				super.onCommand(command);
				
				switch (command.getType())
				{
				case ArduinoCommand.TYPE_ARDUINO_ERROR:
					System.out.println("Error: " + command.getStringData());
					break;

				default:
					break;
				}
			}
		});
	}
	
	
	public DeviceBase getDevice()
	{
		return _device;
	}
	
	
	public void dispose()
	{
		ProtocolDeviceBase connection = _device.getConnection();
		
		if(connection != null)
		{
			connection.close();
		}
	}
	

}
