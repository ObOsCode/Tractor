package controllers.devices;

import java.awt.Component;

import controllers.DeltaRobotManualControl;
import models.devices.robots.deltaRobot.DeltaRobot;
import models.devices.robots.deltaRobot.DeltaRobotConnection;
import models.devices.robots.deltaRobot.events.DeltaRobotEvent;
import models.devices.robots.deltaRobot.events.IDeltaRobotListener;
import ru.roboticsUMK.commandProtocol.deviceConnection.ProtocolDeviceListener;


public class DeltaRobotController extends DeviceControllerBase implements IDeltaRobotListener
{
	
	private DeltaRobotConnection _connection;
	
	private DeltaRobot _robot;
	
	private DeltaRobotManualControl _manualControlManager;
	
	
	public DeltaRobotController(String portName)
	{
		super(new DeltaRobot(new DeltaRobotConnection(portName)));
		
		_robot = (DeltaRobot) super.getDevice();
		_connection = (DeltaRobotConnection) _robot.getConnection();
		
		_connection.addListener(new ProtocolDeviceListener(){
			
			@Override
			public void onConnect()
			{
				super.onConnect();
				
				attachServos();
				
				_robot.addEventListener(DeltaRobotController.this);
				_robot.setToCenter();
			}
		});
	}
	
	
	public void enableManualControl(Component listenerComponent)
	{
		this._manualControlManager = new DeltaRobotManualControl(_robot, listenerComponent);
	}
	
	
	public void disableManualControl()
	{
		this._manualControlManager.dispose();
	}
	

	public void dispose()
	{
		disableManualControl();
		
//		_robot.setToCenter();
//		_robot.setServosToZero();
//		_robot.setServosToHorizontal();
		
		detachServos();
		
		super.dispose();
	}


	@Override
	public void onPositionChange(DeltaRobotEvent event)
	{
		_connection.rotateServo(DeltaRobotConnection.PIN_SERVO_1, _robot.getServo1Angle());
		_connection.rotateServo(DeltaRobotConnection.PIN_SERVO_2, _robot.getServo2Angle());
		_connection.rotateServo(DeltaRobotConnection.PIN_SERVO_3, _robot.getServo3Angle());
		
//		System.out.println("------------------------------------------------------ ");
//		System.out.println("_robot.getServo1Angle() - " + _robot.getServo1Angle());
//		System.out.println("_robot.getServo2Angle() - " + _robot.getServo2Angle());
//		System.out.println("_robot.getServo3Angle() - " + _robot.getServo3Angle());
//	
	}
	
	
	////////////
	//Private
	////////////
	
	
	private void attachServos()
	{
		_connection.attachServo(DeltaRobotConnection.PIN_SERVO_1);
		_connection.attachServo(DeltaRobotConnection.PIN_SERVO_2);
		_connection.attachServo(DeltaRobotConnection.PIN_SERVO_3);
	}
	
	
	private void detachServos()
	{
		//Устанавливает сервы в горизонтальное положение
		_connection.detachServo(DeltaRobotConnection.PIN_SERVO_1);
		_connection.detachServo(DeltaRobotConnection.PIN_SERVO_2);
		_connection.detachServo(DeltaRobotConnection.PIN_SERVO_3);
	}
	
	
}
