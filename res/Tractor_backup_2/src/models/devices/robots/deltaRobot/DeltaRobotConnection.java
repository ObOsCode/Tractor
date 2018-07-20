package models.devices.robots.deltaRobot;

import ru.roboticsUMK.commandProtocol.deviceConnection.arduino.ArduinoDevice;

public class DeltaRobotConnection extends ArduinoDevice
{
	
	public static final byte PIN_SERVO_1 = 9;
	public static final byte PIN_SERVO_2 = 10;
	public static final byte PIN_SERVO_3 = 11;


	public DeltaRobotConnection(String portName)
	{
		super(portName);
	}

}
