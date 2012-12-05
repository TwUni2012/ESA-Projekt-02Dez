package webfrontend.restful_newsticker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;

import webfrontend.WebFrontend;
import webfrontend.restful_newsticker.view.RestFulNewsGui;

public class NewsUpdateThread extends Thread {

	private boolean error;
	
	@Override
	public void run() {
		setErrorState(false);
		
		while(!getErrorState()) {
			try {
				WebFrontend.restfulGui.setButtonState("Loading...", false);
				//connect to the api
				HttpURLConnection connection = (HttpURLConnection)(new URL(WebFrontend.SERVER_URL+WebFrontend.sectionURL+WebFrontend.FORMAT+WebFrontend.apiURL)).openConnection();
				connection.setRequestMethod("GET");
				connection.connect();

				// read the response from the restful service
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String response = reader.readLine();
				reader.close();

				//generate list of articles from the xml-response
				Vector<Article> articles = ArticleFactory.analyzeResponse(response);
				
				if(articles != null)
					WebFrontend.restfulGui.setArticles(articles);
				
//				WebFrontend.restfulGui.setButtonState(WebFrontend.restfulGui.buttonText, true);
				// bissel zu kompliziert oder?!
				setButtonStateToTrue();

				sleep(WebFrontend.UPDATE_RESTFUL_NEWSTICKER);
			}
			catch (IOException e) {
				setButtonStateToTrue();
				setErrorState(true);
					WebFrontend.restfulGui.displayError(e.getMessage());
			} catch (Exception e) {
				setButtonStateToTrue();
				setErrorState(true);
			}
		}
	}
	
	private void setErrorState(boolean state) {
		error = state;
	}
	
	private void setButtonStateToTrue() {
		WebFrontend.restfulGui.setButtonState(RestFulNewsGui.buttonText, true);
	}
	
	private boolean getErrorState() {
		return error;
	}
}
