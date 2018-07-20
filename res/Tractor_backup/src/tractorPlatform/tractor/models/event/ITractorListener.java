package tractorPlatform.tractor.models.event;

public interface ITractorListener
{
	
	public void onEngineStateChange(TractorEvent event);
	
	public void onTurnChange(TractorEvent event);
	
	public void onSpeedChange(TractorEvent event);
	
	public void onMotionStaeChange(TractorEvent event);

}
