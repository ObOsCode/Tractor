package old.gui;

import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.FlowLayout;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JComboBox;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JSpinner;
import javax.swing.JMenuBar;
import java.awt.Choice;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;

public class DeviceListPanel extends JPanel
{

	private static final long serialVersionUID = 2137093251701873056L;

	/**
	 * Create the panel.
	 */
	public DeviceListPanel()
	{
		setPreferredSize(new Dimension(250, 32767));
		setMinimumSize(new Dimension(300, 10));
		setMaximumSize(new Dimension(300, 32767));
		
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Devices list", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JList list = new JList();
		list.setPreferredSize(new Dimension(200, 200));
		list.setMinimumSize(new Dimension(200, 200));
		list.setSize(new Dimension(300, 200));
		add(list);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setPreferredSize(new Dimension(120, 24));
		add(comboBox);
		
		JButton btnNewButton = new JButton("Add");
		btnNewButton.setPreferredSize(new Dimension(65, 25));
		add(btnNewButton);

	}
}
