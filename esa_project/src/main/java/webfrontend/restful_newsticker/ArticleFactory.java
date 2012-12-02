package webfrontend.restful_newsticker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Vector;

public class ArticleFactory {

	public static Vector<Article> analyzeResponse(String response) {
		
		Vector<Article> articles=new Vector<Article>();
		
		response=response.substring(response.indexOf("<")+1, response.lastIndexOf(">"));
		response=response.replaceAll("/>", ">");
		
		String[] lines=response.split("><");
		String title,date,url;
		
		for(String line:lines)
		{
			if(line.contains("content "))
			{
				title=null;
				date=null;
				url=null;
				
				String[] tags=line.split("\" ");
				for(String tag:tags)
				{
					String[] parts=tag.split("=\"");
					if(parts[0].contains("web-publication-date"))
						date=parts[1];
					else if(parts[0].contains("web-title"))
						title=parts[1];
					else if(parts[0].contains("web-url"))
						url=parts[1];
				}
				
				if(title!=null && date!=null && url!=null)
				{
					try {
						Date published=new Date();
						
						String[] parts=date.split("T");
						String[] datepart=parts[0].split("-");
						String[] timepart=parts[1].split(":");
						
						published.setYear(Integer.parseInt(datepart[0])-1900);
						published.setMonth(Integer.parseInt(datepart[1])-1);
						published.setDate(Integer.parseInt(datepart[2]));
						
						published.setHours(Integer.parseInt(timepart[0]));
						published.setMinutes(Integer.parseInt(timepart[1]));
						
						Article a=new Article(url, title, published);
						
						//load the content of the article to extract the preview image url
						HttpURLConnection connection=(HttpURLConnection)(new URL(url)).openConnection();
						connection.setRequestMethod("GET");
						connection.connect();

						// read the response from the restful service
						BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
						String htmlline="";
						boolean found=false;
						while((htmlline=reader.readLine())!=null && !found)
						{
							//look for <meta property="og:image" content="..."/>
							if(htmlline.contains("meta") && htmlline.contains("property=\"og:image\""))
							{
								String imageurl=htmlline.substring(htmlline.indexOf("content=\""), htmlline.length());
								imageurl=imageurl.substring(imageurl.indexOf("\"")+1, imageurl.length());
								imageurl=imageurl.substring(0, imageurl.indexOf("\""));
								a.setImageUrl(imageurl);
								found=true;
							}
						}
						reader.close();
						
						articles.add(a);
					} catch (Exception e) {
						System.err.println(e.getMessage());
					}
				}
			}
		}
		
		return articles;
	}

}
