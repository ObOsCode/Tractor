package models.devices.robots.deltaRobot;

import java.util.ArrayList;

import javafx.geometry.Point3D;
import models.devices.robots.RobotDevice;
import models.devices.robots.RobotType;
import models.devices.robots.deltaRobot.events.DeltaRobotEvent;
import models.devices.robots.deltaRobot.events.IDeltaRobotDispatcher;
import models.devices.robots.deltaRobot.events.IDeltaRobotListener;
import ru.roboticsUMK.commandProtocol.deviceConnection.arduino.ArduinoDevice;


public class DeltaRobot extends RobotDevice implements IDeltaRobotDispatcher
{
	
	private int startAngle = 45;
	private int _servo1Angle = 0;
	private int _servo2Angle = 0;
	private int _servo3Angle = 0;

	public static final int MIN_X = -14;
	public static final int MAX_X = 14;
	public static final int MIN_Y = -14;
	public static final int MAX_Y = 14;
	public static final int MIN_Z = -46;
	public static final int MAX_Z = -30;
	
	private double _x = 0;
	private double _y = 0;
	private double _z = MAX_Z;

	private final int _RF = 15;
	private final int _RE = 38;
	private final int _F = 23;
	private final int _E = 12;

	private final double _SQRT_3 = Math.sqrt(3.0);
	private final double _PI = Math.PI;    // PI
	private final double _SIN_120 = _SQRT_3 / 2.0;
	private final double _COS_120 = -0.5;
//	private final double _TAN_60 = _SQRT_3;
//	private final double _SIN_30 = 0.5;
//	private final double _TAN_30 = 1 / _SQRT_3;
	
	private Point3D _centerPosition = new Point3D(0, 0, MAX_Z);
	

	ArrayList<IDeltaRobotListener> _listenersList = new ArrayList<>();


	public DeltaRobot(ArduinoDevice connection)
	{
		super(connection, RobotType.DELTA);
		setThetasfromXYZ();
	}
	
	
	public double getX()
	{
		return _x;
	}
	
	
	public double getY()
	{
		return _y;
	}
	
	
	public double getZ()
	{
		return _z;
	}
	
	
	public int getServo1Angle()
	{
		return _servo1Angle;
	}
	
	
	public int getServo2Angle()
	{
		return _servo2Angle;
	}
	
	
	public int getServo3Angle()
	{
		return _servo3Angle;
	}
	
	
	public void setX(double value)
	{
		_x = value;
		setThetasfromXYZ();
	}
	
	
	public void setY(double value)
	{
		_y = value;
		setThetasfromXYZ();
	}
	
	
	public void setZ(double value)
	{
		_z = value;
		setThetasfromXYZ();
	}
	
	
	public void setPosition(double x, double y, double z)
	{
		_x = x;
		_y = y;
		_z = z;
		setThetasfromXYZ();
	}
	
	public void setToCenter()
	{
		_x = _centerPosition.getX();
		_y = _centerPosition.getY();
		_z = _centerPosition.getZ();
		setThetasfromXYZ();
	}
	
	
	public void setServosToHorizontal()
	{
		  _servo1Angle = startAngle;
		  _servo2Angle = startAngle;
		  _servo3Angle = startAngle;

		  dispatchEvent(new DeltaRobotEvent(DeltaRobotEvent.POSITION_CHANGE));
	}
	
	
	public void setServosToZero()
	{
		  _servo1Angle = 0;
		  _servo2Angle = 0;
		  _servo3Angle = 0;
		  
		  dispatchEvent(new DeltaRobotEvent(DeltaRobotEvent.POSITION_CHANGE));
	}
	
	
	public Track createTrack(int delay)
	{
		return new Track(delay);
	}
	
	
	////////////
	//Private
	////////////
	
	
	private void setThetasfromXYZ()
	{
	  if (_x < MIN_X) {_x = MIN_X;}
	  
	  if (_x > MAX_X) {_x = MAX_X;}
	  
	  if (_y < MIN_Y) {_y = MIN_Y;}
	  
	  if (_y > MAX_Y) {_y = MAX_Y;}
	  
	  if (_z < MIN_Z) {_z = MIN_Z;}
	  
	  if (_z > MAX_Z) {_z = MAX_Z;}

	  _servo1Angle = (int) (startAngle + deltaCalcAngleYZ(_x, _y, _z));
	  _servo2Angle = (int) (startAngle + deltaCalcAngleYZ(_x * _COS_120 + _y * _SIN_120, _y * _COS_120 - _x * _SIN_120, _z));
	  _servo3Angle = (int) (startAngle + deltaCalcAngleYZ(_x * _COS_120 - _y * _SIN_120, _y * _COS_120 + _x * _SIN_120, _z));
	  
	  dispatchEvent(new DeltaRobotEvent(DeltaRobotEvent.POSITION_CHANGE));
	}
	
	
	private double deltaCalcAngleYZ(double x0, double y0, double z0)
	{
	  double y1 = -0.5 * 0.57735 * _F; // f/2 * tg 30
	  y0 -= 0.5 * 0.57735    * _E;    // shift center to edge
	  // z = a + b*y
	  double a = (x0 * x0 + y0 * y0 + z0 * z0 + _RF * _RF - _RE * _RE - y1 * y1) / (2 * z0);
	  double b = (y1 - y0) / z0;
	  // discriminant
	  double d = -(a + b * y1) * (a + b * y1) + _RF * (b * b * _RF + _RF);
	  if (d < 0) return 999; // non-existing point
	  double yj = (y1 - a * b - Math.sqrt(d)) / (b * b + 1); // choosing outer point
	  double zj = a + b * yj;
	  return 180.0 * Math.atan(-zj / (y1 - yj)) / _PI + ((yj > y1) ? 180.0 : 0.0);
	}
	
	
	
	///////////
	//Classes
	///////////
	
	
	public class Track
	{
		
		private ArrayList<Point3D> _pointsList = new ArrayList<>();
		
		private int _delay = 1000;//delay between points (miliseconds)
		
		private boolean _isStart = false;
		
		
		public Track(int delay)
		{
			_delay = delay;
		}
		
		
		public void addPoint(Point3D point)
		{
			_pointsList.add(point);
		}
		
		
		public void addPoint(double x, double y, double z)
		{
			_pointsList.add(new Point3D(x, y, z));
		}
		
		
		public void stop()
		{
			_isStart = false;
		}
		
		
		public void start()
		{
			
			if(_isStart)return;
			
			_isStart = true;
			
			Thread thread = new Thread(new Runnable()
			{
				
				@Override
				public void run()
				{
					while(true)
					{
						for (int i = 0; i < _pointsList.size(); i++)
						{
							if(!_isStart) return;
							
							Point3D point = _pointsList.get(i);
							DeltaRobot.this.setPosition(point.getX(), point.getY(), point.getZ());
							try
							{
								Thread.currentThread();
								Thread.sleep(_delay);
							} catch (InterruptedException e)
							{
								e.printStackTrace();
							}
						}
					}
				}
			});
			
			thread.start();
		}
	}
	

	public void addEventListener(IDeltaRobotListener listener)
	{
		_listenersList.add(listener);
	}
	
	
	public void dispatchEvent(DeltaRobotEvent event)
	{
		
		for (IDeltaRobotListener listener : _listenersList)
		{
			
			switch (event.getType())
			{
			
			case DeltaRobotEvent.POSITION_CHANGE:
				listener.onPositionChange(event);
				break;
				
			default:
				break;
			}
		}		
	}

}
