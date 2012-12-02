package webfrontend.restful_newsticker.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import webfrontend.WebFrontend;
import webfrontend.restful_newsticker.Article;
import webfrontend.restful_newsticker.NewsUpdateThread;


public class RestFulNewsGui extends Container {

	private NewsUpdateThread updateThread;
	private Vector<Article> articles=null;
	private TickerThread tickerThread;

	public final static String loading="<html><body bgcolor=#ffffff><center><font face=verdana>Loading...</font></center></body></html>";
	public final static String buttonText="Load";

	private int currentArticle;
	private JEditorPane htmlLabel;
	private JTextField requestField;
	private ArticleDialog articleDialog;
	private JTextField sectionField;
	private JButton loadButton;

	public RestFulNewsGui() 
	{
		setLayout(new BorderLayout(5, 5));

		updateThread=new NewsUpdateThread();
		tickerThread=new TickerThread();

		articleDialog=new ArticleDialog();

		htmlLabel=new JEditorPane("text/html",loading);
		htmlLabel.setEditable(false);
		htmlLabel.addHyperlinkListener(new HyperlinkListener() {

			private int selectedArticle;

			@Override
			public void hyperlinkUpdate(HyperlinkEvent e)
			{
				if(e.getEventType()==HyperlinkEvent.EventType.ACTIVATED)
				{
					selectedArticle=currentArticle;
					
					if(!articleDialog.isVisible())
						articleDialog.setVisible(true);

					articleDialog.setHtml(loading,"Loading...");

					HttpURLConnection connection;
					try 
					{
						connection = (HttpURLConnection)(e.getURL()).openConnection();
						connection.setRequestMethod("GET");
						connection.connect();
						
						// read the content of the website till the text is found
						BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
						String htmlline="",html="";
						boolean inText=false;
						while((htmlline=reader.readLine())!=null)
						{
							//find the beginning of the article
							//search for <div id="article-body-blocks">
							if(htmlline.contains("div") && htmlline.contains("id=\"article-body-blocks\""))
								inText=true;
							
							if(inText)
							{
								if(htmlline.contains("/div"))
									inText=false;
								html+=htmlline;
							}
						}
						reader.close();

						try {
							synchronized (articles) {
								articleDialog.setHtml(html,articles.get(selectedArticle).getTitle());
							}
						} catch (Exception e1) {
						}
					} catch (IOException e1) {
						articleDialog.setHtml("<html><body bgcolor=#ffffff><center><font color=#ff0000 face=verdana><b>" +
								e1.getMessage()+
								"</b></font></center></body></html>",e1.getMessage());
					}
				}
			}
		});
		
		sectionField=new JTextField(WebFrontend.sectionURL);
		sectionField.setFont(Font.decode("courier-13"));
		sectionField.setColumns(12);

		requestField=new JTextField(WebFrontend.apiURL);
		requestField.setFont(Font.decode("courier-13"));
		requestField.setColumns(60);
		
		loadButton=new JButton(buttonText);
		loadButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				updateThread.stop();
				updateThread=new NewsUpdateThread();
				WebFrontend.sectionURL=sectionField.getText();
				WebFrontend.apiURL=requestField.getText();
				updateThread.start();
			}
		});

		Container northContainer=new Container();
		northContainer.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		northContainer.add(new JLabel("<html><body bgcolor=#ffffff><font size=3 face=courier>&nbsp;"+WebFrontend.serverURL+"</body></html>"));
		northContainer.add(sectionField);
		northContainer.add(new JLabel("<html><body bgcolor=#ffffff><font size=3 face=courier>"+WebFrontend.format+"</body></html>"));
		northContainer.add(requestField);
		northContainer.add(loadButton);
		
		add(northContainer,BorderLayout.NORTH);
		add(htmlLabel,BorderLayout.CENTER);
		add(new JLabel("<html><body><table border=0 width=100%><tr><td align=right><font color=#777777 face=verdana size=2>" +
				"News Ticker, nutzt den RESTful Web Service der Zeitung <i>The Guardian</i> (www.guardian.co.uk). Aufgabe zum 3.12.2012" +
				"</font></td></tr></table></body></html>"),BorderLayout.SOUTH);

		currentArticle=0;

	}
	
	public void setButtonState(String text,boolean enabled)
	{
		loadButton.setText(text);
		loadButton.setEnabled(enabled);
	}

	public synchronized void setArticles(Vector<Article> articles) 
	{
		//articles have been loaded for the first time
		boolean startTicker=(this.articles==null);

		this.articles = articles;
		if(currentArticle>=this.articles.size())
			currentArticle=-1;

		if(startTicker)
			tickerThread.start();
	}

	public void startThread() 
	{
		updateThread.start();
	}

	public synchronized void showNextArticle() 
	{
		try {
			Article article;
			synchronized (articles) 
			{
				currentArticle++;
				if(currentArticle>=this.articles.size())
					currentArticle=0;
				article=articles.get(currentArticle);
			}

			if(article!=null)
			{
				String html="<html><body bgcolor=#ffffff><table border=0 width=100% height=100%>" +
						"<tr><td rowspan=3 align=center valign=top height=150 width=250><img src=\"" +
						article.getImageUrl()+"\"></td>" +
						"<td height=20 valign=top align=left><b><font size=3 face=verdana color=#000000>" +
						article.getTitle()+
						"<td align=right valign=top><font size=2 face=verdana color=#666666>" +
						article.getPublished()+
						"</b></td></tr><tr><td colspan=2 align=left valign=top><font size=2 face=verdana><a href=\"" +
						article.getUrl()+
						"\">"+article.getUrl()+"</a></td></tr></table></body><html>";
				htmlLabel.setText(html);
			}
		} catch (Exception e) {
		}

	}
	
	public void displayError(String message)
	{
		htmlLabel.setText("<html><body bgcolor=#ffffff><center><font color=#ff0000 face=verdana><b>" +
				message+
				"</b></font></center></body></html>");
		
	}

}
