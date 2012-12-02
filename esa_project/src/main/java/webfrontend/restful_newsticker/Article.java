package webfrontend.restful_newsticker;

import java.util.Date;


public class Article {
	
	private Date published;
	private String url;
	private String title;
	private String imageUrl=null;
	
	public String getPublished() {
		return published.toGMTString();
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}
	
	public Article(String url,String title,Date published) 
	{
		this.title=title;
		this.url=url;
		this.published=published;
	}
	
	@Override
	public String toString() {
		return getPublished()+"\n"+title+"\n"+url;
	}

}
