package webfrontend.restful_newsticker.view;

import webfrontend.WebFrontend;

public class TickerThread extends Thread {
	
	@Override
	public void run() 
	{
		while(true)
		{
			WebFrontend.restfulGui.showNextArticle();
			try {
				sleep(WebFrontend.TICKER_TIME);
			} catch (InterruptedException e) {
			}
		}
	}

}
