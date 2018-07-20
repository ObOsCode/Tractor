package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import models.MainModel;

public class MainWindow
{

	private JFrame _frame;

	private MainModel _model;

	private DeviceListPanel _deviceListPanel;

	private MapPanel _mapPanel;

	private AutopilotPanel _autopilotPanel;
	
	
	public MainWindow(MainModel model)
	{
		_model = model;
		initialize();
	}
	
	
	public JFrame getFrame()
	{
		return _frame;
	}
	
	
	public DeviceListPanel getDeviceListPanel()
	{
		return _deviceListPanel;
	}
	
	
	public MapPanel getMapPanel()
	{
		return _mapPanel;
	}


	public AutopilotPanel getAutopilotPanel()
	{
		return _autopilotPanel;
	}
	
	
	private void initialize()
	{
		_frame = new JFrame();
		_frame.setBounds(100, 100, 640, 480);
		_frame.setVisible(true);
		
		_frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		_deviceListPanel = new DeviceListPanel(_model);
		
		_autopilotPanel = new AutopilotPanel(_model);
		_autopilotPanel.setPreferredSize(new Dimension(2000, 200));
		
		_mapPanel = new MapPanel(_model);
		
		JPanel centerPanel = new JPanel(new BorderLayout());
		centerPanel.add(_autopilotPanel, BorderLayout.NORTH);
		centerPanel.add(_mapPanel, BorderLayout.CENTER);
		
		_frame.getContentPane().add(_deviceListPanel, BorderLayout.WEST);
		_frame.getContentPane().add(centerPanel, BorderLayout.CENTER);
		
		_frame.pack();
	}

}
