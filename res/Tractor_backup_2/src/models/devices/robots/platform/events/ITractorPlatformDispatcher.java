package models.devices.robots.platform.events;


public interface ITractorPlatformDispatcher
{
	
	public void addEventListener(ITractorListener listener);
	public void dispatchEvent(TractorPlatformEvent event);

}
