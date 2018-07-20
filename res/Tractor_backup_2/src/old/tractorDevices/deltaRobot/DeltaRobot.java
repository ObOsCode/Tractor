package old.tractorDevices.deltaRobot;

public class DeltaRobot
{
	
	private int startAngle = 45;
	private int _servo1Angle = 0;
	private int _servo2Angle = 0;
	private int _servo3Angle = 0;

	private double _x = 0;
	private double _y = 0;
	private double _z = -32;

	private final int _MIN_X = -12;
	private final int _MAX_X = 12;
	private final int _MIN_Y = -12;
	private final int _MAX_Y = 12;
	private final int _MIN_Z = -42;
	private final int _MAX_Z = -32;

	private final int _RF = 15;
	private final int _RE = 38;
	private final int _F = 23;
	private final int _E = 12;

	private final double _SQRT_3 = Math.sqrt(3.0);
	private final double _PI = Math.PI;    // PI
	private final double _SIN_120 = _SQRT_3 / 2.0;
	private final double _COS_120 = -0.5;
	private final double _TAN_60 = _SQRT_3;
	private final double _SIN_30 = 0.5;
	private final double _TAN_30 = 1 / _SQRT_3;
	

	public DeltaRobot()
	{
		
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
		_x = value;
		setThetasfromXYZ();
	}
	
	
	public void setZ(double value)
	{
		_x = value;
		setThetasfromXYZ();
	}
	
	
	public void setPosition(double x, double y, double z)
	{
		_x = x;
		_y = y;
		_z = z;
		setThetasfromXYZ();
	}
	
	
	////////////
	//Private
	////////////
	
	
	private void setThetasfromXYZ()
	{
	  if (_x < _MIN_X) {_x = _MIN_X;}
	  
	  if (_x > _MAX_X) {_x = _MAX_X;}
	  
	  if (_y < _MIN_Y) {_y = _MIN_Y;}
	  
	  if (_y > _MAX_Y) {_y = _MAX_Y;}
	  
	  if (_z < _MIN_Z) {_z = _MIN_Z;}
	  
	  if (_z > _MAX_Z) {_z = _MAX_Z;}

	  _servo1Angle = (int) (startAngle + deltaCalcAngleYZ(_x, _y, _z));
	  _servo2Angle = (int) (startAngle + deltaCalcAngleYZ(_x * _COS_120 + _y * _SIN_120, _y * _COS_120 - _x * _SIN_120, _z));
	  _servo3Angle = (int) (startAngle + deltaCalcAngleYZ(_x * _COS_120 - _y * _SIN_120, _y * _COS_120 + _x * _SIN_120, _z));

	  System.out.println("_servo1Angle - " + _servo1Angle);
	  System.out.println("_servo2Angle - " + _servo2Angle);
	  System.out.println("_servo3Angle - " + _servo3Angle);
	
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

}
