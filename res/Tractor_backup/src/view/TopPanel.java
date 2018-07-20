package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

public class TopPanel extends JPanel
{
	private static final long serialVersionUID = -3989545726582199876L;
	private JLabel _connectedToPortLabel;
	private JComboBox<String> _selectPortComboBox;
	
	private final String _LOGIN_PANEL = "loginPanel";
	private final String _LOGOUT_PANEL = "logoutPanel";
	private final String _CONNECT_PANEL = "connectPanel";
	
	private JButton _connectButton;
	private JPasswordField _passwordTF;

	/**
	 * Create the panel.
	 */
	public TopPanel(String[] portNames)
	{
		setBackground(Color.GRAY);

		if(portNames.length==0)
		{
			JLabel noPortLabel = new JLabel("COM ports not found!");
			add(noPortLabel);
			JButton updateButton = new JButton("Update");
			add(updateButton);
			return;
		}
		
		setLayout(new CardLayout(0, 0));
		
		JPanel loginPanel = new JPanel();
		loginPanel.setBackground(Color.LIGHT_GRAY);
		add(loginPanel, _LOGIN_PANEL);
		
		JLabel selectPortLabel = new JLabel("Port:");
		loginPanel.add(selectPortLabel);
		
		_selectPortComboBox = new JComboBox<String>();
		_selectPortComboBox.setModel(new DefaultComboBoxModel<String>(portNames));
		loginPanel.add(_selectPortComboBox);
		
		JLabel passwordLabel = new JLabel("Password:");
		loginPanel.add(passwordLabel);
		
		_passwordTF = new JPasswordField();
		_passwordTF.setColumns(12);
		loginPanel.add(_passwordTF);
		
		_connectButton = new JButton("Connect");
		loginPanel.add(_connectButton);
		
		JPanel logoutPanel = new JPanel();
		logoutPanel.setBackground(Color.LIGHT_GRAY);
		FlowLayout flowLayout = (FlowLayout) logoutPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		add(logoutPanel, _LOGOUT_PANEL);
		
		_connectedToPortLabel = new JLabel("Connected to ...");
		_connectedToPortLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		logoutPanel.add(_connectedToPortLabel);
		
		JButton disconnectButton = new JButton("Disconnect");
		logoutPanel.add(disconnectButton);
		
		JPanel _connectPanel = new JPanel();
		_connectPanel.setBackground(Color.LIGHT_GRAY);
		add(_connectPanel, _CONNECT_PANEL);
		
		JLabel connectLabel = new JLabel("Connect..");
		_connectPanel.add(connectLabel);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
//		progressBar.setSize(100, 40);
		_connectPanel.add(progressBar);

	}
	
	
	public void showLoginPanel()
	{
		CardLayout cl = (CardLayout)getLayout();
		cl.show(this, _LOGIN_PANEL);
	}
	
	
	public void showLogoutPanel(String portName)
	{
		CardLayout cl = (CardLayout)getLayout();
		cl.show(this, _LOGOUT_PANEL);
		_connectedToPortLabel.setText("Connected to -" + portName + "-");
	}
	
	
	public void showConnectPanel()
	{
		CardLayout cl = (CardLayout)getLayout();
		cl.show(this, _CONNECT_PANEL);
	}
	
	
	public String getSelectedPortName()
	{
		return (String)_selectPortComboBox.getSelectedItem();
	}
	
	
	public JButton getConnectButton()
	{
		return _connectButton;
	}
}
