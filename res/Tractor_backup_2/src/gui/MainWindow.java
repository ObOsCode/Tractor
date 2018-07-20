package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import gui.deviceInfoPanels.InfoMainPanel;
import models.MainModel;

public class MainWindow
{

	private JFrame _frame;
	
	private MainWindowController _mainController;
	private MainModel _model;
	
	private DeviceListPanel _deviceListPanel;
	
	private InfoMainPanel _deviceInfoPanel;


	public MainWindow()
	{
		
		_model = new MainModel();
		
		initialize();
		
		_mainController = new MainWindowController(_model, this);
	}
	
	
	public JFrame getFrame()
	{
		return _frame;
	}
	
	
	public DeviceListPanel getDevicesPanel()
	{
		return _deviceListPanel;
	}


	private void initialize()
	{

		_frame = new JFrame();
		_frame.setBounds(0, 0, 1024, 400);
		_frame.setLocationRelativeTo(null);
//		_frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		_frame.getContentPane().setLayout(new BorderLayout(0, 0));
//		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		_frame.addWindowListener(new WindowAdapter()
		{
          public void windowClosing(WindowEvent e) 
          {
        	  _mainController.dispose();
        	  System.out.println("Close application");
              System.exit(0);
          }
		});
		
		_deviceListPanel = new DeviceListPanel(_model);
		_frame.getContentPane().add(_deviceListPanel, BorderLayout.WEST);

		_deviceInfoPanel = new InfoMainPanel(_model);
		_frame.getContentPane().add(_deviceInfoPanel, BorderLayout.CENTER);
		
		
	}
	
	
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					MainWindow window = new MainWindow();
					window._frame.setVisible(true);
					
					
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

}
