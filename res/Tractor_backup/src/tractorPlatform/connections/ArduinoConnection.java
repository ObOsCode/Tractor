package tractorPlatform.connections;

import robotics.protocol.ProtocolCommand;
import robotics.protocol.tractor.ArduinoCommand;
import tractorPlatform.connections.serial.SerialPortProtocolConnection;

public class ArduinoConnection extends CommandsConnectionBase
{
	
	public static final int MAX_ANALOG_VALUE = 200;
	

	public static final int PIN_LEFT_WHEEL = 8;
	public static final int PIN_RIGHT_WHEEL = 10;
	public static final int PIN_TOP_GEAR = 11;
	public static final int PIN_REVERSE_GEAR = 12;
	public static final int PIN_START_ENGINE = 13;
	
	public static final byte LOW = 0x0;
	public static final byte HIGH = 0x1;
	
	public static final int INPUT = 0x0;
	public static final int OUTPUT = 0x1;
	public static final int INPUT_PULLUP = 0x2;
	
	
//	private ProtocolConnnection _connection;
	
	private String _portName;
	
	
	public ArduinoConnection()
	{
		
	}
	
	
	public ArduinoConnection(String portName)
	{
		openSerial(portName);
	}
	
	
	public String getPortName()
	{
		return _portName;
	}

	
	public void openSerial(String portName)
	{
//		_connection = new SerialPortProtocolConnection(portName);
		
		setProtocolConnection(new SerialPortProtocolConnection(portName));
		
		_portName = portName;
	}
	
	
	public void setDefaultPinsMode()
	{
		//Set Arduino pins mode
		setDigitalPin(ArduinoConnection.PIN_START_ENGINE, ArduinoConnection.HIGH);
		setPinMode(ArduinoConnection.PIN_START_ENGINE, ArduinoConnection.OUTPUT);
		
		setDigitalPin(ArduinoConnection.PIN_TOP_GEAR, ArduinoConnection.HIGH);
		setPinMode(ArduinoConnection.PIN_TOP_GEAR, ArduinoConnection.OUTPUT);
		
		setDigitalPin(ArduinoConnection.PIN_REVERSE_GEAR, ArduinoConnection.HIGH);
		setPinMode(ArduinoConnection.PIN_REVERSE_GEAR, ArduinoConnection.OUTPUT);
		
	}
	
	
	public void connect(String password)
	{
		super.sendCommand(new ProtocolCommand(ArduinoCommand.TYPE_ARDUINO_SERVER_CONNECT, password));
	}
	
	
	public void setDigitalPin(int pin, byte value)
	{
		super.sendCommand(new ProtocolCommand(ArduinoCommand.TYPE_ARDUINO_SET_DIGITAL, pin, value));
	}
	
	
	public void setAnalogPin(int pin, int value)
	{
		super.sendCommand(new ProtocolCommand(ArduinoCommand.TYPE_ARDUINO_SET_ANALOG, pin, value));
	}
	
	
	//////////
	//Private
	//////////
	
	
	private void setPinMode(int pin, int mode)
	{
		super.sendCommand(new ProtocolCommand(ArduinoCommand.TYPE_ARDUINO_SET_PIN_MODE, pin, mode));
	}
	
	

	

}
