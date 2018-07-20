package gui.deviceInfoPanels;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import models.devices.robots.deltaRobot.DeltaRobot;
import models.devices.robots.deltaRobot.events.DeltaRobotEvent;
import models.devices.robots.deltaRobot.events.IDeltaRobotListener;


public class DeltaRobotInfoPanel extends JPanel implements IDeltaRobotListener
{

	private static final long serialVersionUID = 7286987866896984566L;

	private DeltaRobot _robot;

	private JSpinner _xSpinner;

	private JSpinner _ySpinner;

	private JSpinner _zSpinner;
	

	public DeltaRobotInfoPanel(DeltaRobot robot)
	{
		this._robot = robot;

		JLabel label = new JLabel("Delta Robot Info Panel");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setPreferredSize(new Dimension(300, 15));
		add(label);
		
		JPanel panel = new JPanel();
		add(panel);
		
		JLabel xLabel = new JLabel("X:");
		panel.add(xLabel);
		
		_xSpinner = new JSpinner();
		_xSpinner.setPreferredSize(new Dimension(70, 20));
		_xSpinner.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(0.5)));
		panel.add(_xSpinner);
		
		JLabel yLabel = new JLabel("Y:");
		panel.add(yLabel);
		
		_ySpinner = new JSpinner();
		_ySpinner.setPreferredSize(new Dimension(70, 20));
		_ySpinner.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(0.5)));
		panel.add(_ySpinner);
		
		JLabel zLabel = new JLabel("Z:");
		panel.add(zLabel);
		
		_zSpinner = new JSpinner();
		_zSpinner.setPreferredSize(new Dimension(70, 20));
		_zSpinner.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(0.5)));
		panel.add(_zSpinner);
		
		_robot.addEventListener(this);
		
		onPositionChange(null);
	}
	
	
	@Override
	public void onPositionChange(DeltaRobotEvent event)
	{
		_xSpinner.setValue(_robot.getX());
		_ySpinner.setValue(_robot.getY());
		_zSpinner.setValue(_robot.getZ());
	}
	
}
