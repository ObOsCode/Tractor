package old.gui;

import old.tractorDevices.connections.DeltaRobotConnection;
import ru.roboticsUMK.serial.SerialPortManager;


public class MainWindowController
{

	public MainWindowController(MainWindow window)
	{
		DeltaRobotConnection deltaCon = new DeltaRobotConnection(SerialPortManager.LINUX_USB_0);
		
		
		
//		IncomingCommandsController _commandsController = new IncomingCommandsController(_tractor, _window, tractorConnection, _controlDeviceConnection);
	}

}
