package controllers.devices;

import models.devices.robots.platform.TractorConnection;
import models.devices.robots.platform.TractorPlatform;

public class TractorPlatformController extends DeviceControllerBase
{
	
	private TractorPlatform _tractor;
	
	private TractorConnection _connection;
	

	public TractorPlatformController(String portName)
	{
		super(new TractorPlatform(new TractorConnection(portName)));
		
		_tractor = (TractorPlatform) super.getDevice();
		
		_connection = (TractorConnection) _tractor.getConnection();

	}

}
