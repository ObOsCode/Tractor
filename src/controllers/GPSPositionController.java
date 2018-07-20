package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import models.MainModel;
import models.devices.robots.tractorPlatform.TractorPlatform;
import ru.roboticsnt.commandProtocol.connections.SocketProtocolConnection;
import ru.roboticsnt.socketServer.SocketServer;
import ru.roboticsnt.socketServer.SocketServerListener;
import ru.roboticsnt.socketServer.SocketServer.SocketClient;

public class GPSPositionController extends AbstractController
{

	private MainModel _model;
	private SocketServer _socketServer;
	
	
	public GPSPositionController(MainModel model)
	{
		_model = model;
		
		startServer();
	}
	
	
	private void startServer()
	{
		
		_socketServer = new SocketServer(SocketProtocolConnection.RTK_SERVER_PORT);
		
		_socketServer.addEventListener(new SocketServerListener(){
			
			@Override
			public void onClientConnect(SocketClient client)
			{
				super.onClientConnect(client);
				
				final Socket socket = client.getSocket();
				
				new Thread(new Runnable()
				{
					
					@Override
					public void run()
					{
						
						System.out.println("New RTK client connect. IP: " + socket.getInetAddress().getHostName());
						
						try
						{
	
							BufferedReader inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				            while (socket.isConnected())
							{
				            	String data = inputStream.readLine();
				            	
				            	if (data == null)
								{
									return;
								}
				            	
				            	data = data.replaceAll("[\\s]{2,}", " ");
				            	
				            	System.out.println("Client data: " + data);
				            	
				            	String[] list = data.split(" ");

				            	try
								{
//					            	String date = list[0];
//					            	String time = list[1];
				            		
					            	double latitude = new Double(list[2]);
					            	double longitude = new Double(list[3]);
					            	
//					            	double height = new Double(list[4]);       	
//					            	int quality = new Integer(list[5]);
//					            	int satellitesCount = new Integer(list[6]);
					            	
					            	TractorPlatform platform = _model.getPlatform();
					            	
					            	if (platform != null)
									{
					            		platform.setGPSPosition(latitude, longitude);
									}

					            	
								} catch (Exception e)
								{
									System.out.println("Error: " + e.getMessage());
								}
				            	
//				            	for (int i = 0; i < list.length; i++)
//								{
//									String item = list[i];
//									
//									System.out.println("Item " + i + " - " + item);
//								}
//				            	
//				            	System.out.println("-------------------------------");			            	
							}
							
						} catch (IOException e)
						{
							System.out.println("Client disconnect");
						}

					}
					
				}).start();
			}
		});
		
		_socketServer.start();
	}

	
	@Override
	public void dispose()
	{
		_socketServer.stop();
	}

}
