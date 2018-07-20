package controllers.devices;

import models.MainModel;
import models.devices.controlDevices.ControlDevice;
import models.events.MainModelListener;
import ru.roboticsUMK.commandProtocol.ProtocolCommand;
import ru.roboticsUMK.commandProtocol.deviceConnection.ProtocolDeviceListener;

public class ControlDevicesController
{
	
	private MainModel _model;
	
	private ModelListener _modelListener;
	
	private ControlDeviceConnectionListener _connectonListener;
	
	private ControlDevice _selectedControlDevice;
	
	
	public ControlDevicesController(MainModel model)
	{
		this._model = model;
		
		_modelListener = new ModelListener();
		
		_connectonListener = new ControlDeviceConnectionListener();
		
		_model.addEventListener(_modelListener);
	}
	
	
	public void dispose()
	{
		_model.removeEventListener(_modelListener);
		
		if(_selectedControlDevice != null)
		{
			_selectedControlDevice.getConnection().removeListener(_connectonListener);				
		}
	}
	
	
	//////////
	//Private
	//////////
	
	//Classes
	
	private class ModelListener extends MainModelListener
	{
		@Override
		public void onSelectedControlDeviceChange()
		{
			super.onSelectedControlDeviceChange();
			
			System.out.println("onSelectedControlDeviceChange");
			
			if(_selectedControlDevice != null)
			{
				_selectedControlDevice.getConnection().removeListener(_connectonListener);				
			}
			
			_selectedControlDevice = _model.getSelectedControlDevice();
			
			_selectedControlDevice.getConnection().addListener(_connectonListener);
		}
	}
	
	
	private class ControlDeviceConnectionListener extends ProtocolDeviceListener
	{
		@Override
		public void onCommand(ProtocolCommand command)
		{
			super.onCommand(command);
			
			System.out.println("command.getData() - " + command.getData());
			System.out.println("command.getStringData() - " + command.getStringData());
		}
	}
}
