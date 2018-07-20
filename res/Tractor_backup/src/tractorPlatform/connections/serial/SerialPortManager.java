package tractorPlatform.connections.serial;

import java.util.ArrayList;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;


public class SerialPortManager implements SerialPortEventListener
{
	
	public static final String LINUX_USB_0 = "/dev/ttyUSB0";
	
	
	public static String[] getPortNames()
	{
		return SerialPortList.getPortNames();
	}
	
	
	private SerialPort _port;
	
	private ArrayList<SerialPortListener> _listenersList = new ArrayList<SerialPortListener>();
	
	
	public SerialPortManager(String portName)
	{
		_port = new SerialPort(portName); 
		
		try 
	    {
	        int mask = SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR;
	        
	        _port.openPort();
	        _port.setParams(9600, 8, 1, 0);
	        _port.setEventsMask(mask);
	        
	        _port.addEventListener(this);

	    }
	    catch (SerialPortException ex) 
	    {
	        System.out.println(ex);
	    }
	}
	
	
	public void writeBytest(byte[] bytes)
	{
		try
		{
			_port.writeBytes(bytes);
		} catch (SerialPortException e)
		{
			e.printStackTrace();
		}
	}
	
	
	public byte[] readBytes(int bytesCount) throws SerialPortException
	{
		return _port.readBytes(bytesCount);
	}
	
	
	public void addEventListener(SerialPortListener listener)
	{
		_listenersList.add(listener);
	}
	
	
	public void removeEventListener(SerialPortListener listener)
	{
		_listenersList.remove(listener);
	}
	
	
	@Override
	public void serialEvent(SerialPortEvent event)
	{

		if(event.isDSR())
		{
			System.out.println("Arduino disconnect!");
		}
		
		
    	if(event.isRXCHAR() && event.getEventValue() > 0)
    	{
    		int bytesCount = event.getEventValue();
    		
	    	try
			{
	    		byte[] buffer = _port.readBytes(bytesCount);
	    		
	    		if(_listenersList.size()>0)
	    		{
	    			for (SerialPortListener listener : _listenersList)
					{
						listener.onData(buffer);
					}
	    		}
	    		
			} catch (SerialPortException e)
			{
				e.printStackTrace();
			}
    	}
	}

}
