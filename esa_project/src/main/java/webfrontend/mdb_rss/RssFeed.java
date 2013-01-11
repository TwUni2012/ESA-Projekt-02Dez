package webfrontend.mdb_rss;

import java.util.Vector;

public class RssFeed {
	
	private Vector<RssArticle> articles;
	
	public RssFeed() {
		articles=new Vector<RssArticle>();
	}
	
	public Vector<RssArticle> getArticles() {
		return articles;
	}
	
	public void addArticles(RssArticle article){
		articles.add(article);
	}
	
	public void removeAllArticles(){
		articles.removeAllElements();
	}
	
	@Override
	public String toString() {
		String s="New RSS Feed";
		
		for(RssArticle a:articles)
		{
			s+=a+"\n";
		}
		
		return s+"\n";
	}

}
