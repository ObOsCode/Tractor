package main;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import controllers.AutopilotController;
import controllers.ControlDeviceController;
import controllers.DevicesListController;
import controllers.GPSPositionController;
import gui.MainWindow;
import models.MainModel;


public class Tractor2Main
{

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				
				MainModel model = new MainModel();
				
				MainWindow window = new MainWindow(model);
						
				final DevicesListController deviceListController = new DevicesListController(model, window);
				final ControlDeviceController controlDeviceController = new ControlDeviceController(model);
				final GPSPositionController gpsPositionController = new GPSPositionController(model);
				final AutopilotController autopilotController = new AutopilotController(model, window);
				
				
				window.getFrame().addWindowListener(new WindowAdapter()
				{
		          public void windowClosing(WindowEvent e) 
		          {
		        	  deviceListController.dispose();
		        	  controlDeviceController.dispose();
		        	  gpsPositionController.dispose();
		        	  autopilotController.dispose();
		        	  
		        	  System.out.println("Close application");
		              System.exit(0);
		          }
				});
				
			}
		});

	}

}
