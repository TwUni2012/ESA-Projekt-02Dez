/* Simple example of a message driven bean */
package services.mdb;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Vector;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import webfrontend.mdb_rss.RssArticle;
import webfrontend.mdb_rss.RssFeed;
import webfrontend.restful_newsticker.ArticleFactory;


@MessageDriven(mappedName = "RSSQueue" , activationConfig = {
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "RSSQueue")
})
public class RSSFeedListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		if(!(message instanceof TextMessage)){
			throw new IllegalArgumentException(
					"Message is not of class " + TextMessage.class.getCanonicalName() + ": " + message
					);
		}
		final TextMessage msg = (TextMessage) message;
		try {
			final String messageText = msg.getText();
			/*
			 * try to load the xml-content of the rss feed 
			 */
			URL feedUrl=new URL(messageText);
			BufferedReader reader=new BufferedReader(new InputStreamReader(feedUrl.openConnection().getInputStream()));
			String line=null;
			Vector<String> xmlContent=new Vector<String>();
			while((line=reader.readLine())!=null)
			{
				line=line.trim();
				if(line.length()>0)
					xmlContent.add(line);
			}
			/*
			 * analyze the xml content and create feed-objects
			 */
			RssFeed newFeed=new RssFeed();
			for(RssArticle article:analyzeXML(xmlContent))
			{
				newFeed.addArticles(article);
			}
			
			System.out.println(newFeed);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private final String TAG_ITEM="item";
	private final String TAG_DESCRIPTION="description";
	private final String TAG_LINK="link";
	private final String TAG_TITLE="title";

	private Vector<RssArticle> analyzeXML(Vector<String> xmlContent) {
		boolean inItem=false;
		Vector<RssArticle> articles=new Vector<RssArticle>();
		RssArticle newArticle=null;

		for(String xmlLine:xmlContent)
		{
			if(!inItem)
			{
				if(xmlLine.contains(getStartTag(TAG_ITEM)))
				{
					/*
					 * new article
					 */
					inItem=true;
					newArticle=new RssArticle();
				}
			}
			else
			{
				if(xmlLine.contains(getEndTag(TAG_ITEM)))
				{
					inItem=false;
					articles.add(newArticle);
					newArticle=null;
				}
				else{
					
					if(xmlLine.contains(getStartTag(TAG_LINK))){
						/*
						 * extract the link to the complete article
						 */
						if(xmlLine.contains(getEndTag(TAG_LINK)))
						{
							try {
								newArticle.setUrl(getStringFromTag(TAG_LINK,xmlLine));
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
					else if(xmlLine.contains(getStartTag(TAG_DESCRIPTION))){
						/*
						 * extract the description of the article
						 */
						if(xmlLine.contains(getEndTag(TAG_DESCRIPTION)))
						{
							try {
								newArticle.setContent(getStringFromTag(TAG_DESCRIPTION,xmlLine));
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
					else if(xmlLine.contains(getStartTag(TAG_TITLE))){
						/*
						 * extract the title of the article
						 */
						if(xmlLine.contains(getEndTag(TAG_TITLE)))
						{
							try {
								newArticle.setTitle(getStringFromTag(TAG_TITLE,xmlLine));
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}					
				}
			}
		}

		return articles;
	}

	private String getStringFromTag(String tag,String line) throws Exception{
		String start=getStartTag(tag);
		String end=getEndTag(tag);
		String result=line.substring(line.indexOf(start)+start.length(), line.indexOf(end));
		result=result.replaceAll("\\<\\!\\[CDATA\\[", "");
		result=result.replaceAll("\\]\\]\\>", "");
		result=ArticleFactory.cleanSpecialTokens(result);
		result=ArticleFactory.cleanFromTags(result);
		
		return result;
	}

	private String getStartTag(String tag){
		return "<"+tag+">";
	}

	private String getEndTag(String tag){
		return "</"+tag+">";
	}
}
