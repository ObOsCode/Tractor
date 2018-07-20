package old.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import java.awt.FlowLayout;

public class MainWindow
{

	private JFrame _frame;


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
					
					new MainWindowController(window);
					
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		_frame = new JFrame();
//		_frame.setBounds(100, 100, 450, 300);
		_frame.setLocationRelativeTo(null);
		_frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		_frame.getContentPane().setLayout(new BorderLayout(0, 0));
//		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		_frame.addWindowListener(new WindowAdapter()
		{
          public void windowClosing(WindowEvent e) 
          {
        	  System.out.println("Close application");
              System.exit(0);
          }
		});
		
		DeviceListPanel deviceListPanel = new DeviceListPanel();
		_frame.getContentPane().add(deviceListPanel, BorderLayout.WEST);
		deviceListPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
	}

}
