package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import gui.DeviceListPanel;
import gui.MainWindow;
import models.MainModel;
import models.devices.DeviceBase;
import models.devices.DeviceType;
import models.devices.controlDevices.ControlDevice;
import models.devices.robots.DeltaRobot;
import models.devices.robots.tractorPlatform.TractorPlatform;
import ru.roboticsnt.commandProtocol.connections.ArduinoProtocolConnection;
import ru.roboticsnt.commandProtocol.connections.ProtocolConnectionBase;
import ru.roboticsnt.commandProtocol.connections.ProtocolConnectionListener;
import ru.roboticsnt.commandProtocol.connections.SocketProtocolConnection;
import ru.roboticsnt.serial.SerialPortManager;
import ru.roboticsnt.socketServer.SocketServer;
import ru.roboticsnt.socketServer.SocketServer.SocketClient;
import ru.roboticsnt.socketServer.SocketServerListener;


public class DevicesListController extends AbstractController
{
	private final int _UPDATE_PORTS_DELAY = 2500;//milliseconds
	
	private MainModel _model;
	private MainWindow _window;
	
	private SocketServer _socketServer;
	private Timer _updateTimer;
	
	private boolean _isConnectedToSerialNow = false;//true when try connect to device
	
	
	public DevicesListController(MainModel model, MainWindow window)
	{
		this._model = model;
		this._window = window;
		
		final DeviceListPanel devicesPanel = _window.getDeviceListPanel();
		
		JButton addDeviceButton = devicesPanel.getAddDeviceButton();
		
		addDeviceButton.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				ProtocolConnectionBase selectedConnection = devicesPanel.getSelectedConnection();

				DeviceType selectedType = DeviceType.getByIndex(devicesPanel.getSelectedTypeIndex());
				
				DeviceBase device = null;
				
				if(selectedType.equals(DeviceType.PLATFORM))
				{
					device = new TractorPlatform(selectedConnection);
				}
				else if(selectedType.equals(DeviceType.DELTA))
				{
					device = new DeltaRobot(selectedConnection);
				}
				else if(selectedType.equals(DeviceType.CONTROL_ANDROID_SMARTPHONE))
				{
					device = new ControlDevice(selectedConnection, selectedType);
				}
				else if(selectedType.equals(DeviceType.CONTROL_ARDUINO))
				{
					device = new ControlDevice(selectedConnection, selectedType);
				}
				
				_model.addDevice(device);
				_model.removeConnection(device.getConnection());
			}
		});
		
		JButton removeDeviceButton = devicesPanel.getRemoveDeviceButton();
		
		removeDeviceButton.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				DeviceBase device = devicesPanel.getSelectedDevice();
				_model.addConnection(device.getConnection());
				_model.removeDevice(device);
			}
		});
		
		JList<DeviceBase> devicesList = devicesPanel.getDevicesList();
		
		devicesList.addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				if(!e.getValueIsAdjusting())
				{
					_model.setSelectedDevice(devicesPanel.getDevicesList().getSelectedValue());
				}
			}
		});
		
		final JComboBox<DeviceBase> controlDevicesComBox = devicesPanel.getControlDevicesComBox();

		controlDevicesComBox.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				DeviceBase device = (DeviceBase) controlDevicesComBox.getSelectedItem();

				_model.setControlDevice(device);
			}
		});
		
		startSocketServer();
		startUpdateSerialPortsTimer();
	}
	
	
	@Override
	public void dispose()
	{
		stopSocketServer();
		stopUpdatePortsTimer();
	}
	
	
	//////////
	//Private
	//////////
	
	
	private void startUpdateSerialPortsTimer()
	{
		System.out.println("Start listen serial ports connections.");
		
		_updateTimer = new Timer();
		
		TimerTask updateTask = new TimerTask()
		{
			
//			private int _portIndex = 0;
			
			@Override
			public void run()
			{
				if(_isConnectedToSerialNow)
				{
					return;
				}
				
				String[] portNames = SerialPortManager.getPortNames();
				
//				if(_portIndex >= portNames.length)
//				{
//					_portIndex = 0;
//				}

				for (int i = 0; i < portNames.length; i++)
				{
					String portName = portNames[i];
					
					if(!_model.isConnectionWithAdressExist(portName))
					{
						System.out.println("New serial port connection on port " + portName);
						
						_isConnectedToSerialNow = true;
						
						ArduinoProtocolConnection connection = new ArduinoProtocolConnection(portName);
						addConnection(connection);
						return;
					}
				}
			}
		};
		
		_updateTimer.schedule(updateTask, 0, _UPDATE_PORTS_DELAY);
	}
	
	
	private void stopUpdatePortsTimer()
	{
		System.out.println("Stop listen serial ports connection.");
		_updateTimer.cancel();
		_updateTimer = null;
	}
	
	
	private void startSocketServer()
	{
		_socketServer = new SocketServer(SocketProtocolConnection.CONNECT_DEVICES_SERVER_PORT);
		
		_socketServer.addEventListener(new SocketServerListener()
		{
			@Override
			public void onClientConnect(SocketClient client)
			{
				super.onClientConnect(client);
				
				if(!_model.isConnectionWithAdressExist(client.getIPAdress()))
				{
					System.out.println("New socket server connection");
					SocketProtocolConnection connection = new SocketProtocolConnection(client.getSocket());
					addConnection(connection);
				}
			}
		});
		
		_socketServer.start();
	}
	
	
	private void stopSocketServer()
	{
		_socketServer.stop();
	}
	
	
	private void addConnection(final ProtocolConnectionBase connection)
	{
		
		connection.addEventListener(new ProtocolConnectionListener()
		{
			
			@Override
			public void onConnect()
			{
				super.onConnect();
				
				System.out.println("New connection on " + connection.getAdress());
				
				_model.addConnection(connection);
				
				_isConnectedToSerialNow = false;
			}
			
			
			@Override
			public void onError(Exception exception)
			{
				super.onError(exception);
				
				System.out.println("Connection error:  " + exception.getMessage());
				
				_isConnectedToSerialNow = false;
			}
			
			
			@Override
			public void onDisconnect()
			{
				super.onDisconnect();
				
				System.out.println("Disconnect on " + connection.getAdress());
				
				_model.removeConnection(connection);
			}
		});
		
		connection.connect();
	}
}
