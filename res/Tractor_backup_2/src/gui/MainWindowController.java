package gui;

import controllers.DeviceListController;
import controllers.devices.ControlDevicesController;
import models.MainModel;


public class MainWindowController
{
	private MainModel _model;
	
	private DeviceListController _deviceListController;
	
	private ControlDevicesController _controlDevicesController;
	
	
	public MainWindowController(MainModel model, MainWindow window)
	{
		_model = model;
		
		_deviceListController = new DeviceListController(_model, window);
		
		_controlDevicesController = new ControlDevicesController(_model);
	}
	
	
	public void dispose()
	{
		_deviceListController.dispose();
		
		_controlDevicesController.dispose();
	}

}
