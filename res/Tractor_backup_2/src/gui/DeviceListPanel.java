package gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import models.MainModel;
import models.devices.controlDevices.ControlDevice;
import models.devices.robots.RobotDevice;
import models.devices.robots.RobotType;
import models.events.MainModelListener;

public class DeviceListPanel extends JPanel
{
	public static final String PRELOADER_PANEL = "preloaderPanel";
	public static final String ADD_DEVICE_PANEL = "addDevicePanel";

	private static final long serialVersionUID = 2137093251701873056L;
	private JComboBox<String> _selectPortComBox;
	private JComboBox<String> _selectTypeComBox;
	private JButton _addDeviceBut;
	private JButton _removeDeviceBut;
	private JList<String> _robotsList;
	
	private MainModel _model;
	
	private Timer _updateTimer;
	private JPanel _addDeviceCardPanel;
	private JPanel preloaderPanel;
	private JTextField txtConnectToDevice;
	private JProgressBar progressBar;
	private JTextArea _messagesTF;
	private JLabel robotsListLabel;
	private JPanel _controlDevicesListPanel;
	private JLabel controlDevicesListLabel;
	private JList<String> _controlDevicesJList;

	
	/**
	 * Create the panel.
	 */
	public DeviceListPanel(MainModel model)
	{
		_model = model;
		
		setPreferredSize(new Dimension(300, 32767));
		setMinimumSize(new Dimension(300, 10));
		setMaximumSize(new Dimension(300, 32767));
		
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Devices", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		_addDeviceCardPanel = new JPanel();
		add(_addDeviceCardPanel);
		_addDeviceCardPanel.setLayout(new CardLayout(0, 0));
		
		JPanel addDeviceFormPanel = new JPanel();
		_addDeviceCardPanel.add(addDeviceFormPanel, ADD_DEVICE_PANEL);
		GridBagLayout gbl_addDevicePanel = new GridBagLayout();
		gbl_addDevicePanel.columnWidths = new int[] {0, 0, 0};
		gbl_addDevicePanel.rowHeights = new int[] {0, 0};
		gbl_addDevicePanel.columnWeights = new double[]{1.0, 1.0, 0.0};
		gbl_addDevicePanel.rowWeights = new double[]{0.0, 0.0};
		addDeviceFormPanel.setLayout(gbl_addDevicePanel);
		
		JLabel lblNewLabel = new JLabel("Port/IP:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		addDeviceFormPanel.add(lblNewLabel, gbc_lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Type:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 0;
		addDeviceFormPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		_selectPortComBox = new JComboBox<String>();
		_selectPortComBox.setPreferredSize(new Dimension(120, 24));
		_selectPortComBox.setMinimumSize(new Dimension(100, 24));
		GridBagConstraints gbc_selectPortComBox = new GridBagConstraints();
		gbc_selectPortComBox.insets = new Insets(0, 0, 0, 5);
		gbc_selectPortComBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_selectPortComBox.gridx = 0;
		gbc_selectPortComBox.gridy = 1;
		addDeviceFormPanel.add(_selectPortComBox, gbc_selectPortComBox);
		
		_selectTypeComBox = new JComboBox<String>();
		_selectTypeComBox.setPreferredSize(new Dimension(80, 24));
		GridBagConstraints gbc_selectTypeComBox = new GridBagConstraints();
		gbc_selectTypeComBox.insets = new Insets(0, 0, 0, 5);
		gbc_selectTypeComBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_selectTypeComBox.gridx = 1;
		gbc_selectTypeComBox.gridy = 1;
		addDeviceFormPanel.add(_selectTypeComBox, gbc_selectTypeComBox);
		
		_addDeviceBut = new JButton("Add");
		GridBagConstraints gbc_addDeviceBut = new GridBagConstraints();
		gbc_addDeviceBut.gridx = 2;
		gbc_addDeviceBut.gridy = 1;
		addDeviceFormPanel.add(_addDeviceBut, gbc_addDeviceBut);
		
		preloaderPanel = new JPanel();
		_addDeviceCardPanel.add(preloaderPanel, PRELOADER_PANEL);
		preloaderPanel.setLayout(new BoxLayout(preloaderPanel, BoxLayout.Y_AXIS));
		
		txtConnectToDevice = new JTextField();
		txtConnectToDevice.setBorder(null);
		txtConnectToDevice.setHorizontalAlignment(SwingConstants.CENTER);
		txtConnectToDevice.setText("Connect to device...");
		txtConnectToDevice.setEditable(false);
		preloaderPanel.add(txtConnectToDevice);
		txtConnectToDevice.setColumns(10);
		
		progressBar = new JProgressBar();
		progressBar.setMaximumSize(new Dimension(80, 14));
		progressBar.setPreferredSize(new Dimension(80, 14));
		progressBar.setToolTipText("");
		progressBar.setIndeterminate(true);
		progressBar.setSize(100, 40);
		preloaderPanel.add(progressBar);
		
		JPanel robotsListPanel = new JPanel();
		add(robotsListPanel);
		robotsListPanel.setLayout(new BoxLayout(robotsListPanel, BoxLayout.Y_AXIS));
		
		robotsListLabel = new JLabel("Robots list:");
		robotsListLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		robotsListPanel.add(robotsListLabel);
		
		_robotsList = new JList<String>();
		_robotsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		_robotsList.setPreferredSize(new Dimension(260, 200));
		_robotsList.setLayoutOrientation(JList.VERTICAL);
		robotsListPanel.add(new JScrollPane(_robotsList));
		
		_removeDeviceBut = new JButton("Disconnect device");
		_removeDeviceBut.setEnabled(false);
		_removeDeviceBut.setAlignmentX(Component.CENTER_ALIGNMENT);
		robotsListPanel.add(_removeDeviceBut);
		
		_messagesTF = new JTextArea();
		_messagesTF.setBorder(null);
		_messagesTF.setMargin(new Insets(10, 0, 10, 0));
		_messagesTF.setOpaque(false);
		_messagesTF.setPreferredSize(new Dimension(0, 0));
		_messagesTF.setEditable(false);

		JScrollPane scroll = new JScrollPane(_messagesTF);
		scroll.setPreferredSize(new Dimension(3, 80));
		scroll.setBorder(null);
	    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		robotsListPanel.add(scroll);
		
		_messagesTF.setLineWrap(true);
		_messagesTF.setWrapStyleWord(true);
		
		_controlDevicesListPanel = new JPanel();
		add(_controlDevicesListPanel);
		_controlDevicesListPanel.setLayout(new BoxLayout(_controlDevicesListPanel, BoxLayout.Y_AXIS));
	
		
		controlDevicesListLabel = new JLabel("Control devices list:");
		controlDevicesListLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		_controlDevicesListPanel.add(controlDevicesListLabel);
		
		
		_controlDevicesJList = new JList<String>();
		_controlDevicesJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		_controlDevicesJList.setPreferredSize(new Dimension(260, 200));
		_controlDevicesJList.setLayoutOrientation(JList.VERTICAL);
		_controlDevicesListPanel.add(new JScrollPane(_controlDevicesJList));
		
		
		_model.addEventListener(new ModelEventListener());
		
		startUpdateTimer();
		setDeviceTypeList();
	}
	
	
	public void showPreloaderPanel()
	{
		CardLayout cl = (CardLayout) _addDeviceCardPanel.getLayout();
		cl.show(_addDeviceCardPanel, PRELOADER_PANEL);
	}
	
	
	public void showAddDeviceFormPanel()
	{
		CardLayout cl = (CardLayout) _addDeviceCardPanel.getLayout();
		cl.show(_addDeviceCardPanel, ADD_DEVICE_PANEL);
	}
	
	
	public String getSelectedPort()
	{
		return (String) _selectPortComBox.getSelectedItem();
	}
	
	
	public int getSelectedTypeIndex()
	{
		return _selectTypeComBox.getSelectedIndex();
	}
	
	
	public int getSelectedRobotIndex()
	{
		return _robotsList.getSelectedIndex();
	}
	
	
	public void setSelectedRobotIndex(int index)
	{
		_robotsList.setSelectedIndex(index);
	}
	
	
	public JButton getAddRobotButton()
	{
		return _addDeviceBut;
	}
	
	
	public JButton getRemoveRobotButton()
	{
		return _removeDeviceBut;
	}
	
	
	public JList<String> getRobotsList()
	{
		return _robotsList;	
	}
	
	
	public JList<String> getControlDevicesList()
	{
		return _controlDevicesJList;	
	}
	
	
	public int getSelectedControlDeviceIndex()
	{
		return _controlDevicesJList.getSelectedIndex();
	}
	

	public void showMessage(String messageText)
	{
		_messagesTF.append("ERROR. ");
		_messagesTF.append(messageText);
		_messagesTF.append("\n");
//		_messagesTF.revalidate();
	}
	
	
	public void dispose()
	{
		stopUpdateTimer();
	}
	
	
	//////////////
	//Private
	/////////////
	
	
	private void setDeviceTypeList()
	{
		_selectTypeComBox.removeAllItems();
		
		ArrayList<RobotType> deviceList = RobotType.getList();

		for (int i = 0; i < deviceList.size(); i++)
		{
			RobotType type = deviceList.get(i);
			_selectTypeComBox.addItem(type.getName());
		}
	}
	

	private void updatePorts()
	{
		if(_selectPortComBox.isPopupVisible())
		{
			return;
		}
		
		int selectedPortIndex = _selectPortComBox.getSelectedIndex();
		
		_selectPortComBox.removeAllItems();
		
		List<String> freePortsList = _model.getFreePortsList();
		
		if(freePortsList.isEmpty())
		{
			_addDeviceBut.setEnabled(false);
			_selectPortComBox.setEnabled(false);
			return;
		}
		
		_addDeviceBut.setEnabled(true);
		_selectPortComBox.setEnabled(true);
		
		for (String port : freePortsList)
		{
			_selectPortComBox.addItem(port);
		}
		
		if(selectedPortIndex >= 0 && selectedPortIndex < freePortsList.size())
		{
			_selectPortComBox.setSelectedIndex(selectedPortIndex);
		}

	}
	
	
	private void startUpdateTimer()
	{
		_updateTimer = new Timer();
		
		TimerTask updateTask = new TimerTask()
		{
			
			@Override
			public void run()
			{
				updatePorts();
			}
		};
		
		_updateTimer.schedule(updateTask, 0, 1000);
	}
	
	
	private void stopUpdateTimer()
	{
		_updateTimer.cancel();
		_updateTimer = null;
	}
	
	
	private class ModelEventListener extends MainModelListener
	{
		@Override
		public void onRobotsListChange()
		{
			super.onRobotsListChange();
			
			ArrayList<RobotDevice> list = _model.getRobotsList();
			
			if(list.size() == 0)
			{
				_removeDeviceBut.setEnabled(false);
			}
			
			DefaultListModel<String> listModel = new DefaultListModel<String>();
			
			for (RobotDevice device : list)
			{
				listModel.addElement(device.getName());
			}
			
			_robotsList.setModel(listModel);
			
			updatePorts();
		}
		
		
		@Override
		public void onSelectedRobotChange()
		{
			super.onSelectedRobotChange();
			
			RobotDevice device = _model.getSelectedRobot();
			
			_removeDeviceBut.setEnabled(device != null);
		}
		
		
		@Override
		public void onControlDevicesListChange()
		{
			super.onControlDevicesListChange();
			
			System.out.println("onControlDevicesListChange");
				
			DefaultListModel<String> listModel = new DefaultListModel<String>();
			
			ArrayList<ControlDevice> devicesList = _model.getControlDevicesList();
			
			for (ControlDevice device : devicesList)
			{
				listModel.addElement(device.getName());
			}
			
			_controlDevicesJList.setModel(listModel);
		}
	}
}
