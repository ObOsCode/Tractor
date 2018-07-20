package gui.deviceInfoPanels;

import javax.swing.JLabel;
import javax.swing.JPanel;

import models.devices.robots.platform.TractorPlatform;


public class TractorPlatformInfoPanel extends JPanel
{

	private static final long serialVersionUID = 8637170801235847042L;


	public TractorPlatformInfoPanel(TractorPlatform tractor)
	{
		JLabel label = new JLabel("Tractor Platform Info Panel");
		add(label);
	}

}
