package webfrontend.restful_newsticker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;

import webfrontend.WebFrontend;

public class NewsUpdateThread extends Thread {

	@Override
	public void run() 
	{
		boolean error=false;
		while(!error)
		{
			try 
			{
				WebFrontend.restfulGui.setButtonState("Loading...", false);
				//connect to the api
				HttpURLConnection connection=(HttpURLConnection)(new URL(WebFrontend.serverURL+WebFrontend.sectionURL+WebFrontend.format+WebFrontend.apiURL)).openConnection();
				connection.setRequestMethod("GET");
				connection.connect();

				// read the response from the restful service
				BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String response=reader.readLine();
				reader.close();

				//generate list of articles from the xml-response
				Vector<Article> articles=ArticleFactory.analyzeResponse(response);
				
				if(articles!=null)
					WebFrontend.restfulGui.setArticles(articles);

				WebFrontend.restfulGui.setButtonState(WebFrontend.restfulGui.buttonText, true);

				sleep(WebFrontend.UPDATE_RESTFUL_NEWSTICKER);
			}
			catch (Exception e) {
				WebFrontend.restfulGui.setButtonState(WebFrontend.restfulGui.buttonText, true);
				error=true;
				if(e instanceof IOException)
				{
					WebFrontend.restfulGui.displayError(e.getMessage());
				}
			}
		}
	}

}
