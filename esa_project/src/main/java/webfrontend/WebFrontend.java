package webfrontend;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

import webfrontend.restful_newsticker.view.RestFulNewsGui;

public class WebFrontend extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final String MISSING_WIDGET = "Widget will be added!";
	
	public static final String SERVER_URL = "http://content.guardianapis.com/";
	public static final String FORMAT = "?format=xml";
	public static final int TICKER_TIME = 5*1000; // 5 sec
	public static final int UPDATE_RESTFUL_NEWSTICKER = 5*60*1000; //5 min

	private static WebFrontend gui;

	public static String sectionURL = "world";
	public static String apiURL="&order-by=newest&date-id=date%2Flast24hours";
	public static RestFulNewsGui restfulGui;

	public WebFrontend(int width, int height) {
		setSize(width,height);
		setLocation(100,100);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridLayout(0, 1, 5, 5));
		
//		add(taskPlanerGui=new TaskPlanerGui());
		add(new JLabel(MISSING_WIDGET));
		restfulGui = new RestFulNewsGui();
		add(restfulGui);
		add(new JLabel(MISSING_WIDGET));
		add(new JLabel(MISSING_WIDGET));
	}

	public static void main(String[] args) {
		gui = new WebFrontend(800, 600);
		gui.setExtendedState(MAXIMIZED_BOTH);
		gui.setVisible(true);
		
		restfulGui.startThread();
	}
}
