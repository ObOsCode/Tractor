package tractorPlatform.connections.serial;

import robotics.protocol.CommandListener;
import robotics.protocol.ProtocolCommand;
import robotics.protocol.ProtocolConnnection;
import robotics.protocol.ProtocolParser;

public class SerialPortProtocolConnection extends ProtocolConnnection implements SerialPortListener
{
	
	private SerialPortManager _port;
	
	private ProtocolParser _parser;
	

	public SerialPortProtocolConnection(String portName)
	{
		_port = new SerialPortManager(portName);
		
		_parser = new ProtocolParser();
		
		_port.addEventListener(this);
		
		_parser.setDetectCommandListener(this);
	}

	
	@Override
	public void sendCommand(ProtocolCommand command)
	{
		_port.writeBytest(command.getBytes());
	}
	
	
	@Override
	public void addCommandListener(CommandListener listener)
	{
		super.addCommandListener(listener);
	}

	
	//Parser listener
	@Override
	public void onCommand(ProtocolCommand command)
	{
		super.onCommand(command);
	}

	
	//Port listener
	@Override
	public void onData(byte[] data)
	{
		_parser.parse(data);
	}

}
