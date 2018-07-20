package old.tractorDevices.connections;

import old.gui.view.MainWindow2;
import old.tractorDevices.tractorPlatform.models.Tractor;
import ru.roboticsUMK.commandProtocol.ProtocolCommand;
import ru.roboticsUMK.commandProtocol.deviceConnection.IProtocolDeviceListener;
import ru.roboticsUMK.commandProtocol.deviceConnection.arduino.ArduinoCommand;
import ru.roboticsUMK.commandProtocol.deviceConnection.tractorPlatformControlDevice.ControlDeviceCommand;

public class IncomingCommandsController
{
	
	private TractorConnection _tractorConnection;
	private ControlDeviceConnection _controlDeviceConnection;
	
	private Tractor _tractor;
	
	private MainWindow2 _window;
	
	private boolean _isAndroidDeviceCommandsEnabled = true;
	
	
	public IncomingCommandsController(Tractor tractor, MainWindow2 window, TractorConnection tractorConnection, ControlDeviceConnection deviceConnection)
	{
		_tractor = tractor;
		
		_window = window;
		
		_tractorConnection = tractorConnection;
//		_tractorConnection.addCommandListener(new ArduinoCommandListener());
		
		_controlDeviceConnection = deviceConnection;

//		_controlDeviceConnection.addCommandListener(new DeviceCommandListener());
	}
	
	
	public void setAndroidDeviceCommandsEnable(boolean value)
	{
		_isAndroidDeviceCommandsEnabled = value;
	}
	
	
					///////////////////////
					///////Classes/////////
					///////////////////////
	
	
	private class DeviceCommandListener implements IProtocolDeviceListener
	{
		@Override
		public void onCommand(ProtocolCommand command)
		{
			if(!_isAndroidDeviceCommandsEnabled)
			{
//				_arduino.sendError(1, "The tractor is controlled from another device!");
				_controlDeviceConnection.sendDeviceConnectResult("The tractor is controlled from another device!");
				return;
			}
			
			switch (command.getType())
			{
			//Control device commands
			
			case ControlDeviceCommand.TYPE_DEVICE_CONNECT:
				
				if(!_tractorConnection.isConnected())
				{
//					_arduino.sendError(1, "Server is not connected to controller");
				}
				
				
				if(command.getIntegerData()==1)// connect command
				{
					System.out.println("Controll device connect");
					
					_controlDeviceConnection.sendDeviceConnectResult("Hello from tractor server");
					
//					_controlDeviceConnection.setIsConnected(true);
					
				}else//disconnect command
				{
					System.out.println("Controll device disconnect");
					
//					_controlDeviceConnection.setIsConnected(false);
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

		@Override
		public void onDisconnect()
		{
			
			
		}

		@Override
		public void onConnect()
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onError(Exception exception)
		{
			// TODO Auto-generated method stub
			
		}
	}
	
	
	
	private class ArduinoCommandListener implements IProtocolDeviceListener
	{

		@Override
		public void onCommand(ProtocolCommand command)
		{

			switch (command.getType())
			{
				//Arduino commands
				
				case ArduinoCommand.TYPE_ARDUINO_SERVER_CONNECT:
					
					System.out.println("Connect to arduino result - " + command.getStringData());
					
//					_tractorConnection.setIsConnected(true);
					
					_window.getTopPanel().showLogoutPanel(_tractorConnection.getPortName());
					
					break;
			}
		}

		@Override
		public void onDisconnect()
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onConnect()
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onError(Exception exception)
		{
			// TODO Auto-generated method stub
			
		}
	}

}
