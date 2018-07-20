package models.devices.robots.deltaRobot.events;

public interface IDeltaRobotDispatcher
{
	
	public void addEventListener(IDeltaRobotListener listener);
	public void dispatchEvent(DeltaRobotEvent event);

}
