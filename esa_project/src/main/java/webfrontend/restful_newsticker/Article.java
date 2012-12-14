package webfrontend.restful_newsticker;



public class Article {
	
	private String url;
	private String title;
	private String imageUrl=null;
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	
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
	
	public Article(String url,String title,int year,int month,int day,int hour,int minute) 
	{
		this.title=title;
		this.url=url;
		this.year=year;
		this.month=month;
		this.day=day;
		this.hour=hour;
		this.minute=minute;
	}
	
	public String getPublished() {
		return year+"-"+month+"-"+day+" "+hour+":"+minute+" GMT";
	}

}
