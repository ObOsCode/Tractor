package models.devices;



public abstract class DeviceTypeBase
{
	
	private String _name;
	private int _index;
	
	
	public DeviceTypeBase(int index, String name)
	{
		this._name = name;
		this._index = index;
	}
	
	
	public String getName()
	{
		return this._name;
	}
	
	
	public int getIndex()
	{
		return this._index;
	}

}
