package gui;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

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

import models.MainModel;
import models.devices.DeviceBase;
import models.devices.DeviceType;
import models.events.MainModelListener;
import ru.roboticsnt.commandProtocol.connections.ProtocolConnectionBase;

public class DeviceListPanel extends JPanel
{
	
	public static final String PRELOADER_PANEL = "preloaderPanel";
	public static final String ADD_DEVICE_PANEL = "addDevicePanel";

	private static final long serialVersionUID = 2137093251701873056L;
	
	private JComboBox<ProtocolConnectionBase> _selectConnectionComBox;
	private JComboBox<DeviceType> _selectDeviceTypeComBox;
	private JButton _addDeviceBut;
	private JButton _removeDeviceBut;
	private JList<DeviceBase> _devicesList;
	
	private JPanel _addDeviceCardPanel;
	private JPanel preloaderPanel;
	private JTextField txtConnectToDevice;
	private JProgressBar progressBar;
	private JTextArea _messagesTF;
	private JLabel robotsListLabel;
	private JPanel _controlDevicesListPanel;
	private JLabel controlDevicesListLabel;

	private MainModel _model;
	private JComboBox<DeviceBase> _selectControlDeviceComBox;
	
	
	public DeviceListPanel(MainModel model)
	{
		_model = model;
		
		setPreferredSize(new Dimension(300, 32767));
//		setBorder(new TitledBorder(new LineBorder(new Color(64, 64, 64)), "Devices", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		
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
		lblNewLabel.setPreferredSize(new Dimension(52, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		addDeviceFormPanel.add(lblNewLabel, gbc_lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Type:");
		lblNewLabel_1.setPreferredSize(new Dimension(39, 20));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 0;
		addDeviceFormPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		_selectConnectionComBox = new JComboBox<ProtocolConnectionBase>();
		_selectConnectionComBox.setPreferredSize(new Dimension(120, 24));
		_selectConnectionComBox.setMinimumSize(new Dimension(100, 24));
		GridBagConstraints gbc_selectPortComBox = new GridBagConstraints();
		gbc_selectPortComBox.insets = new Insets(0, 0, 0, 5);
		gbc_selectPortComBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_selectPortComBox.gridx = 0;
		gbc_selectPortComBox.gridy = 1;
		addDeviceFormPanel.add(_selectConnectionComBox, gbc_selectPortComBox);
		
		_selectDeviceTypeComBox = new JComboBox<DeviceType>();
		_selectDeviceTypeComBox.setPreferredSize(new Dimension(80, 24));
		GridBagConstraints gbc_selectTypeComBox = new GridBagConstraints();
		gbc_selectTypeComBox.insets = new Insets(0, 0, 0, 5);
		gbc_selectTypeComBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_selectTypeComBox.gridx = 1;
		gbc_selectTypeComBox.gridy = 1;
		addDeviceFormPanel.add(_selectDeviceTypeComBox, gbc_selectTypeComBox);
		
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
		
		robotsListLabel = new JLabel("Devices list:");
		robotsListLabel.setPreferredSize(new Dimension(85, 30));
		robotsListLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		robotsListPanel.add(robotsListLabel);
		
		_devicesList = new JList<DeviceBase>();
		_devicesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		_devicesList.setPreferredSize(new Dimension(260, 200));
		_devicesList.setLayoutOrientation(JList.VERTICAL);
		robotsListPanel.add(new JScrollPane(_devicesList));
		
		_removeDeviceBut = new JButton("Remove device");
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
	
		controlDevicesListLabel = new JLabel("Control from:");
		controlDevicesListLabel.setPreferredSize(new Dimension(275, 30));
		controlDevicesListLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		_controlDevicesListPanel.add(controlDevicesListLabel);
		
		_selectControlDeviceComBox = new JComboBox<DeviceBase>();
		_selectControlDeviceComBox.setPreferredSize(new Dimension(200, 24));
		_controlDevicesListPanel.add(_selectControlDeviceComBox);
		
		
		ModelEventListener modelListener = new ModelEventListener();
		_model.addEventListener(modelListener);
		modelListener.onConnectionsListChange();
		modelListener.onDevicesListChange();
		
		setDeviceTypesList();
	}
	
	
	public JComboBox<DeviceBase> getControlDevicesComBox()
	{
		return _selectControlDeviceComBox;
	}
	
	
	public JButton getAddDeviceButton()
	{
		return _addDeviceBut;
	}
	
	
	public JButton getRemoveDeviceButton()
	{
		return _removeDeviceBut;
	}
	
	
	public int getSelectedTypeIndex()
	{
		return _selectDeviceTypeComBox.getSelectedIndex();
	}
	
	
	public ProtocolConnectionBase getSelectedConnection()
	{
		return (ProtocolConnectionBase) _selectConnectionComBox.getSelectedItem();
	}
	
	
	public DeviceBase getSelectedDevice()
	{
		return _devicesList.getSelectedValue();
	}
	
	
	public JList<DeviceBase> getDevicesList()
	{
		return _devicesList;
	}
	
	
	
	
	//////////
	//Private 
	//////////
	
	private void setDeviceTypesList()
	{
		_selectDeviceTypeComBox.removeAllItems();
		
		ArrayList<DeviceType> deviceList = DeviceType.getList();

		for (int i = 0; i < deviceList.size(); i++)
		{
			DeviceType type = deviceList.get(i);
			_selectDeviceTypeComBox.addItem(type);
		}

	}
	
	
	private class ModelEventListener extends MainModelListener
	{
		
		@Override
		public void onConnectionsListChange()
		{
			super.onConnectionsListChange();
			
			_selectConnectionComBox.removeAllItems();
			
			ArrayList<ProtocolConnectionBase> connectionsList = _model.getConnectionsList();
			
			if(connectionsList.isEmpty())
			{
				_addDeviceBut.setEnabled(false);
				_selectConnectionComBox.setEnabled(false);
				return;
			}
			
			_addDeviceBut.setEnabled(true);
			_selectConnectionComBox.setEnabled(true);
			
			for (ProtocolConnectionBase connection : connectionsList)
			{
				_selectConnectionComBox.addItem(connection);
			}
		}
		
		
		@Override
		public void onDevicesListChange()
		{
			super.onDevicesListChange();
			
			ArrayList<DeviceBase> list = _model.getDevicesList();
			
			_selectControlDeviceComBox.removeAllItems();
			
			if(list.size() == 0)
			{
				_removeDeviceBut.setEnabled(false);
			}
			
			DefaultListModel<DeviceBase> listModel = new DefaultListModel<DeviceBase>();
			
			for (DeviceBase device : list)
			{
				listModel.addElement(device);
				
				DeviceType deviceType = device.getType();

				if(deviceType.equals(DeviceType.CONTROL_ANDROID_SMARTPHONE) 
						|| deviceType.equals(DeviceType.CONTROL_ARDUINO))
				{
					_selectControlDeviceComBox.addItem(device);
				}
			}
			
			_selectControlDeviceComBox.setEnabled(_selectControlDeviceComBox.getItemCount() > 0);
			
			_devicesList.setModel(listModel);
		}
		
		
		@Override
		public void onSelectedDeviceChange()
		{
			super.onSelectedDeviceChange();
			
			DeviceBase device = _model.getSelectedDevice();
			
			_removeDeviceBut.setEnabled(device != null);
		}
		
	}
}
