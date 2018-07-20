package view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import tractorPlatform.tractor.models.Tractor;

import javax.swing.JToggleButton;

public class TractorInfoPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9078712407064291682L;
	
	private Tractor _tractor;
	
	private JTextField _speedTF;
	private JTextField _turnTextField;

	public ButtonGroup selectControllDeviceButtonGroup;
	
	public JToggleButton startEngineButton;
	
	public static final String SELECT_ANDROID_DEVICE_COMMAND = "selectAndroidDevice";
	public static final String SELECT_KEYBOARD_COMMAND = "selectKeyboard";
	
	
	
	public TractorInfoPanel(Tractor tractor)
	{
//		setPreferredSize(new Dimension(180, 32767));
		
		_tractor = tractor;
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Tractor info", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 183, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblSpeed = new JLabel("Speed:");
		GridBagConstraints gbc_lblSpeed = new GridBagConstraints();
		gbc_lblSpeed.insets = new Insets(0, 0, 5, 5);
		gbc_lblSpeed.anchor = GridBagConstraints.EAST;
		gbc_lblSpeed.gridx = 0;
		gbc_lblSpeed.gridy = 0;
		add(lblSpeed, gbc_lblSpeed);
		
		_speedTF = new JTextField();
		_speedTF.setEditable(false);
		_speedTF.setText("0 km/h");
		GridBagConstraints gbc__speedTF = new GridBagConstraints();
		gbc__speedTF.anchor = GridBagConstraints.WEST;
		gbc__speedTF.insets = new Insets(0, 0, 5, 0);
		gbc__speedTF.gridx = 1;
		gbc__speedTF.gridy = 0;
		add(_speedTF, gbc__speedTF);
		_speedTF.setColumns(10);
		
		JLabel lblTurn = new JLabel("Turn:");
		GridBagConstraints gbc_lblTurn = new GridBagConstraints();
		gbc_lblTurn.anchor = GridBagConstraints.EAST;
		gbc_lblTurn.insets = new Insets(0, 0, 5, 5);
		gbc_lblTurn.gridx = 0;
		gbc_lblTurn.gridy = 1;
		add(lblTurn, gbc_lblTurn);
		
		_turnTextField = new JTextField();
		_turnTextField.setEditable(false);
		_turnTextField.setText("0");
		GridBagConstraints gbc__turnTextField = new GridBagConstraints();
		gbc__turnTextField.insets = new Insets(0, 0, 5, 0);
		gbc__turnTextField.anchor = GridBagConstraints.WEST;
		gbc__turnTextField.gridx = 1;
		gbc__turnTextField.gridy = 1;
		add(_turnTextField, gbc__turnTextField);
		_turnTextField.setColumns(10);	
		
		JLabel lblControlWith = new JLabel("Control with:");
		GridBagConstraints gbc_lblControlWith = new GridBagConstraints();
		gbc_lblControlWith.anchor = GridBagConstraints.EAST;
		gbc_lblControlWith.insets = new Insets(0, 0, 5, 5);
		gbc_lblControlWith.gridx = 0;
		gbc_lblControlWith.gridy = 3;
		add(lblControlWith, gbc_lblControlWith);
		
		JRadioButton androidDeviceButton = new JRadioButton("Android device");
		GridBagConstraints gbc__androidDeviceButton = new GridBagConstraints();
		gbc__androidDeviceButton.anchor = GridBagConstraints.WEST;
		gbc__androidDeviceButton.insets = new Insets(0, 0, 5, 0);
		gbc__androidDeviceButton.gridx = 1;
		gbc__androidDeviceButton.gridy = 3;
		androidDeviceButton.setActionCommand(SELECT_ANDROID_DEVICE_COMMAND);
		add(androidDeviceButton, gbc__androidDeviceButton);

		JRadioButton keyboardButton = new JRadioButton("Keyboard");
		GridBagConstraints gbc__keyboard = new GridBagConstraints();
		gbc__keyboard.insets = new Insets(0, 0, 5, 0);
		gbc__keyboard.anchor = GridBagConstraints.WEST;
		gbc__keyboard.gridx = 1;
		gbc__keyboard.gridy = 4;
		keyboardButton.setActionCommand(SELECT_KEYBOARD_COMMAND);
		add(keyboardButton, gbc__keyboard);
		
		
		selectControllDeviceButtonGroup = new ButtonGroup();
		
		selectControllDeviceButtonGroup.add(androidDeviceButton);
		selectControllDeviceButtonGroup.add(keyboardButton);
		
		
		androidDeviceButton.setFocusable(false);
		keyboardButton.setFocusable(false);
		androidDeviceButton.setSelected(true);
		
		JLabel lblEngine = new JLabel("Engine:");
		GridBagConstraints gbc_lblEngine = new GridBagConstraints();
		gbc_lblEngine.anchor = GridBagConstraints.EAST;
		gbc_lblEngine.insets = new Insets(0, 0, 0, 5);
		gbc_lblEngine.gridx = 0;
		gbc_lblEngine.gridy = 6;
		add(lblEngine, gbc_lblEngine);
		
		startEngineButton = new JToggleButton("START");
		GridBagConstraints gbc__startEngineButton = new GridBagConstraints();
		gbc__startEngineButton.anchor = GridBagConstraints.WEST;
		gbc__startEngineButton.gridx = 1;
		gbc__startEngineButton.gridy = 6;
		add(startEngineButton, gbc__startEngineButton);
		
		startEngineButton.setFocusable(false);

	}
	
	
	public void update()
	{
		
		_speedTF.setText(Math.round(_tractor.getSpeed()/0.28) + " km/h");
//		_speedTF.setText(_tractor.getSpeed() + " m/sec");
		
		
		
		String direction = "";
		
		int turn = _tractor.getTurn();
		
		if(turn>0)
		{
			direction = "right";
		}
		if(turn<0)
		{
			direction = "left";
		}
		
		_turnTextField.setText(Math.abs(turn) + " " + direction);
	}
	
	
	

}
