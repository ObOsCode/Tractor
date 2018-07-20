package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.DefaultMapController;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;
import org.openstreetmap.gui.jmapviewer.tilesources.OfflineOsmTileSource;

import models.MainModel;
import models.devices.robots.tractorPlatform.GPSPoint;
import models.devices.robots.tractorPlatform.PlatformTrack;
import models.devices.robots.tractorPlatform.TractorPlatform;
import models.devices.robots.tractorPlatform.events.TractorPlatformEvent;
import models.devices.robots.tractorPlatform.events.TractorPlatformListener;
import models.events.MainModelListener;

public class MapPanel extends JPanel
{

	private static final long serialVersionUID = 5482745157149103674L;

	private final String _TILES_FOLDER_PATH = "/home/user/programs/jTileDownloader/tiles/";
//	private final String _TILES_FOLDER_PATH = "/home/user/programs/jTileDownloader/tiles2(Uzlovaya)/";
//	private final String _TILES_FOLDER_PATH = "/home/user/programs/jTileDownloader/tiles3/";
	
	private final String _TRACTOR_ICO_PATH = "file:///home/user/projects/eclipse/Tractor2.0/res/images/platform_marker.png";
	
	private final double _START_LATITUDE = 53.9358935;
	private final double _START_LONGITUDE = 38.1528319;
	private final int _START_ZOOM = 11;
	private final int _END_ZOOM = 22;

	private MainModel _model;
	
	private TractorPlatform _platform;

	private JMapViewer _map;
	private MapMarker _platformMarker;
	
	
	public MapPanel(MainModel model)
	{
		_model = model;
		
		createMap();
		
		_model.addEventListener(new PlatformConnectlListener());
		
	}
	
	
	public void showTrack(PlatformTrack track)
	{
		_map.removeAllMapPolygons();

		ArrayList<GPSPoint> pointsList = track.getPointList();
		
		if(pointsList.size() <= 0)
		{
			return;
		}
		
		ArrayList<Coordinate> route = new ArrayList<Coordinate>();
		
		for (GPSPoint point : pointsList)
		{
			route.add(new Coordinate(point.getLatitude(), point.getLongitude()));
		}
		
		GPSPoint lastPoint = pointsList.get(pointsList.size() - 1);
		route.add(new Coordinate(lastPoint.getLatitude(), lastPoint.getLongitude()));
		
		_map.addMapPolygon(new MapPolygonImpl(route));
		
		_map.setDisplayToFitMapPolygons();
		
//		MapMarker marker = new MapMarkerDot(_platform.getGPSPosition().getLatitude(), _platform.getGPSPosition().getLongitude());
//		marker.setLayer(_trackLayer);
//		_map.addMapMarker(marker);
	}
	
	
	public void clearMap()
	{
		_map.removeAll();
	}
	
	
	private void createMap()
	{
		File tilesFolder = new File(_TILES_FOLDER_PATH);
		
		String tilesURL = "";
		
		try
		{
			tilesURL = tilesFolder.toURI().toURL().toString();
		} catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		
		_map = new JMapViewer();
		
		_map.setPreferredSize(new Dimension(1200, 800));
//		_map.setMinimumSize(new Dimension(200, 200));
//		_map.setLayout(new FlowLayout());
//		_map.revalidate();
//		_map.setSize(new Dimension(getWidth(), getHeight()));
		
		_map.setTileSource(new OfflineOsmTileSource(tilesURL, _START_ZOOM, _END_ZOOM));
//		map.setTileSource(new OsmTileSource.Mapnik());
		_map.setZoomContolsVisible(false);
		_map.setScrollWrapEnabled(true);
		_map.setDisplayPosition(new Coordinate(_START_LATITUDE, _START_LONGITUDE), _START_ZOOM);
        add(_map);

        new DefaultMapController(_map);
		
		_map.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				super.mouseClicked(e);
				
				Point clickPoint = e.getPoint();
				
				System.out.println("Click position - " + _map.getPosition(clickPoint).toString());
			}
		});
	}
	
	
	private class PlatformConnectlListener extends MainModelListener
	{
		
		private PlatformPositionChangeListener _platformPositionListener;


		public PlatformConnectlListener()
		{
			_platformPositionListener = new PlatformPositionChangeListener();
		}
		
		
		@Override
		public void onPlatformConnect()
		{
			super.onPlatformConnect();
			
			_platform = _model.getPlatform();
			_platform.addEventListener(_platformPositionListener);
			
			_platformMarker = new TractorMarker(0, 0);
			_map.addMapMarker(_platformMarker);
		}
		
		
		@Override
		public void onPlatformDisconnect()
		{
			super.onPlatformDisconnect();
			
			_platform.removeEventListener(_platformPositionListener);
			
			_map.removeMapMarker(_platformMarker);
			_platformMarker = null;
		}
	}
	
	
	private class PlatformPositionChangeListener extends TractorPlatformListener
	{
		
		@Override
		public void onGPSPositionChange(TractorPlatformEvent event)
		{
			super.onGPSPositionChange(event);
			
			double platformLat = _platform.getGPSPosition().getLatitude();
			double platformLon = _platform.getGPSPosition().getLongitude();
			_platformMarker.setLat(platformLat);
			_platformMarker.setLon(platformLon);
			
			_map.updateUI();
		}
	}
	
	
	private class TractorMarker extends MapMarkerDot
	{
		
		private final int _WIDTH = 10;
		private final int _LENGTH = 10;
		
		private Image _tractorIcon;
		
		
		public TractorMarker(double lat, double lon)
		{
			super(new Coordinate(lat, lon));
			
			try
			{
				_tractorIcon = ImageIO.read(new URL(_TRACTOR_ICO_PATH));
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		
		@Override
		public void paint(Graphics g, Point position, int radius)
		{
			g.drawImage(_tractorIcon, position.x, position.y - _tractorIcon.getHeight(null), null);
			
			Graphics2D graph = (Graphics2D) g;
			
			graph.setColor(Color.RED);
//			graph.fillRect(position.x -_WIDTH/2, position.y -_LENGTH/2, _WIDTH, _LENGTH);
			graph.fillOval(position.x, position.y, _WIDTH, _LENGTH);
		}
		

	}
	
	
}
