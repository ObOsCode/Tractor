package old.gui.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;

import old.gui.view.MainWindow2;
import old.gui.view.TractorInfoPanel;
import old.tractorDevices.connections.ControlDeviceConnection;
import old.tractorDevices.connections.DeltaRobotConnection;
import old.tractorDevices.connections.IncomingCommandsController;
import old.tractorDevices.connections.TractorConnection;
import old.tractorDevices.tractorPlatform.controllers.KeyboardController;
import old.tractorDevices.tractorPlatform.controllers.TractorController;
import old.tractorDevices.tractorPlatform.models.Tractor;
import ru.roboticsUMK.commandProtocol.deviceConnection.arduino.ArduinoDevice;
import ru.roboticsUMK.serial.SerialPortManager;

public class MainWindowController2
{
	
	public static final int UPDATE_DELAY = 125;
	
	MainWindow2 _window;
	
	Tractor _tractor;
	
	private KeyboardController _keyboardController;
	
	private Timer _updateTimer;
	
	private ControlDeviceConnection _controlDeviceConnection;
	
	private IncomingCommandsController _commandsController;
	
	
	/**
	 * @param window
	 */
	public MainWindowController2(MainWindow2 window, Tractor tractor)
	{
		
		this._window = window;
		this._tractor = tractor;
		
		JButton connectButton  = _window.getTopPanel().getConnectButton();
		
		_keyboardController = new KeyboardController(_tractor, _window);
		
		
		if(connectButton==null)
		{
			return;
		}
		
		connectButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				_window.getTopPanel().showConnectPanel();
				
				Thread connectThread = new Thread(new Runnable()
				{
					@Override
					public void run()
					{
						
						DeltaRobotConnection deltaCon = new DeltaRobotConnection(SerialPortManager.LINUX_USB_0);
//						
						try
						{
							TimeUnit.SECONDS.sleep(2);
							deltaCon.attachServo(DeltaRobotConnection.PIN_SERVO_1);
							deltaCon.attachServo(DeltaRobotConnection.PIN_SERVO_2);
							
							TimeUnit.SECONDS.sleep(1);
							deltaCon.rotateServo(DeltaRobotConnection.PIN_SERVO_1, 55);
							deltaCon.rotateServo(DeltaRobotConnection.PIN_SERVO_2, 75);
							TimeUnit.SECONDS.sleep(1);
							deltaCon.rotateServo(DeltaRobotConnection.PIN_SERVO_1, 75);
							deltaCon.rotateServo(DeltaRobotConnection.PIN_SERVO_2, 55);

						} catch (InterruptedException event)
						{
							event.printStackTrace();
						}
						
//						createConnections();
//						startUpdateTimer();
					}
				});
				
				connectThread.start();
			}
		});
		
		
		TractorInfoPanel infoPanel = _window.getInfoPanel();
		
		//Start/Stop engine
		
		JToggleButton startEngineButton = infoPanel.startEngineButton;
		
		startEngineButton.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				if(startEngineButton.isSelected())
				{
					_tractor.startEngine();

				}else
				{
					_tractor.stopEngine();
				}
				
				System.out.println("is start - " + _tractor.isEngineStarted());
			}
		});
		
		//Select control device
		
		ButtonGroup group = infoPanel.selectControllDeviceButtonGroup;
		
		Enumeration<AbstractButton> buttonsList = group.getElements();
		
		while(buttonsList.hasMoreElements())
		{
			JRadioButton button = (JRadioButton) buttonsList.nextElement();
			
			button.addActionListener(new ActionListener()
			{
				
				@Override
				public void actionPerformed(ActionEvent e)
				{
					
					switch (e.getActionCommand())
					{
					case TractorInfoPanel.SELECT_ANDROID_DEVICE_COMMAND:
						
						_keyboardController.setEnabled(false);
						
						_commandsController.setAndroidDeviceCommandsEnable(true);
						
						break;
						
					case TractorInfoPanel.SELECT_KEYBOARD_COMMAND:
						
						_commandsController.setAndroidDeviceCommandsEnable(false);
						
						_keyboardController.setEnabled(true);
						
//						_deviceConnection.sendError(45, "The tractor is controlled from another device!");
						
						break;

					default:
						break;
					}
				}
			});
		}

	}
	
	
	private void createConnections()
	{
		String portName = _window.getTopPanel().getSelectedPortName();
		
		TractorConnection tractorConnection = new TractorConnection(portName);	
		
		//whait 2 seconds after open serial port
		try
		{
			TimeUnit.SECONDS.sleep(2);
//			Thread.sleep(2000);
		} catch (InterruptedException event)
		{
			event.printStackTrace();
		}
		
		_controlDeviceConnection = new ControlDeviceConnection();
//		_controlDeviceConnection.setConnection(tractorConnection.getConnection());
		
		_commandsController = new IncomingCommandsController(_tractor, _window, tractorConnection, _controlDeviceConnection);

		new TractorController(_tractor, _window, tractorConnection, _controlDeviceConnection);
	
		tractorConnection.setDefaultPinsMode();
		
//		tractorConnection.sendConnectResult("password");
	}
	
	
	///////////
	//Private//
	///////////
	
	
	private void startUpdateTimer()
	{
		_updateTimer = new Timer();
		
		_updateTimer.schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				update();
			}
			
		}, 0, UPDATE_DELAY);
	}
	
	
	@SuppressWarnings("unused")
	private void stopUpdateTimer()
	{
		_updateTimer.cancel();
	}
	
	
	private void update()
	{
		_tractor.update();
		
		_window.update();
		
		if(_keyboardController.isEnabled())
		{
			_keyboardController.update();
		}
	}
	
	
}//class
