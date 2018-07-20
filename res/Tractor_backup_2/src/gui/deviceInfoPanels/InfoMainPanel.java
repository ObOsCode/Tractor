package gui.deviceInfoPanels;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import models.MainModel;
import models.devices.robots.RobotDevice;
import models.devices.robots.RobotType;
import models.devices.robots.deltaRobot.DeltaRobot;
import models.devices.robots.platform.TractorPlatform;
import models.events.MainModelListener;

public class InfoMainPanel extends JPanel
{

	private static final long serialVersionUID = -6770481245306516024L;
	
//	private final String DEFAULT_PANEL = "defaultPanel";
//	private final String DELTA_ROBOT_PANEL = "deltaRobotPanel";
//	private final String TRACTOR_PLATFORM_PANEL = "tractorPlatformPanel";

	private MainModel _model;

	private JPanel _panelsContainer;

	private JPanel _defaultPanel;
	
	
	public InfoMainPanel(MainModel model)
	{
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Device info", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		this._model = model;
		
		_panelsContainer = new JPanel();
		add(_panelsContainer);
		
		_defaultPanel = new JPanel();
		JLabel lblSelectDevice = new JLabel("Select a device");
		_defaultPanel.add(lblSelectDevice);
		_panelsContainer.add(_defaultPanel);
		
		_model.addEventListener(new ModelEventListener());
	}
	
	
	///////////
	//Private
	///////////
	
	
	private class ModelEventListener extends MainModelListener
	{
		@Override
		public void onSelectedRobotChange()
		{
			super.onSelectedRobotChange();

			_panelsContainer.removeAll();
			
			RobotDevice device = _model.getSelectedRobot();
			
			if(device != null)
			{
				if(device.getType().equals(RobotType.DELTA))
				{
					_panelsContainer.add(new DeltaRobotInfoPanel((DeltaRobot) device));
				}
				
				if(device.getType().equals(RobotType.PLATFORM))
				{
					_panelsContainer.add(new TractorPlatformInfoPanel((TractorPlatform) device));
				}
			}else
			{
				_panelsContainer.add(_defaultPanel);
			}
			
			_panelsContainer.revalidate();
		}
		
		
		@Override
		public void onRobotsListChange()
		{
			super.onRobotsListChange();
		}
	}

}
