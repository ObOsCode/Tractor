package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import models.MainModel;
import models.devices.robots.tractorPlatform.PlatformTrack;
import models.events.MainModelListener;

public class AutopilotPanel extends JPanel
{

	private static final long serialVersionUID = 5164736724122054874L;
	private JButton _recordTrackButton;
	private JList<PlatformTrack> _trackList;
	
	private MainModel _model;
	private JTextField _trackNameTF;
	private JButton _goTrackButton;
	private JButton _stopTrackButton;
	private JButton _removeTrackButton;
	
	
	public AutopilotPanel(MainModel model)
	{
		_model = model;
			
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		JLabel recordTrackLabel = new JLabel("Record track:");
		_recordTrackButton = new JButton("Start");
		_recordTrackButton.setEnabled(false);
		
		_trackNameTF = new JTextField();
		_trackNameTF.setText("Track name");
		_trackNameTF.setMaximumSize(new Dimension(250, 30));
		
		JPanel recordTrackPanel = new JPanel();
		recordTrackPanel.setLayout(new BoxLayout(recordTrackPanel, BoxLayout.Y_AXIS));
		recordTrackPanel.add(recordTrackLabel);
		recordTrackPanel.add(Box.createVerticalStrut(10));
		recordTrackPanel.add(_trackNameTF);
		recordTrackPanel.add(Box.createVerticalStrut(10));
		recordTrackPanel.add(_recordTrackButton);
		
		add(recordTrackPanel);
		
		_trackList = new JList<>();
		add(new JScrollPane(_trackList));
		
		_goTrackButton = new JButton("Go");
		_goTrackButton.setEnabled(false);
		_stopTrackButton = new JButton("Stop");
		_stopTrackButton.setEnabled(false);
		_removeTrackButton = new JButton("Remove");
		_removeTrackButton.setEnabled(false);
		
		JPanel listButtonsPanel = new JPanel();
		listButtonsPanel.setLayout(new GridLayout(3, 1));
		listButtonsPanel.add(_goTrackButton);
		listButtonsPanel.add(_stopTrackButton);
		listButtonsPanel.add(_removeTrackButton);
		add(listButtonsPanel);
		
		_model.addEventListener(new ModelEventListener());
	}


	public JList<PlatformTrack> getTrackList()
	{
		return _trackList;
	}


	public JButton getGoTrackButton()
	{
		return _goTrackButton;
	}


	public JButton getStopTrackButton()
	{
		return _stopTrackButton;
	}


	public JButton getRemoveTrackButton()
	{
		return _removeTrackButton;
	}


	public String getNewTrackName()
	{
		return _trackNameTF.getText();
	}
	
	
	public JButton getRecordTrackButton()
	{
		return _recordTrackButton;
	}
	
	
	private class ModelEventListener extends MainModelListener
	{
		
		@Override
		public void onTrackListChange()
		{
			super.onTrackListChange();
			
			ArrayList<PlatformTrack> list = _model.getTrackList();
			
			DefaultListModel<PlatformTrack> listModel = new DefaultListModel<>();
			
			for (PlatformTrack track : list)
			{
				listModel.addElement(track);
			}
			
			_trackList.setModel(listModel);
		}
	}

}
