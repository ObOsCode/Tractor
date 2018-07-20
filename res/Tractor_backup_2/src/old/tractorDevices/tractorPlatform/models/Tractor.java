package old.tractorDevices.tractorPlatform.models;

import old.tractorDevices.tractorPlatform.models.event.TractorEvent;
import old.tractorDevices.tractorPlatform.models.event.TractorEventsDispatcher;

public class Tractor extends TractorEventsDispatcher
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
	
	long _lastChangeDirectionTime = 0;
	
	
	//________________//_______________//____________________//
	
	
	public final double WIDTH = 1;//meters
	public final double LENGTH = 2.2;//meters

	private double _speed = 0;//meters per second
	private double _rotation = 90;//degrees
	private double _x = 0;//meters
	private double _y = 0;//meters
	
	private int _MAX_ROTATION_FREQUENCY = 2; //rot per second
	
	
	public Tractor()
	{
		
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
		_isEngineStarted = true;
		
		dispatchEvent(new TractorEvent(TractorEvent.ENGINE_STATE_CHANGE));
	}
	
	
	public void stopEngine()
	{
		_isEngineStarted = false;
		dispatchEvent(new TractorEvent(TractorEvent.ENGINE_STATE_CHANGE));
	}
	
	
	public boolean isEngineStarted()
	{
		return _isEngineStarted;
	}
	
	
	//Turn
	public void setTurn(int value)
	{
		if(!_isEngineStarted)
		{
			return;
		}
		
		_installedTurn = value;
		dispatchEvent(new TractorEvent(TractorEvent.TURN_CHANGE));
	}
	
	
	public int getTurn()
	{
		return _installedTurn;
	}
	
	
	public double getSpeed()
	{
		return _speed;
	}
	
	
	//Power
	public void setPower(int value)
	{
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
		
//		_speed = MAX_SPEED/2 * (_leftWheelPower/100.00 + _rightWheelPower/100.00);
	
		dispatchEvent(new TractorEvent(TractorEvent.SPEED_CHANGE));
	}
	
	
	public void update()
	{
		_y+= _speed * Math.sin(Math.toRadians(_rotation)); 
		_x+= _speed * Math.cos(Math.toRadians(_rotation));
	}
	
	
	public int getMotionState()
	{
		return _motionState;
	}
	
	
	private void setMotionState(int newState)
	{
		if(_motionState==newState)
		{
			return;
		}
		
		_motionState = newState;
		super.dispatchEvent(new TractorEvent(TractorEvent.MOTION_STATE_CHANGE));
	}

	


}//class
