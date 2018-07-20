package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controllers.devices.DeltaRobotController;
import controllers.devices.DeviceControllerBase;
import controllers.devices.TractorPlatformController;
import gui.DeviceListPanel;
import gui.MainWindow;
import models.MainModel;
import models.devices.controlDevices.ControlDevice;
import models.devices.controlDevices.ControlDeviceType;
import models.devices.robots.RobotDevice;
import models.devices.robots.RobotType;
import ru.roboticsUMK.commandProtocol.deviceConnection.ProtocolDeviceBase;
import ru.roboticsUMK.commandProtocol.deviceConnection.ProtocolDeviceListener;
import ru.roboticsUMK.commandProtocol.deviceConnection.tractorPlatformControlDevice.WiFiConnection;

public class DeviceListController 
{
	private MainModel _model;
	
	private DeviceListPanel _panel;
	
	private MainWindow _window;
	
	private ArrayList<DeviceControllerBase> _robotControllersList = new ArrayList<>();


	public DeviceListController(MainModel model, MainWindow window)
	{
		this._model = model;
		this._window = window;
		this._panel = _window.getDevicesPanel();
		
		JButton addRobotButton = _panel.getAddRobotButton();
		addRobotButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				RobotDevice robot = addRobot();
				ProtocolDeviceBase connection = robot.getConnection();
				connection.addListener(new RobotsConnectionListener(robot));
				
				_panel.showPreloaderPanel();
				
				Thread connectThread = new Thread(new Runnable()
				{
					@Override
					public void run()
					{
						connection.connect();
					}
				});
				
				connectThread.start();
			}
		});
		
		JButton removeRobotButton = _panel.getRemoveRobotButton();
		removeRobotButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				removeRobot(_panel.getRobotsList().getSelectedIndex());
			}
		});
		
		JList<String> robotsList = _panel.getRobotsList();
		robotsList.addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				if(!e.getValueIsAdjusting())
				{
					selectRobot();
				}
			}
		});
		
		JList<String> controlDevicesList = _panel.getControlDevicesList();
		controlDevicesList.addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				if(!e.getValueIsAdjusting())
				{
					selectControlDevice();
				}
			}
		});
		
		
		WIFISocketServer socketServer = new WIFISocketServer(5555);
		socketServer.start();
	}
	
	
	public void dispose()
	{
		_panel.dispose();
		removeAllRobots();
	}
	
	
	///////////
	//Private
	///////////
	
	
	private void addControlDevice(ControlDevice device)
	{
		_model.addControlDevice(device);
	}
	
	
	private void selectControlDevice()
	{
		_model.setSelectedControlDevice(_panel.getSelectedControlDeviceIndex());
	}
	
	
	private RobotDevice addRobot()
	{
		RobotType newRobotType = RobotType.getByIndex(_panel.getSelectedTypeIndex());
		
		RobotDevice robot = null;
		
		if(newRobotType.equals(RobotType.DELTA))
		{
			robot = createDeltaRobot();
		}
		
		if(newRobotType.equals(RobotType.PLATFORM))
		{
			robot = createTractorPlatform();
		}
		
		return robot;
	}
	
	
	private void selectRobot()
	{
		_model.setSelectedRobot(_panel.getSelectedRobotIndex());
	}
	
	
	private void removeRobot(int index)
	{
		DeviceControllerBase controller = _robotControllersList.get(index);
		
		controller.dispose();
	}
	
	
	private void removeAllRobots()
	{
		for (int i = 0; i < _robotControllersList.size(); i++)
		{
			removeRobot(i);
		}
	}
	
	
	private RobotDevice createDeltaRobot()
	{
		DeltaRobotController controller = new DeltaRobotController(_panel.getSelectedPort());
		controller.enableManualControl(_panel);
		_robotControllersList.add(controller);
		
		return (RobotDevice) controller.getDevice();
	}
	
	
	private RobotDevice createTractorPlatform()
	{
		TractorPlatformController controller = new TractorPlatformController(_panel.getSelectedPort());
		_robotControllersList.add(controller);
		
		return (RobotDevice) controller.getDevice();
	}
	
	
	//////////
	//Class
	//////////
	
	private class WIFISocketServer
	{
		
		private ServerSocket _server;
		
		private int _port;
		
		
		public WIFISocketServer(int port)
		{
			_port = port;
		}
		
		
		public void start()
		{
			Thread serverThread = new Thread(new Runnable()
			{
	
				@Override
				public void run()
				{
					try
					{
						_server = new ServerSocket(_port);
						
						while (!_server.isClosed())
						{
							try
							{
								
								Socket client = _server.accept();
								
								while (!client.isClosed())
								{
//									addControlDevice(new ControlDevice(new WiFiConnection(client), ControlDeviceType.CONTROL_ANDROID_WIFI));
								}
								
								
							} catch (IOException e)
							{
								e.printStackTrace();
							}
						}
						
					} catch (IOException e)
					{
						e.printStackTrace();
					}
				}
			});
			
			serverThread.start();
		}
		
		
		public void stop()
		{
			try
			{
				_server.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	
	private class RobotsConnectionListener extends ProtocolDeviceListener
	{
		
		private RobotDevice _robot;
		
		
		public RobotsConnectionListener(RobotDevice robot)
		{
			this._robot = robot;
		}
		

		@Override
		public void onConnect()
		{
			super.onConnect();
			
			_model.addRobot(this._robot);
			
			_panel.showAddDeviceFormPanel();
			
			if(_robot.getType().equals(RobotType.PLATFORM))
			{
				ControlDevice newControlDevice = new ControlDevice(_robot.getConnection(), ControlDeviceType.CONTROL_ARDUINO_BT);
				addControlDevice(newControlDevice);
			}
		}
		
		
		@Override
		public void onDisconnect()
		{
			super.onDisconnect();
			
			_model.removeRobot(this._robot);
			
			for (DeviceControllerBase controller : _robotControllersList)
			{
				if(controller.getDevice().equals(this._robot))
				{
					_robotControllersList.remove(controller);
					break;
				}
			}
		}
		
		
		@Override
		public void onError(Exception exception)
		{
			super.onError(exception);
			
			_panel.showMessage(exception.getMessage());
			
			_robot.getConnection().close();
			
			_panel.showAddDeviceFormPanel();
		}
			
	}
	
}
