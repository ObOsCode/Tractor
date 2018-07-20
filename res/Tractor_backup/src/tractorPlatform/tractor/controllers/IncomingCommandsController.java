package tractorPlatform.tractor.controllers;

import robotics.protocol.CommandListener;
import robotics.protocol.ProtocolCommand;
import robotics.protocol.tractor.ArduinoCommand;
import robotics.protocol.tractor.ControlDeviceCommand;
import tractorPlatform.connections.ArduinoConnection;
import tractorPlatform.connections.ControlDeviceConnection;
import tractorPlatform.tractor.models.Tractor;
import view.MainWindow;

public class IncomingCommandsController
{
	
	private ArduinoConnection _arduino;
	private ControlDeviceConnection _controlDevice;
	
	private Tractor _tractor;
	
	private MainWindow _window;
	
	private boolean _isAndroidDeviceCommandsEnabled = true;
	
	
	public IncomingCommandsController(Tractor tractor, MainWindow window, ArduinoConnection arduinoConnection, ControlDeviceConnection deviceConnection)
	{
		_tractor = tractor;
		
		_window = window;
		
		_arduino = arduinoConnection;
		_arduino.addCommandListener(new ArduinoCommandListener());
		
		_controlDevice = deviceConnection;

		_controlDevice.addCommandListener(new DeviceCommandListener());
	}
	
	
	public void setAndroidDeviceCommandsEnable(boolean value)
	{
		_isAndroidDeviceCommandsEnabled = value;
	}
	
	
					///////////////////////
					///////Classes/////////
					///////////////////////
	
	
	private class DeviceCommandListener implements CommandListener
	{
		@Override
		public void onCommand(ProtocolCommand command)
		{
			if(!_isAndroidDeviceCommandsEnabled)
			{
//				_arduino.sendError(1, "The tractor is controlled from another device!");
				_controlDevice.sendDeviceConnectResult("The tractor is controlled from another device!");
				return;
			}
			
			switch (command.getType())
			{
			//Control device commands
			
			case ControlDeviceCommand.TYPE_DEVICE_CONNECT:
				
				if(!_arduino.isConnected())
				{
//					_arduino.sendError(1, "Server is not connected to controller");
				}
				
				
				if(command.getIntegerData()==1)// connect command
				{
					System.out.println("Controll device connect");
					
					_controlDevice.sendDeviceConnectResult("Hello from tractor server");
					
					_controlDevice.setIsConnected(true);
					
				}else//disconnect command
				{
					System.out.println("Controll device disconnect");
					
					_controlDevice.setIsConnected(false);
				}

				break;
				
			case ControlDeviceCommand.TYPE_START_ENGINE:
				
				if(command.getIntegerData()==1)
				{
					_tractor.startEngine();
				}else
				{
					_tractor.stopEngine();
				}
				
				break;
				
			case ControlDeviceCommand.TYPE_SET_POWER:
				
//				if(!_tractor.isEngineStarted())
//				{
////					_arduino.sendError("Engine is not running");
//					return;
//				}

				_tractor.setPower(command.getIntegerData());
		
				break;
				
				
			case ControlDeviceCommand.TYPE_SET_TURN:
				
//				if(!_tractor.isEngineStarted())
//				{
////					_arduino.sendError("Engine is not running");
//					return;
//				}
				
				_tractor.setTurn(command.getIntegerData());
				
				break;
			
			}
		}
	}
	
	
	
	private class ArduinoCommandListener implements CommandListener
	{

		@Override
		public void onCommand(ProtocolCommand command)
		{

			switch (command.getType())
			{
				//Arduino commands
				
				case ArduinoCommand.TYPE_ARDUINO_SERVER_CONNECT_RESULT:
					
					System.out.println("Connect to arduino result - " + command.getIntegerData());
					
					_arduino.setIsConnected(true);
					
					_window.getTopPanel().showLogoutPanel(_arduino.getPortName());
					
					break;
			}
		}
	}

}
