package webfrontend;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

import webfrontend.restful_newsticker.view.RestFulNewsGui;
import webfrontend.soap_globalweather.view.GlobalWeatherGUI;

public class WebFrontend extends JFrame {
	
	private final static String missingWidget="Widget will be added!";
	public final static String serverURL="http://content.guardianapis.com/";
	public static String sectionURL="world";
	public final static String format="?format=xml";
	public static String apiURL="&order-by=newest&date-id=date%2Flast24hours";
	
	public final static int TICKER_TIME=5*1000; //10 sec
	public final static int UPDATE_RESTFUL_NEWSTICKER=5*60*1000; //5 min
	
	private static WebFrontend gui;

	public static RestFulNewsGui restfulGui;
	public GlobalWeatherGUI globalWeatherGUI;

	public WebFrontend(int width, int height) 
	{
		setSize(width,height);
		setLocation(100,100);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridLayout(0, 1, 5, 5));
		
//		add(taskPlanerGui=new TaskPlanerGui());
		add(new JLabel(missingWidget));
		
		add(restfulGui=new RestFulNewsGui());
		
		add(globalWeatherGUI=new GlobalWeatherGUI());
		
		add(new JLabel(missingWidget));
		
	}

	public static void main(String[] args) 
	{
		gui=new WebFrontend(1024,768);
//		gui.setExtendedState(MAXIMIZED_BOTH);
		gui.setVisible(true);
		
		restfulGui.startThread();
	}

}
