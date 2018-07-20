package old.tractorDevices.connections;

import ru.roboticsUMK.commandProtocol.ProtocolCommand;
import ru.roboticsUMK.commandProtocol.deviceConnection.ProtocolDeviceBase;
import ru.roboticsUMK.commandProtocol.deviceConnection.arduino.ArduinoCommand;
import ru.roboticsUMK.commandProtocol.deviceConnection.arduino.ArduinoDevice;
import ru.roboticsUMK.serial.SerialPortProtocolConnection;

public class TractorConnection extends ArduinoDevice
{
	
	public static final int MAX_ANALOG_VALUE = 200;
	

	public static final int PIN_LEFT_WHEEL = 8;
	public static final int PIN_RIGHT_WHEEL = 10;
	public static final int PIN_TOP_GEAR = 11;
	public static final int PIN_REVERSE_GEAR = 12;
	public static final int PIN_START_ENGINE = 13;
	

	public TractorConnection(String portName)
	{
		super(portName);
	}
	
	
	public void setDefaultPinsMode()
	{
		//Set Arduino pins mode
		setDigitalPin(TractorConnection.PIN_START_ENGINE, TractorConnection.HIGH);
		setPinMode(TractorConnection.PIN_START_ENGINE, TractorConnection.OUTPUT);
		
		setDigitalPin(TractorConnection.PIN_TOP_GEAR, TractorConnection.HIGH);
		setPinMode(TractorConnection.PIN_TOP_GEAR, TractorConnection.OUTPUT);
		
		setDigitalPin(TractorConnection.PIN_REVERSE_GEAR, TractorConnection.HIGH);
		setPinMode(TractorConnection.PIN_REVERSE_GEAR, TractorConnection.OUTPUT);
		
	}
	

}
