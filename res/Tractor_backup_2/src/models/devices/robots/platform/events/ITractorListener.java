package models.devices.robots.platform.events;

public interface ITractorListener
{
	
	public void onEngineStateChange(TractorPlatformEvent event);
	
	public void onTurnChange(TractorPlatformEvent event);
	
	public void onSpeedChange(TractorPlatformEvent event);
	
	public void onMotionStaeChange(TractorPlatformEvent event);

}
