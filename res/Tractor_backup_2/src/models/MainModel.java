package models;

import java.util.ArrayList;
import java.util.List;

import models.devices.controlDevices.ControlDevice;
import models.devices.robots.RobotDevice;
import models.devices.robots.RobotType;
import models.devices.robots.platform.TractorPlatform;
import models.events.MainModelEventDispatcher;
import models.events.MainModelEvet;
import ru.roboticsUMK.commandProtocol.deviceConnection.arduino.ArduinoDevice;
import ru.roboticsUMK.serial.SerialPortManager;

public class MainModel extends MainModelEventDispatcher
{
	
	private ArrayList<RobotDevice> _robotsList = new ArrayList<>();
	
	private RobotDevice _selectedRobot;
	
	private ArrayList<ControlDevice> _controlDevicesList = new ArrayList<>();
	
	private ControlDevice _selectedControlDevice;
	
	private TractorPlatform _tractor;
	
	
	public TractorPlatform getTractorPlatform()
	{
		return _tractor;
	}
	
	
	//////////////////
	//Control devices
	//////////////////
	
	
	public void addControlDevice(ControlDevice device)
	{
		_controlDevicesList.add(device);
		dispatchEvent(new MainModelEvet(MainModelEvet.CONTROL_DEVICES_LIST_CHANGE));
	}
	
	
	public void removeControlDevice(ControlDevice device)
	{
		_controlDevicesList.remove(device);
		dispatchEvent(new MainModelEvet(MainModelEvet.CONTROL_DEVICES_LIST_CHANGE));
	}
	
	
	public void setSelectedControlDevice(int index)
	{
		_selectedControlDevice = _controlDevicesList.get(index);
		dispatchEvent(new MainModelEvet(MainModelEvet.SELECTED_CONTROL_DEVICE_CHANGE));
	}
	
	
	public ControlDevice getSelectedControlDevice()
	{
		return _selectedControlDevice;
	}
	
	
	public ArrayList<ControlDevice> getControlDevicesList()
	{
		return _controlDevicesList;
	}
	
	
	/////////
	//Robots
	/////////
	
	
	public void addRobot(RobotDevice robot)
	{
		if(robot.getType().equals(RobotType.PLATFORM))
		{
			_tractor = (TractorPlatform) robot;
		}
		_robotsList.add(robot);
		dispatchEvent(new MainModelEvet(MainModelEvet.ROBOTS_LIST_CHANGE));
	}
	
	
	public void removeRobot(RobotDevice robot)
	{
		if(robot.getType().equals(RobotType.PLATFORM))
		{
			_tractor = null;
		}
		
		_robotsList.remove(robot);
		dispatchEvent(new MainModelEvet(MainModelEvet.ROBOTS_LIST_CHANGE));
	}
	
	
	public void setSelectedRobot(int index)
	{
		if(index<0)
		{
			_selectedRobot = null;
		}else
		{
			_selectedRobot = _robotsList.get(index);
		}
		
		dispatchEvent(new MainModelEvet(MainModelEvet.SELECTED_ROBOT_CHANGE));
	}
	
	
	public RobotDevice getSelectedRobot()
	{
		return _selectedRobot;
	}
	
	
	public ArrayList<RobotDevice> getRobotsList()
	{
		return _robotsList;
	}
	
	
	public List<String> getFreePortsList()
	{
		List<String> result = new ArrayList<>();
		
		String[] allPorts = SerialPortManager.getPortNames();
		
		for (int i = 0; i < allPorts.length; i++)
		{
			String port = allPorts[i];
			
			boolean isPortBusy = false;
			
			for (RobotDevice device : _robotsList)
			{
				ArduinoDevice deviceConnection = (ArduinoDevice) device.getConnection();
				
				if(deviceConnection.getPortName().equals(port))
				{
					isPortBusy = true;
					break;
				}
			}
			
			if(!isPortBusy)
			{
				result.add(port);
			}
		}

		return result;
	}

}
