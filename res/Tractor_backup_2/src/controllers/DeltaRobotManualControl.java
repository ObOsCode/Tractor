package controllers;

import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import models.devices.robots.deltaRobot.DeltaRobot;

public class DeltaRobotManualControl
{
	
	private final double CHANGE_POSITION_STEP = 0.5;
	
	private Component _keyListenerComponent;
	
	private KeyboardListener _keyboardListener;
	
	private DeltaRobot _robot;
	
	private DeltaRobot.Track _track;
	

	public DeltaRobotManualControl(DeltaRobot robot, Component window)
	{
		this._robot = robot;
		this._keyListenerComponent = window;
		
		this._keyListenerComponent.setFocusable(true);
		
		_track = robot.createTrack(500);
		
		_track.addPoint(-10, 10, DeltaRobot.MAX_Z);
		_track.addPoint(-10, 10, DeltaRobot.MAX_Z - 6);
		_track.addPoint(-10, 10, DeltaRobot.MAX_Z);
		_track.addPoint(10, 10, DeltaRobot.MAX_Z);
		_track.addPoint(10, 10, DeltaRobot.MAX_Z - 6);
		_track.addPoint(10, 10, DeltaRobot.MAX_Z);
		_track.addPoint(10, -10, DeltaRobot.MAX_Z);
		_track.addPoint(10, -10, DeltaRobot.MAX_Z - 6);
		_track.addPoint(10, -10, DeltaRobot.MAX_Z);
		_track.addPoint(-10, -10, DeltaRobot.MAX_Z);
		_track.addPoint(-10, -10, DeltaRobot.MAX_Z - 6);
		_track.addPoint(-10, -10, DeltaRobot.MAX_Z);
		
//		track.start();
		
		_keyboardListener = new KeyboardListener();
		this._keyListenerComponent.addKeyListener(_keyboardListener);
	}
	
	
	public void dispose()
	{
		_keyListenerComponent.removeKeyListener(_keyboardListener);
		_track.stop();
	}
	
	
	//////////
	//Classes
	//////////
	
	
	private class KeyboardListener extends KeyAdapter
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			switch (e.getKeyCode())
			{
			
			case KeyEvent.VK_LEFT:
				_robot.setX(_robot.getX() - CHANGE_POSITION_STEP);
				break;
				
			case KeyEvent.VK_RIGHT:
				_robot.setX(_robot.getX() + CHANGE_POSITION_STEP);
				break;
				
			case KeyEvent.VK_UP:
				_robot.setY(_robot.getY() + CHANGE_POSITION_STEP);
				break;
				
			case KeyEvent.VK_DOWN:
				_robot.setY(_robot.getY() - CHANGE_POSITION_STEP);
				break;
				
			case KeyEvent.VK_PAGE_UP:
				_robot.setZ(_robot.getZ() + CHANGE_POSITION_STEP);
				break;
				
			case KeyEvent.VK_PAGE_DOWN:
				_robot.setZ(_robot.getZ() - CHANGE_POSITION_STEP);
				break;
				
			case KeyEvent.VK_C:
				_robot.setToCenter();
				break;
				
			case KeyEvent.VK_S:
				_track.start();
				break;
				
			case KeyEvent.VK_D:
				_track.stop();
				break;
				
			case KeyEvent.VK_H:
				_robot.setServosToHorizontal();
				break;
				
			case KeyEvent.VK_O:
				_robot.setServosToZero();
				break;
				
			default:
				break;
			}
		}
	}
	
	

}
