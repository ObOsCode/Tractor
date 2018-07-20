package old.tractorDevices.connections;

import ru.roboticsUMK.commandProtocol.deviceConnection.arduino.ArduinoDevice;

public class DeltaRobotConnection extends ArduinoDevice
{
	
	public static final byte PIN_SERVO_1 = 4;
	public static final byte PIN_SERVO_2 = 5;
	public static final byte PIN_SERVO_3 = 6;



	public DeltaRobotConnection(String portName)
	{
		super(portName);
	}

}
