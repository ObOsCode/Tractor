package models.devices.robots.tractorPlatform;

import models.devices.DeviceType;
import models.devices.robots.tractorPlatform.events.TractorPlatformEvent;
import models.devices.robots.tractorPlatform.events.TractorPlatformEventDispatcher;
import ru.roboticsnt.commandProtocol.connections.ProtocolConnectionBase;


public class TractorPlatform extends TractorPlatformEventDispatcher
{
	
	public static final int FORWARD = 1;
	public static final int BACKWARD = 2;
	public static final int STOPPED = 0;

	private int _lastDirection = STOPPED;
	private int _motionState = STOPPED;
	
	private boolean _isEngineStarted = false;
	private int _installedTurn = 0;// from (-100) to (100)
	private int _leftWheelPower;// from (-100) to (100)
	private int _rightWheelPower;// from (-100) to (100)
	
	private int _MAX_TURN = 20;//????????

	private final int _CHANGE_DIRECTION_DELAY = 3500;
	private long _lastChangeDirectionTime = 0;
	
	private GPSPoint _gpsPosition;
	private PlatformTrack _recordableTrack;
	private boolean _isTrackRecorded = false;
	
	
	public TractorPlatform(ProtocolConnectionBase connection)
	{
		super(connection, DeviceType.PLATFORM);
	}
	
	
	public int getLeftWheelPower()
	{
		return _leftWheelPower;
	}
	
	
	public int getRightWheelPower()
	{
		return _rightWheelPower;
	}
	
	
	//Engine
	public void startEngine()
	{
		System.out.println("Start engine");
		
		_isEngineStarted = true;
		dispatchEvent(new TractorPlatformEvent(TractorPlatformEvent.ENGINE_STATE_CHANGE));
	}
	
	
	public void stopEngine()
	{
		System.out.println("Stop engine");
		
		_isEngineStarted = false;
		dispatchEvent(new TractorPlatformEvent(TractorPlatformEvent.ENGINE_STATE_CHANGE));
	}
	
	
	public boolean isEngineStarted()
	{
		return _isEngineStarted;
	}
	
	
	//Turn
	public void setTurn(int value)
	{
		
		System.out.println("Set turn: " + value);
		
		if(!_isEngineStarted)
		{
			return;
		}
		
		_installedTurn = value;
		dispatchEvent(new TractorPlatformEvent(TractorPlatformEvent.TURN_CHANGE));
	}
	
	
	public int getTurn()
	{
		return _installedTurn;
	}
	
	
	//Power
	public void setPower(int value)
	{
		
		System.out.println("Set power: " + value);
		
		if(!_isEngineStarted)
		{
			return;
		}
		
		long time = System.currentTimeMillis();
		
		boolean isForward = value > 0;
		boolean isReverse = value < 0;
		boolean isStop = value == 0;
		
		if ((_lastDirection == FORWARD && isReverse || _lastDirection == BACKWARD && isForward) 
				&& ((time - _lastChangeDirectionTime) < _CHANGE_DIRECTION_DELAY))
		{
		    _leftWheelPower = 0;
		    _rightWheelPower = 0;
		    return;
		}
		
		if (isReverse)
		{
			_lastDirection = BACKWARD;
			setMotionState(BACKWARD);
		}
		
		if (isForward)
		{
			_lastDirection = FORWARD;
			setMotionState(FORWARD);
		}
		
		if(isStop)
		{
			setMotionState(STOPPED);
		}
	
		_lastChangeDirectionTime = time;
	  
		if (_installedTurn == 0)
		{
		    _leftWheelPower = Math.abs(value);
		    _rightWheelPower = Math.abs(value);
		}
		else
		{
			//Speed koef
			double Ks = (1.00 - (_MAX_TURN / 100.00)) * (Math.abs(value) / 100.00);
			//Turn koef
			double Kt = (1.00 - Ks) * (1.00 - (Math.abs(_installedTurn) / 100.00));
	
			double K = Ks + Kt;
	
		    if (_installedTurn < 0)
		    {
		    	_leftWheelPower = (int) (Math.abs(value) * K);
		    	_rightWheelPower = Math.abs(value);
		    }
		    else
		    {
		    	_leftWheelPower = Math.abs(value);
		    	_rightWheelPower = (int) (Math.abs(value) * K);
		    }
		}

		dispatchEvent(new TractorPlatformEvent(TractorPlatformEvent.SPEED_CHANGE));
	}
	
	
	public int getMotionState()
	{
		return _motionState;
	}
	
	
	private void setMotionState(int newState)
	{
		if(_motionState == newState)
		{
			return;
		}
		
		_motionState = newState;
		dispatchEvent(new TractorPlatformEvent(TractorPlatformEvent.MOTION_STATE_CHANGE));
	}

	
	public void setGPSPosition(double latitude, double longitude)
	{
		_gpsPosition = new GPSPoint(latitude, longitude);
		
		if(_isTrackRecorded)
		{
			_recordableTrack.addPoint(_gpsPosition);
		}
		
		dispatchEvent(new TractorPlatformEvent(TractorPlatformEvent.GPS_POSITION_CHANGE));
	}
	
	
	public GPSPoint getGPSPosition()
	{
		return _gpsPosition;
	}
	
	
	public PlatformTrack getRecordableTrack()
	{
		return _recordableTrack;
	}


	public boolean isTrackRecorded()
	{
		return _isTrackRecorded;
	}
	
	
	public void startRecordTrack(String trackName)
	{
		_recordableTrack = new PlatformTrack(trackName);
		_isTrackRecorded = true;
	}
	
	
	public void stopRecordTrack()
	{
		_isTrackRecorded = false;
	}
	
	
}