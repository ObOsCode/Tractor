package old.gui.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import ru.roboticsUMK.Utils;


public class CameraPanel extends JPanel
{
	
	private static final long serialVersionUID = -4462365071713911390L;
	
	Image _cameraImage;
	
	private JLabel _label;
	
	private int _VIDEO_WIDTH = 320;
	private int _VIDEO_HEIGHT = 240;
	
	private final int _RECT_COUNT = 7;
	
	private final int _RECT_HEIGHT = 80;
	
	private List<Rect> _rectList = new ArrayList<Rect>();
	
	
	public CameraPanel()
	{
		
		setPreferredSize(new Dimension(360, 2000));
		setMaximumSize(new Dimension(32767, 200));
		setMinimumSize(new Dimension(360, 10));
		
		_label = new JLabel();
		add(_label);
		
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Cameras", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        
//        System.out.println(Core.NATIVE_LIBRARY_NAME);
        
//        String cameraStreamURL = "rtsp://admin:Sa8quahni!#@192.168.0.245:554/h264/ch01/main/av_stream?resolution=640x480&req_fps=30&.mjpg";
        String cameraStreamURL = "rtsp://admin:123Qwerty@192.168.0.202:554/h264/ch01/main/av_stream?resolution=320x240&req_fps=25&.mjpg";

        //        VideoCapture video = new VideoCapture(0);  
        VideoCapture video = new VideoCapture(cameraStreamURL);  
        
	    video.set(Videoio.CV_CAP_PROP_FRAME_WIDTH, _VIDEO_WIDTH);
	    video.set(Videoio.CV_CAP_PROP_FRAME_HEIGHT, _VIDEO_HEIGHT);
	    
	    double fps = video.get(Videoio.CAP_PROP_FPS);
//	    System.out.println("Video FPS - " + Double.toString(fps));
	    

	    int rectWidth = _VIDEO_WIDTH/_RECT_COUNT;
	    int rectY = 0;
	    
	    for (int i = 0; i < _RECT_COUNT; i++)
		{
	    	Rect rect = new Rect(rectWidth * i, rectY, rectWidth, _RECT_HEIGHT);
	    	_rectList.add(rect);
		}
	    
		Mat displayFrame = new Mat(_VIDEO_WIDTH, _VIDEO_HEIGHT, CvType.CV_8UC3);
		
        Thread thread = new Thread(new Runnable()
		{
			
			@Override
			public void run()
			{
		        while(true)
		        {
		            if(video.isOpened())
		            {
		            	
		            	try
						{
		            		video.read(displayFrame); 
			            	showFrame(displayFrame);
			            	
						} catch (Exception e)
						{
							System.out.println(e.getMessage());
						}
		            	
		            	

//		            	Mat binaryFrame = showFrame.clone();
//						
//		            	Imgproc.cvtColor(binaryFrame, binaryFrame, Imgproc.COLOR_BGR2HSV);
//		            	
//		            	//Yellow
//		            	Core.inRange(binaryFrame, new Scalar(7, 104, 115), new Scalar(58, 233, 230), binaryFrame);
//						
//		        	    for (int i = 0; i < _RECT_COUNT; i++)
//		        		{
//		        	    	Rect rect = _rectList.get(i);
//		        	    	Mat frame = binaryFrame.submat(rect);
//		        	    	drawRect(showFrame, rect, frame);
//		        		}
		        	    
		        	    

		            }
		        }
			}
		});
        
        thread.start();
	}
	
	
	private void showFrame(Mat frame)
	{
    	MatOfByte buffer = new MatOfByte();
    	
    	Imgcodecs.imencode(".jpeg", frame, buffer);
    	
    	try
		{
			Image img = ImageIO.read(new ByteArrayInputStream(buffer.toArray()));
			_label.setIcon(new ImageIcon(img));

		} catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	
	private void drawRect(Mat frame, Rect rect, Mat frame2)
	{
		Point point1 = new Point(rect.x, rect.y);
		Point point2 = new Point(rect.width + rect.x, rect.height + rect.y);
		
		Imgproc.rectangle(frame, point1, point2, new Scalar(255,0,0), 2);
		
		double sum = Core.sumElems(frame2).val[0];
		
		double maxSum = (_VIDEO_WIDTH/_RECT_COUNT) * _RECT_HEIGHT * 255;
		
		double percents = Utils.round((sum/maxSum) * 100, 2);
		
		Imgproc.putText(frame, Double.toString(percents) + "%", new Point(rect.x + 5, rect.y + 20), Core.FONT_ITALIC, 0.3, new Scalar(255, 255, 255));
	}
	
	
//	@Override
//	protected void paintComponent(Graphics g)
//	{
//		int x = (getWidth() - _VIDEO_WIDTH)/2;
////		int y = (getHeight() - _VIDEO_HEIGHT)/2;
//		int y = 20;
//		
//		g.drawImage(_cameraImage, x, y, null);
//	}

}//class
