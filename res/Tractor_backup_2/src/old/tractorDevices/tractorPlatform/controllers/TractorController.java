package old.tractorDevices.tractorPlatform.controllers;

import javax.swing.JToggleButton;

import old.gui.view.MainWindow2;
import old.tractorDevices.connections.ControlDeviceConnection;
import old.tractorDevices.connections.TractorConnection;
import old.tractorDevices.tractorPlatform.models.Tractor;
import old.tractorDevices.tractorPlatform.models.event.ITractorListener;
import old.tractorDevices.tractorPlatform.models.event.TractorEvent;

public class TractorController
{
	
	private Tractor _tractor;
	private TractorConnection _tractorConnection; 
	
	private MainWindow2 _window;
	

	public TractorController(Tractor tractor, MainWindow2 window, TractorConnection tractorConnection, ControlDeviceConnection deviceConnection)
	{
		this._tractor = tractor;
		this._window = window;
		this._tractorConnection = tractorConnection;
	
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
				pinValue = TractorConnection.LOW;//Enabled - LOW value
				engineButton.setText("STOP");
				engineButton.setSelected(true);
			}else
			{
				pinValue = TractorConnection.HIGH;
				engineButton.setText("START");
				engineButton.setSelected(false);
			}
			
			_tractorConnection.setDigitalPin(TractorConnection.PIN_START_ENGINE, pinValue);
		}
		

		@Override
		public void onTurnChange(TractorEvent event)
		{
			onSpeedChange(event);
		}
		
		
		@Override
		public void onSpeedChange(TractorEvent event)
		{
			int leftWheelValue = TractorConnection.MAX_ANALOG_VALUE * _tractor.getLeftWheelPower() / 100;
			
			_tractorConnection.setAnalogPin(TractorConnection.PIN_LEFT_WHEEL, leftWheelValue);
			
			int rightWheelValue = TractorConnection.MAX_ANALOG_VALUE * _tractor.getRightWheelPower() / 100;

			_tractorConnection.setAnalogPin(TractorConnection.PIN_RIGHT_WHEEL, rightWheelValue);
		}

		
		@Override
		public void onMotionStaeChange(TractorEvent event)
		{
			int tractorMotionState = _tractor.getMotionState();
			
			switch (tractorMotionState)
			{
			case Tractor.FORWARD:
				_tractorConnection.setDigitalPin(TractorConnection.PIN_TOP_GEAR, TractorConnection.LOW);
				_tractorConnection.setDigitalPin(TractorConnection.PIN_REVERSE_GEAR, TractorConnection.HIGH);
				break;
			case Tractor.BACKWARD:
				_tractorConnection.setDigitalPin(TractorConnection.PIN_TOP_GEAR, TractorConnection.HIGH);
				_tractorConnection.setDigitalPin(TractorConnection.PIN_REVERSE_GEAR, TractorConnection.LOW);
				break;
			case Tractor.STOPPED:
				_tractorConnection.setDigitalPin(TractorConnection.PIN_TOP_GEAR, TractorConnection.HIGH);
				_tractorConnection.setDigitalPin(TractorConnection.PIN_REVERSE_GEAR, TractorConnection.HIGH);
				break;

			default:
				break;
			}			
		}
		
	}
	

}//class
