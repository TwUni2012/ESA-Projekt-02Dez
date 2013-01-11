package webfrontend.mdb_rss;

public class RssArticle {
	
	private String url,imageUrl,content,title;
	
	public RssArticle() {
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public RssArticle(String url, String imageUrl, String content, String title) {
		this.url = url;
		this.imageUrl = imageUrl;
		this.content = content;
		this.title = title;
	}
	
	@Override
	public String toString() {
		String s="RSS Article";
		
		s+="\nTitle:\t"+title;
		s+="\nURL:\t"+url;
		s+="\nDesc:\t"+content;
		
		return s;
	}

}
