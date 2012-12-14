package webfrontend.soap_globalweather;

public class Weather {

	private int temperature,winddirection;
	private double windspeed;
	private String condition;
	private int year,month,day,hour,minute;
	
	public int getTemperature() {
		return temperature;
	}
	
	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}
	
	public int getWindDirection() {
		return winddirection;
	}
	
	public void setWindDirection(int winddirection) {
		this.winddirection = winddirection;
	}
	
	public double getWindspeed() {
		return windspeed;
	}
	
	public void setWindspeed(double windspeed) {
		this.windspeed = windspeed;
	}
	
	public String getCondition() {
		return condition;
	}
	
	public void setCondition(String condition) {
		this.condition = condition;
	}
	
	public int getYear() {
		return year;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public int getMonth() {
		return month;
	}
	
	public void setMonth(int month) {
		this.month = month;
	}
	
	public int getDay() {
		return day;
	}
	
	public void setDay(int day) {
		this.day = day;
	}
	
	public int getHour() {
		return hour;
	}
	
	public void setHour(int hour) {
		this.hour = hour;
	}
	
	public int getMinute() {
		return minute;
	}
	
	public void setMinute(int minute) {
		this.minute = minute;
	}


}
