package tractorPlatform.tractor.controllers;

import javax.swing.JToggleButton;

import tractorPlatform.connections.ArduinoConnection;
import tractorPlatform.connections.ControlDeviceConnection;
import tractorPlatform.tractor.models.Tractor;
import tractorPlatform.tractor.models.event.ITractorListener;
import tractorPlatform.tractor.models.event.TractorEvent;
import view.MainWindow;

public class TractorController
{
	
	private Tractor _tractor;
	private ArduinoConnection _arduinoConnection; 
//	private ControlDeviceConnection _deviceConnection;
	
	private MainWindow _window;
	

	public TractorController(Tractor tractor, MainWindow window, ArduinoConnection arduinoConnection, ControlDeviceConnection deviceConnection)
	{
		this._tractor = tractor;
		this._window = window;
		this._arduinoConnection = arduinoConnection;
//		this._deviceConnection = deviceConnection;
	
		_tractor.addEventListener(new TractorListener());
	}
	
	
	
	private class TractorListener implements ITractorListener
	{

		@Override
		public void onEngineStateChange(TractorEvent event)
		{
			byte pinValue;
			
			JToggleButton engineButton = _window.getInfoPanel().startEngineButton;
			
			if(_tractor.isEngineStarted())
			{
				pinValue = ArduinoConnection.LOW;//Enabled - LOW value
				engineButton.setText("STOP");
				engineButton.setSelected(true);
			}else
			{
				pinValue = ArduinoConnection.HIGH;
				engineButton.setText("START");
				engineButton.setSelected(false);
			}
			
			_arduinoConnection.setDigitalPin(ArduinoConnection.PIN_START_ENGINE, pinValue);
		}
		

		@Override
		public void onTurnChange(TractorEvent event)
		{
			onSpeedChange(event);
		}
		
		
		@Override
		public void onSpeedChange(TractorEvent event)
		{
			int leftWheelValue = ArduinoConnection.MAX_ANALOG_VALUE * _tractor.getLeftWheelPower() / 100;
			
			_arduinoConnection.setAnalogPin(ArduinoConnection.PIN_LEFT_WHEEL, leftWheelValue);
			
			int rightWheelValue = ArduinoConnection.MAX_ANALOG_VALUE * _tractor.getRightWheelPower() / 100;

			_arduinoConnection.setAnalogPin(ArduinoConnection.PIN_RIGHT_WHEEL, rightWheelValue);
		}

		
		@Override
		public void onMotionStaeChange(TractorEvent event)
		{
			int tractorMotionState = _tractor.getMotionState();
			
			switch (tractorMotionState)
			{
			case Tractor.FORWARD:
				_arduinoConnection.setDigitalPin(ArduinoConnection.PIN_TOP_GEAR, ArduinoConnection.LOW);
				_arduinoConnection.setDigitalPin(ArduinoConnection.PIN_REVERSE_GEAR, ArduinoConnection.HIGH);
				break;
			case Tractor.BACKWARD:
				_arduinoConnection.setDigitalPin(ArduinoConnection.PIN_TOP_GEAR, ArduinoConnection.HIGH);
				_arduinoConnection.setDigitalPin(ArduinoConnection.PIN_REVERSE_GEAR, ArduinoConnection.LOW);
				break;
			case Tractor.STOPPED:
				_arduinoConnection.setDigitalPin(ArduinoConnection.PIN_TOP_GEAR, ArduinoConnection.HIGH);
				_arduinoConnection.setDigitalPin(ArduinoConnection.PIN_REVERSE_GEAR, ArduinoConnection.HIGH);
				break;

			default:
				break;
			}			
		}
		
	}
	

}//class
