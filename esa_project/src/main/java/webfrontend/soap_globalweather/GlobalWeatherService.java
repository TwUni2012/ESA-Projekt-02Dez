package webfrontend.soap_globalweather;

import generated.GlobalWeatherSoap;

import java.net.URL;

import javax.swing.JLabel;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class GlobalWeatherService {

	private Weather weather;
	private GlobalWeatherSoap soap;
	private String country;
	private String city;
	private JLabel label;

	public GlobalWeatherService(JLabel label) 
	{
		String url="http://www.webserviceX.NET";

		try {
			final Service service=Service.create(
					new URL("http://www.webservicex.com/globalweather.asmx?wsdl"), 
					new QName(url, "GlobalWeather"));

			soap=service.getPort(GlobalWeatherSoap.class);
			
			this.label=label;

		} catch (Exception e) 
		{
			
		}
	}

	public void updateWeather(String country, String city)
	{
		try
		{
			city=city.substring(0,1).toUpperCase()+city.substring(1,city.length());
			Weather newWeather=new Weather();

			String xml=soap.getWeather(city,country);
			String value=null;
			String[] parts;

			/*
			 * date and time
			 */
			value = getValueFromXMLTag(xml,"Time");
			parts=value.split(" ");

			String[] dateParts=parts[parts.length-3].split("\\.");
			newWeather.setYear(Integer.parseInt(dateParts[0]));
			newWeather.setMonth(Integer.parseInt(dateParts[1]));
			newWeather.setDay(Integer.parseInt(dateParts[2]));

			newWeather.setHour(Integer.parseInt(parts[parts.length-2])/100);
			newWeather.setMinute(Integer.parseInt(parts[parts.length-2])%100);

			/*
			 * temperature
			 */
			value=getValueFromXMLTag(xml, "Temperature");
			newWeather.setTemperature(Integer.parseInt(value.substring(value.indexOf("(")+1, value.indexOf(" C)"))));

			/*
			 * wind direction and speed
			 */
			value=getValueFromXMLTag(xml, "Wind");
			newWeather.setWindDirection(Integer.parseInt(value.substring(value.indexOf("(")+1, value.indexOf(" degrees)"))));

			newWeather.setWindspeed(round(Double.parseDouble(value.substring(value.indexOf("at ")+3, value.indexOf(" MPH")))/0.62,2));

			/*
			 * sky conditions
			 */
			value=getValueFromXMLTag(xml, "SkyConditions");
			newWeather.setCondition(value.trim());

			weather=newWeather;
			this.country=country;
			this.city=city;
			label.setText("");
		}
		catch(Exception e){
			label.setText("Couldn't load Weather for "+city+", "+country);
		}

	}

	private static double round(double value, int digits) 
	{
		return (double)((int)value+(Math.round(Math.pow(10,digits)*(value-(int)value)))/(Math.pow(10,digits)));
	}

	private static String getValueFromXMLTag(String line,String tag) 
	{
		String startTag="<"+tag+">",endTag="</"+tag+">";
		String value=null;

		if(!line.contains(startTag) || !line.contains(endTag))
			return null;

		int start=line.indexOf(startTag)+startTag.length();
		int end=line.indexOf(endTag);

		if(start>=end)
			return null;

		value=line.substring(start, end);

		return value;
	}

	public int getTemperature() throws NullPointerException {
		return weather.getTemperature();
	}

	public int getWindDirection() throws NullPointerException {
		return weather.getWindDirection();
	}

	public double getWindspeed() throws NullPointerException {
		return weather.getWindspeed();
	}

	public String getCondition() throws NullPointerException {
		return weather.getCondition();
	}

	public int getYear() throws NullPointerException {
		return weather.getYear();
	}

	public int getMonth() throws NullPointerException {
		return weather.getMonth();
	}

	public int getDay() throws NullPointerException {
		return weather.getDay();
	}

	public int getHour() throws NullPointerException {
		return weather.getHour();
	}

	public int getMinute() throws NullPointerException {
		return weather.getMinute();
	}

	public String getCity() throws NullPointerException {
		return city;
	}
	
	public String getCountry() throws NullPointerException {
		return country;
	}

	public String getCitiesByCountry(String country) {
		return soap.getCitiesByCountry(country);
	}

}
