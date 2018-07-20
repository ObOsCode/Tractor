package tractorPlatform.connections;

import robotics.protocol.CommandListener;
import robotics.protocol.ProtocolCommand;
import robotics.protocol.ProtocolConnnection;

public abstract class CommandsConnectionBase
{
	
	private boolean _isConnected = false;
	
	protected ProtocolConnnection _connection;

	
	public boolean isConnected()
	{
		return _isConnected;
	}


	public void setIsConnected(boolean value)
	{
		this._isConnected = value;
	}
	
	
	public void addCommandListener(CommandListener listener)
	{
		_connection.addCommandListener(listener);
	}
	
	
	public void removeCommandListener(CommandListener listener)
	{
		_connection.removeCommandListener(listener);
	}
	
	
	public void setProtocolConnection(ProtocolConnnection connection)
	{
		_connection = connection;
	}
	
	
	public ProtocolConnnection getProtocolConnection()
	{
		return _connection;
	}
	
	
	public void sendError(byte type, String message)
	{
		sendCommand(new ProtocolCommand(type, message));
	}
	
	
	protected void sendCommand(ProtocolCommand command)
	{	
		_connection.sendCommand(command);
	}

}
