package old.gui.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import old.gui.controllers.MainWindowController2;
import old.tractorDevices.tractorPlatform.models.Tractor;

public class TractorWorldPanel extends JPanel
{

	private static final long serialVersionUID = -6559461416964315256L;

	private TractorView _tractorView;
	
	private WorldView _worldView;
	
	
	public TractorWorldPanel(Tractor tractor)
	{
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Tractor", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		_tractorView = new TractorView(tractor);
		_worldView = new WorldView(_tractorView);
	}
	
	
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		
		Graphics2D graph = (Graphics2D) g;
		
		_tractorView.draw(graph);
		
		_worldView.draw(graph);
	}
	
	
	public void update()
	{
		repaint();
	}
	
	
					/////////////////////////
					/////////Classes/////////
					/////////////////////////
	
	
	public class WorldView
	{
//		public static final int UPDATE_WORLD_DELAY = 100;//milliseconds
		
//		public final int CENTIMETERS_IN_PIXEL = 20;
//		public final int MILLIMETERS_IN_PIXEL = 200;
		public final double METERS_IN_PIXEL = 0.2;
		
		private final int _CELL_SIZE = 1;//metters
		
		private TractorView _tractorView;
		
		
		public WorldView(TractorView tractorView)
		{
			_tractorView = tractorView;
		}
		
		
		public void draw(Graphics2D graph)
		{
			double xPos = _tractorView.getX();
			double yPos = _tractorView.getY();
			
//			int firstLineY = 
			
			for (int i = 0; i < 20; i++)
			{
				
			}
			
		}
		
	}
	
	
	
	private class TractorView
	{
		
		private Tractor _model;
		
		private final int TRACTOR_START_X = 200;
		private final int TRACTOR_START_Y = 200;
		
		private final int BODY_WIDTH = 60;
		private final int BODY_HEIGHT = 110;
		
		private final int BIG_WHEEL_WIDTH = 20;
		private final int BIG_WHEEL_HEIGHT = 50;
		
		private final int SMALL_WHEEL_WIDTH = 16;
		private final int SMALL_WHEEL_HEIGHT = 30;
		
		private final int MAX_WHEEL_ROTATION = 40;
		
		
//		private final int _ROTATION_STEP = 10;
		
		private double _x = 0;//meters
		private double _y = 0;//meters
		
		private int _rotation = 90;
		
		
		public TractorView(Tractor model)
		{
			_model = model;

		}
		
		
		public double getX()
		{
			return _x;
		}


		public double getY()
		{
			return _y;
		}
		
		
//		private double kmPerHourToPixelPerSecond(int val)
//		{
//			return 1.38 * val;
//		}
		
		private double kmPerHourToMetersPerSecond(double val)
		{
			return 0.28 * val;
		}
		
		
		private void update()
		{
			
			double speedMetPerSec = kmPerHourToMetersPerSecond(_model.getSpeed()) * MainWindowController2.UPDATE_DELAY / 1000;
			
			double rotInRad = Math.toRadians(_rotation);
			
			_y+= speedMetPerSec * Math.sin(rotInRad); 
			_x+= speedMetPerSec * Math.cos(rotInRad);
			
//			System.out.println("Y - " + _y);
//			System.out.println("X - " + _x);
			
		}
		
		
		public void draw(Graphics2D graph)
		{
			update();
			
			int wheelsRotation =  -MAX_WHEEL_ROTATION * _model.getTurn()/100;
			
			AffineTransform curTrans = new AffineTransform();
			
			AffineTransform startTrans = graph.getTransform();
			startTrans.translate(TRACTOR_START_X, TRACTOR_START_Y);
			
			Rectangle body = new Rectangle(-BODY_WIDTH/2, -BODY_HEIGHT/2, BODY_WIDTH, BODY_HEIGHT);
			
			Rectangle bigWheel = new Rectangle(-BIG_WHEEL_WIDTH/2, -BIG_WHEEL_HEIGHT/2, BIG_WHEEL_WIDTH, BIG_WHEEL_HEIGHT);
			
			Rectangle smallWheel = new Rectangle(-SMALL_WHEEL_WIDTH/2, -SMALL_WHEEL_HEIGHT/2, SMALL_WHEEL_WIDTH, SMALL_WHEEL_HEIGHT);
			
			graph.setColor(Color.BLACK);
			
			curTrans.setTransform(startTrans);
			curTrans.translate(-BODY_WIDTH/2, -BODY_HEIGHT/2 + BIG_WHEEL_HEIGHT/2);
			graph.setTransform(curTrans);
			graph.fill(bigWheel);
	
			curTrans.setTransform(startTrans);
			curTrans.translate(BODY_WIDTH/2, -BODY_HEIGHT/2 + BIG_WHEEL_HEIGHT/2);
			graph.setTransform(curTrans);
			graph.fill(bigWheel);
			
			curTrans.setTransform(startTrans);
			curTrans.translate(-BODY_WIDTH/2, BODY_HEIGHT/2 - SMALL_WHEEL_HEIGHT/2);
			curTrans.rotate(Math.toRadians(wheelsRotation));
			graph.setTransform(curTrans);
			graph.fill(smallWheel);
			
			curTrans.setTransform(startTrans);
			curTrans.translate(BODY_WIDTH/2, BODY_HEIGHT/2 - SMALL_WHEEL_HEIGHT/2);
			curTrans.rotate(Math.toRadians(wheelsRotation));
			graph.setTransform(curTrans);
			graph.fill(smallWheel);
			
			graph.setColor(Color.GRAY);
			
			graph.setTransform(startTrans);
			
			graph.fill(body);
		}
	}
}
