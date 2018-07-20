package controllers;

import models.MainModel;
import models.devices.robots.tractorPlatform.TractorPlatform;
import models.events.MainModelListener;
import ru.roboticsnt.commandProtocol.ProtocolCommand;
import ru.roboticsnt.commandProtocol.commands.ControlDeviceCommand;
import ru.roboticsnt.commandProtocol.connections.ProtocolConnectionListener;
import ru.roboticsnt.commandProtocol.connections.ProtocolConnnectionBase;

public class ControlDeviceController extends AbstractController
{
	
	private MainModel _model;
	
	private ProtocolConnnectionBase _deviceConnection;

	private ControlDeviceListener _controlDeviceCommandsListener;


	public ControlDeviceController(MainModel model)
	{
		this._model = model;
		
		_controlDeviceCommandsListener = new ControlDeviceListener();
		
		_model.addEventListener(new MainModelListener()
		{
			@Override
			public void onControlDeviceChange()
			{
				
				super.onControlDeviceChange();
				
				if (_deviceConnection != null)
				{
					_deviceConnection.removeEventListener(_controlDeviceCommandsListener);
				}
				
				if (_model.getControlDevice() == null)
				{
					return;
				}
				
				System.out.println("Control device controller. Control new device - " + _model.getControlDevice());
				
				_deviceConnection = _model.getControlDevice().getConnection();
				
				_deviceConnection.addEventListener(_controlDeviceCommandsListener);
			}
		});
	}


	@Override
	public void dispose()
	{
		if (_deviceConnection != null)
		{
			_deviceConnection.removeEventListener(_controlDeviceCommandsListener);
		}
	}
	
	
	private class ControlDeviceListener extends ProtocolConnectionListener
	{
		@Override
		public void onCommand(ProtocolCommand command)
		{
			super.onCommand(command);
			
			TractorPlatform platform = ControlDeviceController.this._model.getPlatform();
			
			if (platform == null)
			{
				return;
			}
			
			switch (command.getType())
			{
			
			case ControlDeviceCommand.TYPE_SET_POWER:
				platform.setPower(command.getIntegerData());
				break;
				
			case ControlDeviceCommand.TYPE_SET_TURN:
				platform.setTurn(command.getIntegerData());
				break;
				
			case ControlDeviceCommand.TYPE_START_ENGINE:
				
				if(command.getIntegerData() == 1)
				{
					platform.startEngine();
				}
				else
				{
					platform.stopEngine();
				}
				
				break;

			default:
				break;
			}
		}
	}

}
