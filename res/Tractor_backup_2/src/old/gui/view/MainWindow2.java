package old.gui.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import old.gui.controllers.MainWindowController2;
import old.tractorDevices.tractorPlatform.models.Tractor;
import ru.roboticsUMK.serial.SerialPortManager;

public class MainWindow2
{

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args)
//	{
//		EventQueue.invokeLater(new Runnable()
//		{
//			public void run()
//			{
//				try
//				{
//					Tractor tractor = new Tractor();
//					
//					MainWindow2 window = new MainWindow2(tractor);
//					window._frame.setVisible(true);
//					
//					new MainWindowController2(window, tractor);
//					
//				} catch (Exception e)
//				{
//					e.printStackTrace();
//				}
//			}
//		});
//	}
	
	
	private JFrame _frame;
	
	private TopPanel _topPanel;
	
	private TractorInfoPanel _infoPanel;
	
	private TractorWorldPanel _world;
	
	private CameraPanel _cameraPanel;
	
	private Tractor _tractor;


	public MainWindow2(Tractor tractor)
	{
		_tractor = tractor;
		
		initialize();
	}


	private void initialize()
	{
		_frame = new JFrame();
		_frame.setBounds(100, 100, 1024, 600);
		_frame.setLocationRelativeTo(null);
		_frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		_frame.addWindowListener(new WindowAdapter()
		{
          public void windowClosing(WindowEvent e) 
          {
        	  System.out.println("Close application");
              System.exit(0);
          }
		});
		
		
		_topPanel = new TopPanel(SerialPortManager.getPortNames());
		_frame.getContentPane().add(_topPanel, BorderLayout.NORTH);
		
		_infoPanel = new TractorInfoPanel(_tractor);
		_frame.getContentPane().add(_infoPanel, BorderLayout.WEST);
		
		_cameraPanel = new CameraPanel();
		_frame.getContentPane().add(_cameraPanel, BorderLayout.EAST);
		
//		_world = new TractorWorldPanel(_tractor);
//		_frame.getContentPane().add(_world, BorderLayout.CENTER);
		
		
//		createUpdateTimer();
	}
	
	
	public JFrame getFrame()
	{
		return _frame;
	}


	public TopPanel getTopPanel()
	{
		return _topPanel;
	}
	
	
	public TractorInfoPanel getInfoPanel()
	{
		return _infoPanel;
	}
	
	
	public void update()
	{
		_infoPanel.update();
//		_world.update();
	}
	
	

}
