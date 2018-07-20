package tractorPlatform.tractor.controllers;

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

import tractorPlatform.connections.ArduinoConnection;
import tractorPlatform.connections.ControlDeviceConnection;
import tractorPlatform.tractor.models.Tractor;
import view.MainWindow;
import view.TractorInfoPanel;

public class MainWindowController
{
	
	public static final int UPDATE_DELAY = 125;
	
	MainWindow _window;
	
	Tractor _tractor;
	
	private KeyboardController _keyboardController;
	
	private Timer _updateTimer;
	
	private ControlDeviceConnection _deviceConnection;
	
	private IncomingCommandsController _commandsController;
	
	
	/**
	 * @param window
	 */
	public MainWindowController(MainWindow window, Tractor tractor)
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
						createConnections();
						startUpdateTimer();
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
		
		ArduinoConnection arduinoConnection = new ArduinoConnection(portName);	
		
		//whait 2 seconds after open serial port
		try
		{
			TimeUnit.SECONDS.sleep(2);
//			Thread.sleep(2000);
		} catch (InterruptedException event)
		{
			event.printStackTrace();
		}
		
		_deviceConnection = new ControlDeviceConnection();
		_deviceConnection.setProtocolConnection(arduinoConnection.getProtocolConnection());
		
		_commandsController = new IncomingCommandsController(_tractor, _window, arduinoConnection, _deviceConnection);

		new TractorController(_tractor, _window, arduinoConnection, _deviceConnection);
	
		
		arduinoConnection.setDefaultPinsMode();
		
		arduinoConnection.connect("password");
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
