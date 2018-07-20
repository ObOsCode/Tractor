package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.google.gson.Gson;

import gui.AutopilotPanel;
import gui.MainWindow;
import gui.MapPanel;
import models.MainModel;
import models.devices.robots.tractorPlatform.PlatformTrack;
import models.devices.robots.tractorPlatform.TractorPlatform;
import models.events.MainModelListener;
import ru.roboticsnt.utils.FileSystemUtils;


public class AutopilotController extends AbstractController
{
	
	private final String _TRACK_FILES_FOLDER_PATH = "/home/user/projects/eclipse/Tractor2.0/res/tracks";

	private MainModel _model;
	
	private AutopilotPanel _autopilotPanel;
	private MapPanel _mapPanel;
	
	private JButton _recordTrackButton;
	
	
	public AutopilotController(MainModel model, MainWindow window)
	{
		_model = model;
		
		_autopilotPanel = window.getAutopilotPanel();
		_mapPanel = window.getMapPanel();
		_recordTrackButton = _autopilotPanel.getRecordTrackButton();
		
		final JList<PlatformTrack> trackList = _autopilotPanel.getTrackList();
		
		trackList.addListSelectionListener(e -> {
			if(!e.getValueIsAdjusting())
			{
				if(trackList.getSelectedIndex() == -1)
				{
					return;
				}
				_mapPanel.showTrack(trackList.getSelectedValue());
			}
		});
		
		_model.addEventListener(new ModelEventListener());
		
		loadTrackFiles();
	}
	
	
	@Override
	public void dispose()
	{
		
	}
	
	
	private void loadTrackFiles()
	{
		File folderPath = new File(_TRACK_FILES_FOLDER_PATH);
		
		File[] files = folderPath.listFiles();
		
		for (File file : files)
		{
			Gson gson = new Gson();
			
			String JSONString = FileSystemUtils.getFileContent(file.getAbsolutePath());
			
			PlatformTrack track = gson.fromJson(JSONString, PlatformTrack.class);
			
			_model.addTrack(track);
		}
	}
	
	
	private void saveTrack(PlatformTrack track)
	{
		System.out.println("Save file!!!!!!!!");
		
		Gson gson = new Gson();
		String JSONString = gson.toJson(track);
		String filePath = _TRACK_FILES_FOLDER_PATH + "/" + track.toString();
		FileSystemUtils.saveFile(filePath, JSONString);
	}
	
	
	private class ModelEventListener extends MainModelListener
	{
		
		private RecordTrackButtonListener _recordTrackListener = null;
		
		
		@Override
		public void onPlatformConnect()
		{
			super.onPlatformConnect();
			
			_recordTrackListener = new RecordTrackButtonListener();
			_recordTrackButton.setEnabled(true);
			_recordTrackButton.addActionListener(_recordTrackListener);
		}
		
		
		@Override
		public void onPlatformDisconnect()
		{
			super.onPlatformDisconnect();
			
			_recordTrackButton.removeActionListener(_recordTrackListener);
			_recordTrackButton.setEnabled(false);
			_recordTrackListener = null;
		}
	}
	
	
	private class RecordTrackButtonListener implements ActionListener
	{

		private TractorPlatform _platform = _model.getPlatform();
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			
			if(_platform.isTrackRecorded())
			{
				PlatformTrack platformTrack = _platform.getRecordableTrack();
				_model.addTrack(platformTrack);
				_platform.stopRecordTrack();
				saveTrack(platformTrack);

				_recordTrackButton.setText("Start");
			}else
			{
				_platform.startRecordTrack(_autopilotPanel.getNewTrackName());
				_recordTrackButton.setText("Stop");
			}
		}
		
	}

}
