package webfrontend.mdb_rss.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.util.LinkedHashMap;

import javax.naming.NamingException;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;

import webfrontend.WebFrontend;
import webfrontend.mdb_rss.RssFeed;
import webfrontend.mdb_rss.RssFeedUpdateThread;

public class MdbRssGui extends Container{
	
	private JEditorPane htmlLabel;
	private LinkedHashMap<String, RssFeed> feeds;
	private JComboBox<String> feedList;
	private RssFeedUpdateThread updateThread;
	private RssTickerThread tickerThread;

	public MdbRssGui() {
		setLayout(new BorderLayout(5, 5));
		htmlLabel=new JEditorPane("text/html",WebFrontend.loading);
		htmlLabel.setEditable(false);

		Container northContainer=new Container();
		northContainer.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		northContainer.add(feedList=new JComboBox<String>());
		
		add(northContainer,BorderLayout.NORTH);
		add(htmlLabel,BorderLayout.CENTER);
		add(new JLabel("<html><body><table border=0 width=100%><tr><td align=right><font color=#777777 face=verdana size=2>" +
				"RSS-Reader, kommuniziert mit einem MDB der verschiedene RSS-Feeds aufbereitet. Aufgabe zum 14.01.2013" +
				"</font></td></tr></table></body></html>"),BorderLayout.SOUTH);
		
		try {
			updateThread=new RssFeedUpdateThread();
			tickerThread=new RssTickerThread();
			
			updateThread.start();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

}
